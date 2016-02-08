package com.renevo.nethercore;

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
        addCompressedRecipe(new ItemStack(Blocks.netherrack), NetherCoreBlocks.compressedNetherrackSingle);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackSingle, NetherCoreBlocks.compressedNetherrackDouble);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackDouble, NetherCoreBlocks.compressedNetherrackTriple);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackTriple, NetherCoreBlocks.compressedNetherrackQuadruple);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackQuadruple, NetherCoreBlocks.compressedNetherrackQuintuple);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackQuintuple, NetherCoreBlocks.compressedNetherrackSextuple);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackSextuple, NetherCoreBlocks.compressedNetherrackSeptuple);
        addCompressedRecipe(NetherCoreBlocks.compressedNetherrackSeptuple, NetherCoreBlocks.compressedNetherrackOctuple);
    }

    public static void registerIntegrations() {
        TinkersIntegration.addTinkersSmelting(NetherCoreBlocks.netherOreIron, NetherCoreBlocks.netherOreIron, FluidRegistry.getFluidStack("iron", TinkersIntegration.VALUE_NetherOre), 1000);
        TinkersIntegration.addTinkersSmelting(NetherCoreBlocks.netherOreGold, NetherCoreBlocks.netherOreGold, FluidRegistry.getFluidStack("gold", TinkersIntegration.VALUE_NetherOre), 1000);
    }

    private static void addCompressedRecipe(ItemStack input, ItemStack output) {
        ItemStack decompress = input.copy();
        decompress.stackSize = 9;
        GameRegistry.addRecipe(output.copy(), "###", "###", "###", '#', input.copy());
        GameRegistry.addRecipe(decompress, "#", '#', output.copy());
    }
}
