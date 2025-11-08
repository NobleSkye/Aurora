package com.aurora.features;

/**
 * No Fall Damage feature - prevents fall damage
 * Implemented via LivingEntityMixin
 */
public class NoFallDamageFeature extends AbstractFeature {
    
    public NoFallDamageFeature() {
        super("NoFallDamage", "Never take fall damage");
    }
}
