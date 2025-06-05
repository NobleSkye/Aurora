package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.FastBreakFeature;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class FastBreakMixin {
    @Shadow
    private int blockBreakingCooldown;

    @Inject(method = "updateBlockBreakingProgress", at = @At("HEAD"))
    private void onUpdateBlockBreakingProgress(CallbackInfoReturnable<Boolean> cir) {
        FastBreakFeature fastBreak = (FastBreakFeature) AuroraMod.getInstance()
            .getFeatureManager().getFeature("fastbreak");
        
        if (fastBreak != null && fastBreak.isEnabled()) {
            blockBreakingCooldown = 0;
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        FastBreakFeature fastBreak = (FastBreakFeature) AuroraMod.getInstance()
            .getFeatureManager().getFeature("fastbreak");
        
        if (fastBreak != null && fastBreak.isEnabled() && blockBreakingCooldown > 0) {
            blockBreakingCooldown = 0;
        }
    }

    @Inject(method = "getBlockBreakingProgress", at = @At("RETURN"), cancellable = true)
    private void onGetBlockBreakingProgress(CallbackInfoReturnable<Float> cir) {
        FastBreakFeature fastBreak = (FastBreakFeature) AuroraMod.getInstance()
            .getFeatureManager().getFeature("fastbreak");
        
        if (fastBreak != null && fastBreak.isEnabled()) {
            // Set break progress to complete instantly
            cir.setReturnValue(1.0f);
        }
    }
}
