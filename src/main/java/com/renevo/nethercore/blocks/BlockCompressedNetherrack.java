package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import slimeknights.mantle.block.EnumBlock;

public class BlockCompressedNetherrack extends EnumBlock<BlockCompressedNetherrack.CompressionDepth>  {

    public static final PropertyEnum<CompressionDepth> TYPE = PropertyEnum.create("type", CompressionDepth.class);
    public static final Block.SoundType soundTypeNetherOre = new Block.SoundType("stone", 1.0F, 1.0F);

    public BlockCompressedNetherrack() {
        this(Material.rock);
    }

    public BlockCompressedNetherrack(Material material) {
        super(material, TYPE, CompressionDepth.class);

        setHardness(2.0f);
        setResistance(5.0F);
        setStepSound(soundTypeNetherOre);
        setHarvestLevel("pickaxe", 2); // 1 is stone required (0 wood, 1 stone, 2 iron)
        setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
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
