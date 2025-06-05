package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.ForcePlaceFeature;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class ForcePlaceMixin {
    
    @Inject(method = "canPlace", at = @At("HEAD"), cancellable = true)
    private void onCanPlace(ItemPlacementContext context, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        ForcePlaceFeature forcePlace = (ForcePlaceFeature) AuroraMod.getInstance()
            .getFeatureManager().getFeature("forceplace");
        
        if (forcePlace != null && forcePlace.isEnabled()) {
            cir.setReturnValue(true);
        }
    }
}
