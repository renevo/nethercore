package com.renevo.nethercore.common;

import net.minecraftforge.fml.common.registry.GameRegistry;

import com.renevo.nethercore.NetherCoreAchievements;
import com.renevo.nethercore.NetherCoreRegistry;
import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.world.NetherOreGenerator;

public class CommonProxy {

    public void preInit() {
        // blocks, items, worldgen, etc...
        NetherCoreBlocks.init();
        NetherCoreRegistry.registerOreDictionary();

        GameRegistry.registerWorldGenerator(new NetherOreGenerator(), 1);
        NetherCoreAchievements.init();
    }

    public void init() {
        // recipes
        NetherCoreRegistry.registerRecipes();
        NetherCoreRegistry.registerSmelting();
    }

    public void postInit() {
        // fuels, integrations
        NetherCoreRegistry.registerIntegrations();
    }
}
