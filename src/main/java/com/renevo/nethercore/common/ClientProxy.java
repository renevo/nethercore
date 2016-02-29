package com.renevo.nethercore.common;

import com.renevo.nethercore.Util;
import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.client.ItemBlockModelSetter;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;

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
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("stairs_stone"), "facing=west,half=bottom,shape=straight"));
        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockNetherStoneBrickStairs);
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("stairs_stone_brick"), "facing=west,half=bottom,shape=straight"));
        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockNetherStoneCobbleStairs);
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("stairs_stone_cobble"), "facing=west,half=bottom,shape=straight"));


    }
}
