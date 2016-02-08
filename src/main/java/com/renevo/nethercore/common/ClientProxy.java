package com.renevo.nethercore.common;

import com.renevo.nethercore.client.ItemBlockModelSetter;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();

        MinecraftForge.EVENT_BUS.register(new ItemBlockModelSetter());
    }

    @Override
    public void init() {
        super.init();

    }

    @Override
    public void postInit() {
        super.postInit();

    }
}
