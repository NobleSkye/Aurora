package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.ReplaceFeature;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ReplaceMixin {
    
    @Shadow @Final private MinecraftClient client;
    
    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void onInteractBlock(net.minecraft.client.network.ClientPlayerEntity player, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        ReplaceFeature replace = AuroraMod.getInstance().getFeatureManager().getFeature(ReplaceFeature.class);
        if (replace == null || !replace.isEnabled()) return;
        
        ItemStack stack = player.getStackInHand(hand);
        if (!(stack.getItem() instanceof BlockItem blockItem)) return;
        
        BlockPos pos = hitResult.getBlockPos();
        BlockState currentState = client.world.getBlockState(pos);
        
        // Don't replace air or the same block
        if (currentState.isAir() || currentState.getBlock() == blockItem.getBlock()) return;
        
        // Send break packet to server
        if (client.getNetworkHandler() != null) {
            client.getNetworkHandler().sendPacket(
                new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, hitResult.getSide())
            );
            client.getNetworkHandler().sendPacket(
                new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, hitResult.getSide())
            );
        }
        
        // Create placement context for the new block
        ItemPlacementContext context = new ItemPlacementContext(player, hand, stack, hitResult);
        BlockState newState = blockItem.getBlock().getPlacementState(context);
        
        if (newState != null) {
            // Place the new block using the interaction manager
            ActionResult result = ((ClientPlayerInteractionManager) (Object) this).interactBlock(
                player, hand, hitResult
            );
            if (result.isAccepted()) {
                player.swingHand(hand);
            }
            cir.setReturnValue(result);
        }
    }
}
