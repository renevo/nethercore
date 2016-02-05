package com.renevo.nethercore.client;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy {

    public static void initClient() {
        MinecraftForge.EVENT_BUS.register(new ItemBlockModelSetter());
    }
}
