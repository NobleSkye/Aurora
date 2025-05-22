package com.aurora.client.feature;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class ReplaceFeature extends AbstractFeature {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public ReplaceFeature() {
        super("Replace", "Replace blocks instantly without breaking");
    }

    public void onRightClick() {
        if (!isEnabled() || client.world == null || client.player == null) return;

        HitResult hitResult = client.crosshairTarget;
        if (hitResult == null || hitResult.getType() != HitResult.Type.BLOCK) return;

        if (!(client.player.getMainHandStack().getItem() instanceof BlockItem blockItem)) return;

        BlockPos pos = ((BlockHitResult) hitResult).getBlockPos();
        BlockState state = blockItem.getBlock().getDefaultState();
        
        // Replace the block instantly
        client.world.setBlockState(pos, state);
    }
}
