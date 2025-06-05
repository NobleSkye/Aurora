package com.aurora.features;

import com.aurora.AuroraMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class FeatureManager {
    private final Map<String, Feature> features = new HashMap<>();
    private final MinecraftClient client = MinecraftClient.getInstance();
    
    public FeatureManager() {
        registerFeatures();
    }
    
    private void registerFeatures() {
        registerFeature(new NoClipFeature());
        registerFeature(new FastPlaceFeature());
        registerFeature(new FastBreakFeature());
        registerFeature(new ForcePlaceFeature());
        registerFeature(new ReplaceFeature());
        registerFeature(new TinkerFeature());
        registerFeature(new FreezeUpdatesFeature());
    }
    
    private void registerFeature(Feature feature) {
        features.put(feature.getName().toLowerCase(), feature);
        AuroraMod.LOGGER.info("Registered feature: {}", feature.getName());
    }
    
    public void tick() {
        features.values().forEach(Feature::tick);
    }
    
    public Feature getFeature(String name) {
        return features.get(name.toLowerCase());
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Feature> T getFeature(Class<T> featureClass) {
        return (T) features.values().stream()
            .filter(feature -> featureClass.isInstance(feature))
            .findFirst()
            .orElse(null);
    }
    
    public Map<String, Feature> getAllFeatures() {
        return new HashMap<>(features);
    }
    
    public void toggleFeature(String name) {
        Feature feature = getFeature(name);
        if (feature != null) {
            feature.toggle();
            if (client.player != null) {
                client.player.sendMessage(Text.literal("§6[Aurora] §f" + feature.getName() + " is now " + 
                    (feature.isEnabled() ? "§aEnabled" : "§cDisabled")), false);
            }
        }
    }
    
    public void enableFeature(String name) {
        Feature feature = getFeature(name);
        if (feature != null) {
            feature.setEnabled(true);
            if (client.player != null) {
                client.player.sendMessage(Text.literal("§6[Aurora] §a" + feature.getName() + " enabled"), false);
            }
        }
    }
    
    public void disableFeature(String name) {
        Feature feature = getFeature(name);
        if (feature != null) {
            feature.setEnabled(false);
            if (client.player != null) {
                client.player.sendMessage(Text.literal("§6[Aurora] §c" + feature.getName() + " disabled"), false);
            }
        }
    }
}
