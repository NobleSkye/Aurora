package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.NoClipFeature;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    
    @Inject(method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;", at = @At("HEAD"), cancellable = true)
    private void onAdjustMovementForCollisions(Vec3d movement, CallbackInfoReturnable<Vec3d> cir) {
        Entity entity = (Entity) (Object) this;
        MinecraftClient client = MinecraftClient.getInstance();
        
        // Only apply to the player entity
        if (entity == client.player) {
            NoClipFeature noClip = AuroraMod.getInstance().getFeatureManager().getFeature(NoClipFeature.class);
            if (noClip != null && noClip.isEnabled()) {
                // Return original movement without collision adjustment
                cir.setReturnValue(movement);
            }
        }
    }
}
