package com.renevo.nethercore.blocks;

import com.renevo.nethercore.item.ItemStoneSlab;
import com.renevo.nethercore.tileentity.TileEntityNetherFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Locale;

import slimeknights.mantle.block.EnumBlock;
import com.renevo.nethercore.Util;
import com.renevo.nethercore.item.ItemBlockMeta;

public final class NetherCoreBlocks {

    private NetherCoreBlocks() {
    }

    public static final Block.SoundType soundTypeNetherStone = new Block.SoundType("stone", 1.0F, 1.0F);

    // blocks
    public static BlockNetherOre blockNetherOre;
    public static BlockCompressedNetherrack blockCompressedNetherrack;
    public static BlockNetherStone blockNetherStone;
    public static BlockNetherStairs blockNetherStoneBrickStairs;
    public static BlockNetherStairs blockNetherStoneCobbleStairs;
    public static BlockNetherStairs blockNetherStoneStairs;
    public static BlockStoneSlab blockNetherHalfSlab;
    public static BlockStoneSlab blockNetherDoubleSlab;
    public static BlockStoneWall blockNetherStoneWall;
    public static BlockNetherGrass blockNetherGrass;

    public static BlockNetherFurnace blockNetherFurnace;
    public static BlockNetherFurnace blockNetherFurnaceLit;

    public static BlockLightRod blockLightRod;

    public static void init() {
        blockNetherOre = registerEnumBlock(new BlockNetherOre(), "ore");
        blockCompressedNetherrack = registerEnumBlock(new BlockCompressedNetherrack(), "compressed_netherrack");
        blockNetherStone = registerEnumBlock(new BlockNetherStone(), "stone");

        blockNetherStoneStairs = registerBlock(new BlockNetherStairs(blockNetherStone.getDefaultState()
                .withProperty(BlockNetherStone.TYPE, BlockNetherStone.StoneType.STONE)), "stairs_stone");
        blockNetherStoneCobbleStairs = registerBlock(new BlockNetherStairs(blockNetherStone.getDefaultState()
                .withProperty(BlockNetherStone.TYPE, BlockNetherStone.StoneType.COBBLE)), "stairs_stone_cobble");
        blockNetherStoneBrickStairs = registerBlock(new BlockNetherStairs(blockNetherStone.getDefaultState()
                .withProperty(BlockNetherStone.TYPE, BlockNetherStone.StoneType.BRICK)), "stairs_stone_brick");

        BlockHalfStoneSlab halfStoneSlab = new BlockHalfStoneSlab();
        BlockDoubleStoneSlab doubleStoneSlab = new BlockDoubleStoneSlab();
        blockNetherHalfSlab = registerEnumBlock(halfStoneSlab, halfStoneSlab, doubleStoneSlab, "slab_half_stone");
        blockNetherDoubleSlab = registerEnumBlock(doubleStoneSlab, halfStoneSlab, doubleStoneSlab, "slab_double_stone");

        blockNetherStoneWall = registerEnumBlock(new BlockStoneWall(), "wall_stone");

        blockNetherGrass = registerBlock(new BlockNetherGrass(), "nether_grass");

        blockNetherFurnace = registerBlock(new BlockNetherFurnace(false), "nether_furnace");
        blockNetherFurnaceLit = registerBlock(new BlockNetherFurnace(true), "nether_furnace_lit");

        blockLightRod = registerBlock(new BlockLightRod(), "nether_rod");

        GameRegistry.registerTileEntity(TileEntityNetherFurnace.class, "nether_furnace");
    }

    private static <T extends EnumBlock<?>> T registerEnumBlock(T block, String name) {
        registerBlock(block, ItemBlockMeta.class, name);
        ItemBlockMeta.setMappingProperty(block, block.prop);
        return block;
    }

    private static <T extends EnumBlockSlab<?>> T registerEnumBlock(T block, T halfBlock, T fullBlock, String name) {
        if (block == halfBlock) {
            registerBlock(block, ItemStoneSlab.class, name, halfBlock, fullBlock);
        } else {
            registerBlock(block, ItemBlockMeta.class, name);
            ItemBlockMeta.setMappingProperty(block, block.prop);
        }

        return block;
    }

    private static <T extends EnumBlockWall<?>> T registerEnumBlock(T block, String name) {
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
        if (!name.equals(name.toLowerCase(Locale.US))) {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        block.setUnlocalizedName(Util.prefix(name));
        block.setRegistryName(Util.getResource(name));
        GameRegistry.registerBlock(block, itemBlockClazz, name, itemCtorArgs);
        return block;
    }
}
