package com.aurora.features;

public abstract class AbstractFeature implements Feature {
    protected final String name;
    protected final String description;
    protected boolean enabled = false;
    
    protected AbstractFeature(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        boolean wasEnabled = this.enabled;
        this.enabled = enabled;
        
        if (!wasEnabled && enabled) {
            onEnable();
        } else if (wasEnabled && !enabled) {
            onDisable();
        }
    }
    
    @Override
    public void toggle() {
        setEnabled(!enabled);
    }
    
    @Override
    public void tick() {
        if (enabled) {
            onTick();
        }
    }
    
    protected void onTick() {}
}
