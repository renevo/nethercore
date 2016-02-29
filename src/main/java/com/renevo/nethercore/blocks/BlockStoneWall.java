package com.renevo.nethercore.blocks;

import com.renevo.nethercore.NetherCoreRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

public class BlockStoneWall extends EnumBlockWall<BlockStoneWall.WallType> {
    public static final PropertyEnum<WallType> VARIANT = PropertyEnum.create("variant", WallType.class);

    public BlockStoneWall() {
        this(Material.rock);
    }

    public BlockStoneWall(Material material) {
        super(material, VARIANT, WallType.class);

        IBlockState blockState = this.blockState.getBaseState();
        this.setDefaultState(blockState.withProperty(VARIANT, WallType.STONE).withProperty(UP, true).withProperty(NORTH, true).withProperty(EAST, false).withProperty(SOUTH, true).withProperty(WEST, false));
        this.setCreativeTab(NetherCoreRegistry.tabNetherCore);
        this.setHardness(3F);
        this.setResistance(20F);
        this.setHarvestLevel("pickaxe", 1); // 1 is stone required (0 wood, 1 stone, 2 iron)
        this.setStepSound(NetherCoreBlocks.soundTypeNetherStone);
    }

    public enum WallType implements IStringSerializable, EnumBlockWall.IEnumMeta {
        STONE,
        COBBLESTONE,
        BRICK;

        public final int meta;

        WallType() {
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

    }
}
