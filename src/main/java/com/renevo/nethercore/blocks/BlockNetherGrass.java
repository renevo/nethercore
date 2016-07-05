package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockNetherGrass extends Block {
    public static final PropertyBool BURNING = PropertyBool.create("burning");

    public BlockNetherGrass() {
        super(Material.ROCK);

        this.setTickRandomly(true);

        this.setHarvestLevel("pickaxe", 1); // 1 is stone required (0 wood, 1 stone, 2 iron)
        this.setHardness(Blocks.NETHERRACK.getBlockHardness(null, null, null));
        this.setResistance(Blocks.NETHERRACK.getExplosionResistance(null));

        this.setDefaultState(this.blockState.getBaseState().withProperty(BURNING, false));
        this.setSoundType(NetherCoreBlocks.soundTypeNetherStone);
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
    }

    @Override
    public void updateTick(World world, BlockPos blockPos, IBlockState blockState, Random random) {
        if (!world.isRemote) {
            if (world.provider.doesWaterVaporize()) {
                int rate = blockState.getValue(BURNING) ? 12 : 4;
                IBlockState blockUp = world.getBlockState(blockPos.up());
                if (blockUp.isOpaqueCube()) {
                    world.setBlockState(blockPos, Blocks.NETHERRACK.getDefaultState());
                    return;
                }

                for (int i = 0; i < rate; ++i) {
                    BlockPos blockpos = blockPos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    IBlockState blockOnTop = world.getBlockState(blockpos.up());
                    if (blockOnTop.isOpaqueCube()) {
                        continue; // just don't process
                    }

                    IBlockState iblockstate = world.getBlockState(blockpos);

                    if (iblockstate.getBlock() == Blocks.NETHERRACK) {
                        world.setBlockState(blockpos, NetherCoreBlocks.blockNetherGrass.getDefaultState());
                        if (blockOnTop.getMaterial() == Material.AIR) {
                            world.setBlockState(blockpos.up(), Blocks.FIRE.getDefaultState());
                        }
                    }

                    if (iblockstate.getBlock() == NetherCoreBlocks.blockNetherOre && iblockstate.getBlock().getMetaFromState(iblockstate) == BlockNetherOre.OreTypes.COAL.getMeta()) {
                        world.setBlockState(blockpos, NetherCoreBlocks.blockNetherOre.getDefaultState().withProperty(BlockNetherOre.TYPE, BlockNetherOre.OreTypes.NETHERCOAL));
                        if (blockOnTop.getMaterial() == Material.AIR) {
                            world.setBlockState(blockpos.up(), Blocks.FIRE.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    @Override
    public IBlockState getActualState(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
        Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
        return blockState.withProperty(BURNING, block == Blocks.FIRE || block == Blocks.LAVA || block == Blocks.FLOWING_LAVA);
    }

    @Override
    public Item getItemDropped(IBlockState blockState, Random random, int meta) {
        return Blocks.NETHERRACK.getItemDropped(Blocks.NETHERRACK.getDefaultState(), random, meta);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, EntityLiving.SpawnPlacementType spawnPlacementType) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getMetaFromState(IBlockState blockState) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BURNING);
    }
}
