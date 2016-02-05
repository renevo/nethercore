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

        // TODO: configure the XP, hardness, resistance, and harvest levels
        setHardness(3.0f);
        setResistance(5.0F);
        setStepSound(soundTypeNetherOre);
        setHarvestLevel("pickaxe", 2); // i think that 2 is diamond pick axe? need to verify
        setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    @Override
    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        return MathHelper.getRandomIntegerInRange(rand, 4, 6); // TODO: adjust random xp amount
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
