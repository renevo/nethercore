package com.renevo.nethercore.common;

import com.renevo.nethercore.Util;
import com.renevo.nethercore.blocks.BlockStoneSlab;
import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.client.ItemBlockModelSetter;
import com.renevo.nethercore.item.NetherCoreItems;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;

import java.util.Locale;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    public void init() {
        super.init();

    }

    @Override
    public void postInit() {
        super.postInit();

    }

    @Override
    public void registerModels() {
        MinecraftForge.EVENT_BUS.register(new ItemBlockModelSetter());

        // individual items
        Item itemToAdd;

        // stairs
        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockNetherStoneStairs);
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("stairs_stone"), "inventory"));
        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockNetherStoneBrickStairs);
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("stairs_stone_brick"), "inventory"));
        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockNetherStoneCobbleStairs);
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("stairs_stone_cobble"), "inventory"));

        // slabs
        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockNetherHalfSlab);
        for (BlockStoneSlab.SlabType t : BlockStoneSlab.SlabType.values()) {
            ModelLoader.setCustomModelResourceLocation(itemToAdd, t.getMeta(), new ModelResourceLocation(Util.getResource("slab_half_stone"), "half=bottom,variant=" + t.getName().toLowerCase(Locale.US)));
        }

        // grass
        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockNetherGrass);
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("nether_grass"), "inventory"));


        // items
        itemToAdd = NetherCoreItems.netherSpore;
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("nether_spore"), "inventory"));
    }
}
