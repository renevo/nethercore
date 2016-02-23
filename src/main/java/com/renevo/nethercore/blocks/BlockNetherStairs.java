package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockNetherStairs extends BlockStairs {

    public BlockNetherStairs(IBlockState blockState) {
        super(blockState);

        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }
}
