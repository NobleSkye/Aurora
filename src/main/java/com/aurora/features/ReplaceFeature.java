package com.aurora.features;

public class ReplaceFeature extends AbstractFeature {
    
    public ReplaceFeature() {
        super("Replace", "Replace blocks instantly");
    }
    
    // This feature is implemented via ReplaceMixin which handles right-click interactions
    // The mixin intercepts interactBlock calls and performs instant block replacement
}
