package com.aurora.client.feature;

public abstract class AbstractFeature implements Feature {
    private boolean enabled = false;
    private final String name;
    private final String description;

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
        this.enabled = enabled;
    }

    @Override
    public void toggle() {
        this.enabled = !this.enabled;
    }
}
