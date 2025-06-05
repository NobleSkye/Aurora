package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.FastPlaceFeature;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class FastPlaceClientMixin {
    @Shadow
    public GameOptions options;
    
    @Shadow
    private int itemUseCooldown;
    
    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    private void onHandleInputEvents(CallbackInfo ci) {
        FastPlaceFeature fastPlace = (FastPlaceFeature) AuroraMod.getInstance()
            .getFeatureManager().getFeature("fastplace");
        
        if (fastPlace != null && fastPlace.isEnabled()) {
            // Reset item use cooldown to allow rapid placement
            if (itemUseCooldown > 0) {
                itemUseCooldown = 0;
            }
        }
    }
}
