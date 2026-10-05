package com.rhenium.mixin.client;

import com.rhenium.config.RheniumConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Particle.class)
public class ParticleCullMixin {

    @Shadow protected double x;
    @Shadow protected double y;
    @Shadow protected double z;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void rhenium$cullDistantParticle(CallbackInfo ci) {
        RheniumConfig cfg = RheniumConfig.get();
        if (!cfg.enableMod || !cfg.enableParticleCull) return;

        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        double distSq = client.player.distanceToSqr(x, y, z);
        int dist = cfg.particleCullDistance;

        if (distSq > dist * dist) {
            ci.cancel();
        }
    }
}