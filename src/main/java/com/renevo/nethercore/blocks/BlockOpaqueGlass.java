package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOpaqueGlass extends BlockBreakable {

    public BlockOpaqueGlass() {
        super(Material.GLASS, false);

        this.setHardness(0.3F);
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
        this.setSoundType(SoundType.GLASS);
        this.setLightOpacity(255); // block all light
    }

    @Override
    public boolean canCreatureSpawn(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, EntityLiving.SpawnPlacementType spawnPlacementType) {
        return false;
    }

    // TODO: Find out how this is replaced in the future...
    @Override
    public MapColor getMapColor(IBlockState p_getMapColor_1_) {
        return MapColor.GRAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
