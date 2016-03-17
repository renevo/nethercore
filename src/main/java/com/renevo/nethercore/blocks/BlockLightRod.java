package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockLightRod extends Block {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    protected static final AxisAlignedBB BB_AXIS_Y = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
    protected static final AxisAlignedBB BB_AXIS_Z = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 1.0D);
    protected static final AxisAlignedBB BB_AXIS_X = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);

    private MapColor mapColor = MapColor.sandColor;

    public BlockLightRod() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
        this.setHardness(0.3F);
        this.setLightLevel(1.0F);
    }

    public BlockLightRod(MapColor color) {
        super(Material.circuits);
        mapColor = color;
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
        this.setHardness(0.3F);
        this.setLightLevel(1.0F);
    }

    public MapColor getMapColor(IBlockState p_getMapColor_1_) {
        return this.mapColor;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos blockPos) {
        AxisAlignedBB thisBB = this.getBoundingBox(blockAccess.getBlockState(blockPos));
        this.setBlockBounds((float) thisBB.minX, (float) thisBB.minY, (float) thisBB.minZ, (float) thisBB.maxX, (float) thisBB.maxY, (float) thisBB.maxZ);
    }

    @Override
    public void setBlockBoundsForItemRender() {
        AxisAlignedBB thisBB = BB_AXIS_X;
        this.setBlockBounds((float) thisBB.minX, (float) thisBB.minY, (float) thisBB.minZ, (float) thisBB.maxX, (float) thisBB.maxY, (float) thisBB.maxZ);
    }

    @Override
    public void addCollisionBoxesToList(World world, BlockPos blockPos, IBlockState blockState, AxisAlignedBB alignedBB, List<AxisAlignedBB> alignedBBs, Entity entity) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        super.addCollisionBoxesToList(world, blockPos, blockState, alignedBB, alignedBBs, entity);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return true;
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos blockPos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState iblockstate = world.getBlockState(blockPos.offset(facing.getOpposite()));

        if (iblockstate.getBlock() == this) {
            EnumFacing enumfacing = iblockstate.getValue(FACING);

            if (enumfacing == facing) {
                return this.getDefaultState().withProperty(FACING, facing.getOpposite());
            }
        }

        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public void onBlockAdded(World world, BlockPos blockPos, IBlockState state) {
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();
        state = state.withProperty(FACING, EnumFacing.getFront(meta));
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, FACING);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state) {
        switch (state.getValue(FACING).getAxis()) {
            case X:
            default:
                return BB_AXIS_X;
            case Z:
                return BB_AXIS_Z;
            case Y:
                return BB_AXIS_Y;
        }
    }
}
