package com.renevo.nethercore.client.gui.config;

import com.renevo.nethercore.Util;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class ModConfigGui extends GuiConfig {
    public ModConfigGui(GuiScreen parent) {
        super(parent, getConfigElements(), Util.MODID, false, false, "Nether Core Configuration");
    }

    private static List<IConfigElement> getConfigElements() {
        return new ArrayList<IConfigElement>();
    }
}
