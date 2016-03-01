package com.renevo.nethercore.item;

import com.renevo.nethercore.Util;
import com.renevo.nethercore.blocks.BlockCompressedNetherrack;
import com.renevo.nethercore.blocks.BlockNetherOre;
import com.renevo.nethercore.blocks.BlockNetherStone;
import com.renevo.nethercore.blocks.NetherCoreBlocks;
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

    // TODO: Add the other item stacks for the blocks, and stop using new ItemStack in things

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
    }
}
