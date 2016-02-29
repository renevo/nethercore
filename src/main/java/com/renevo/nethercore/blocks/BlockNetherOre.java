package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

import java.util.Random;

public class BlockNetherOre extends EnumBlock<BlockNetherOre.OreTypes> {

    public static final PropertyEnum<OreTypes> TYPE = PropertyEnum.create("type", OreTypes.class);

    public BlockNetherOre() {
        this(Material.rock);
    }

    public BlockNetherOre(Material material) {
        super(material, TYPE, OreTypes.class);

        setHardness(3.0f);
        setResistance(5.0F);
        setStepSound(NetherCoreBlocks.soundTypeNetherStone);
        setHarvestLevel("pickaxe", 2); // 2 is iron required (0 wood, 1 stone, 2 iron)
        setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    @Override
    public int getExpDrop(IBlockAccess world, BlockPos blockPos, int fortune) {
        OreTypes meta = OreTypes.values()[this.getMetaFromState(world.getBlockState(blockPos))];
        int xp = 0;
        Random rand = world instanceof World ? ((World) world).rand : new Random();

        // numbers taken from vanilla
        switch (meta) {
            case COAL:
                return MathHelper.getRandomIntegerInRange(rand, 0, 2);
            case REDSTONE:
                return MathHelper.getRandomIntegerInRange(rand, 2, 5);
            case LAPIS:
                return MathHelper.getRandomIntegerInRange(rand, 2, 5);
            case DIAMOND:
                return MathHelper.getRandomIntegerInRange(rand, 3, 7);
            case EMERALD:
                return MathHelper.getRandomIntegerInRange(rand, 3, 7);
        }

        return xp;
    }

    @Override
    public Item getItemDropped(IBlockState blockState, Random random, int p_getItemDropped_3_) { // what is param 3?
        OreTypes meta = OreTypes.values()[this.getMetaFromState(blockState)];
        switch (meta) {
            case COAL:
                return Items.coal;
            case REDSTONE:
                return Items.redstone;
            case LAPIS:
                return Items.dye;
            case DIAMOND:
                return Items.diamond;
            case EMERALD:
                return Items.emerald;
        }

        return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(IBlockState blockState, int bonus, Random random) {
        int dropCount= 1;

        Item dropped = this.getItemDropped(blockState, random, 0);
        if (dropped == Items.dye) {
            dropCount = 4 + random.nextInt(5) /* nether bonus */ + quantityDroppedWithBonus(4 + random.nextInt(5), bonus, random);
        }

        if (dropped == Items.diamond || dropped == Items.emerald || dropped == Items.coal) {
            dropCount = 1 /* nether bonus */ + quantityDroppedWithBonus(1, bonus, random);
        }

        if (dropped == Items.redstone) {
            dropCount = 4 + random.nextInt(2) /* nether bonus */ + quantityDroppedWithBonus(4 + random.nextInt(2), bonus, random);
        }

        return dropCount;
    }

    @Override
    public int damageDropped(IBlockState blockState) {
        OreTypes meta = OreTypes.values()[this.getMetaFromState(blockState)];
        if (meta == OreTypes.LAPIS) {
            return EnumDyeColor.BLUE.getDyeDamage();
        }

        if (meta == OreTypes.IRON || meta == OreTypes.GOLD) {
            return super.damageDropped(blockState);
        }

        // might need to be more specific here with redstone, diamond, coal, emerald, but I think these are just damage 0
        return 0;
    }

    @Override
    public int getDamageValue(World world, BlockPos blockPos) {
        return this.getMetaFromState(world.getBlockState(blockPos));
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    public int quantityDroppedWithBonus(int baseRate, int bonus, Random random) {
        if (bonus > 0) {
            int i = random.nextInt(bonus + 2) - 1;
            if (i < 0) {
                i = 0;
            }

            return baseRate * (i + 1);
        } else {
            return baseRate;
        }
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
