package com.aurora.gui;

import com.aurora.AuroraMod;
import com.aurora.features.Feature;
import com.aurora.features.FeatureManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * Axiom-style toolbar that appears when holding Left Alt
 */
public class AuroraToolbar {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private boolean visible = false;
    private int selectedIndex = -1;
    private List<Feature> features = new ArrayList<>();
    
    // Toolbar appearance constants
    private static final int TOOLBAR_HEIGHT = 50;
    private static final int BUTTON_WIDTH = 45;
    private static final int BUTTON_HEIGHT = 40;
    private static final int BUTTON_PADDING = 5;
    private static final int TOOLBAR_BACKGROUND = 0x88000000;
    private static final int BUTTON_BACKGROUND = 0xCC333333;
    private static final int BUTTON_HOVER = 0xCC555555;
    private static final int BUTTON_ACTIVE = 0xCC00AA00;
    
    public AuroraToolbar() {
        updateFeaturesList();
    }
    
    public void updateFeaturesList() {
        FeatureManager manager = AuroraMod.getInstance().getFeatureManager();
        if (manager != null) {
            features.clear();
            features.addAll(manager.getAllFeatures().values());
        }
    }
    
    public void render(DrawContext context, float tickDelta) {
        if (!visible || client == null || client.player == null) return;
        
        try {
            renderToolbar(context);
        } catch (Exception e) {
            AuroraMod.LOGGER.error("Error rendering Aurora toolbar: ", e);
        }
    }
    
    private void renderToolbar(DrawContext context) {
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        
        // Calculate toolbar position (centered at bottom)
        int toolbarWidth = features.size() * (BUTTON_WIDTH + BUTTON_PADDING) - BUTTON_PADDING;
        int toolbarX = (screenWidth - toolbarWidth) / 2;
        int toolbarY = screenHeight - TOOLBAR_HEIGHT - 20;
        
        // Draw toolbar background
        context.fill(toolbarX - 10, toolbarY - 5, 
                    toolbarX + toolbarWidth + 10, toolbarY + TOOLBAR_HEIGHT,
                    TOOLBAR_BACKGROUND);
        
        // Draw feature buttons
        for (int i = 0; i < features.size(); i++) {
            Feature feature = features.get(i);
            int buttonX = toolbarX + i * (BUTTON_WIDTH + BUTTON_PADDING);
            int buttonY = toolbarY + 5;
            
            // Determine button color
            int buttonColor = BUTTON_BACKGROUND;
            if (i == selectedIndex) {
                buttonColor = BUTTON_HOVER;
            } else if (feature.isEnabled()) {
                buttonColor = BUTTON_ACTIVE;
            }
            
            // Draw button background
            context.fill(buttonX, buttonY, 
                        buttonX + BUTTON_WIDTH, buttonY + BUTTON_HEIGHT,
                        buttonColor);
            
            // Draw button text (first letter of feature name)
            String buttonText = feature.getName().substring(0, 1).toUpperCase();
            int textX = buttonX + BUTTON_WIDTH / 2 - client.textRenderer.getWidth(buttonText) / 2;
            int textY = buttonY + BUTTON_HEIGHT / 2 - client.textRenderer.fontHeight / 2;
            
            context.drawText(client.textRenderer, Text.literal(buttonText), 
                           textX, textY, 0xFFFFFF, true);
            
            // Draw feature name below button if hovered
            if (i == selectedIndex) {
                int nameX = buttonX + BUTTON_WIDTH / 2 - client.textRenderer.getWidth(feature.getName()) / 2;
                int nameY = buttonY + BUTTON_HEIGHT + 5;
                context.drawText(client.textRenderer, Text.literal(feature.getName()),
                               nameX, nameY, 0xFFFFFF, true);
                
                // Draw status
                String status = feature.isEnabled() ? "§aON" : "§cOFF";
                int statusX = buttonX + BUTTON_WIDTH / 2 - client.textRenderer.getWidth(status) / 2;
                int statusY = nameY + 12;
                context.drawText(client.textRenderer, Text.literal(status),
                               statusX, statusY, 0xFFFFFF, true);
            }
        }
        
        // Draw instructions at the top
        String instructions = "Hold Alt + Click to toggle features";
        int instructionsX = screenWidth / 2 - client.textRenderer.getWidth(instructions) / 2;
        int instructionsY = toolbarY - 25;
        context.drawText(client.textRenderer, Text.literal("§7" + instructions),
                       instructionsX, instructionsY, 0xFFFFFF, true);
    }
    
    public void tick() {
        // Check if Left Alt is held
        boolean altHeld = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS;
        
        if (altHeld && !visible) {
            show();
        } else if (!altHeld && visible) {
            hide();
        }
        
        // Update selected index based on mouse position
        if (visible) {
            updateSelection();
        }
    }
    
    private void updateSelection() {
        if (client.mouse == null) return;
        
        int mouseX = (int) (client.mouse.getX() * client.getWindow().getScaledWidth() / client.getWindow().getWidth());
        int mouseY = (int) (client.mouse.getY() * client.getWindow().getScaledHeight() / client.getWindow().getHeight());
        
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        
        int toolbarWidth = features.size() * (BUTTON_WIDTH + BUTTON_PADDING) - BUTTON_PADDING;
        int toolbarX = (screenWidth - toolbarWidth) / 2;
        int toolbarY = screenHeight - TOOLBAR_HEIGHT - 20;
        
        selectedIndex = -1;
        
        for (int i = 0; i < features.size(); i++) {
            int buttonX = toolbarX + i * (BUTTON_WIDTH + BUTTON_PADDING);
            int buttonY = toolbarY + 5;
            
            if (mouseX >= buttonX && mouseX <= buttonX + BUTTON_WIDTH &&
                mouseY >= buttonY && mouseY <= buttonY + BUTTON_HEIGHT) {
                selectedIndex = i;
                break;
            }
        }
    }
    
    public void show() {
        visible = true;
        updateFeaturesList();
        AuroraMod.LOGGER.info("Aurora toolbar shown");
    }
    
    public void hide() {
        visible = false;
        selectedIndex = -1;
        AuroraMod.LOGGER.info("Aurora toolbar hidden");
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public boolean handleClick(double mouseX, double mouseY, int button) {
        if (!visible || button != 0) return false; // Only handle left clicks
        
        if (selectedIndex >= 0 && selectedIndex < features.size()) {
            Feature feature = features.get(selectedIndex);
            feature.toggle();
            
            if (client.player != null) {
                String status = feature.isEnabled() ? "§aEnabled" : "§cDisabled";
                client.player.sendMessage(Text.literal("§6[Aurora] §f" + feature.getName() + " " + status), true);
            }
            
            return true;
        }
        
        return false;
    }
}