package com.renevo.nethercore.world;

import com.renevo.nethercore.blocks.NetherCoreBlocks;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class NetherOreGenerator implements IWorldGenerator {

    // TODO: configure all these things...

    private static final WorldGenMinable netherCoalGenerator = new WorldGenMinable(NetherCoreBlocks.blockNetherOre.getStateFromMeta(0), 16, BlockHelper.forBlock(Blocks.netherrack));
    private static final WorldGenMinable netherIronGenerator = new WorldGenMinable(NetherCoreBlocks.blockNetherOre.getStateFromMeta(1), 8, BlockHelper.forBlock(Blocks.netherrack));
    private static final WorldGenMinable netherGoldGenerator = new WorldGenMinable(NetherCoreBlocks.blockNetherOre.getStateFromMeta(2), 8, BlockHelper.forBlock(Blocks.netherrack));
    private static final WorldGenMinable netherRedstoneGenerator = new WorldGenMinable(NetherCoreBlocks.blockNetherOre.getStateFromMeta(3), 7, BlockHelper.forBlock(Blocks.netherrack));
    private static final WorldGenMinable netherLapisGenerator = new WorldGenMinable(NetherCoreBlocks.blockNetherOre.getStateFromMeta(4), 6, BlockHelper.forBlock(Blocks.netherrack));
    private static final WorldGenMinable netherDiamondGenerator = new WorldGenMinable(NetherCoreBlocks.blockNetherOre.getStateFromMeta(5), 7, BlockHelper.forBlock(Blocks.netherrack));
    private static final WorldGenMinable netherEmeraldGenerator = new WorldGenMinable(NetherCoreBlocks.blockNetherOre.getStateFromMeta(6), 4, BlockHelper.forBlock(Blocks.netherrack));

    @Override
    public void generate(Random random, int i, int i1, World world, IChunkProvider iChunkProvider, IChunkProvider iChunkProvider1) {
        boolean isHell = (iChunkProvider instanceof ChunkProviderHell);
        if (!isHell) {
            return;
        }

        int frequency;

        BlockPos blockPos = new BlockPos(i * 16, 0, i1 * 16);

        for(frequency = 0; frequency < 20; ++frequency) {
            this.netherCoalGenerator.generate(world, random, blockPos.add(random.nextInt(16), random.nextInt(108) + 10, random.nextInt(16)));
        }

        for(frequency = 0; frequency < 20; ++frequency) {
            netherIronGenerator.generate(world, random, blockPos.add(random.nextInt(16), random.nextInt(108) + 10, random.nextInt(16)));
        }

        for(frequency = 0; frequency < 2; ++frequency) {
            netherGoldGenerator.generate(world, random, blockPos.add(random.nextInt(16), random.nextInt(108) + 10, random.nextInt(16)));
        }

        for(frequency = 0; frequency < 8; ++frequency) {
            netherRedstoneGenerator.generate(world, random, blockPos.add(random.nextInt(16), random.nextInt(108) + 10, random.nextInt(16)));
        }

        for(frequency = 0; frequency < 1; ++frequency) {
            netherLapisGenerator.generate(world, random, blockPos.add(random.nextInt(16), random.nextInt(108) + 10, random.nextInt(16)));
        }

        for(frequency = 0; frequency < 1; ++frequency) {
            netherDiamondGenerator.generate(world, random, blockPos.add(random.nextInt(16), random.nextInt(30), random.nextInt(16)));
            netherDiamondGenerator.generate(world, random, blockPos.add(random.nextInt(16), random.nextInt(30) + 98, random.nextInt(16)));
        }

        for(frequency = 0; frequency < 1; ++frequency) {
            netherEmeraldGenerator.generate(world, random, blockPos.add(random.nextInt(16), random.nextInt(54) + 10, random.nextInt(16)));
        }
    }
}
