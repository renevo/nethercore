package com.renevo.nethercore;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import com.renevo.nethercore.common.CommonProxy;
import com.renevo.nethercore.common.Config;

@Mod(
        modid = Util.MODID,
        dependencies = "required-after:Forge@[12.17,);required-after:mantle@[1.9.4-0.10,)",
        acceptedMinecraftVersions = "1.9.4",
        updateJSON = "https://raw.githubusercontent.com/RenEvo/nethercore/master/update.json",
        useMetadata = true,
        guiFactory="com.renevo.nethercore.client.gui.config.ModGuiFactory")
public class NetherCore
{
    public Logger log;

    @Mod.Instance(Util.MODID)
    public static NetherCore instance;

    @SidedProxy(clientSide="com.renevo.nethercore.common.ClientProxy", serverSide="com.renevo.nethercore.common.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        log = event.getModLog();
        Config.loadConfiguration(event.getSuggestedConfigurationFile());
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
}
