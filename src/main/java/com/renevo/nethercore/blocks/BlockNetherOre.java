package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import com.renevo.nethercore.item.NetherCoreItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

import java.util.Locale;
import java.util.Random;

public class BlockNetherOre extends EnumBlock<BlockNetherOre.OreTypes> {

    public static final PropertyEnum<OreTypes> TYPE = PropertyEnum.create("type", OreTypes.class);

    public BlockNetherOre() {
        this(Material.ROCK);
    }

    public BlockNetherOre(Material material) {
        super(material, TYPE, OreTypes.class);

        setHardness(3.0f);
        setResistance(5.0F);
        setSoundType(NetherCoreBlocks.soundTypeNetherStone);
        setHarvestLevel("pickaxe", 2); // 2 is iron required (0 wood, 1 stone, 2 iron)
        setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    @Override
    public boolean isFireSource(World world, BlockPos blockPos, EnumFacing facing) {
        return facing == EnumFacing.UP;
    }

    @Override
    public int getExpDrop(IBlockState blockState, IBlockAccess world, BlockPos blockPos, int fortune) {
        OreTypes meta = OreTypes.values()[this.getMetaFromState(blockState)];
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
            case NETHERCOAL: // bit of a bump from vanilla
                return MathHelper.getRandomIntegerInRange(rand, 1, 3);
        }

        return xp;
    }

    @Override
    public Item getItemDropped(IBlockState blockState, Random random, int p_getItemDropped_3_) { // what is param 3?
        OreTypes meta = OreTypes.values()[this.getMetaFromState(blockState)];
        switch (meta) {
            case COAL:
                return Items.COAL;
            case REDSTONE:
                return Items.REDSTONE;
            case LAPIS:
                return Items.DYE;
            case DIAMOND:
                return Items.DIAMOND;
            case EMERALD:
                return Items.EMERALD;
            case NETHERCOAL:
                return NetherCoreItems.netherCoal;
        }

        return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(IBlockState blockState, int bonus, Random random) {
        int dropCount= 1;

        Item dropped = this.getItemDropped(blockState, random, 0);
        if (dropped == Items.DYE) {
            dropCount = 4 + random.nextInt(5) + quantityDroppedWithBonus(4 + random.nextInt(5), bonus, random);
        }

        if (dropped == Items.DIAMOND || dropped == Items.EMERALD || dropped == Items.COAL) {
            dropCount = 1 + quantityDroppedWithBonus(1, bonus, random);
        }

        if (dropped == Items.REDSTONE) {
            dropCount = 4 + random.nextInt(2) + quantityDroppedWithBonus(4 + random.nextInt(2), bonus, random);
        }

        // no bonus
        if (dropped == NetherCoreItems.netherCoal) {
            dropCount = quantityDroppedWithBonus(1, bonus, random);
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

    @Override
    public boolean canCreatureSpawn(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, EntityLiving.SpawnPlacementType spawnPlacementType) {
        OreTypes meta = OreTypes.values()[this.getMetaFromState(blockAccess.getBlockState(blockPos))];
        return meta != OreTypes.NETHERCOAL;
    }

    public enum OreTypes implements IStringSerializable, EnumBlock.IEnumMeta {
        COAL,
        IRON,
        GOLD,
        REDSTONE,
        LAPIS,
        DIAMOND,
        EMERALD,
        NETHERCOAL;

        public final int meta;

        OreTypes() {
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
