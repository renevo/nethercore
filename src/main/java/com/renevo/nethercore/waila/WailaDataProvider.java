package com.renevo.nethercore.waila;

import com.renevo.nethercore.blocks.BlockLightRod;
import com.renevo.nethercore.blocks.BlockNetherFurnace;
import com.renevo.nethercore.blocks.NetherCoreBlocks;
import com.renevo.nethercore.item.NetherCoreItems;
import com.renevo.nethercore.tileentity.TileEntityNetherFurnace;
import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public final class WailaDataProvider implements IWailaDataProvider {

    public static void callbackRegister(IWailaRegistrar register) {
        WailaDataProvider instance = new WailaDataProvider();

        register.registerStackProvider(instance, BlockLightRod.class);
        register.registerBodyProvider(instance, BlockNetherFurnace.class);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getBlock() == NetherCoreBlocks.blockLightRod) {
            return NetherCoreItems.netherRod;
        }

        return new ItemStack(accessor.getBlockState().getBlock());
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getBlock() instanceof BlockNetherFurnace) {
            TileEntity te = accessor.getWorld().getTileEntity(accessor.getPosition());
            if (te instanceof TileEntityNetherFurnace) {
                TileEntityNetherFurnace furnace = (TileEntityNetherFurnace)te;
                currenttip.add(SpecialChars.ITALIC + Integer.toString(getCookProgressScaled(furnace)) + "% Complete");
            }
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        return tag;
    }

    private int getCookProgressScaled(TileEntityNetherFurnace tileFurnace) {
        int cookTime = tileFurnace.getField(2);
        int totalCookTime = tileFurnace.getField(3);
        return totalCookTime != 0 && cookTime != 0?cookTime  / totalCookTime:0;
    }
}
