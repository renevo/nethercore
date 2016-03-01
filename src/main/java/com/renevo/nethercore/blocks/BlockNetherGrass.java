package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockNetherGrass extends Block {
    public static final PropertyBool BURNING = PropertyBool.create("burning");

    public BlockNetherGrass() {
        super(Material.rock);

        this.setTickRandomly(true);

        this.setHarvestLevel("pickaxe", 1); // 1 is stone required (0 wood, 1 stone, 2 iron)
        this.setHardness(Blocks.netherrack.getBlockHardness(null, null));
        this.setResistance(Blocks.netherrack.getExplosionResistance(null));

        this.setDefaultState(this.blockState.getBaseState().withProperty(BURNING, false));
        this.setStepSound(NetherCoreBlocks.soundTypeNetherStone);
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    public void updateTick(World world, BlockPos blockPos, IBlockState blockState, Random random) {
        if (!world.isRemote) {
            if (world.provider.doesWaterVaporize()) {
                int rate = blockState.getValue(BURNING) ? 12 : 4;

                for (int i = 0; i < rate; ++i) {
                    BlockPos blockpos = blockPos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    Block blockOnTop = world.getBlockState(blockpos.up()).getBlock();
                    IBlockState iblockstate = world.getBlockState(blockpos);
                    if (iblockstate.getBlock() == Blocks.netherrack && blockOnTop.isAir(world, blockpos.up())) {
                        world.setBlockState(blockpos, NetherCoreBlocks.blockNetherGrass.getDefaultState());
                        world.setBlockState(blockpos.up(), Blocks.fire.getDefaultState());
                    }
                }
            }
        }
    }

    public IBlockState getActualState(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
        Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
        return blockState.withProperty(BURNING, block == Blocks.fire || block == Blocks.lava || block == Blocks.flowing_lava);
    }

    public Item getItemDropped(IBlockState blockState, Random random, int meta) {
        return Blocks.netherrack.getItemDropped(Blocks.netherrack.getDefaultState(), random, meta);
    }

    public boolean canSilkHarvest(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer entityPlayer) {
        return false;
    }

    public boolean canCreatureSpawn(IBlockAccess blockAccess, BlockPos blockPos, EntityLiving.SpawnPlacementType spawnPlacementType) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    public int getMetaFromState(IBlockState blockState) {
        return 0;
    }

    protected BlockState createBlockState() {
        return new BlockState(this, BURNING);
    }
}
