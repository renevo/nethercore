package com.renevo.nethercore;

import com.renevo.nethercore.common.Config;
import com.renevo.nethercore.item.NetherCoreItems;
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
    private NetherCoreRegistry() {
    }

    public static CreativeTab tabNetherCore = new CreativeTab("NetherCore", new ItemStack(Blocks.NETHERRACK));

    public static void registerSmelting() {
        GameRegistry.addSmelting(NetherCoreItems.netherOreCoal.copy(), new ItemStack(Items.COAL, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreItems.netherOreIron.copy(), new ItemStack(Blocks.IRON_ORE, 2), 0.2f);
        GameRegistry.addSmelting(NetherCoreItems.netherOreGold.copy(), new ItemStack(Blocks.GOLD_ORE, 2), 0.2f);
        GameRegistry.addSmelting(NetherCoreItems.netherOreRedstone.copy(), new ItemStack(Items.REDSTONE, 2), 0.2f);
        GameRegistry.addSmelting(NetherCoreItems.netherOreLapis.copy(), new ItemStack(Items.DYE, 2, EnumDyeColor.BLUE.getDyeDamage()), 0.2f);
        GameRegistry.addSmelting(NetherCoreItems.netherOreDiamond.copy(), new ItemStack(Items.DIAMOND, 2), 0.2f);
        GameRegistry.addSmelting(NetherCoreItems.netherOreEmerald.copy(), new ItemStack(Items.EMERALD, 2), 0.2f);

        GameRegistry.addSmelting(NetherCoreItems.stoneCobble, NetherCoreItems.stone, 0.2F);
        GameRegistry.addSmelting(NetherCoreItems.stoneBrick, NetherCoreItems.stoneBrickCracked, 0.0F);

        // collides with Soul Shards - The old way, need to adjust if present
        // TODO: Alternate recipe - not sure
        if (Config.enableSoulGlassRecipe) {
            GameRegistry.addSmelting(Blocks.SOUL_SAND, NetherCoreItems.soulGlass, 0.0F);
        }

        if (Config.enableSmeltedNetherStar) {
            GameRegistry.addSmelting(NetherCoreItems.compressedNetherrackOctuple, new ItemStack(Items.NETHER_STAR, 1), 10.0f);
        }
    }

    public static void registerOreDictionary() {
        OreDictionary.registerOre("oreNetherCoal", NetherCoreItems.netherOreCoal.copy());
        OreDictionary.registerOre("oreNetherIron", NetherCoreItems.netherOreIron.copy());
        OreDictionary.registerOre("oreNetherGold", NetherCoreItems.netherOreGold.copy());
        OreDictionary.registerOre("oreNetherRedstone", NetherCoreItems.netherOreRedstone.copy());
        OreDictionary.registerOre("oreNetherLapis", NetherCoreItems.netherOreLapis.copy());
        OreDictionary.registerOre("oreNetherDiamond", NetherCoreItems.netherOreDiamond.copy());
        OreDictionary.registerOre("oreNetherEmerald", NetherCoreItems.netherOreEmerald.copy());
        OreDictionary.registerOre("oreNetherNetherCoal", NetherCoreItems.netherOreNetherCoal.copy());

        OreDictionary.registerOre("cobblestone", NetherCoreItems.stoneCobble.copy());
    }

    public static void registerFuels() {
        GameRegistry.registerFuelHandler(new FuelHandler());
    }

    public static void registerRecipes() {
        ItemStack result;

        result = NetherCoreItems.stoneCobble.copy();
        result.stackSize = 1;
        GameRegistry.addRecipe(result,
                "##",
                "##",
                '#', Blocks.NETHERRACK);

        result = NetherCoreItems.stoneBrick.copy();
        result.stackSize = 4;
        GameRegistry.addRecipe(result,
                "##",
                "##",
                '#', NetherCoreItems.stone);

        result = NetherCoreItems.stoneRoad.copy();
        result.stackSize = 4;
        GameRegistry.addRecipe(result,
                "##",
                "##",
                '#', NetherCoreItems.stoneBrick);

        result = NetherCoreItems.stoneCreeper.copy();
        result.stackSize = 1;
        GameRegistry.addRecipe(result,
                "#",
                "G",
                '#', NetherCoreItems.stonePaver,
                'G', Items.GUNPOWDER);

        result = NetherCoreItems.stoneBrickSquare.copy();
        result.stackSize = 8;
        GameRegistry.addRecipe(result,
                "###",
                "# #",
                "###",
                '#', NetherCoreItems.stoneBrick);

        result = NetherCoreItems.stoneBrickFancy.copy();
        result.stackSize = 5;
        GameRegistry.addRecipe(result,
                "## ",
                " # ",
                " ##",
                '#', NetherCoreItems.stoneBrick);

        result = NetherCoreItems.stonePaver.copy();
        result.stackSize = 7;
        GameRegistry.addRecipe(result,
                "###",
                " # ",
                "###",
                '#', NetherCoreItems.stoneBrick);

        if (Config.enableNetherSporeRecipe) {
            GameRegistry.addRecipe(new ItemStack(NetherCoreItems.netherSpore),
                    "MGM",
                    "WSW",
                    "BGB",
                    'M', Items.MAGMA_CREAM,
                    'G', Items.GHAST_TEAR,
                    'S', Items.WHEAT_SEEDS,
                    'B', Items.BLAZE_POWDER,
                    'W', Items.NETHER_WART);
        }

        if (Config.enableNetherFurnaceRecipe) {
            GameRegistry.addRecipe(new ItemStack(NetherCoreBlocks.blockNetherFurnace),
                    "CCC",
                    "CMC",
                    "CCC",
                    'C', NetherCoreItems.stoneCobble,
                    'M', Items.MAGMA_CREAM);
        }

        result = NetherCoreItems.netherRod.copy();
        result.stackSize = 4;
        GameRegistry.addRecipe(result,
                "R",
                "B",
                'R', Items.BLAZE_ROD,
                'B', Blocks.NETHER_BRICK);

        addCompressedRecipe(new ItemStack(NetherCoreItems.netherCoal), NetherCoreItems.netherCoalBlock);

        if (Config.enableCompressedNetherrackRecipes) {
            addCompressedRecipe(new ItemStack(Blocks.NETHERRACK), NetherCoreItems.compressedNetherrackSingle);
            addCompressedRecipe(NetherCoreItems.compressedNetherrackSingle, NetherCoreItems.compressedNetherrackDouble);
            addCompressedRecipe(NetherCoreItems.compressedNetherrackDouble, NetherCoreItems.compressedNetherrackTriple);
            addCompressedRecipe(NetherCoreItems.compressedNetherrackTriple, NetherCoreItems.compressedNetherrackQuadruple);
            addCompressedRecipe(NetherCoreItems.compressedNetherrackQuadruple, NetherCoreItems.compressedNetherrackQuintuple);
            addCompressedRecipe(NetherCoreItems.compressedNetherrackQuintuple, NetherCoreItems.compressedNetherrackSextuple);
            addCompressedRecipe(NetherCoreItems.compressedNetherrackSextuple, NetherCoreItems.compressedNetherrackSeptuple);
            addCompressedRecipe(NetherCoreItems.compressedNetherrackSeptuple, NetherCoreItems.compressedNetherrackOctuple);
        }

        addSlabRecipe(NetherCoreItems.stone, NetherCoreItems.slabStone);
        addSlabRecipe(NetherCoreItems.stoneCobble, NetherCoreItems.slabStoneCobble);
        addSlabRecipe(NetherCoreItems.stoneBrick, NetherCoreItems.slabStoneBrick);
        addSlabRecipe(NetherCoreItems.stoneRoad, NetherCoreItems.slabStoneRoad);

        addStairRecipe(NetherCoreItems.stone, NetherCoreItems.stairsStone);
        addStairRecipe(NetherCoreItems.stoneCobble, NetherCoreItems.stairsStoneCobble);
        addStairRecipe(NetherCoreItems.stoneBrick, NetherCoreItems.stairsStoneBrick);

        addWallRecipe(NetherCoreItems.stone, NetherCoreItems.wallStone, false);
        addWallRecipe(NetherCoreItems.stoneCobble, NetherCoreItems.wallStoneCobble, false);
        addWallRecipe(new ItemStack(Blocks.NETHER_BRICK), NetherCoreItems.wallNetherBrick, true);
        addWallRecipe(new ItemStack(Blocks.field_189879_dh /*Red Nether Brick */), NetherCoreItems.wallRedNetherBrick, false);
    }

    public static void registerIntegrations() {
        TinkersIntegration.addTinkersSmelting(NetherCoreItems.netherOreIron, FluidRegistry.getFluid("iron"), TinkersIntegration.VALUE_NetherOre);
        TinkersIntegration.addTinkersSmelting(NetherCoreItems.netherOreGold, FluidRegistry.getFluid("gold"), TinkersIntegration.VALUE_NetherOre);
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
