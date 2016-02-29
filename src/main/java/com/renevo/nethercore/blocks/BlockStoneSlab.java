package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
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
import slimeknights.mantle.block.EnumBlock;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public abstract class BlockStoneSlab extends BlockSlab {
    public static final PropertyBool SEAMLESS = PropertyBool.create("seamless");
    public static final PropertyEnum<SlabType> VARIANT = PropertyEnum.create("variant", SlabType.class);
    public static final Block.SoundType soundTypeNetherStone = new Block.SoundType("stone", 1.0F, 1.0F);

    public BlockStoneSlab() {
        super(Material.rock);

        IBlockState blockState = this.blockState.getBaseState();
        if(this.isDouble()) {
            blockState = blockState.withProperty(SEAMLESS, false);
        } else {
            blockState = blockState.withProperty(HALF, EnumBlockHalf.BOTTOM);
        }

        this.setDefaultState(blockState.withProperty(VARIANT, SlabType.STONE));
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
        this.setHardness(3F);
        this.setResistance(20F);
        this.setHarvestLevel("pickaxe", 1); // 1 is stone required (0 wood, 1 stone, 2 iron)
        this.setStepSound(soundTypeNetherStone);
    }

    public Item getItemDropped(IBlockState p_getItemDropped_1_, Random p_getItemDropped_2_, int p_getItemDropped_3_) {
        return Item.getItemFromBlock(NetherCoreBlocks.blockNetherHalfSlab);
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_getItem_1_, BlockPos p_getItem_2_) {
        return Item.getItemFromBlock(NetherCoreBlocks.blockNetherHalfSlab);
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName() + "." + SlabType.byMetadata(meta).getUnlocalizedName();
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    public Object getVariant(ItemStack itemStack) {
        return SlabType.byMetadata(itemStack.getMetadata() & 7);
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTab, List<ItemStack> subBlocks) {
        if(item != Item.getItemFromBlock(NetherCoreBlocks.blockNetherDoubleSlab)) {
            for (SlabType slabType : SlabType.values()) {
                subBlocks.add(new ItemStack(item, 1, slabType.getMeta()));
            }
        }
    }

    public IBlockState getStateFromMeta(int meta) {
        IBlockState blockState = this.getDefaultState().withProperty(VARIANT, SlabType.byMetadata(meta & 7));
        if(this.isDouble()) {
            blockState = blockState.withProperty(SEAMLESS, (meta & 8) != 0);
        } else {
            blockState = blockState.withProperty(HALF, (meta & 8) == 0?EnumBlockHalf.BOTTOM:EnumBlockHalf.TOP);
        }

        return blockState;
    }

    public int getMetaFromState(IBlockState blockState) {
        byte stateMeta = 0;
        int meta = stateMeta | blockState.getValue(VARIANT).getMeta();
        if(this.isDouble()) {
            if(blockState.getValue(SEAMLESS)) {
                meta |= 8;
            }
        } else if(blockState.getValue(HALF) == EnumBlockHalf.TOP) {
            meta |= 8;
        }

        return meta;
    }

    protected BlockState createBlockState() {
        return this.isDouble()?new BlockState(this, SEAMLESS, VARIANT):new BlockState(this, HALF, VARIANT);
    }

    public int damageDropped(IBlockState blockState) {
        return blockState.getValue(VARIANT).getMeta();
    }

    public MapColor getMapColor(IBlockState blockState) {
        return blockState.getValue(VARIANT).getMapColor();
    }

    public enum SlabType implements IStringSerializable, EnumBlock.IEnumMeta {
        COBBLESTONE,
        STONE,
        STONEBRICK;

        public final int meta;

        SlabType() {
            meta = ordinal();
        }

        @Override
        public String getName() {
            return this.toString();
        }

        @Override
        public int getMeta() {
            return meta;
        }

        public String getUnlocalizedName() {
            return getName().toLowerCase(Locale.US);
        }

        public MapColor getMapColor() {
            return MapColor.stoneColor;
        }

        private static final BlockStoneSlab.SlabType[] META_LOOKUP;

        static {
            META_LOOKUP = new BlockStoneSlab.SlabType[values().length];

            for (SlabType slabType : values()) {
                META_LOOKUP[slabType.getMeta()] = slabType;
            }
        }

        public static BlockStoneSlab.SlabType byMetadata(int meta) {
            if(meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }
    }
}
