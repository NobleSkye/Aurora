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
            // Enable creative flight and invulnerability
            client.player.getAbilities().allowFlying = true;
            client.player.getAbilities().flying = true;
            client.player.getAbilities().invulnerable = true;
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (client.player != null) {
            if (enabled) {
                client.player.noClip = true;
                client.player.getAbilities().allowFlying = true;
                client.player.getAbilities().flying = true;
                client.player.getAbilities().invulnerable = true;
            } else {
                client.player.noClip = false;
                // Only disable flight/invulnerability if not in creative
                if (!client.player.isCreative()) {
                    client.player.getAbilities().allowFlying = false;
                    client.player.getAbilities().flying = false;
                    client.player.getAbilities().invulnerable = false;
                }
            }
        }
    }
}
