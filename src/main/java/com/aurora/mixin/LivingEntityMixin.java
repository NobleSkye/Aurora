package com.aurora.mixin;

import com.aurora.AuroraMod;
import com.aurora.features.NoFallDamageFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // Check if this is the player and if NoFallDamage is enabled
        NoFallDamageFeature feature = AuroraMod.getInstance()
            .getFeatureManager()
            .getFeature(NoFallDamageFeature.class);
        
        if (feature != null && feature.isEnabled()) {
            LivingEntity entity = (LivingEntity) (Object) this;
            if (entity.getWorld() != null && entity.getWorld().isClient) {
                // Cancel fall damage
                if (source.getType().msgId().contains("fall")) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
