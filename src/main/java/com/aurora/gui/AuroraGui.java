package com.aurora.gui;

import com.aurora.AuroraMod;
import com.aurora.features.*;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.*;

public class AuroraGui {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private long lastAltActiveFrame = 0L;
    
    // Feature categories for better organization
    private static final Map<String, List<Class<? extends Feature>>> FEATURE_CATEGORIES = new LinkedHashMap<>();
    
    static {
        FEATURE_CATEGORIES.put("Movement", Arrays.asList(
            NoClipFeature.class,
            ExtendedReachFeature.class,
            FlySpeedFeature.class,
            NoFallDamageFeature.class
        ));
        FEATURE_CATEGORIES.put("Building", Arrays.asList(
            FastPlaceFeature.class,
            FastBreakFeature.class,
            ForcePlaceFeature.class,
            ReplaceFeature.class,
            TinkerFeature.class
        ));
        FEATURE_CATEGORIES.put("Utilities", Arrays.asList(
            FullBrightFeature.class,
            FreezeUpdatesFeature.class
        ));
    }
    
    public AuroraGui() {
        registerHudRenderer();
    }
    
    private void registerHudRenderer() {
        HudRenderCallback.EVENT.register((context, tickCounter) -> renderHud(context, 1.0f));
    }
    
    private void renderHud(DrawContext context, float tickDelta) {
        try {
            if (context == null || client == null) return;
            renderActiveFeatures(context);
            if (isLeftAltDown()) {
                lastAltActiveFrame = System.nanoTime();
                renderToolbar(context);
            }
        } catch (Exception e) {
            System.err.println("Error rendering Aurora HUD: " + e.getMessage());
            // Don't crash the game, just skip rendering this frame
        }
    }
    
    private void renderActiveFeatures(DrawContext context) {
        try {
            FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
            if (manager == null) return;
            
            List<Feature> activeFeatures = new ArrayList<>();
            
            for (Feature feature : manager.getAllFeatures().values()) {
                if (feature != null && feature.isEnabled()) {
                    activeFeatures.add(feature);
                }
            }
            
            if (activeFeatures.isEmpty()) return;
            
            // Header
            context.drawText(client.textRenderer, 
                Text.literal("§6§lAurora Active:"), 
                10, 5, 0xFFAA00, true);
            
            int y = 18;
            for (Feature feature : activeFeatures) {
                if (feature != null && feature.getName() != null) {
                    // Color code based on category
                    String color = getFeatureColor(feature);
                    context.drawText(client.textRenderer, 
                        Text.literal(color + "• " + feature.getName()), 
                        10, y, 0xFFFFFF, true);
                    y += 11;
                }
            }
        } catch (Exception e) {
            System.err.println("Error rendering active features: " + e.getMessage());
        }
    }
    
    private String getFeatureColor(Feature feature) {
        // Assign colors based on category
        for (Map.Entry<String, List<Class<? extends Feature>>> entry : FEATURE_CATEGORIES.entrySet()) {
            for (Class<? extends Feature> featureClass : entry.getValue()) {
                if (featureClass.isInstance(feature)) {
                    switch (entry.getKey()) {
                        case "Movement": return "§b"; // Cyan
                        case "Building": return "§a"; // Green
                        case "Utilities": return "§e"; // Yellow
                    }
                }
            }
        }
        return "§f"; // White default
    }
    
    private void renderToolbar(DrawContext context) {
        FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
        if (manager == null) return;

        int screenW = context.getScaledWindowWidth();
        int screenH = context.getScaledWindowHeight();
        
        int y = screenH - 60; // Moved up to accommodate more features
        int categoryY = y;
        
        // Title
        int titleX = screenW / 2 - 60;
        context.drawText(client.textRenderer, Text.literal("§6§lAurora Building Tools"), 
            titleX, y - 15, 0xFFAA00, true);
        
        int featureIndex = 1;
        
        // Render by category
        for (Map.Entry<String, List<Class<? extends Feature>>> entry : FEATURE_CATEGORIES.entrySet()) {
            String categoryName = entry.getKey();
            List<Class<? extends Feature>> featureClasses = entry.getValue();
            
            // Category header
            context.drawText(client.textRenderer, 
                Text.literal("§7" + categoryName + ":"), 
                10, categoryY, 0xAAAAAA, false);
            categoryY += 12;
            
            // Features in this category
            for (Class<? extends Feature> featureClass : featureClasses) {
                Feature feature = manager.getFeature(featureClass);
                if (feature != null) {
                    renderFeatureButton(context, feature, featureIndex, 10, categoryY);
                    categoryY += 14;
                    featureIndex++;
                }
            }
            
            categoryY += 5; // Space between categories
        }
        
        // Instructions at bottom
        context.drawText(client.textRenderer, 
            Text.literal("§7Hold L-Alt + [number] to toggle features"), 
            screenW / 2 - 100, screenH - 20, 0xAAAAAA, false);
    }
    
    private void renderFeatureButton(DrawContext context, Feature feature, int number, int x, int y) {
        int width = 200;
        int height = 12;
        
        // Background
        int bgColor = feature.isEnabled() ? 0x8800AA00 : 0x88333333;
        context.fill(x, y, x + width, y + height, bgColor);
        
        // Border
        int borderColor = feature.isEnabled() ? 0xFF00FF00 : 0xFF666666;
        context.fill(x, y, x + width, y + 1, borderColor); // Top
        context.fill(x, y + height - 1, x + width, y + height, borderColor); // Bottom
        context.fill(x, y, x + 1, y + height, borderColor); // Left
        context.fill(x + width - 1, y, x + width, y + height, borderColor); // Right
        
        // Text
        String status = feature.isEnabled() ? "§a[ON]" : "§c[OFF]";
        String label = "§7" + number + ". §f" + feature.getName() + " " + status;
        context.drawText(client.textRenderer, Text.literal(label), x + 4, y + 2, 0xFFFFFF, false);
    }

    private boolean isLeftAltDown() {
        try {
            long handle = client.getWindow().getHandle();
            return org.lwjgl.glfw.GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS;
        } catch (Exception e) {
            return false;
        }
    }

    // Called from key handling to attempt number activation while Alt held
    public void handleNumberKey(int numberIndex) {
        if (!isLeftAltDown()) return; // only when Alt active
        FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
        List<Feature> feats = new ArrayList<>(manager.getAllFeatures().values());
        if (numberIndex < 1 || numberIndex > feats.size()) return;
        Feature f = feats.get(numberIndex - 1);
        if (f != null) {
            f.toggle();
        }
    }
}
