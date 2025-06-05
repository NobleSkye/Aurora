package com.aurora.keybind;

import com.aurora.AuroraMod;
import com.aurora.features.ReplaceFeature;
import com.aurora.features.TinkerFeature;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class AuroraKeybinds {
    private KeyBinding menuKey;
    private KeyBinding replaceKey;
    private KeyBinding tinkerKey;
    
    public AuroraKeybinds() {
        registerKeybinds();
    }
    
    private void registerKeybinds() {
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.aurora.menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            "category.aurora"
        ));
        
        replaceKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.aurora.replace",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.aurora"
        ));
        
        tinkerKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.aurora.tinker",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_T,
            "category.aurora"
        ));
    }
    
    public void handleInput() {
        if (menuKey.wasPressed()) {
            AuroraMod.getInstance().getGui().toggleMenu();
        }
        
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
