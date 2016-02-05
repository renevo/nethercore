package com.renevo.nethercore;

import com.renevo.nethercore.client.CreativeTab;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public final class NetherCoreRegistry {
    private NetherCoreRegistry() {}

    public static CreativeTab tabNetherCore = new CreativeTab("NetherCore", new ItemStack(Blocks.netherrack));
}
