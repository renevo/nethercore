package com.renevo.nethercore.waila;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public final class WailaIntegration {

    private WailaIntegration() {}

    public static void register() {
        FMLInterModComms.sendMessage("Waila", "register", "com.renevo.nethercore.waila.WailaDataProvider.callbackRegister");
    }
}
