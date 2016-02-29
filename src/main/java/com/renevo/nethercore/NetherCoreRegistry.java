package com.renevo.nethercore;

import com.renevo.nethercore.blocks.BlockNetherStone;
import com.renevo.nethercore.blocks.BlockStoneSlab;
import com.renevo.nethercore.blocks.BlockStoneWall;
import com.renevo.nethercore.tconstruct.TinkersIntegration;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import slimeknights.mantle.client.CreativeTab;
import com.renevo.nethercore.blocks.NetherCoreBlocks;

public final class NetherCoreRegistry {
    private NetherCoreRegistry() {}

    public static CreativeTab tabNetherCore = new CreativeTab("NetherCore", new ItemStack(Blocks.netherrack));

    public static void registerSmelting() {
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreCoal.copy(), new ItemStack(Items.coal, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreIron.copy(), new ItemStack(Blocks.iron_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreGold.copy(), new ItemStack(Blocks.gold_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreRedstone.copy(), new ItemStack(Items.redstone, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreLapis.copy(), new ItemStack(Items.dye, EnumDyeColor.BLUE.getDyeDamage(), 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreDiamond.copy(), new ItemStack(Items.diamond, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreEmerald.copy(), new ItemStack(Items.emerald, 2), 0.0f);

        GameRegistry.addSmelting(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.COBBLE.getMeta()), new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.STONE.getMeta()), 0.2F);
        GameRegistry.addSmelting(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK.getMeta()), new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK_CRACKED.getMeta()), 0.0F);

        // TODO: option to disable this
        GameRegistry.addSmelting(NetherCoreBlocks.compressedNetherrackOctuple, new ItemStack(Items.nether_star, 1), 10.0f);
    }

    public static void registerOreDictionary() {
        OreDictionary.registerOre("oreNetherCoal", NetherCoreBlocks.netherOreCoal.copy());
        OreDictionary.registerOre("oreNetherIron", NetherCoreBlocks.netherOreIron.copy());
        OreDictionary.registerOre("oreNetherGold", NetherCoreBlocks.netherOreGold.copy());
        OreDictionary.registerOre("oreNetherRedstone", NetherCoreBlocks.netherOreRedstone.copy());
        OreDictionary.registerOre("oreNetherLapis", NetherCoreBlocks.netherOreLapis.copy());
        OreDictionary.registerOre("oreNetherDiamond", NetherCoreBlocks.netherOreDiamond.copy());
        OreDictionary.registerOre("oreNetherEmerald", NetherCoreBlocks.netherOreEmerald.copy());
    }

    public static void registerRecipes() {
        GameRegistry.addRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.COBBLE.getMeta()), "##", "##", '#', Blocks.netherrack);
        GameRegistry.addRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 4, BlockNetherStone.StoneType.BRICK.getMeta()), "##", "##", '#', new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.STONE.getMeta()));
        GameRegistry.addRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 4, BlockNetherStone.StoneType.ROAD.getMeta()), "##", "##", '#', new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK.getMeta()));
        GameRegistry.addRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.CREEPER.getMeta()), "#", "G", '#', new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.PAVER.getMeta()), 'G', Items.gunpowder);
        GameRegistry.addRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 8, BlockNetherStone.StoneType.BRICK_SQUARE.getMeta()), "###", "# #", "###", '#', new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK.getMeta()));
        GameRegistry.addRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 5, BlockNetherStone.StoneType.BRICK_FANCY.getMeta()), "## ", " # ", " ##", '#', new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK.getMeta()));
        GameRegistry.addRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 7, BlockNetherStone.StoneType.PAVER.getMeta()), "###", " # ", "###", '#', new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK.getMeta()));

        addCompressedRecipe(new ItemStack(Blocks.netherrack), NetherCoreBlocks.compressedNetherrackSingle);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackSingle, NetherCoreBlocks.compressedNetherrackDouble);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackDouble, NetherCoreBlocks.compressedNetherrackTriple);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackTriple, NetherCoreBlocks.compressedNetherrackQuadruple);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackQuadruple, NetherCoreBlocks.compressedNetherrackQuintuple);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackQuintuple, NetherCoreBlocks.compressedNetherrackSextuple);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackSextuple, NetherCoreBlocks.compressedNetherrackSeptuple);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackSeptuple, NetherCoreBlocks.compressedNetherrackOctuple);

        addSlabRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.STONE.getMeta()), new ItemStack(NetherCoreBlocks.blockNetherHalfSlab, 1, BlockStoneSlab.SlabType.STONE.getMeta()));
        addSlabRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.COBBLE.getMeta()), new ItemStack(NetherCoreBlocks.blockNetherHalfSlab, 1, BlockStoneSlab.SlabType.COBBLESTONE.getMeta()));
        addSlabRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK.getMeta()), new ItemStack(NetherCoreBlocks.blockNetherHalfSlab, 1, BlockStoneSlab.SlabType.STONEBRICK.getMeta()));
        addSlabRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.ROAD.getMeta()), new ItemStack(NetherCoreBlocks.blockNetherHalfSlab, 1, BlockStoneSlab.SlabType.ROAD.getMeta()));

        addStairRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.STONE.getMeta()), new ItemStack(NetherCoreBlocks.blockNetherStoneStairs));
        addStairRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.COBBLE.getMeta()), new ItemStack(NetherCoreBlocks.blockNetherStoneCobbleStairs));
        addStairRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.BRICK.getMeta()), new ItemStack(NetherCoreBlocks.blockNetherStoneBrickStairs));

        addWallRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.STONE.getMeta()), new ItemStack(NetherCoreBlocks.blockNetherStoneWall, 1, BlockStoneWall.WallType.STONE.getMeta()), false);
        addWallRecipe(new ItemStack(NetherCoreBlocks.blockNetherStone, 1, BlockNetherStone.StoneType.COBBLE.getMeta()), new ItemStack(NetherCoreBlocks.blockNetherStoneWall, 1, BlockStoneWall.WallType.COBBLESTONE.getMeta()), false);
        addWallRecipe(new ItemStack(Blocks.nether_brick), new ItemStack(NetherCoreBlocks.blockNetherStoneWall, 1, BlockStoneWall.WallType.BRICK.getMeta()), true);
    }

    public static void registerIntegrations() {
        TinkersIntegration.addTinkersSmelting(NetherCoreBlocks.netherOreIron, FluidRegistry.getFluid("iron"), TinkersIntegration.VALUE_NetherOre);
        TinkersIntegration.addTinkersSmelting(NetherCoreBlocks.netherOreGold, FluidRegistry.getFluid("gold"), TinkersIntegration.VALUE_NetherOre);
    }

    private static void addCompressedRecipe(ItemStack input, ItemStack output) {
        ItemStack decompress = input.copy();
        decompress.stackSize = 9;
        GameRegistry.addRecipe(output.copy(), "###", "###", "###", '#', input.copy());
        GameRegistry.addRecipe(decompress, "#", '#', output.copy());
    }

    private static void addWallRecipe(ItemStack input, ItemStack output, boolean explicit) {
        ItemStack wall = output.copy();
        wall.stackSize = 6;
        ItemStack block = input.copy();
        block.stackSize = 1;
        if (!explicit) {
            GameRegistry.addRecipe(wall.copy(), "###", "###", '#', block.copy());
        } else {
            GameRegistry.addRecipe(wall.copy(), "###", "###", "   ", '#', block.copy());
        }
    }

    private static void addStairRecipe(ItemStack input, ItemStack output) {
        ItemStack stairs = output.copy();
        stairs.stackSize = 4;
        ItemStack block = input.copy();
        block.stackSize = 1;
        GameRegistry.addRecipe(stairs.copy(), "#  ", "## ", "###", '#', block.copy());
    }

    private static void addSlabRecipe(ItemStack input, ItemStack output) {
        ItemStack slabs = output.copy();
        slabs.stackSize = 6;
        ItemStack block = input.copy();
        block.stackSize = 1;
        GameRegistry.addRecipe(slabs.copy(), "###", '#', block.copy());
    }
}
