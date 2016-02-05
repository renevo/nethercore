package com.renevo.nethercore.blocks;

import com.renevo.nethercore.Constants;
import com.renevo.nethercore.item.ItemBlockMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Locale;

public final class NetherCoreBlocks {

    private NetherCoreBlocks() {}

    // blocks
    public static BlockNetherOre blockNetherOre;

    // item stacks
    public static ItemStack netherOreCoal;
    public static ItemStack netherOreIron;
    public static ItemStack netherOreGold;
    public static ItemStack netherOreRedstone;
    public static ItemStack netherOreLapis;
    public static ItemStack netherOreDiamond;
    public static ItemStack netherOreEmerald;

    public static void preInit(FMLPreInitializationEvent event) {
        blockNetherOre = registerEnumBlock(new BlockNetherOre(), "ore");

        netherOreCoal = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.COAL.getMeta());
        netherOreIron = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.IRON.getMeta());
        netherOreGold = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.GOLD.getMeta());
        netherOreRedstone = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.REDSTONE.getMeta());
        netherOreLapis = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.LAPIS.getMeta());
        netherOreDiamond = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.DIAMOND.getMeta());
        netherOreEmerald = new ItemStack(blockNetherOre, 1, BlockNetherOre.OreTypes.EMERALD.getMeta());
    }

    private static <T extends EnumBlock<?>> T registerEnumBlock(T block, String name) {
        registerBlock(block, ItemBlockMeta.class, name);
        ItemBlockMeta.setMappingProperty(block, block.prop);
        return block;
    }

    private static <T extends Block> T registerBlock(T block, String name) {
        block.setUnlocalizedName(Constants.prefix(name));
        block.setRegistryName(Constants.getResource(name));
        GameRegistry.registerBlock(block, Constants.resource(name));
        return block;
    }

    protected static <T extends Block> T registerBlock(T block, Class<? extends ItemBlock> itemBlockClazz, String name, Object... itemCtorArgs) {
        if(!name.equals(name.toLowerCase(Locale.US))) {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        block.setUnlocalizedName(Constants.prefix(name));
        block.setRegistryName(Constants.getResource(name));
        GameRegistry.registerBlock(block, itemBlockClazz, name, itemCtorArgs);
        return block;
    }
}
