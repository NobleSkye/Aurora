package com.aurora.client;

import com.aurora.client.feature.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class AuroraClient implements ClientModInitializer {
    private static AuroraClient INSTANCE;
    private final List<Feature> features = new ArrayList<>();
    private KeyBinding menuKey;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        
        // Register keybindings
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.aurora.menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_ALT,
            "category.aurora.general"
        ));

        // Initialize features
        registerFeatures();

        // Setup tick listener for keybindings
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (menuKey.wasPressed()) {
                // TODO: Show radial menu
            }
        });
    }

    private void registerFeatures() {
        // Register all mod features
        features.add(new NoClipFeature());
        features.add(new FastPlaceFeature());
        features.add(new TinkerToolFeature());
        features.add(new FreezeUpdatesFeature());
        features.add(new ForcePlaceFeature());
        features.add(new ReplaceFeature());
        features.add(new FastBreakFeature());
        // More features will be added here
    }

    public static AuroraClient getInstance() {
        return INSTANCE;
    }

    public List<Feature> getFeatures() {
        return features;
    }
}
