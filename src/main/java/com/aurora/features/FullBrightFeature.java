package com.aurora.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

/**
 * Full Bright feature - provides permanent night vision effect
 */
public class FullBrightFeature extends AbstractFeature {
    private final MinecraftClient client = MinecraftClient.getInstance();
    
    public FullBrightFeature() {
        super("FullBright", "See clearly in the dark with night vision");
    }
    
    @Override
    protected void onTick() {
        if (client.player != null) {
            // Add infinite night vision effect
            client.player.addStatusEffect(
                new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false, false)
            );
        }
    }
    
    @Override
    public void onDisable() {
        if (client.player != null) {
            client.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    }
}
