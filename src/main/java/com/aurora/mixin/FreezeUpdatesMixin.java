package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.FreezeUpdatesFeature;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class FreezeUpdatesMixin {
    
    @Inject(method = "updateNeighborsAlways", at = @At("HEAD"), cancellable = true)
    private void onUpdateNeighborsAlways(BlockPos pos, Block block, CallbackInfo ci) {
        FreezeUpdatesFeature freezeUpdates = AuroraMod.getInstance()
            .getFeatureManager().getFeature(FreezeUpdatesFeature.class);
        
        if (freezeUpdates != null && freezeUpdates.isEnabled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = "updateNeighborsExcept", at = @At("HEAD"), cancellable = true)
    private void onUpdateNeighborsExcept(BlockPos pos, Block block, net.minecraft.util.math.Direction skipSide, WireOrientation wireOrientation, CallbackInfo ci) {
        FreezeUpdatesFeature freezeUpdates = AuroraMod.getInstance()
            .getFeatureManager().getFeature(FreezeUpdatesFeature.class);
        
        if (freezeUpdates != null && freezeUpdates.isEnabled()) {
            ci.cancel();
        }
    }
}
