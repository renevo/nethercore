package com.renevo.nethercore.common;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public final class Config {

    private static Configuration forgeConfiguration;

    private Config() {}

    public static void loadConfiguration(File configurationFile) {
        forgeConfiguration = new Configuration(configurationFile);

        forgeConfiguration.load();

        // TODO: Load configuration stuff...

        forgeConfiguration.save();
    }
}
