package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public class BlockStoneWall extends EnumBlockWall<BlockStoneWall.WallType> {
    public static final PropertyEnum<WallType> VARIANT = PropertyEnum.create("stone_variant", WallType.class);

    public BlockStoneWall(Block block) {
        super(block, VARIANT, WallType.class);

        IBlockState blockState = this.blockState.getBaseState();
        this.setDefaultState(blockState.withProperty(VARIANT, WallType.STONE).withProperty(UP, false).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
        this.setHarvestLevel("pickaxe", 1); // 1 is stone required (0 wood, 1 stone, 2 iron)
    }

    public enum WallType implements IStringSerializable, EnumBlockWall.IEnumMeta {
        STONE,
        COBBLESTONE,
        BRICK,
        REDBRICK;

        public final int meta;

        WallType() {
            meta = ordinal();
        }

        @Override
        public String getName() {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta() {
            return meta;
        }

    }
}
