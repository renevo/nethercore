package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Locale;

public abstract class BlockStoneSlab extends EnumBlockSlab<BlockStoneSlab.SlabType> {
    public static final PropertyEnum<SlabType> VARIANT = PropertyEnum.create("variant", SlabType.class);

    public BlockStoneSlab() {
        this(Material.rock);
    }

    @Override
    public Block halfSlab() { return NetherCoreBlocks.blockNetherHalfSlab; }

    @Override
    public Block fullSlab() { return NetherCoreBlocks.blockNetherDoubleSlab; }

    public BlockStoneSlab(Material material) {
        super(material, VARIANT, SlabType.class);

        IBlockState blockState = this.blockState.getBaseState();
        if (this.isDouble()) {
            blockState = blockState.withProperty(SEAMLESS, false);
        } else {
            blockState = blockState.withProperty(HALF, EnumBlockHalf.BOTTOM);
        }

        this.setDefaultState(blockState.withProperty(VARIANT, SlabType.STONE));
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
        this.setHardness(3F);
        this.setResistance(20F);
        this.setHarvestLevel("pickaxe", 1); // 1 is stone required (0 wood, 1 stone, 2 iron)
        this.setStepSound(NetherCoreBlocks.soundTypeNetherStone);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, EntityLiving.SpawnPlacementType spawnPlacementType) {
        return false;
    }

    public enum SlabType implements IStringSerializable, EnumBlockSlab.IEnumMeta {
        STONE,
        COBBLESTONE,
        STONEBRICK,
        ROAD;

        public final int meta;

        SlabType() {
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
