package com.renevo.nethercore;

import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.client.ClientProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MODID, version = Constants.VERSION)
public class NetherCoreMod
{

    @Mod.Instance(Constants.MODID)
    public static NetherCoreMod instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetherCoreBlocks.preInit(event);

        if (event.getSide().isClient()) {
            ClientProxy.initClient();
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
