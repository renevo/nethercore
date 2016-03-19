package com.renevo.nethercore.common;

import com.renevo.nethercore.GuiHandler;
import com.renevo.nethercore.NetherCore;
import com.renevo.nethercore.item.NetherCoreItems;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.renevo.nethercore.NetherCoreAchievements;
import com.renevo.nethercore.NetherCoreRegistry;
import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.world.NetherOreGenerator;

public class CommonProxy {

    public void preInit() {
        NetherCoreBlocks.init();
        NetherCoreItems.init();

        NetherCoreRegistry.registerOreDictionary();

        if (Config.enableWorldOreGeneration) {
            GameRegistry.registerWorldGenerator(new NetherOreGenerator(), 1);
        }

        if (Config.enableAchievements) {
            NetherCoreAchievements.init();
        }

        registerModels();
    }

    public void init() {
        NetherCoreRegistry.registerRecipes();
        NetherCoreRegistry.registerSmelting();

        NetworkRegistry.INSTANCE.registerGuiHandler(NetherCore.instance, new GuiHandler());
    }

    public void postInit() {
        NetherCoreRegistry.registerFuels();
        NetherCoreRegistry.registerIntegrations();
    }

    public void registerModels() {

    }
}
