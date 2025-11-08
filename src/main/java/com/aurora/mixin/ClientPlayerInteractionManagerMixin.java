package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.ExtendedReachFeature;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    
    @Inject(method = "getReachDistance", at = @At("RETURN"), cancellable = true)
    private void onGetReachDistance(CallbackInfoReturnable<Float> cir) {
        ExtendedReachFeature feature = AuroraMod.getInstance()
            .getFeatureManager()
            .getFeature(ExtendedReachFeature.class);
        
        if (feature != null && feature.isEnabled()) {
            cir.setReturnValue((float) feature.getReachDistance());
        }
    }
}
