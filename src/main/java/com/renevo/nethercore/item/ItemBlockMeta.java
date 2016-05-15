package com.renevo.nethercore.item;

import com.renevo.nethercore.Util;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import java.util.List;
import java.util.Locale;

// TODO: Get rid of unchecked

public class ItemBlockMeta extends ItemColored {

    protected IProperty mappingProperty;

    public ItemBlockMeta(Block block) {
        super(block, true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if(mappingProperty == null) return super.getUnlocalizedName(stack);

        IBlockState state = block.getStateFromMeta(stack.getMetadata());
        String name = state.getValue(mappingProperty).toString().toLowerCase(Locale.US);
        return super.getUnlocalizedName(stack) + "." + name;
    }

    @SuppressWarnings("unchecked")
    public static void setMappingProperty(Block block, IProperty<?> property) {
        ((ItemBlockMeta) Item.getItemFromBlock(block)).mappingProperty = property;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if(I18n.canTranslate(this.getUnlocalizedName(stack) + ".tooltip")) {
            tooltip.add(TextFormatting.GRAY.toString() + Util.translateRecursive(this.getUnlocalizedName(stack) + ".tooltip"));
        }
        else if(I18n.canTranslate(super.getUnlocalizedName(stack) + ".tooltip")) {
            tooltip.add(TextFormatting.GRAY.toString() + Util.translateRecursive(super.getUnlocalizedName(stack) + ".tooltip"));
        }
        super.addInformation(stack, playerIn, tooltip, advanced);
    }
}