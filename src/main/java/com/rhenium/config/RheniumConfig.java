package com.rhenium.config;

import com.rhenium.RheniumMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.*;

public class RheniumConfig {

    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("rhenium.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public boolean enableMod = true;
    public boolean enableBlockEntityThrottle = true;
    public boolean enableChunkLoadSpread = true;
    public boolean enableEntityTickCull = true;
    public boolean enableItemEntityThrottle = true;
    public boolean enableChestRenderCull = true;
    public int entityCullDistance = 48;
    public int chestRenderDistance = 24;
    public boolean enableParticleCull = true;
    public int particleCullDistance = 32;
    public boolean enableEntityRendererCull = true;
    public int entityRendererCullDistance = 64;

    private static RheniumConfig instance;

    public static RheniumConfig get() {
        if (instance == null) load();
        return instance;
    }

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader r = Files.newBufferedReader(CONFIG_PATH)) {
                instance = GSON.fromJson(r, RheniumConfig.class);
            } catch (IOException e) {
                RheniumMod.LOGGER.error("[Rhenium] Failed to load config, using defaults.");
                instance = new RheniumConfig();
            }
        } else {
            instance = new RheniumConfig();
            save();
        }
    }

    public static void save() {
        try (Writer w = Files.newBufferedWriter(CONFIG_PATH)) {
            GSON.toJson(instance, w);
        } catch (IOException e) {
            RheniumMod.LOGGER.error("[Rhenium] Failed to save config.");
        }
    }
}