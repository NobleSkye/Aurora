package com.aurora.features;

public class FastPlaceFeature extends AbstractFeature {
    
    public FastPlaceFeature() {
        super("FastPlace", "Place blocks without delay");
    }
    
    // This feature is implemented via mixin to remove item use cooldown
    // See FastPlaceMixin for implementation
}
