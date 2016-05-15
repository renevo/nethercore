package com.renevo.nethercore.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EnumBlockWall<E extends Enum<E> & EnumBlockWall.IEnumMeta & IStringSerializable> extends Block {
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    protected static final AxisAlignedBB[] BASE_BBS = new AxisAlignedBB[]{new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 0.875D, 0.6875D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    protected static final AxisAlignedBB[] ACTUAL_BBS;

    private final E[] values;
    public final PropertyEnum<E> prop;
    private static PropertyEnum<?> tmp;

    public EnumBlockWall(Material material, PropertyEnum<E> prop, Class<E> clazz) {
        super(preInit(material, prop));
        this.prop = prop;
        values = clazz.getEnumConstants();
    }

    @SuppressWarnings("unchecked")
    private static Material preInit(Material material, PropertyEnum<?> property) {
        tmp = property;
        return material;
    }

    @Override
    public boolean isFullCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess blockAccess, BlockPos blockPos) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState p_getBoundingBox_1_, IBlockAccess p_getBoundingBox_2_, BlockPos p_getBoundingBox_3_) {
        p_getBoundingBox_1_ = this.getActualState(p_getBoundingBox_1_, p_getBoundingBox_2_, p_getBoundingBox_3_);
        return BASE_BBS[getFacingFlags(p_getBoundingBox_1_)];
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState p_getSelectedBoundingBox_1_, World p_getSelectedBoundingBox_2_, BlockPos p_getSelectedBoundingBox_3_) {
        p_getSelectedBoundingBox_1_ = this.getActualState(p_getSelectedBoundingBox_1_, p_getSelectedBoundingBox_2_, p_getSelectedBoundingBox_3_);
        return ACTUAL_BBS[getFacingFlags(p_getSelectedBoundingBox_1_)];
    }

    // face flags... GET YO FACE... not sure what better to call this
    // basically, this selects the Bounding Box based on the sides that are connected.
    private static int getFacingFlags(IBlockState blockState) {
        int faceFlags = 0;
        if(blockState.getValue(NORTH)) {
            faceFlags |= 1 << EnumFacing.NORTH.getHorizontalIndex();
        }

        if(blockState.getValue(EAST)) {
            faceFlags |= 1 << EnumFacing.EAST.getHorizontalIndex();
        }

        if(blockState.getValue(SOUTH)) {
            faceFlags |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
        }

        if(blockState.getValue(WEST)) {
            faceFlags |= 1 << EnumFacing.WEST.getHorizontalIndex();
        }

        return faceFlags;
    }

    @SuppressWarnings("SimplifiableConditionalExpression") // more readable this way
    protected boolean canConnectTo(IBlockAccess blockAccess, BlockPos blockPos) {
        IBlockState block = blockAccess.getBlockState(blockPos);
        return block.getBlock() == Blocks.barrier
                ? false
                : (block.getBlock() != this && !(block.getBlock() instanceof BlockFenceGate)
                ? (block.getMaterial().isOpaque() && block.isFullCube()
                ? block.getMaterial() != Material.gourd
                : false)
                : true);
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (E type : values) {
            list.add(new ItemStack(this, 1, type.getMeta()));
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, EnumFacing enumFacing) {
        return enumFacing != EnumFacing.DOWN || super.shouldSideBeRendered(blockState, blockAccess, blockPos, enumFacing);
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
        return blockState.withProperty(UP, !blockAccess.isAirBlock(blockPos.up())).withProperty(NORTH, this.canConnectTo(blockAccess, blockPos.north())).withProperty(EAST, this.canConnectTo(blockAccess, blockPos.east())).withProperty(SOUTH, this.canConnectTo(blockAccess, blockPos.south())).withProperty(WEST, this.canConnectTo(blockAccess, blockPos.west()));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, UP, NORTH, EAST, WEST, SOUTH, prop == null ? tmp : prop);
    }

    protected E fromMeta(int meta) {
        if (meta < 0 || meta >= values.length) {
            meta = 0;
        }

        return values[meta];
    }

    static {
        ACTUAL_BBS = new AxisAlignedBB[]{BASE_BBS[0].setMaxY(1.5D), BASE_BBS[1].setMaxY(1.5D), BASE_BBS[2].setMaxY(1.5D), BASE_BBS[3].setMaxY(1.5D), BASE_BBS[4].setMaxY(1.5D), BASE_BBS[5].setMaxY(1.5D), BASE_BBS[6].setMaxY(1.5D), BASE_BBS[7].setMaxY(1.5D), BASE_BBS[8].setMaxY(1.5D), BASE_BBS[9].setMaxY(1.5D), BASE_BBS[10].setMaxY(1.5D), BASE_BBS[11].setMaxY(1.5D), BASE_BBS[12].setMaxY(1.5D), BASE_BBS[13].setMaxY(1.5D), BASE_BBS[14].setMaxY(1.5D), BASE_BBS[15].setMaxY(1.5D)};
    }

    public interface IEnumMeta {
        int getMeta();
    }
}
