package com.aurora.features;

public class ForcePlaceFeature extends AbstractFeature {
    
    public ForcePlaceFeature() {
        super("ForcePlace", "Place blocks anywhere");
    }
    
    // This feature is implemented via mixin to bypass placement restrictions
    // See ForcePlaceMixin for implementation
}
