package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.TinkerFeature;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.state.property.Property;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ClientPlayerEntity.class)
public class TinkerMixin {
    
    @Inject(method = "swingHand", at = @At("HEAD"))
    private void onSwingHand(Hand hand, CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        TinkerFeature tinker = AuroraMod.getInstance().getFeatureManager().getFeature(TinkerFeature.class);
        
        if (tinker == null || !tinker.isEnabled()) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;
        
        HitResult hitResult = client.crosshairTarget;
        if (hitResult == null || hitResult.getType() != HitResult.Type.BLOCK) return;
        
        ItemStack stack = player.getStackInHand(hand);
        if (!stack.isEmpty()) return; // Only work with empty hand
        
        BlockPos pos = ((BlockHitResult) hitResult).getBlockPos();
        BlockState currentState = client.world.getBlockState(pos);
        
        // Get all possible properties for this block
        Collection<Property<?>> properties = currentState.getProperties();
        if (properties.isEmpty()) return;
        
        // Cycle through the first property's values
        Property<?> firstProperty = properties.iterator().next();
        cycleProperty(currentState, pos, firstProperty, client);
    }
    
    private static <T extends Comparable<T>> void cycleProperty(BlockState state, BlockPos pos, Property<T> property, MinecraftClient client) {
        T currentValue = state.get(property);
        Collection<T> allowedValues = property.getValues();
        
        // Find the next allowed value
        T nextValue = null;
        boolean foundCurrent = false;
        
        for (T value : allowedValues) {
            if (foundCurrent) {
                nextValue = value;
                break;
            }
            if (value.equals(currentValue)) {
                foundCurrent = true;
            }
        }
        
        // If we didn't find a next value, use the first one
        if (nextValue == null) {
            nextValue = allowedValues.iterator().next();
        }
        
        // Set the new block state
        BlockState newState = state.with(property, nextValue);
        
        // Send update packet to server
        if (client.getNetworkHandler() != null) {
            client.getNetworkHandler().sendPacket(
                new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, null)
            );
            client.getNetworkHandler().sendPacket(
                new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, null)
            );
        }
        
        // Update the block state
        client.world.setBlockState(pos, newState);
    }
}
