package com.aurora.client.feature;

import com.aurora.mixin.MinecraftClientMixin;

public class FastPlaceFeature extends AbstractFeature {
    public FastPlaceFeature() {
        super("FastPlace", "Remove block placement delay");
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        com.aurora.client.util.FastPlaceUtil.setFastPlaceEnabled(enabled);
    }
}
