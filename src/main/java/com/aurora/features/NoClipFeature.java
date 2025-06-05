package com.aurora.features;

import net.minecraft.client.MinecraftClient;

public class NoClipFeature extends AbstractFeature {
    private final MinecraftClient client = MinecraftClient.getInstance();
    
    public NoClipFeature() {
        super("NoClip", "Pass through blocks freely");
    }
    
    @Override
    protected void onTick() {
        if (client.player != null) {
            client.player.noClip = true;
            client.player.getAbilities().allowFlying = true;
            client.player.getAbilities().flying = true;
        }
    }
    
    @Override
    public void onDisable() {
        if (client.player != null) {
            client.player.noClip = false;
            if (!client.player.isCreative()) {
                client.player.getAbilities().allowFlying = false;
                client.player.getAbilities().flying = false;
            }
        }
    }
}
