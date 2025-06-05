package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.FastPlaceFeature;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class FastPlaceMixin {
    @Shadow
    private int itemUseCooldown;
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        FastPlaceFeature fastPlace = (FastPlaceFeature) AuroraMod.getInstance()
            .getFeatureManager().getFeature("fastplace");
        
        if (fastPlace != null && fastPlace.isEnabled() && itemUseCooldown > 0) {
            itemUseCooldown = 0;
        }
    }
}

@Mixin(ClientPlayerInteractionManager.class)
class FastPlaceInteractionMixin {
    @Shadow
    private int blockBreakingCooldown;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onInteractionTick(CallbackInfo ci) {
        FastPlaceFeature fastPlace = (FastPlaceFeature) AuroraMod.getInstance()
            .getFeatureManager().getFeature("fastplace");
        
        if (fastPlace != null && fastPlace.isEnabled() && blockBreakingCooldown > 0) {
            blockBreakingCooldown = 0;
        }
    }
}
