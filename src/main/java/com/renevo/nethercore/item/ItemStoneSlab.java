package com.renevo.nethercore.item;

import com.renevo.nethercore.blocks.EnumBlockSlab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;

public class ItemStoneSlab extends ItemSlab {

    public ItemStoneSlab(Block block, EnumBlockSlab<?> half, EnumBlockSlab<?> full) {
        super(block, half, full);
    }
}
