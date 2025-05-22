package com.aurora.client.gui;

import com.aurora.client.feature.Feature;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import java.util.List;

public class AuroraHotbar extends Screen {
    private final List<Feature> features;

    public AuroraHotbar(List<Feature> features) {
        super(Text.literal("Aurora Hotbar"));
        this.features = features;
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 70;
        int buttonHeight = 20;
        int x = this.width / 2 - (features.size() * (buttonWidth + 5) / 2); // Center the buttons
        int y = this.height - 40; // Position above the hotbar

        for (Feature feature : features) {
            ButtonWidget button = ButtonWidget.builder(
                Text.literal(feature.getName()),
                btn -> feature.toggle()
            )
            .dimensions(x, y, buttonWidth, buttonHeight)
            .narrationSupplier((btn) -> Text.literal(feature.getName()))
            .build();
            this.addDrawableChild(button);
            x += buttonWidth + 5;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
