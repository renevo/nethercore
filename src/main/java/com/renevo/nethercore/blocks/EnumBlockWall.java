package com.renevo.nethercore.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
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
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess blockAccess, BlockPos blockPos) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos blockPos) {
        boolean north = this.canConnectTo(blockAccess, blockPos.north());
        boolean south = this.canConnectTo(blockAccess, blockPos.south());
        boolean west = this.canConnectTo(blockAccess, blockPos.west());
        boolean east = this.canConnectTo(blockAccess, blockPos.east());
        float minX = 0.25F;
        float maxX = 0.75F;
        float minZ = 0.25F;
        float maxZ = 0.75F;
        float maxY = 1.0F;
        if (north) {
            minZ = 0.0F;
        }

        if (south) {
            maxZ = 1.0F;
        }

        if (west) {
            minX = 0.0F;
        }

        if (east) {
            maxX = 1.0F;
        }

        if (north && south && !west && !east) {
            maxY = 0.8125F;
            minX = 0.3125F;
            maxX = 0.6875F;
        } else if (!north && !south && west && east) {
            maxY = 0.8125F;
            minZ = 0.3125F;
            maxZ = 0.6875F;
        }

        this.setBlockBounds(minX, 0.0F, minZ, maxX, maxY, maxZ);
    }

    public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        this.maxY = 1.5D;
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }

    public boolean canConnectTo(IBlockAccess blockAccess, BlockPos blockPos) {
        Block block = blockAccess.getBlockState(blockPos).getBlock();
        return block == Blocks.barrier
                ? false
                : (block != this && !(block instanceof BlockFenceGate)
                ? (block.getMaterial().isOpaque() && block.isFullCube()
                ? block.getMaterial() != Material.gourd
                : false)
                : true);
    }

    public boolean canPlaceTorchOnTop(IBlockAccess blockAccess, BlockPos blockPos) {
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
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos blockPos, EnumFacing enumFacing) {
        return enumFacing == EnumFacing.DOWN
                ? super.shouldSideBeRendered(blockAccess, blockPos, enumFacing)
                : true;
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

    protected BlockState createBlockState() {
        return new BlockState(this, UP, NORTH, EAST, WEST, SOUTH, prop == null ? tmp : prop);
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
