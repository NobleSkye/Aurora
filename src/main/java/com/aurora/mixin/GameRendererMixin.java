package com.aurora.mixin;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "renderWorld", at = @At("HEAD"))
    private void onRenderWorld(net.minecraft.client.render.RenderTickCounter unused, CallbackInfo ci) {
        // Add any render-related feature processing here
    }
}
