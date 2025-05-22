package com.aurora.client.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.state.property.Property;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;

public class TinkerToolFeature extends AbstractFeature {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public TinkerToolFeature() {
        super("Tinker Tool", "Cycle through block states");
    }

    public void onRightClick() {
        if (!isEnabled() || client.world == null || client.player == null) return;

        HitResult hitResult = client.crosshairTarget;
        if (hitResult == null || hitResult.getType() != HitResult.Type.BLOCK) return;

        BlockPos pos = ((BlockHitResult) hitResult).getBlockPos();
        BlockState currentState = client.world.getBlockState(pos);
        Block block = currentState.getBlock();

        // Get all possible properties for this block
        Collection<Property<?>> properties = currentState.getProperties();
        if (properties.isEmpty()) return;

        // Cycle through the first property's values
        Property<?> firstProperty = properties.iterator().next();
        cycleProperty(currentState, pos, firstProperty);
    }

    private <T extends Comparable<T>> void cycleProperty(BlockState state, BlockPos pos, Property<T> property) {
        T currentValue = state.get(property);
        Collection<T> allowedValues = property.getValues();
        
        // Find the next allowed value
        T nextValue = null;
        boolean foundCurrent = false;
        
        for (T value : allowedValues) {
            if (foundCurrent) {
                nextValue = value;
                break;
            }
            if (value.equals(currentValue)) {
                foundCurrent = true;
            }
        }
        
        // If we didn't find a next value, use the first one
        if (nextValue == null) {
            nextValue = allowedValues.iterator().next();
        }

        // Set the new block state
        BlockState newState = state.with(property, nextValue);
        client.world.setBlockState(pos, newState);
    }
}
