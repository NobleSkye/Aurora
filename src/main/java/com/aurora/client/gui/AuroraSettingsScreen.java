package com.aurora.client.gui;

import com.aurora.client.AuroraConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class AuroraSettingsScreen extends Screen {
    private final Screen parent;
    private ButtonWidget toggleModeButton;

    public AuroraSettingsScreen(Screen parent) {
        super(Text.literal("Aurora Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 4;
        toggleModeButton = ButtonWidget.builder(
            getToggleModeText(),
            btn -> {
                AuroraConfig.setMenuHoldToShow(!AuroraConfig.isMenuHoldToShow());
                btn.setMessage(getToggleModeText());
            }
        ).dimensions(centerX - 75, y, 150, 20).build();
        this.addDrawableChild(toggleModeButton);

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Done"),
            btn -> MinecraftClient.getInstance().setScreen(parent)
        ).dimensions(centerX - 75, y + 30, 150, 20).build());
    }

    private Text getToggleModeText() {
        return AuroraConfig.isMenuHoldToShow()
            ? Text.literal("Menu Mode: Hold (click to toggle)")
            : Text.literal("Menu Mode: Toggle (click to toggle)");
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
