package com.aurora.mixin;

// This mixin is disabled because the Controls/KeyBinds screen class does not exist in 1.21.4 mappings.
// TODO: Update this file when the correct class name for the keybinds/controls screen is known for 1.21.4.

import com.aurora.client.gui.AuroraSettingsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO: Confirm the correct class for Controls/KeyBinds screen in 1.21.4. This is a guess based on previous mappings.
@Mixin(targets = "net.minecraft.client.gui.screen.option.ControlsOptionsScreen")
public abstract class ControlsOptionsScreenMixin extends Screen {
    protected ControlsOptionsScreenMixin(Text title) { super(title); }

    @Inject(method = "init", at = @At("HEAD"))
    private void aurora$addAuroraSettingsButton(CallbackInfo ci) {
        int x = this.width / 2 - 155;
        int y = this.height / 6 - 12;
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Aurora Settings"),
            btn -> {
                // Toggle Aurora Settings screen as a toggle button
                if (MinecraftClient.getInstance().currentScreen instanceof AuroraSettingsScreen) {
                    MinecraftClient.getInstance().setScreen(this);
                } else {
                    MinecraftClient.getInstance().setScreen(new AuroraSettingsScreen(this));
                }
            }
        ).dimensions(x, y, 150, 20).build());
    }
}
