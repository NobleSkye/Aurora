package com.aurora.mixin;

import com.aurora.client.gui.AuroraSettingsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    protected OptionsScreenMixin(Text title) { super(title); }

    @Inject(method = "init", at = @At("HEAD"))
    private void aurora$addAuroraSettingsButton(CallbackInfo ci) {
        int x = this.width / 2 - 155;
        int y = this.height / 6 - 12;
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Aurora Settings"),
            btn -> MinecraftClient.getInstance().setScreen(new AuroraSettingsScreen(this))
        ).dimensions(x, y, 150, 20).build());
    }
}
