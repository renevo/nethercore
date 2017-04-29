package com.renevo.nethercore.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class EnumBlockWall<E extends Enum<E> & EnumBlockWall.IEnumMeta & IStringSerializable> extends BlockWall {
    private final E[] values;
    public final PropertyEnum<E> prop;
    private static PropertyEnum<?> tmp;

    public EnumBlockWall(Block block, PropertyEnum<E> prop, Class<E> clazz) {
        super(preInit(block, prop));
        this.prop = prop;
        values = clazz.getEnumConstants();
    }

    private static Block preInit(Block block, PropertyEnum<?> property) {
        tmp = property;
        return block;
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        for (E type : values) {
            list.add(new ItemStack(this, 1, type.getMeta()));
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(prop, fromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(prop).getMeta();
    }

    @Override
    public IBlockState getActualState(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
        return super.getActualState(blockState, blockAccess, blockPos).withProperty(prop, fromMeta(getMetaFromState(blockState)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT, UP, NORTH, EAST, WEST, SOUTH, prop == null ? tmp : prop);
    }

    protected E fromMeta(int meta) {
        if (meta < 0 || meta >= values.length) {
            meta = 0;
        }

        return values[meta];
    }

    public interface IEnumMeta {
        int getMeta();
    }
}
