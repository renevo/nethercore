package com.renevo.nethercore.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public abstract class EnumBlockSlab<E extends Enum<E> & EnumBlockSlab.IEnumMeta & IStringSerializable> extends BlockSlab {
    private final E[] values;
    public static final PropertyBool SEAMLESS = PropertyBool.create("seamless");
    public final PropertyEnum<E> prop;
    private static PropertyEnum<?> tmp;

    public EnumBlockSlab(Material material, PropertyEnum<E> prop, Class<E> clazz) {
        super(preInit(material, prop));
        this.prop = prop;
        values = clazz.getEnumConstants();
    }

    @Override
    public abstract boolean isDouble();

    public abstract Block halfSlab();
    public abstract Block fullSlab();

    public Item getItemDropped(IBlockState blockState, Random random, int fortune) {
        return Item.getItemFromBlock(this.halfSlab());
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(this.halfSlab());
    }

    @SuppressWarnings("unchecked")
    private static Material preInit(Material material, PropertyEnum<?> property) {
        tmp = property;
        return material;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        if (!isDouble()) {
            for (E type : values) {
                list.add(new ItemStack(this, 1, type.getMeta()));
            }
        }
    }

    @Override
    protected BlockState createBlockState() {
        return this.isDouble()
                ? new BlockState(this, SEAMLESS, prop == null ? tmp : prop)
                : new BlockState(this, HALF, prop == null ? tmp : prop);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState blockState = this.getDefaultState().withProperty(prop, fromMeta(meta));
        if (this.isDouble()) {
            blockState = blockState.withProperty(SEAMLESS, (meta & 8) != 0);
        } else {
            blockState = blockState.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }

        return blockState;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        byte stateMeta = 0;
        int meta = stateMeta | state.getValue(this.prop).getMeta();
        if (this.isDouble()) {
            if (state.getValue(SEAMLESS)) {
                meta |= 8;
            }
        } else if (state.getValue(HALF) == EnumBlockHalf.TOP) {
            meta |= 8;
        }

        return meta;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(this.prop).getMeta();
    }

    protected E fromMeta(int meta) {
        if (meta < 0 || meta >= values.length) {
            meta = 0;
        }

        return values[meta];
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return this.prop;
    }

    @Override
    public Object getVariant(ItemStack itemStack) {
        int meta = itemStack.getMetadata() & 7;
        Object[] values = this.prop.getAllowedValues().toArray();
        if (meta < 0 || meta >= values.length)
            meta = 0;

        return values[meta];
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName() + '.' + fromMeta(meta).getName().toLowerCase(Locale.US);
    }

    public interface IEnumMeta {
        int getMeta();
    }
}
