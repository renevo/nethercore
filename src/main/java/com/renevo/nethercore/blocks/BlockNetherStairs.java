package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockNetherStairs extends BlockStairs {

    public BlockNetherStairs(IBlockState blockState) {
        super(blockState);

        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, EntityLiving.SpawnPlacementType spawnPlacementType) {
        return false;
    }
}
