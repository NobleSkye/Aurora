package com.aurora.gui;

import com.aurora.AuroraMod;
import com.aurora.features.Feature;
import com.aurora.features.FeatureManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class AuroraGui {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private boolean menuVisible = false;
    
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
            if (menuVisible) {
                renderMenu(context);
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
    
    private void renderMenu(DrawContext context) {
        int centerX = context.getScaledWindowWidth() / 2;
        int centerY = context.getScaledWindowHeight() / 2;
        
        // Draw background
        context.fill(centerX - 150, centerY - 100, centerX + 150, centerY + 100, 0x80000000);
        
        // Draw title
        context.drawCenteredTextWithShadow(client.textRenderer, 
            Text.literal("§6Aurora Builder Mode"), 
            centerX, centerY - 80, 0xFFFFFF);
        
        // Draw features
        FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
        int y = centerY - 50;
        int index = 1;
        
        for (Feature feature : manager.getAllFeatures().values()) {
            String status = feature.isEnabled() ? "§aON" : "§cOFF";
            context.drawCenteredTextWithShadow(client.textRenderer,
                Text.literal("§f[" + index + "] " + feature.getName() + " " + status),
                centerX, y, 0xFFFFFF);
            y += 15;
            index++;
        }
        
        // Draw instructions
        context.drawCenteredTextWithShadow(client.textRenderer,
            Text.literal("§7Use /aurora commands to control features"),
            centerX, centerY + 70, 0xAAAAAA);
    }
    
    public void toggleMenu() {
        try {
            menuVisible = !menuVisible;
            System.out.println("Aurora menu toggled: " + (menuVisible ? "ON" : "OFF"));
        } catch (Exception e) {
            System.err.println("Error toggling Aurora menu: " + e.getMessage());
            e.printStackTrace();
            menuVisible = false; // Safe fallback
        }
    }
    
    public boolean isMenuVisible() {
        return menuVisible;
    }
}
