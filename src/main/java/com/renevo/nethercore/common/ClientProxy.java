package com.renevo.nethercore.common;

import com.renevo.nethercore.Util;
import com.renevo.nethercore.blocks.BlockStoneSlab;
import com.renevo.nethercore.blocks.BlockStoneWall;
import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.item.NetherCoreItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import slimeknights.mantle.item.ItemBlockMeta;

import java.util.Locale;

@SuppressWarnings("unused")
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
        // individual items
        Item itemToAdd;

        // enum blocks
        registerItemBlockMeta(NetherCoreBlocks.blockCompressedNetherrack);
        registerItemBlockMeta(NetherCoreBlocks.blockNetherOre);
        registerItemBlockMeta(NetherCoreBlocks.blockNetherStone);

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

        // walls
        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockNetherStoneWall);
        for (BlockStoneWall.WallType wall : BlockStoneWall.WallType.values()) {
            String variantName = "inventory_" + wall.getName();
            ModelLoader.setCustomModelResourceLocation(itemToAdd, wall.getMeta(), new ModelResourceLocation(itemToAdd.getRegistryName(), variantName));
        }

        // grass
        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockNetherGrass);
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("nether_grass"), "inventory"));

        // furnace
        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockNetherFurnace);
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("nether_furnace"), "inventory"));

        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockLightRod);
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("nether_rod"), "inventory"));

        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockSoulGlass);
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("soul_glass"), "inventory"));

        itemToAdd = Item.getItemFromBlock(NetherCoreBlocks.blockNetherCoal);
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("nether_coal_block"), "inventory"));

        // items
        itemToAdd = NetherCoreItems.netherSpore;
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("nether_spore"), "inventory"));

        itemToAdd = NetherCoreItems.netherCoal;
        ModelLoader.setCustomModelResourceLocation(itemToAdd, 0, new ModelResourceLocation(Util.getResource("nether_coal"), "inventory"));
    }

    private void registerItemBlockMeta(Block block) {
        if (block == null) {
            return;
        }

        ((ItemBlockMeta)Item.getItemFromBlock(block)).registerItemModels();
    }
}
