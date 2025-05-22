package com.aurora.client.feature;

import net.minecraft.client.MinecraftClient;

public class NoClipFeature extends AbstractFeature {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public NoClipFeature() {
        super("NoClip", "Pass through blocks while retaining interaction capabilities");
    }

    @Override
    public void onTick() {
        if (isEnabled() && client.player != null) {
            client.player.noClip = true;
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled && client.player != null) {
            client.player.noClip = false;
        }
    }
}
