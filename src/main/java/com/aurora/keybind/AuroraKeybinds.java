package com.aurora.keybind;

import com.aurora.AuroraMod;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class AuroraKeybinds {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private boolean leftAltPressed = false;
    private boolean wasLeftAltPressed = false;
    
    public AuroraKeybinds() {
        // No keybinds to register - we handle Left Alt directly
    }
    
    public void handleInput() {
        // Check if Left Alt is currently pressed
        boolean currentLeftAltState = isLeftAltPressed();
        
        // Update GUI based on Left Alt state
        if (currentLeftAltState != wasLeftAltPressed) {
            AuroraMod.getInstance().getGui().setToolbarVisible(currentLeftAltState);
            wasLeftAltPressed = currentLeftAltState;
        }
        
        leftAltPressed = currentLeftAltState;
    }
    
    private boolean isLeftAltPressed() {
        if (client.getWindow() == null) return false;
        long windowHandle = client.getWindow().getHandle();
        return GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS;
    }
    
    public boolean isLeftAltHeld() {
        return leftAltPressed;
    }
}
