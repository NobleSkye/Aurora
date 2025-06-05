package com.aurora.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class FastBreakFeature extends AbstractFeature {
    private final MinecraftClient client = MinecraftClient.getInstance();
    
    public FastBreakFeature() {
        super("FastBreak", "Break blocks instantly");
    }
    
    @Override
    protected void onTick() {
        if (client.interactionManager == null || client.crosshairTarget == null) return;
        
        if (client.interactionManager.isBreakingBlock() && 
            client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) client.crosshairTarget;
            client.interactionManager.breakBlock(blockHit.getBlockPos());
        }
    }
}
