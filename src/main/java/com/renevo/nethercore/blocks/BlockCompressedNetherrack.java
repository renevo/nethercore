package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Locale;

public class BlockCompressedNetherrack extends EnumBlock<BlockCompressedNetherrack.CompressionDepth>  {

    public static final PropertyEnum<CompressionDepth> TYPE = PropertyEnum.create("type", CompressionDepth.class);

    public BlockCompressedNetherrack() {
        this(Material.ROCK);
    }

    public BlockCompressedNetherrack(Material material) {
        super(material, TYPE, CompressionDepth.class);

        setHardness(2.0f);
        setResistance(5.0F);
        setSoundType(NetherCoreBlocks.soundTypeNetherStone);
        setHarvestLevel("pickaxe", 1); // 1 is stone required (0 wood, 1 stone, 2 iron)
        setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, EntityLiving.SpawnPlacementType spawnPlacementType) {
        return false;
    }

    @Override
    public boolean isFireSource(World world, BlockPos blockPos, EnumFacing facing) {
        return facing == EnumFacing.UP;
    }

    public enum CompressionDepth implements IStringSerializable, EnumBlock.IEnumMeta {
        SINGLE,
        DOUBLE,
        TRIPLE,
        QUADRUPLE,
        QUINTUPLE,
        SEXTUPLE,
        SEPTUPLE,
        OCTUPLE;

        public final int meta;

        CompressionDepth() {
            meta = ordinal();
        }

        @Override
        public String getName() {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta() {
            return meta;
        }
    }
}
