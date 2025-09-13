package com.aurora.gui;

import com.aurora.AuroraMod;
import com.aurora.features.Feature;
import com.aurora.features.FeatureManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class AuroraGui {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private boolean toolbarVisible = false;
    private int hoveredButtonIndex = -1;
    
    // Toolbar layout constants
    private static final int TOOLBAR_HEIGHT = 32;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 24;
    private static final int BUTTON_MARGIN = 4;
    private static final int TOOLBAR_PADDING = 8;
    
    public AuroraGui() {
        registerHudRenderer();
    }
    
    private void registerHudRenderer() {
        HudRenderCallback.EVENT.register((context, tickCounter) -> renderHud(context, tickCounter));
    }
    
    private void renderHud(DrawContext context, float tickDelta) {
        try {
            if (context == null || client == null) return;
            
            // Always show active features in top-left corner
            renderActiveFeatures(context);
            
            // Show toolbar when Left Alt is held
            if (toolbarVisible) {
                renderToolbar(context);
            }
        } catch (Exception e) {
            System.err.println("Error rendering Aurora HUD: " + e.getMessage());
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
            
            int y = 10;
            for (Feature feature : activeFeatures) {
                if (feature != null && feature.getName() != null) {
                    context.drawText(client.textRenderer, 
                        Text.literal("§a[" + feature.getName() + "]"), 
                        10, y, 0x00FF00, true);
                    y += 12;
                }
            }
        } catch (Exception e) {
            System.err.println("Error rendering active features: " + e.getMessage());
        }
    }
    
    private void renderToolbar(DrawContext context) {
        FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
        if (manager == null) return;
        
        List<Feature> features = new ArrayList<>(manager.getAllFeatures().values());
        if (features.isEmpty()) return;
        
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        
        // Calculate toolbar dimensions
        int totalWidth = features.size() * (BUTTON_WIDTH + BUTTON_MARGIN) - BUTTON_MARGIN + 2 * TOOLBAR_PADDING;
        int toolbarX = (screenWidth - totalWidth) / 2;
        int toolbarY = screenHeight - TOOLBAR_HEIGHT - 50; // Above hotbar
        
        // Update hovered button based on mouse position
        updateHoveredButton(context, toolbarX, toolbarY, features.size());
        
        // Draw toolbar background
        context.fill(
            toolbarX - 2, toolbarY - 2,
            toolbarX + totalWidth + 2, toolbarY + TOOLBAR_HEIGHT + 2,
            0x90000000 // Semi-transparent black
        );
        
        context.fill(
            toolbarX, toolbarY,
            toolbarX + totalWidth, toolbarY + TOOLBAR_HEIGHT,
            0x40FFFFFF // Light overlay
        );
        
        // Draw feature buttons
        for (int i = 0; i < features.size(); i++) {
            Feature feature = features.get(i);
            if (feature == null) continue;
            
            int buttonX = toolbarX + TOOLBAR_PADDING + i * (BUTTON_WIDTH + BUTTON_MARGIN);
            int buttonY = toolbarY + (TOOLBAR_HEIGHT - BUTTON_HEIGHT) / 2;
            
            drawFeatureButton(context, feature, buttonX, buttonY, i == hoveredButtonIndex);
        }
        
        // Draw instructions at the bottom
        String instruction = "Hold Left Alt to show toolbar | Click to toggle features | " +
                           (hoveredButtonIndex >= 0 && hoveredButtonIndex < features.size() ? 
                            features.get(hoveredButtonIndex).getName() + " - " + getFeatureDescription(features.get(hoveredButtonIndex)) : 
                            "Hover over buttons for descriptions");
                            
        context.drawCenteredTextWithShadow(client.textRenderer,
            Text.literal("§7" + instruction),
            screenWidth / 2, toolbarY + TOOLBAR_HEIGHT + 10, 0xFFFFFF);
    }
    
    private void drawFeatureButton(DrawContext context, Feature feature, int x, int y, boolean hovered) {
        boolean enabled = feature.isEnabled();
        
        // Button background color based on state
        int backgroundColor;
        int textColor;
        
        if (hovered) {
            backgroundColor = enabled ? 0xFF4CAF50 : 0xFF616161; // Green or gray when hovered
            textColor = 0xFFFFFF;
        } else {
            backgroundColor = enabled ? 0xFF2E7D32 : 0xFF424242; // Dark green or dark gray
            textColor = enabled ? 0xFFFFFF : 0xFFBDBDBD;
        }
        
        // Draw button background
        context.fill(x, y, x + BUTTON_WIDTH, y + BUTTON_HEIGHT, backgroundColor);
        
        // Draw button border
        int borderColor = hovered ? 0xFFFFFFFF : 0xFF666666;
        context.drawBorder(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, borderColor);
        
        // Draw feature name
        String buttonText = feature.getName();
        if (buttonText.length() > 10) {
            buttonText = buttonText.substring(0, 8) + "..";
        }
        
        int textWidth = client.textRenderer.getWidth(buttonText);
        int textX = x + (BUTTON_WIDTH - textWidth) / 2;
        int textY = y + (BUTTON_HEIGHT - client.textRenderer.fontHeight) / 2;
        
        context.drawText(client.textRenderer, Text.literal(buttonText), textX, textY, textColor, false);
        
        // Draw status indicator
        String statusIndicator = enabled ? "●" : "○";
        int indicatorColor = enabled ? 0xFF4CAF50 : 0xFF757575;
        context.drawText(client.textRenderer, Text.literal(statusIndicator), 
            x + BUTTON_WIDTH - 10, y + 2, indicatorColor, false);
    }
    
    private void updateHoveredButton(DrawContext context, int toolbarX, int toolbarY, int buttonCount) {
        if (client.mouse == null) return;
        
        double mouseX = client.mouse.getX() * client.getWindow().getScaledWidth() / client.getWindow().getWidth();
        double mouseY = client.mouse.getY() * client.getWindow().getScaledHeight() / client.getWindow().getHeight();
        
        hoveredButtonIndex = -1;
        
        for (int i = 0; i < buttonCount; i++) {
            int buttonX = toolbarX + TOOLBAR_PADDING + i * (BUTTON_WIDTH + BUTTON_MARGIN);
            int buttonY = toolbarY + (TOOLBAR_HEIGHT - BUTTON_HEIGHT) / 2;
            
            if (mouseX >= buttonX && mouseX <= buttonX + BUTTON_WIDTH &&
                mouseY >= buttonY && mouseY <= buttonY + BUTTON_HEIGHT) {
                hoveredButtonIndex = i;
                break;
            }
        }
    }
    
    public boolean handleClick(double mouseX, double mouseY) {
        if (!toolbarVisible) return false;
        
        FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
        if (manager == null) return false;
        
        List<Feature> features = new ArrayList<>(manager.getAllFeatures().values());
        if (hoveredButtonIndex >= 0 && hoveredButtonIndex < features.size()) {
            Feature feature = features.get(hoveredButtonIndex);
            if (feature != null) {
                feature.toggle();
                return true;
            }
        }
        
        return false;
    }
    
    private String getFeatureDescription(Feature feature) {
        switch (feature.getName().toLowerCase()) {
            case "noclip":
                return "Walk through blocks";
            case "fastplace":
                return "Place blocks instantly";
            case "fastbreak":
                return "Break blocks instantly";
            case "forceplace":
                return "Force place blocks anywhere";
            case "replace":
                return "Replace blocks while building";
            case "tinker":
                return "Advanced block manipulation";
            case "freezeupdates":
                return "Prevent block updates";
            default:
                return "Toggle this feature";
        }
    }
    
    public void setToolbarVisible(boolean visible) {
        this.toolbarVisible = visible;
    }
    
    public boolean isToolbarVisible() {
        return toolbarVisible;
    }
    
    // Legacy method for compatibility - deprecated
    @Deprecated
    public void toggleMenu() {
        // No-op - we use toolbar now
    }
    
    @Deprecated
    public boolean isMenuVisible() {
        return toolbarVisible;
    }
}
