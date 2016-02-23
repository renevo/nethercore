package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;
import slimeknights.mantle.block.EnumBlock;

public class BlockNetherStone extends EnumBlock<BlockNetherStone.StoneType> {

    public final static PropertyEnum<StoneType> TYPE = PropertyEnum.create("type", StoneType.class);
    public static final Block.SoundType soundTypeNetherStone = new Block.SoundType("stone", 1.0F, 1.0F);

    public BlockNetherStone() {
        super(Material.rock, TYPE, StoneType.class);
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
        this.setHardness(3F);
        this.setResistance(20F);
        this.setHarvestLevel("pickaxe", 1); // 1 is stone required (0 wood, 1 stone, 2 iron)
        this.setStepSound(soundTypeNetherStone);
    }

    public enum StoneType implements IStringSerializable, EnumBlock.IEnumMeta {
        STONE,
        COBBLE,
        PAVER,
        BRICK,
        BRICK_CRACKED,
        BRICK_FANCY,
        BRICK_SQUARE,
        ROAD,
        CREEPER;

        public final int meta;
        StoneType() {
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
