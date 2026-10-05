package com.rhenium.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class RheniumConfigScreen {

    public static Screen create(Screen parent) {
        RheniumConfig cfg = RheniumConfig.get();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Rhenium"))
                .setSavingRunnable(RheniumConfig::save);

        ConfigEntryBuilder eb = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));
        general.addEntry(eb.startBooleanToggle(Component.literal("Enable Mod"), cfg.enableMod)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Master toggle. Disables all optimizations at once."))
                .setSaveConsumer(val -> cfg.enableMod = val)
                .build());

        ConfigCategory optimizations = builder.getOrCreateCategory(Component.literal("Optimizations"));

        optimizations.addEntry(eb.startBooleanToggle(Component.literal("Block Entity Throttle"), cfg.enableBlockEntityThrottle)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Throttles block entity ticking."))
                .setSaveConsumer(val -> cfg.enableBlockEntityThrottle = val)
                .build());

        optimizations.addEntry(eb.startBooleanToggle(Component.literal("Item Entity Throttle"), cfg.enableItemEntityThrottle)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Throttles ticking of stationary ground items."))
                .setSaveConsumer(val -> cfg.enableItemEntityThrottle = val)
                .build());

        optimizations.addEntry(eb.startIntSlider(Component.literal("Chest Render Distance"), cfg.chestRenderDistance, 8, 64)
                .setDefaultValue(24)
                .setTooltip(Component.literal("Distance in blocks beyond which chest animations stop rendering. Lower = better FPS."))
                .setSaveConsumer(val -> cfg.chestRenderDistance = val)
                .build());

        optimizations.addEntry(eb.startBooleanToggle(Component.literal("Particle Culling"), cfg.enableParticleCull)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Stops spawning particles beyond the set distance."))
                .setSaveConsumer(val -> cfg.enableParticleCull = val)
                .build());

        optimizations.addEntry(eb.startIntSlider(Component.literal("Particle Cull Distance"), cfg.particleCullDistance, 8, 64)
                .setDefaultValue(32)
                .setTooltip(Component.literal("Distance in blocks beyond which particles are culled."))
                .setSaveConsumer(val -> cfg.particleCullDistance = val)
                .build());

        optimizations.addEntry(eb.startBooleanToggle(Component.literal("Entity Renderer Culling"), cfg.enableEntityRendererCull)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Skips rendering entities beyond the set distance."))
                .setSaveConsumer(val -> cfg.enableEntityRendererCull = val)
                .build());

        optimizations.addEntry(eb.startIntSlider(Component.literal("Entity Render Distance"), cfg.entityRendererCullDistance, 16, 128)
                .setDefaultValue(64)
                .setTooltip(Component.literal("Distance in blocks beyond which entities are not rendered."))
                .setSaveConsumer(val -> cfg.entityRendererCullDistance = val)
                .build());
        return builder.build();
    }
}