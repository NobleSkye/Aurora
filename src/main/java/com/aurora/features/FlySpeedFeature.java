package com.aurora.features;

import net.minecraft.client.MinecraftClient;

/**
 * Fly Speed feature - increases flying speed for faster building
 */
public class FlySpeedFeature extends AbstractFeature {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private float speedMultiplier = 5.0f;
    private float originalFlySpeed = 0.05f;
    
    public FlySpeedFeature() {
        super("FlySpeed", "Fly faster for quick building (5x speed)");
    }
    
    @Override
    public void onEnable() {
        if (client.player != null) {
            originalFlySpeed = client.player.getAbilities().getFlySpeed();
        }
    }
    
    @Override
    protected void onTick() {
        if (client.player != null) {
            client.player.getAbilities().setFlySpeed(originalFlySpeed * speedMultiplier);
        }
    }
    
    @Override
    public void onDisable() {
        if (client.player != null) {
            client.player.getAbilities().setFlySpeed(originalFlySpeed);
        }
    }
    
    public float getSpeedMultiplier() {
        return speedMultiplier;
    }
    
    public void setSpeedMultiplier(float multiplier) {
        this.speedMultiplier = Math.max(1.0f, Math.min(20.0f, multiplier));
    }
}
