package com.renevo.nethercore.common;

import com.renevo.nethercore.item.NetherCoreItems;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.renevo.nethercore.NetherCoreAchievements;
import com.renevo.nethercore.NetherCoreRegistry;
import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.world.NetherOreGenerator;

public class CommonProxy {

    public void preInit() {
        // blocks, items, worldgen, etc...
        NetherCoreBlocks.init();
        NetherCoreItems.init();

        NetherCoreRegistry.registerOreDictionary();

        GameRegistry.registerWorldGenerator(new NetherOreGenerator(), 1);
        NetherCoreAchievements.init();

        registerModels();
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

    public void registerModels() {

    }
}
