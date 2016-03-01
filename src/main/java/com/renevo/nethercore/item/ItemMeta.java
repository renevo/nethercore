package com.renevo.nethercore.item;

import com.renevo.nethercore.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class ItemMeta extends Item {

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if(StatCollector.canTranslate(this.getUnlocalizedName(stack) + ".tooltip")) {
            tooltip.add(EnumChatFormatting.GRAY.toString() + Util.translateRecursive(this.getUnlocalizedName(stack) + ".tooltip"));
        }
        else if(StatCollector.canTranslate(super.getUnlocalizedName(stack) + ".tooltip")) {
            tooltip.add(EnumChatFormatting.GRAY.toString() + Util.translateRecursive(super.getUnlocalizedName(stack) + ".tooltip"));
        }
        super.addInformation(stack, playerIn, tooltip, advanced);
    }
}
