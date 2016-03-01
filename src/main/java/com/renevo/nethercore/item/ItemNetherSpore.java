package com.renevo.nethercore.item;

import com.renevo.nethercore.NetherCoreRegistry;
import com.renevo.nethercore.blocks.NetherCoreBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemNetherSpore extends ItemMeta {

    public ItemNetherSpore() {
        this.setMaxDamage(0);
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float p_onItemUse_6_, float p_onItemUse_7_, float p_onItemUse_8_) {
        if(enumFacing != EnumFacing.UP) {
            return false;
        } else if(!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        } else if(world.getBlockState(blockPos).getBlock() == Blocks.netherrack && world.provider.doesWaterVaporize() && world.isAirBlock(blockPos.up())) {
            world.setBlockState(blockPos, NetherCoreBlocks.blockNetherGrass.getDefaultState());
            world.setBlockState(blockPos.up(), Blocks.fire.getDefaultState());

            --itemStack.stackSize;
            return true;
        } else {
            return false;
        }
    }
}
