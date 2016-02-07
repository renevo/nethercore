package com.renevo.nethercore;

import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.client.CreativeTab;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public final class NetherCoreRegistry {
    private NetherCoreRegistry() {}

    public static CreativeTab tabNetherCore = new CreativeTab("NetherCore", new ItemStack(Blocks.netherrack));

    public static void addSmelting() {
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreCoal.copy(), new ItemStack(Blocks.coal_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreIron.copy(), new ItemStack(Blocks.iron_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreGold.copy(), new ItemStack(Blocks.gold_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreRedstone.copy(), new ItemStack(Blocks.redstone_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreLapis.copy(), new ItemStack(Blocks.lapis_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreDiamond.copy(), new ItemStack(Blocks.diamond_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreEmerald.copy(), new ItemStack(Blocks.emerald_ore, 2), 0.0f);

    }

    public static void addOreDictionary() {
        OreDictionary.registerOre("oreNetherCoal", NetherCoreBlocks.netherOreCoal.copy());
        OreDictionary.registerOre("oreNetherIron", NetherCoreBlocks.netherOreIron.copy());
        OreDictionary.registerOre("oreNetherGold", NetherCoreBlocks.netherOreGold.copy());
        OreDictionary.registerOre("oreNetherRedstone", NetherCoreBlocks.netherOreRedstone.copy());
        OreDictionary.registerOre("oreNetherLapis", NetherCoreBlocks.netherOreLapis.copy());
        OreDictionary.registerOre("oreNetherDiamond", NetherCoreBlocks.netherOreDiamond.copy());
        OreDictionary.registerOre("oreNetherEmerald", NetherCoreBlocks.netherOreEmerald.copy());
    }
}
