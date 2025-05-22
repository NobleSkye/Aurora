package com.aurora.mixin;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
        @Inject(method = "updateListeners", at = @At("HEAD"), cancellable = true)
    private void onUpdateListeners(CallbackInfo ci) {
        if (com.aurora.client.util.FreezeUpdatesUtil.areFreezeUpdatesEnabled()) {
            ci.cancel();
        }
    }
}
