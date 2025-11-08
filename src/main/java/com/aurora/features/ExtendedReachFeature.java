package com.aurora.features;

/**
 * Extended Reach feature - allows reaching blocks from much farther away
 * Default: 100 blocks for creative building (Axiom-like)
 * Implemented via ClientPlayerInteractionManagerMixin
 */
public class ExtendedReachFeature extends AbstractFeature {
    private double reachDistance = 100.0;
    
    public ExtendedReachFeature() {
        super("ExtendedReach", "Reach blocks from up to 100 blocks away");
    }
    
    public double getReachDistance() {
        return reachDistance;
    }
    
    public void setReachDistance(double distance) {
        this.reachDistance = Math.max(1.0, Math.min(1000.0, distance));
    }
}
