package com.renevo.nethercore;

import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.client.CreativeTab;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class NetherCoreRegistry {
    private NetherCoreRegistry() {}

    public static CreativeTab tabNetherCore = new CreativeTab("NetherCore", new ItemStack(Blocks.netherrack));

    public static void addSmelting() {
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreCoal.getItem(), new ItemStack(Blocks.coal_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreIron.getItem(), new ItemStack(Blocks.iron_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreGold.getItem(), new ItemStack(Blocks.gold_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreRedstone.getItem(), new ItemStack(Blocks.redstone_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreLapis.getItem(), new ItemStack(Blocks.lapis_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreDiamond.getItem(), new ItemStack(Blocks.diamond_ore, 2), 0.0f);
        GameRegistry.addSmelting(NetherCoreBlocks.netherOreEmerald.getItem(), new ItemStack(Blocks.emerald_ore, 2), 0.0f);

    }
}
