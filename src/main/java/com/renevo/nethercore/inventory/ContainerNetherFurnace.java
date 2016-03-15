package com.renevo.nethercore.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerNetherFurnace extends Container {
    private final IInventory tileFurnace;
    private int cookTime;
    private int totalCookTime;
    private int furnaceBurnTime;
    private int currentItemBurnTime;

    public ContainerNetherFurnace(InventoryPlayer playerInventory, IInventory inventory) {
        this.tileFurnace = inventory;
        this.addSlotToContainer(new Slot(inventory, 0, 56, 17));
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, inventory, 1, 116, 35));

        int lvt_3_2_;
        for (lvt_3_2_ = 0; lvt_3_2_ < 3; ++lvt_3_2_) {
            for (int lvt_4_1_ = 0; lvt_4_1_ < 9; ++lvt_4_1_) {
                this.addSlotToContainer(new Slot(playerInventory, lvt_4_1_ + lvt_3_2_ * 9 + 9, 8 + lvt_4_1_ * 18, 84 + lvt_3_2_ * 18));
            }
        }

        for (lvt_3_2_ = 0; lvt_3_2_ < 9; ++lvt_3_2_) {
            this.addSlotToContainer(new Slot(playerInventory, lvt_3_2_, 8 + lvt_3_2_ * 18, 142));
        }

    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (ICrafting crafting : this.crafters) {
            if (this.furnaceBurnTime != this.tileFurnace.getField(0)) {
                crafting.sendProgressBarUpdate(this, 0, this.tileFurnace.getField(0));
            }

            if (this.currentItemBurnTime != this.tileFurnace.getField(1)) {
                crafting.sendProgressBarUpdate(this, 1, this.tileFurnace.getField(1));
            }

            if (this.cookTime != this.tileFurnace.getField(2)) {
                crafting.sendProgressBarUpdate(this, 2, this.tileFurnace.getField(2));
            }

            if (this.totalCookTime != this.tileFurnace.getField(3)) {
                crafting.sendProgressBarUpdate(this, 3, this.tileFurnace.getField(3));
            }
        }

        this.furnaceBurnTime = this.tileFurnace.getField(0);
        this.currentItemBurnTime = this.tileFurnace.getField(1);
        this.cookTime = this.tileFurnace.getField(2);
        this.totalCookTime = this.tileFurnace.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int fieldId, int fieldValue) {
        this.tileFurnace.setField(fieldId, fieldValue);
    }

    public boolean canInteractWith(EntityPlayer player) {
        return this.tileFurnace.isUseableByPlayer(player);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemStack = null;
        Slot slot = this.inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            itemStack = slotStack.copy();
            if (slotIndex == 1) {
                if (!this.mergeItemStack(slotStack, 2, 38, true)) {
                    return null;
                }

                slot.onSlotChange(slotStack, itemStack);
            } else if (slotIndex != 0) {
                if (FurnaceRecipes.instance().getSmeltingResult(slotStack) != null) {
                    if (!this.mergeItemStack(slotStack, 0, 1, false)) {
                        return null;
                    }
                } else if (slotIndex >= 2 && slotIndex < 29) {
                    if (!this.mergeItemStack(slotStack, 29, 38, false)) {
                        return null;
                    }
                } else if (slotIndex >= 29 && slotIndex < 38 && !this.mergeItemStack(slotStack, 2, 29, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(slotStack, 2, 38, false)) {
                return null;
            }

            if (slotStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (slotStack.stackSize == itemStack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, slotStack);
        }

        return itemStack;
    }
}