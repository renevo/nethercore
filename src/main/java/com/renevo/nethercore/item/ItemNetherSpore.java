package com.renevo.nethercore.item;

import com.renevo.nethercore.NetherCoreRegistry;
import com.renevo.nethercore.blocks.NetherCoreBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemNetherSpore extends ItemMeta {

    public ItemNetherSpore() {
        this.setMaxDamage(0);
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY,
        float hitZ) {
        ItemStack itemStack = player.getActiveItemStack();
        if(facing != EnumFacing.UP) {
            return EnumActionResult.PASS;
        } else if(!player.canPlayerEdit(pos.offset(facing), facing, itemStack)) {
            return EnumActionResult.PASS;
        } else if(world.getBlockState(pos).getBlock() == Blocks.NETHERRACK && world.provider.doesWaterVaporize() && world.isAirBlock(pos.up())) {
            world.setBlockState(pos, NetherCoreBlocks.blockNetherGrass.getDefaultState());
            world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());

            itemStack.shrink(1);
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.PASS;
        }
    }
}
