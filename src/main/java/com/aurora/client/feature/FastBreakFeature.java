package com.aurora.client.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.hit.BlockHitResult;

public class FastBreakFeature extends AbstractFeature {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public FastBreakFeature() {
        super("Fast Break", "Break blocks instantly");
    }

    @Override
    public void onTick() {
        if (!isEnabled() || client.interactionManager == null) return;

        // If we're breaking a block, attempt to break it instantly
        if (client.interactionManager.isBreakingBlock() && client.crosshairTarget instanceof BlockHitResult hit) {
            client.interactionManager.breakBlock(hit.getBlockPos());
        }
    }
}
