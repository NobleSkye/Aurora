package com.aurora.client.gui;

import com.aurora.client.feature.Feature;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class AuroraMenu extends Screen {
    private final List<Feature> features;
    private boolean isOpen = false;

    public AuroraMenu(List<Feature> features) {
        super(Text.literal("Aurora Menu"));
        this.features = features;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonWidth = 120;
        int buttonHeight = 20;
        int padding = 5;

        for (int i = 0; i < features.size(); i++) {
            Feature feature = features.get(i);
            int x = centerX - buttonWidth / 2;
            int y = centerY - (features.size() * (buttonHeight + padding)) / 2 + i * (buttonHeight + padding);

            // Draw button background
            context.fill(x, y, x + buttonWidth, y + buttonHeight, 
                feature.isEnabled() ? 0xFF00FF00 : 0xFF505050);

            // Draw feature name
            context.drawCenteredTextWithShadow(this.textRenderer, 
                feature.getName(), centerX, y + 6, 0xFFFFFFFF);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonWidth = 120;
        int buttonHeight = 20;
        int padding = 5;

        for (int i = 0; i < features.size(); i++) {
            Feature feature = features.get(i);
            int x = centerX - buttonWidth / 2;
            int y = centerY - (features.size() * (buttonHeight + padding)) / 2 + i * (buttonHeight + padding);

            if (mouseX >= x && mouseX < x + buttonWidth && 
                mouseY >= y && mouseY < y + buttonHeight) {
                feature.toggle();
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
