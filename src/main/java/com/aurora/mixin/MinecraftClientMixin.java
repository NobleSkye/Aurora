package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.FastPlaceFeature;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    private int itemUseCooldown;

        @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        FastPlaceFeature fastPlace = AuroraMod.getInstance().getFeatureManager().getFeature(FastPlaceFeature.class);
        if (fastPlace != null && fastPlace.isEnabled() && itemUseCooldown > 0) {
            itemUseCooldown = 0;
        }
    }
}
