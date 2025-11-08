package com.aurora.keybind;

import com.aurora.AuroraMod;
import com.aurora.features.ReplaceFeature;
import com.aurora.features.TinkerFeature;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class AuroraKeybinds {
    private KeyBinding replaceKey;
    private KeyBinding tinkerKey;
    
    public AuroraKeybinds() {
        registerKeybinds();
    }
    
    private void registerKeybinds() {
        replaceKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.aurora.replace",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.aurora"
        ));
        
        tinkerKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.aurora.tinker",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_L,
            "category.aurora"
        ));
    }
    
    public void handleInput() {
        if (replaceKey.wasPressed()) {
            ReplaceFeature replace = AuroraMod.getInstance()
                .getFeatureManager().getFeature(ReplaceFeature.class);
            if (replace != null) {
                replace.toggle();
            }
        }
        
        if (tinkerKey.wasPressed()) {
            TinkerFeature tinker = AuroraMod.getInstance()
                .getFeatureManager().getFeature(TinkerFeature.class);
            if (tinker != null) {
                tinker.toggle();
            }
        }

        // Detect number keys 1-9 while Left Alt is held for toolbar toggles.
        long window = AuroraMod.getInstance().getGui() == null ? 0 : MinecraftClient.getInstance().getWindow().getHandle();
        if (window != 0 && isLeftAltDown()) {
            for (int i = 0; i < 9; i++) {
                int key = GLFW.GLFW_KEY_1 + i;
                if (GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS) {
                    // Delegate to GUI for feature index toggle (1-based)
                    AuroraMod.getInstance().getGui().handleNumberKey(i + 1);
                }
            }
        }
    }

    private boolean isLeftAltDown() {
        try {
            return GLFW.glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS;
        } catch (Exception e) {
            return false;
        }
    }
}
