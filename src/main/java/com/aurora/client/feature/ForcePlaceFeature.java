package com.aurora.client.feature;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ForcePlaceFeature extends AbstractFeature {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public ForcePlaceFeature() {
        super("Force Place", "Place blocks anywhere");
    }

    public boolean onBlockPlace(BlockPos pos, Direction direction) {
        if (!isEnabled() || client.world == null || client.player == null) return false;

        HitResult hitResult = client.crosshairTarget;
        if (hitResult == null || hitResult.getType() != HitResult.Type.BLOCK) return false;

        if (!(client.player.getMainHandStack().getItem() instanceof BlockItem)) return false;

        // Allow placement regardless of normal placement rules
        return true;
    }
}
