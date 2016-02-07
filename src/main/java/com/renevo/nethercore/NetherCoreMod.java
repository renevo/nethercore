package com.renevo.nethercore;

import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.client.ClientProxy;
import com.renevo.nethercore.world.NetherOreGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Constants.MODID, useMetadata = true)
public class NetherCoreMod
{

    @Mod.Instance(Constants.MODID)
    public static NetherCoreMod instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetherCoreBlocks.init();

        if (event.getSide().isClient()) {
            ClientProxy.initClient();
        }

        GameRegistry.registerWorldGenerator(new NetherOreGenerator(), 1);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetherCoreAchievements.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        NetherCoreRegistry.addSmelting();
    }
}
