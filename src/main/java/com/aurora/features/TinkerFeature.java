package com.aurora.features;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.state.property.Property;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;

public class TinkerFeature extends AbstractFeature {
    private final MinecraftClient client = MinecraftClient.getInstance();
    
    public TinkerFeature() {
        super("Tinker", "Cycle through block states");
    }
    
    public void performTinker() {
        if (!isEnabled() || client.world == null || client.player == null) return;
        
        HitResult hitResult = client.crosshairTarget;
        if (hitResult == null || hitResult.getType() != HitResult.Type.BLOCK) return;
        
        BlockPos pos = ((BlockHitResult) hitResult).getBlockPos();
        BlockState currentState = client.world.getBlockState(pos);
        Block block = currentState.getBlock();
        
        Collection<Property<?>> properties = currentState.getProperties();
        if (properties.isEmpty()) return;
        
        Property<?> firstProperty = properties.iterator().next();
        cycleProperty(currentState, pos, firstProperty);
    }
    
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void cycleProperty(BlockState state, BlockPos pos, Property<T> property) {
        T currentValue = state.get(property);
        Collection<T> allowedValues = property.getValues();
        
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
        
        if (nextValue == null) {
            nextValue = allowedValues.iterator().next();
        }
        
        BlockState newState = state.with(property, nextValue);
        client.world.setBlockState(pos, newState);
    }
}
