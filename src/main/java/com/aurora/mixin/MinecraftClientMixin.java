package com.aurora.mixin;

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

    private static boolean fastPlaceEnabled = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (fastPlaceEnabled && itemUseCooldown > 0) {
            itemUseCooldown = 0;
        }
    }

    public static void setFastPlaceEnabled(boolean enabled) {
        fastPlaceEnabled = enabled;
    }
}
