package com.aurora.mixin;

// This mixin is temporarily disabled until we implement the Aurora settings screen
/*
@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    protected OptionsScreenMixin(Text title) { super(title); }

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
