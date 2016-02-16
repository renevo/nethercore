package com.renevo.nethercore.tconstruct;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

public final class TinkersIntegration {

    public static final int VALUE_Ingot = 144;
    public static final int VALUE_Ore = VALUE_Ingot*2;
    public static final int VALUE_NetherOre = VALUE_Ore*2;

    private TinkersIntegration() {}

    public static void addTinkersSmelting(ItemStack cook, Fluid output, int amount) {
        if (cook == null || output == null || amount <= 0) {
            return;
        }

        // TODO: Check configuration
        if (!Loader.isModLoaded("tconstruct")) {
            return;
        }

        try {
            slimeknights.tconstruct.library.TinkerRegistry.registerMelting(cook, output, amount);
        } catch (Exception ex) {
            FMLLog.warning("Unable to integrate with Tinkers Registry - Did the API change?");
        }
    }
}
