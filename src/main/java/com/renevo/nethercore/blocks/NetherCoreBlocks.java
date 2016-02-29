package com.renevo.nethercore.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Locale;

import slimeknights.mantle.block.EnumBlock;
import com.renevo.nethercore.Util;
import com.renevo.nethercore.item.ItemBlockMeta;

public final class NetherCoreBlocks {

    private NetherCoreBlocks() {}

    // blocks
    public static BlockNetherOre blockNetherOre;
    public static BlockCompressedNetherrack blockCompressedNetherrack;
    public static BlockNetherStone blockNetherStone;
    public static BlockNetherStairs blockNetherStoneBrickStairs;
    public static BlockNetherStairs blockNetherStoneCobbleStairs;
    public static BlockNetherStairs blockNetherStoneStairs;
    public static BlockHalfStoneSlab blockNetherHalfSlab;
    public static BlockDoubleStoneSlab blockNetherDoubleSlab;

    // item stacks
    public static ItemStack netherOreCoal;
    public static ItemStack netherOreIron;
    public static ItemStack netherOreGold;
    public static ItemStack netherOreRedstone;
    public static ItemStack netherOreLapis;
    public static ItemStack netherOreDiamond;
    public static ItemStack netherOreEmerald;

    public static ItemStack compressedNetherrackSingle;
    public static ItemStack compressedNetherrackDouble;
    public static ItemStack compressedNetherrackTriple;
    public static ItemStack compressedNetherrackQuadruple;
    public static ItemStack compressedNetherrackQuintuple;
    public static ItemStack compressedNetherrackSextuple;
    public static ItemStack compressedNetherrackSeptuple;
    public static ItemStack compressedNetherrackOctuple;

    public static ItemStack stone;
    public static ItemStack stoneCobble;
    public static ItemStack stonePaver;
    public static ItemStack stoneBrick;
    public static ItemStack stoneBrickCracked;
    public static ItemStack stoneBrickFancy;
    public static ItemStack stoneBrickSquare;
    public static ItemStack stoneRoad;
    public static ItemStack stoneCreeper;

    public static void init() {
        blockNetherOre = registerEnumBlock(new BlockNetherOre(), "ore");
        blockCompressedNetherrack = registerEnumBlock(new BlockCompressedNetherrack(), "compressed_netherrack");
        blockNetherStone = registerEnumBlock(new BlockNetherStone(), "stone");

        blockNetherStoneStairs = registerBlock(new BlockNetherStairs(blockNetherStone.getDefaultState()
                .withProperty(BlockNetherStone.TYPE, BlockNetherStone.StoneType.STONE)), "stairs_stone");
        blockNetherStoneBrickStairs = registerBlock(new BlockNetherStairs(blockNetherStone.getDefaultState()
                .withProperty(BlockNetherStone.TYPE, BlockNetherStone.StoneType.BRICK)), "stairs_stone_brick");
        blockNetherStoneCobbleStairs = registerBlock(new BlockNetherStairs(blockNetherStone.getDefaultState()
                .withProperty(BlockNetherStone.TYPE, BlockNetherStone.StoneType.COBBLE)), "stairs_stone_cobble");

        blockNetherHalfSlab = registerBlock(new BlockHalfStoneSlab(), "stone_slab");
        blockNetherDoubleSlab = registerBlock(new BlockDoubleStoneSlab(), "double_stone_slab");

        netherOreCoal = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.COAL.getMeta());
        netherOreIron = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.IRON.getMeta());
        netherOreGold = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.GOLD.getMeta());
        netherOreRedstone = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.REDSTONE.getMeta());
        netherOreLapis = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.LAPIS.getMeta());
        netherOreDiamond = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.DIAMOND.getMeta());
        netherOreEmerald = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.EMERALD.getMeta());

        compressedNetherrackSingle = new ItemStack(blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.SINGLE.getMeta());
        compressedNetherrackDouble = new ItemStack(blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.DOUBLE.getMeta());
        compressedNetherrackTriple = new ItemStack(blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.TRIPLE.getMeta());
        compressedNetherrackQuadruple = new ItemStack(blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.QUADRUPLE.getMeta());
        compressedNetherrackQuintuple = new ItemStack(blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.QUINTUPLE.getMeta());
        compressedNetherrackSextuple = new ItemStack(blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.SEXTUPLE.getMeta());
        compressedNetherrackSeptuple = new ItemStack(blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.SEPTUPLE.getMeta());
        compressedNetherrackOctuple = new ItemStack(blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.OCTUPLE.getMeta());

        stone = new ItemStack(blockNetherStone, 1, BlockNetherStone.StoneType.STONE.getMeta());
        stoneCobble = new ItemStack(blockNetherStone, 1, BlockNetherStone.StoneType.COBBLE.getMeta());
        stonePaver = new ItemStack(blockNetherStone, 1, BlockNetherStone.StoneType.PAVER.getMeta());
        stoneBrick = new ItemStack(blockNetherStone, 1, BlockNetherStone.StoneType.BRICK.getMeta());
        stoneBrickCracked = new ItemStack(blockNetherStone, 1, BlockNetherStone.StoneType.BRICK_CRACKED.getMeta());
        stoneBrickFancy = new ItemStack(blockNetherStone, 1, BlockNetherStone.StoneType.BRICK_FANCY.getMeta());
        stoneBrickSquare = new ItemStack(blockNetherStone, 1, BlockNetherStone.StoneType.BRICK_SQUARE.getMeta());
        stoneRoad = new ItemStack(blockNetherStone, 1, BlockNetherStone.StoneType.ROAD.getMeta());
        stoneCreeper = new ItemStack(blockNetherStone, 1, BlockNetherStone.StoneType.CREEPER.getMeta());
    }

    private static <T extends EnumBlock<?>> T registerEnumBlock(T block, String name) {
        registerBlock(block, ItemBlockMeta.class, name);
        ItemBlockMeta.setMappingProperty(block, block.prop);
        return block;
    }

    private static <T extends Block> T registerBlock(T block, String name) {
        block.setUnlocalizedName(Util.prefix(name));
        block.setRegistryName(Util.getResource(name));
        GameRegistry.registerBlock(block, Util.resource(name));
        return block;
    }

    protected static <T extends Block> T registerBlock(T block, Class<? extends ItemBlock> itemBlockClazz, String name, Object... itemCtorArgs) {
        if(!name.equals(name.toLowerCase(Locale.US))) {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        block.setUnlocalizedName(Util.prefix(name));
        block.setRegistryName(Util.getResource(name));
        GameRegistry.registerBlock(block, itemBlockClazz, name, itemCtorArgs);
        return block;
    }
}
