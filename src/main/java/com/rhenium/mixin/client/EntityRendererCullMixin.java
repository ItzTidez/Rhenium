package com.rhenium.mixin.client;

import com.rhenium.config.RheniumConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public class EntityRendererCullMixin {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void rhenium$cullDistantEntities(
            E entity, net.minecraft.client.renderer.culling.Frustum frustum,
            double x, double y, double z,
            float tickDelta,
            CallbackInfoReturnable<Boolean> cir) {

        RheniumConfig cfg = RheniumConfig.get();
        if (!cfg.enableMod || !cfg.enableEntityRendererCull) return;

        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        double distSq = client.player.distanceToSqr(x, y, z);
        int dist = cfg.entityRendererCullDistance;

        if (distSq > dist * dist) {
            cir.setReturnValue(false);
        }
    }
}