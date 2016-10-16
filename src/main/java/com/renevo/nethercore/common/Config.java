package com.renevo.nethercore.common;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public final class Config {

    private static Configuration forgeConfiguration;

    private Config() {}

    public static boolean enableAchievements = true;
    public static boolean enableSmeltedNetherStar = true;
    public static boolean enableCompressedNetherrackRecipes = true;
    public static boolean enableNetherSporeRecipe = true;
    public static boolean enableNetherFurnaceRecipe = true;
    public static boolean enableSoulGlassRecipe = true;

    public static boolean enableWorldOreGeneration = true;

    public static boolean enableTinkersIntegration = true;

    public static void loadConfiguration(File configurationFile) {
        forgeConfiguration = new Configuration(configurationFile);

        forgeConfiguration.load();

        enableAchievements = forgeConfiguration.get("General", "Enable Achievements", true, "When false, no achievements will be registered or available").getBoolean();
        enableSmeltedNetherStar = forgeConfiguration.get("General", "Enable Nether Star Smelting", true, "When false, the Nether Star can not be smelted with Octuple Compressed Netherrack").getBoolean();
        enableCompressedNetherrackRecipes = forgeConfiguration.get("General", "Enable Compressed Netherrack", true, "When false, the Compressed Netherrack recipes will not be enabled").getBoolean();
        enableNetherSporeRecipe = forgeConfiguration.get("General", "Enable Nether Spore", true, "When false, the Nether Spore will not be craftable").getBoolean();
        enableNetherFurnaceRecipe = forgeConfiguration.get("General", "Enable Nether Furance", true, "When false, the Nether Furnace will not be craftable").getBoolean();
        enableSoulGlassRecipe = forgeConfiguration.get("General", "Enable Soul Glass", true, "When false, Soul Glass can not be obtained from Soul Sand when smelted").getBoolean();

        enableWorldOreGeneration = forgeConfiguration.get("World", "Generate Ores", true, "When false, no ores will be generated in the Nether").getBoolean();

        enableTinkersIntegration = forgeConfiguration.get("Integration", "Tinkers", true, "When false, nothing will be integrated with Tinkers").getBoolean();

        forgeConfiguration.save();
    }
}
