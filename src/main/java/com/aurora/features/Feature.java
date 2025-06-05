package com.aurora.features;

public interface Feature {
    String getName();
    String getDescription();
    boolean isEnabled();
    void setEnabled(boolean enabled);
    void toggle();
    void tick();
    
    default void onEnable() {}
    default void onDisable() {}
}
