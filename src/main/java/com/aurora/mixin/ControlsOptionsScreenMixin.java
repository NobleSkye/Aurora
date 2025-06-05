package com.aurora.mixin;

// This mixin is disabled because the Controls/KeyBinds screen class does not exist in 1.21.4 mappings.
// TODO: Update this file when the correct class name for the keybinds/controls screen is known for 1.21.4.

// TODO: Confirm the correct class for Controls/KeyBinds screen in 1.21.4. This is a guess based on previous mappings.
/*
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
                // TODO: Implement Aurora settings screen
            }
        ).dimensions(x, y, 150, 20).build());
    }
}
*/
