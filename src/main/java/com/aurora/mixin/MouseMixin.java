package com.aurora.mixin;

import com.aurora.AuroraMod;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        // Only handle left mouse button press
        if (button == 0 && action == 1) { // GLFW_MOUSE_BUTTON_LEFT and GLFW_PRESS
            AuroraMod mod = AuroraMod.getInstance();
            if (mod != null && mod.getToolbar().isVisible()) {
                Mouse mouse = (Mouse) (Object) this;
                double mouseX = mouse.getX();
                double mouseY = mouse.getY();
                
                if (mod.getToolbar().handleClick(mouseX, mouseY, button)) {
                    ci.cancel(); // Cancel the click if toolbar handled it
                }
            }
        }
    }
}