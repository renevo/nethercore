package com.renevo.nethercore;

import com.renevo.nethercore.client.gui.inventory.GuiNetherFurnace;
import com.renevo.nethercore.inventory.ContainerNetherFurnace;
import com.renevo.nethercore.tileentity.TileEntityNetherFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_NETHER_FURNACE = 0;


    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        if (id == GUI_NETHER_FURNACE) {
            return new ContainerNetherFurnace(entityPlayer.inventory, (TileEntityNetherFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        if (id == GUI_NETHER_FURNACE) {
            return new GuiNetherFurnace(entityPlayer.inventory, (TileEntityNetherFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
