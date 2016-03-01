package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import slimeknights.mantle.block.EnumBlock;

public class BlockCompressedNetherrack extends EnumBlock<BlockCompressedNetherrack.CompressionDepth>  {

    public static final PropertyEnum<CompressionDepth> TYPE = PropertyEnum.create("type", CompressionDepth.class);

    public BlockCompressedNetherrack() {
        this(Material.rock);
    }

    public BlockCompressedNetherrack(Material material) {
        super(material, TYPE, CompressionDepth.class);

        setHardness(2.0f);
        setResistance(5.0F);
        setStepSound(NetherCoreBlocks.soundTypeNetherStone);
        setHarvestLevel("pickaxe", 1); // 1 is stone required (0 wood, 1 stone, 2 iron)
        setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    public boolean canCreatureSpawn(IBlockAccess blockAccess, BlockPos blockPos, EntityLiving.SpawnPlacementType spawnPlacementType) {
        return false;
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
            return this.toString();
        }

        @Override
        public int getMeta() {
            return meta;
        }
    }
}
