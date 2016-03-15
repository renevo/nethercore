package com.renevo.nethercore.blocks;

import com.renevo.nethercore.GuiHandler;
import com.renevo.nethercore.NetherCore;
import com.renevo.nethercore.NetherCoreRegistry;
import com.renevo.nethercore.tileentity.TileEntityNetherFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockNetherFurnace extends BlockContainer {
    public static final PropertyDirection FACING;
    private final boolean isBurning;
    private static boolean keepInventory;

    protected BlockNetherFurnace(boolean isBurning) {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setHardness(3.5F);
        this.setStepSound(NetherCoreBlocks.soundTypeNetherStone);
        this.isBurning = isBurning;

        if (!this.isBurning) {
            this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
        } else {
            this.setCreativeTab(null);
        }

        if (this.isBurning) {
            this.setLightLevel(0.875F);
        }
    }

    public Item getItemDropped(IBlockState blockState, Random random, int fortune) {
        return Item.getItemFromBlock(NetherCoreBlocks.blockNetherFurnace);
    }

    public void onBlockAdded(World world, BlockPos blockPos, IBlockState blockState) {
        this.setDefaultFacing(world, blockPos, blockState);
    }

    private void setDefaultFacing(World world, BlockPos blockPos, IBlockState blockState) {
        if(!world.isRemote) {
            Block blockNorth = world.getBlockState(blockPos.north()).getBlock();
            Block blockSouth = world.getBlockState(blockPos.south()).getBlock();
            Block blockWest = world.getBlockState(blockPos.west()).getBlock();
            Block blockEast = world.getBlockState(blockPos.east()).getBlock();
            EnumFacing facing = blockState.getValue(FACING);
            if(facing == EnumFacing.NORTH && blockNorth.isFullBlock() && !blockSouth.isFullBlock()) {
                facing = EnumFacing.SOUTH;
            } else if(facing == EnumFacing.SOUTH && blockSouth.isFullBlock() && !blockNorth.isFullBlock()) {
                facing = EnumFacing.NORTH;
            } else if(facing == EnumFacing.WEST && blockWest.isFullBlock() && !blockEast.isFullBlock()) {
                facing = EnumFacing.EAST;
            } else if(facing == EnumFacing.EAST && blockEast.isFullBlock() && !blockWest.isFullBlock()) {
                facing = EnumFacing.WEST;
            }

            world.setBlockState(blockPos, blockState.withProperty(FACING, facing), 2);
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos blockPos, IBlockState blockState, Random random) {
        if(this.isBurning) {
            EnumFacing facing = blockState.getValue(FACING);
            double x = (double)blockPos.getX() + 0.5D;
            double y = (double)blockPos.getY() + random.nextDouble() * 6.0D / 16.0D;
            double z = (double)blockPos.getZ() + 0.5D;
            double offset = 0.52D;
            double randomized = random.nextDouble() * 0.6D - 0.3D;
            switch(facing) {
                case WEST:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x - offset, y, z + randomized, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle(EnumParticleTypes.FLAME, x - offset, y, z + randomized, 0.0D, 0.0D, 0.0D);
                    break;
                case EAST:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + offset, y, z + randomized, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle(EnumParticleTypes.FLAME, x + offset, y, z + randomized, 0.0D, 0.0D, 0.0D);
                    break;
                case NORTH:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + randomized, y, z - offset, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle(EnumParticleTypes.FLAME, x + randomized, y, z - offset, 0.0D, 0.0D, 0.0D);
                    break;
                case SOUTH:
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + randomized, y, z + offset, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle(EnumParticleTypes.FLAME, x + randomized, y, z + offset, 0.0D, 0.0D, 0.0D);
            }

        }
    }

    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumFacing facing, float x, float y, float z) {
        if(world.isRemote) {
            return true;
        } else {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if(tileEntity instanceof TileEntityNetherFurnace) {
                player.openGui(NetherCore.instance, GuiHandler.GUI_NETHER_FURNACE, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            }

            return true;
        }
    }

    public static void setState(boolean burning, World world, BlockPos blockPos) {
        IBlockState blockState = world.getBlockState(blockPos);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        keepInventory = true;
        if(burning) {
            world.setBlockState(blockPos, NetherCoreBlocks.blockNetherFurnaceLit.getDefaultState().withProperty(FACING, blockState.getValue(FACING)), 3);
            world.setBlockState(blockPos, NetherCoreBlocks.blockNetherFurnaceLit.getDefaultState().withProperty(FACING, blockState.getValue(FACING)), 3);
        } else {
            world.setBlockState(blockPos, NetherCoreBlocks.blockNetherFurnace.getDefaultState().withProperty(FACING, blockState.getValue(FACING)), 3);
            world.setBlockState(blockPos, NetherCoreBlocks.blockNetherFurnace.getDefaultState().withProperty(FACING, blockState.getValue(FACING)), 3);
        }

        keepInventory = false;
        if(tileEntity != null) {
            tileEntity.validate();
            world.setTileEntity(blockPos, tileEntity);
        }
    }

    public TileEntity createNewTileEntity(World world, int unk) {
        return new TileEntityNetherFurnace();
    }

    public IBlockState onBlockPlaced(World world, BlockPos blockPos, EnumFacing facing, float x, float y, float z, int unk, EntityLivingBase entity) {
        return this.getDefaultState().withProperty(FACING, entity.getHorizontalFacing().getOpposite());
    }

    public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState blockState, EntityLivingBase entity, ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(FACING, entity.getHorizontalFacing().getOpposite()), 2);
        if(itemStack.hasDisplayName()) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if(tileEntity instanceof TileEntityNetherFurnace) {
                ((TileEntityNetherFurnace)tileEntity).setCustomInventoryName(itemStack.getDisplayName());
            }
        }

    }

    public void breakBlock(World world, BlockPos blockPos, IBlockState blockState) {
        if(!keepInventory) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if(tileEntity instanceof TileEntityNetherFurnace) {
                InventoryHelper.dropInventoryItems(world, blockPos, (TileEntityNetherFurnace)tileEntity);
                world.updateComparatorOutputLevel(blockPos, this);
            }
        }

        super.breakBlock(world, blockPos, blockState);
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        return Container.calcRedstone(world.getTileEntity(blockPos));
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(NetherCoreBlocks.blockNetherFurnace);
    }

    public int getRenderType() {
        return 3;
    }

    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState p_getStateForEntityRender_1_) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }

    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getFront(meta);
        if(facing.getAxis() == EnumFacing.Axis.Y) {
            facing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, facing);
    }

    public int getMetaFromState(IBlockState blockState) {
        return blockState.getValue(FACING).getIndex();
    }

    protected BlockState createBlockState() {
        return new BlockState(this, FACING);
    }

    static {
        FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    }
}
