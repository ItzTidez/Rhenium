package com.rhenium;

import com.rhenium.config.RheniumConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RheniumMod implements ModInitializer {
    public static final String MOD_ID = "rhenium";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        RheniumConfig.load();
        LOGGER.info("Rhenium loaded");
    }
}