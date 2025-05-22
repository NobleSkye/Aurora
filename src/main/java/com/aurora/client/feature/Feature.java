package com.aurora.client.feature;

public interface Feature {
    String getName();
    String getDescription();
    boolean isEnabled();
    void setEnabled(boolean enabled);
    void toggle();
    
    // Optional method for features that need to run every tick
    default void onTick() {}
    
    // Optional method for features that need to handle key presses
    default void onKeyPress(int key, int scanCode, int modifiers) {}
}
