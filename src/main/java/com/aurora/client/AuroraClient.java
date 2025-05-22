package com.aurora.client;

import com.aurora.client.feature.*;
import com.aurora.client.gui.AuroraHotbar;
import com.aurora.client.gui.AuroraSettingsScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class AuroraClient implements ClientModInitializer {
    private static AuroraClient INSTANCE;
    private final List<Feature> features = new ArrayList<>();
    private KeyBinding menuKey;
    private boolean hotbarMenuOpen = false;

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
            MinecraftClient mc = MinecraftClient.getInstance();
            boolean keyDown = menuKey.isPressed();
            if (com.aurora.client.AuroraConfig.isMenuHoldToShow()) {
                if (keyDown && !hotbarMenuOpen) {
                    mc.setScreen(new com.aurora.client.gui.AuroraHotbar(features));
                    hotbarMenuOpen = true;
                } else if (!keyDown && hotbarMenuOpen) {
                    if (mc.currentScreen instanceof com.aurora.client.gui.AuroraHotbar) {
                        mc.setScreen(null); // Close the menu
                    }
                    hotbarMenuOpen = false;
                }
            } else {
                if (menuKey.wasPressed()) {
                    if (!(mc.currentScreen instanceof com.aurora.client.gui.AuroraHotbar)) {
                        mc.setScreen(new com.aurora.client.gui.AuroraHotbar(features));
                        hotbarMenuOpen = true;
                    } else {
                        mc.setScreen(null);
                        hotbarMenuOpen = false;
                    }
                }
            }
        });
    }

    private void registerFeatures() {
        // Register all mod features
        NoClipFeature noClip = new NoClipFeature();
        noClip.setEnabled(true); // Enable NoClip by default
        features.add(noClip);
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
