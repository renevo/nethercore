package com.renevo.nethercore;

import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.item.NetherCoreItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {

    @Override
    public int getBurnTime(ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }

        Item item = itemStack.getItem();

        if (item == NetherCoreItems.netherCoal) {
            return 1600 * 2; // coal x 2
        }

        if (item instanceof ItemBlock && ((ItemBlock)item).getBlock() == NetherCoreBlocks.blockNetherCoal) {
            return 16000 * 2; // coal_block x 2
        }

        return 0;
    }
}
