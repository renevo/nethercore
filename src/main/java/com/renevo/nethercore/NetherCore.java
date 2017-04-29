package com.renevo.nethercore;

import com.renevo.nethercore.common.CommonProxy;
import com.renevo.nethercore.common.Config;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Util.MODID,
        version = "${version}",
        dependencies = "required-after:forge@[13.20,);",
        acceptedMinecraftVersions = "[1.11,)",
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
