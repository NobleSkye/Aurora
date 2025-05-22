package com.aurora.mixin;

import com.aurora.client.AuroraClient;
import com.aurora.client.feature.Feature;
import com.aurora.client.feature.NoClipFeature;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Inject(method = "shouldSlowDown", at = @At("HEAD"), cancellable = true)
    private void onShouldSlowDown(CallbackInfoReturnable<Boolean> cir) {
        // Prevent slowdown in fluids when NoClip is enabled
        if (AuroraClient.getInstance().getFeatures().stream()
                .filter(f -> f instanceof NoClipFeature)
                .anyMatch(Feature::isEnabled)) {
            cir.setReturnValue(false);
        }
    }
}
