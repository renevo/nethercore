package com.renevo.nethercore.item;

import com.renevo.nethercore.blocks.BlockDoubleStoneSlab;
import com.renevo.nethercore.blocks.BlockHalfStoneSlab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;

public class ItemStoneSlab extends ItemSlab {

    public ItemStoneSlab(Block block, BlockHalfStoneSlab half, BlockDoubleStoneSlab full) {
        super(block, half, full);
    }
}
