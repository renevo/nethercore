package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNetherOre extends EnumBlock<BlockNetherOre.OreTypes> {

    public static final PropertyEnum<OreTypes> TYPE = PropertyEnum.create("type", OreTypes.class);
    public static final Block.SoundType soundTypeNetherOre = new Block.SoundType("stone", 1.0F, 1.0F);

    public BlockNetherOre() {
        this(Material.rock);
    }

    public BlockNetherOre(Material material) {
        super(material, TYPE, OreTypes.class);

        setHardness(3.0f);
        setResistance(5.0F);
        setStepSound(soundTypeNetherOre);
        setHarvestLevel("pickaxe", 2); // 2 is iron required (0 wood, 1 stone, 2 iron)
        setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    public enum OreTypes implements IStringSerializable, EnumBlock.IEnumMeta {
        COAL,
        IRON,
        GOLD,
        REDSTONE,
        LAPIS,
        DIAMOND,
        EMERALD;

        public final int meta;

        OreTypes() {
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
