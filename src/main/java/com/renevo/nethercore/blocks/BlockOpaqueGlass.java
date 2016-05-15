package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOpaqueGlass extends Block {

    public BlockOpaqueGlass() {
        super(Material.glass, MapColor.grayColor);

        this.setHardness(0.3F);
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
        this.setStepSound(SoundType.GLASS);
        this.setLightOpacity(255); // block all light
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, EntityLiving.SpawnPlacementType spawnPlacementType) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, EnumFacing facing) {
        return !(blockState.getBlock() instanceof BlockOpaqueGlass) && super.shouldSideBeRendered(blockState, blockAccess, blockPos, facing);
    }
}
