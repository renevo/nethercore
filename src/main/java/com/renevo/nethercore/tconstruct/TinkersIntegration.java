package com.renevo.nethercore.tconstruct;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public final class TinkersIntegration {

    public static final int VALUE_Ingot = 144;
    public static final int VALUE_Ore = VALUE_Ingot*2;
    public static final int VALUE_NetherOre = VALUE_Ore*2;

    private TinkersIntegration() {}

    public static void addTinkersSmelting(ItemStack cook, ItemStack render, FluidStack output, int temperature) {
        if (cook == null || render == null || output == null) {
            return;
        }

        // TODO: Check configuration
        if (!Loader.isModLoaded("tconstruct")) {
            return;
        }

        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound itemToCook = new NBTTagCompound();
        cook.writeToNBT(itemToCook);
        tag.setTag("Item", itemToCook);

        NBTTagCompound blockToRender = new NBTTagCompound();
        render.writeToNBT(blockToRender);
        tag.setTag("Block", blockToRender);

        output.writeToNBT(tag);
        tag.setInteger("Temperature", temperature);

        FMLInterModComms.sendMessage("TConstruct", "addSmelteryMelting", tag);
    }
}
