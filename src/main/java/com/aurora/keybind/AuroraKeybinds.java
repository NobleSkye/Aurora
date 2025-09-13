package com.aurora.keybind;

import com.aurora.AuroraMod;
import com.aurora.features.ReplaceFeature;
import com.aurora.features.TinkerFeature;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class AuroraKeybinds {
    // Keep some legacy keybinds for backwards compatibility
    private KeyBinding replaceKey;
    private KeyBinding tinkerKey;
    
    public AuroraKeybinds() {
        registerKeybinds();
    }
    
    private void registerKeybinds() {
        // Legacy keybinds for direct feature access (optional)
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
        // Handle legacy keybinds
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
    }
}
