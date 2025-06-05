package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.NoClipFeature;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    
    @Inject(method = "shouldSlowDown", at = @At("HEAD"), cancellable = true)
    private void onShouldSlowDown(CallbackInfoReturnable<Boolean> cir) {
        // Prevent slowdown in fluids when NoClip is enabled
        NoClipFeature noClip = AuroraMod.getInstance().getFeatureManager().getFeature(NoClipFeature.class);
        if (noClip != null && noClip.isEnabled()) {
            cir.setReturnValue(false);
        }
    }
    
    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    private void onPushOutOfBlocks(double x, double z, CallbackInfo ci) {
        // Prevent pushing out of blocks when NoClip is enabled
        NoClipFeature noClip = AuroraMod.getInstance().getFeatureManager().getFeature(NoClipFeature.class);
        if (noClip != null && noClip.isEnabled()) {
            ci.cancel();
        }
    }
}
