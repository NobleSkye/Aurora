package com.aurora.mixin;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    private static boolean freezeUpdates = false;

    @Inject(method = "updateListeners", at = @At("HEAD"), cancellable = true)
    private void onUpdateListeners(CallbackInfo ci) {
        if (freezeUpdates) {
            ci.cancel();
        }
    }

    public static void setFreezeUpdates(boolean freeze) {
        freezeUpdates = freeze;
    }
}
