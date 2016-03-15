package com.renevo.nethercore.client.gui.inventory;

import com.renevo.nethercore.Util;
import com.renevo.nethercore.inventory.ContainerNetherFurnace;
import com.renevo.nethercore.tileentity.TileEntityNetherFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiNetherFurnace extends GuiContainer {
    private static final ResourceLocation furnaceGuiTextures = Util.getResource("textures/gui/container/nether_furnace.png");
    private final InventoryPlayer playerInventory;
    private IInventory tileFurnace;

    public GuiNetherFurnace(InventoryPlayer playerInventory, IInventory inventory) {
        super(new ContainerNetherFurnace(playerInventory, inventory));
        this.playerInventory = playerInventory;
        this.tileFurnace = inventory;
    }

    protected void drawGuiContainerForegroundLayer(int p_drawGuiContainerForegroundLayer_1_, int p_drawGuiContainerForegroundLayer_2_) {
        String displayName = this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(displayName, this.xSize / 2 - this.fontRendererObj.getStringWidth(displayName) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_drawGuiContainerBackgroundLayer_1_, int p_drawGuiContainerBackgroundLayer_2_, int p_drawGuiContainerBackgroundLayer_3_) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        int offsetLeft = (this.width - this.xSize) / 2;
        int offsetTop = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetLeft, offsetTop, 0, 0, this.xSize, this.ySize);
        int val;
        if(TileEntityNetherFurnace.isBurning(this.tileFurnace)) {
            val = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(offsetLeft + 23, offsetTop + 36 + 12 - val, 176, 12 - val, 14, val + 1);
        }

        val = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(offsetLeft + 79, offsetTop + 34, 176, 14, val + 1, 16);
    }

    private int getCookProgressScaled(int p_getCookProgressScaled_1_) {
        int cookTime = this.tileFurnace.getField(2);
        int totalCookTime = this.tileFurnace.getField(3);
        return totalCookTime != 0 && cookTime != 0?cookTime * p_getCookProgressScaled_1_ / totalCookTime:0;
    }

    private int getBurnLeftScaled(int p_getBurnLeftScaled_1_) {
        int currentItemBurnTime = this.tileFurnace.getField(1);
        if(currentItemBurnTime == 0) {
            currentItemBurnTime = 200;
        }

        return this.tileFurnace.getField(0) * p_getBurnLeftScaled_1_ / currentItemBurnTime;
    }
}
