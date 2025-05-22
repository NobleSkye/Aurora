package com.aurora.client.feature;

import com.aurora.mixin.ClientWorldMixin;
import net.minecraft.client.MinecraftClient;

public class FreezeUpdatesFeature extends AbstractFeature {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public FreezeUpdatesFeature() {
        super("Freeze Updates", "Prevent block updates from occurring");
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        com.aurora.client.util.FreezeUpdatesUtil.setFreezeUpdatesEnabled(enabled);
    }
}
