package com.rhenium.mixin.client;

import com.rhenium.config.RheniumConfig;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityRenderer.class)
public interface ChestRenderDistanceMixin {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void rhenium$cullDistantChests(BlockEntity blockEntity, Vec3 pos, CallbackInfoReturnable<Boolean> cir) {
        RheniumConfig cfg = RheniumConfig.get();
        if (!cfg.enableMod || !cfg.enableChestRenderCull) return;

        if (!(blockEntity instanceof ChestBlockEntity) &&
            !(blockEntity instanceof TrappedChestBlockEntity)) return;

        double distSq = pos.distanceToSqr(
            blockEntity.getBlockPos().getX() + 0.5,
            blockEntity.getBlockPos().getY() + 0.5,
            blockEntity.getBlockPos().getZ() + 0.5);

        int dist = cfg.chestRenderDistance;
        if (distSq > dist * dist) {
            cir.setReturnValue(false);
        }
    }
}