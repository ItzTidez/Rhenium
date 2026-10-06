package com.rhenium.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    private int rhenium$tickCounter = 0;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void rhenium$throttleItemTick(CallbackInfo ci) {
        ItemEntity self = (ItemEntity)(Object)this;

        // Throttling items stationary on ground
        if (self.onGround() && self.getDeltaMovement().lengthSqr() < 0.001) {
            rhenium$tickCounter++;
            if (rhenium$tickCounter % 3 != 0) {
                ci.cancel();
            }
        } else {
            rhenium$tickCounter = 0;
        }
    }
}