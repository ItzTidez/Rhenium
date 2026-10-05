package com.rhenium.mixin;

import com.rhenium.config.RheniumConfig;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {

    @Shadow private int cooldownTime;

    private int rhenium$skipCounter = 0;

    @Inject(
        method = "pushItemsTick",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void rhenium$throttleIdleHopper(
            Level level, BlockPos pos, BlockState state,
            HopperBlockEntity blockEntity, CallbackInfo ci) {

        RheniumConfig cfg = RheniumConfig.get();
        if (!cfg.enableMod || !cfg.enableBlockEntityThrottle) return;

        HopperBlockEntityMixin self = (HopperBlockEntityMixin)(Object)blockEntity;

        // cooldownTime > 0 means vanilla is already throttling it (found nothing to move)
        // We extend that further — only tick every 8 ticks instead of every tick
        if (self.cooldownTime > 0) {
            self.rhenium$skipCounter++;
            if (self.rhenium$skipCounter % 8 != 0) {
                ci.cancel();
            }
        } else {
            // Hopper is active, reset skip counter so it gets full throughput
            self.rhenium$skipCounter = 0;
        }
    }
}