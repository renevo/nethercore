package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOpaqueGlass extends Block {

    public BlockOpaqueGlass() {
        super(Material.glass, MapColor.grayColor);

        this.setHardness(0.3F);
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
        this.setStepSound(Block.soundTypeGlass);
        this.setLightOpacity(255); // block all light
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canCreatureSpawn(IBlockAccess blockAccess, BlockPos blockPos, EntityLiving.SpawnPlacementType spawnPlacementType) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos blockPos, EnumFacing facing) {
        IBlockState blockState = blockAccess.getBlockState(blockPos);
        Block block = blockState.getBlock();

        return !(block instanceof BlockOpaqueGlass) && super.shouldSideBeRendered(blockAccess, blockPos, facing);
    }
}
