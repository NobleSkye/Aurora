package com.aurora.features;

public class FreezeUpdatesFeature extends AbstractFeature {
    
    public FreezeUpdatesFeature() {
        super("FreezeUpdates", "Prevent block updates");
    }
    
    // This feature is implemented via mixin to cancel block updates
    // See FreezeUpdatesMixin for implementation
}
