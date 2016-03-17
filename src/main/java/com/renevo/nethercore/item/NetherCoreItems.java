package com.renevo.nethercore.item;

import com.renevo.nethercore.Util;
import com.renevo.nethercore.blocks.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class NetherCoreItems {
    private NetherCoreItems() {}

    public static Item netherSpore;

    // item block stacks
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

    public static ItemStack slabStone;
    public static ItemStack slabStoneCobble;
    public static ItemStack slabStoneBrick;
    public static ItemStack slabStoneRoad;

    public static ItemStack stairsStone;
    public static ItemStack stairsStoneCobble;
    public static ItemStack stairsStoneBrick;

    public static ItemStack wallStone;
    public static ItemStack wallStoneCobble;
    public static ItemStack wallNetherBrick;

    public static ItemStack netherFurnace;

    public static ItemStack netherRod;

    public static void init() {
         GameRegistry.registerItem(netherSpore = new ItemNetherSpore().setUnlocalizedName(Util.prefix("nether_spore")), "nether_spore");

        // item blocks
        netherOreCoal = new ItemStack(NetherCoreBlocks.blockNetherOre, 1, BlockNetherOre.OreTypes.COAL.getMeta());
        netherOreIron = new ItemStack(NetherCoreBlocks.blockNetherOre, 1, BlockNetherOre.OreTypes.IRON.getMeta());
        netherOreGold = new ItemStack(NetherCoreBlocks.blockNetherOre, 1, BlockNetherOre.OreTypes.GOLD.getMeta());
        netherOreRedstone = new ItemStack(NetherCoreBlocks.blockNetherOre, 1, BlockNetherOre.OreTypes.REDSTONE.getMeta());
        netherOreLapis = new ItemStack(NetherCoreBlocks.blockNetherOre, 1, BlockNetherOre.OreTypes.LAPIS.getMeta());
        netherOreDiamond = new ItemStack(NetherCoreBlocks.blockNetherOre, 1, BlockNetherOre.OreTypes.DIAMOND.getMeta());
        netherOreEmerald = new ItemStack(NetherCoreBlocks.blockNetherOre, 1, BlockNetherOre.OreTypes.EMERALD.getMeta());

        compressedNetherrackSingle = new ItemStack(NetherCoreBlocks.blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.SINGLE.getMeta());
        compressedNetherrackDouble = new ItemStack(NetherCoreBlocks.blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.DOUBLE.getMeta());
        compressedNetherrackTriple = new ItemStack(NetherCoreBlocks.blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.TRIPLE.getMeta());
        compressedNetherrackQuadruple = new ItemStack(NetherCoreBlocks.blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.QUADRUPLE.getMeta());
        compressedNetherrackQuintuple = new ItemStack(NetherCoreBlocks.blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.QUINTUPLE.getMeta());
        compressedNetherrackSextuple = new ItemStack(NetherCoreBlocks.blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.SEXTUPLE.getMeta());
        compressedNetherrackSeptuple = new ItemStack(NetherCoreBlocks.blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.SEPTUPLE.getMeta());
        compressedNetherrackOctuple = new ItemStack(NetherCoreBlocks.blockCompressedNetherrack, 1, BlockCompressedNetherrack.CompressionDepth.OCTUPLE.getMeta());

        stone = new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.STONE.getMeta());
        stoneCobble = new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.COBBLE.getMeta());
        stonePaver = new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.PAVER.getMeta());
        stoneBrick = new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK.getMeta());
        stoneBrickCracked = new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK_CRACKED.getMeta());
        stoneBrickFancy = new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK_FANCY.getMeta());
        stoneBrickSquare = new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK_SQUARE.getMeta());
        stoneRoad = new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.ROAD.getMeta());
        stoneCreeper = new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.CREEPER.getMeta());

        slabStone = new ItemStack(NetherCoreBlocks.blockNetherHalfSlab, 1, BlockStoneSlab.SlabType.STONE.getMeta());
        slabStoneCobble = new ItemStack(NetherCoreBlocks.blockNetherHalfSlab, 1, BlockStoneSlab.SlabType.COBBLESTONE.getMeta());
        slabStoneBrick = new ItemStack(NetherCoreBlocks.blockNetherHalfSlab, 1, BlockStoneSlab.SlabType.STONEBRICK.getMeta());
        slabStoneRoad = new ItemStack(NetherCoreBlocks.blockNetherHalfSlab, 1, BlockStoneSlab.SlabType.ROAD.getMeta());

        stairsStone = new ItemStack(NetherCoreBlocks.blockNetherStoneStairs);
        stairsStoneCobble = new ItemStack(NetherCoreBlocks.blockNetherStoneCobbleStairs);
        stairsStoneBrick = new ItemStack(NetherCoreBlocks.blockNetherStoneBrickStairs);

        wallStone = new ItemStack(NetherCoreBlocks.blockNetherStoneWall, 1, BlockStoneWall.WallType.STONE.getMeta());
        wallStoneCobble = new ItemStack(NetherCoreBlocks.blockNetherStoneWall, 1, BlockStoneWall.WallType.COBBLESTONE.getMeta());
        wallNetherBrick = new ItemStack(NetherCoreBlocks.blockNetherStoneWall, 1, BlockStoneWall.WallType.BRICK.getMeta());

        netherFurnace = new ItemStack(NetherCoreBlocks.blockNetherFurnace);

        netherRod = new ItemStack(NetherCoreBlocks.blockLightRod);
    }
}
