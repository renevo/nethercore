package com.renevo.nethercore.tileentity;

import com.renevo.nethercore.Util;
import com.renevo.nethercore.blocks.BlockNetherFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityNetherFurnace extends TileEntityLockable implements ITickable, ISidedInventory {
    private static final int[] slotsTop = new int[]{0};
    private static final int[] slotsBottom = new int[]{2, 1};
    private static final int[] slotsSides = new int[]{1};
    private ItemStack[] furnaceItemStacks = new ItemStack[3];
    private int furnaceBurnTime;
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;
    private String furnaceCustomName;
    IItemHandler handlerTop;
    IItemHandler handlerBottom;
    IItemHandler handlerSide;

    public TileEntityNetherFurnace() {
        this.handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
        this.handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
        this.handlerSide = new SidedInvWrapper(this, EnumFacing.WEST);
    }

    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    public ItemStack getStackInSlot(int slotIndex) {
        return this.furnaceItemStacks[slotIndex];
    }

    public ItemStack decrStackSize(int slotIndex, int amount) {
        if(this.furnaceItemStacks[slotIndex] != null) {
            ItemStack itemstack;
            if(this.furnaceItemStacks[slotIndex].stackSize <= amount) {
                itemstack = this.furnaceItemStacks[slotIndex];
                this.furnaceItemStacks[slotIndex] = null;
                return itemstack;
            } else {
                itemstack = this.furnaceItemStacks[slotIndex].splitStack(amount);
                if(this.furnaceItemStacks[slotIndex].stackSize == 0) {
                    this.furnaceItemStacks[slotIndex] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    public ItemStack removeStackFromSlot(int slotIndex) {
        if(this.furnaceItemStacks[slotIndex] != null) {
            ItemStack itemstack = this.furnaceItemStacks[slotIndex];
            this.furnaceItemStacks[slotIndex] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        boolean flag = itemStack != null && itemStack.isItemEqual(this.furnaceItemStacks[slotIndex]) && ItemStack.areItemStackTagsEqual(itemStack, this.furnaceItemStacks[slotIndex]);
        this.furnaceItemStacks[slotIndex] = itemStack;
        if(itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        if(slotIndex == 0 && !flag) {
            this.totalCookTime = this.getCookTime(itemStack);
            this.cookTime = 0;
            this.markDirty();
        }

    }

    public String getName() {
        return this.hasCustomName()?this.furnaceCustomName: Util.prefix("container.nether_furnace");
    }

    public boolean hasCustomName() {
        return this.furnaceCustomName != null && this.furnaceCustomName.length() > 0;
    }

    public void setCustomInventoryName(String p_setCustomInventoryName_1_) {
        this.furnaceCustomName = p_setCustomInventoryName_1_;
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        NBTTagList nbttaglist = tagCompound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            byte j = nbttagcompound.getByte("Slot");
            if(j >= 0 && j < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }

        this.furnaceBurnTime = tagCompound.getShort("BurnTime");
        this.cookTime = tagCompound.getShort("CookTime");
        this.totalCookTime = tagCompound.getShort("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
        if(tagCompound.hasKey("CustomName", 8)) {
            this.furnaceCustomName = tagCompound.getString("CustomName");
        }

    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
        tagCompound.setShort("CookTime", (short)this.cookTime);
        tagCompound.setShort("CookTimeTotal", (short)this.totalCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for(int i = 0; i < this.furnaceItemStacks.length; ++i) {
            if(this.furnaceItemStacks[i] != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        tagCompound.setTag("Items", nbttaglist);
        if(this.hasCustomName()) {
            tagCompound.setString("CustomName", this.furnaceCustomName);
        }

    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    public void update() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if(this.isBurning()) {
            --this.furnaceBurnTime;
        }

        if(!this.worldObj.isRemote) {
            if(this.isBurning() || this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null) {
                if(!this.isBurning() && this.canSmelt()) {
                    this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
                    if(this.isBurning()) {
                        flag1 = true;
                        if(this.furnaceItemStacks[1] != null) {
                            --this.furnaceItemStacks[1].stackSize;
                            if(this.furnaceItemStacks[1].stackSize == 0) {
                                this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem().getContainerItem(this.furnaceItemStacks[1]);
                            }
                        }
                    }
                }

                if(this.isBurning() && this.canSmelt()) {
                    ++this.cookTime;
                    if(this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.furnaceItemStacks[0]);
                        this.smeltItem();
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if(!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
            }

            if(flag != this.isBurning()) {
                flag1 = true;
                BlockNetherFurnace.setState(this.isBurning(), this.worldObj, this.pos);
            }
        }

        if(flag1) {
            this.markDirty();
        }

    }

    public int getCookTime(ItemStack itemStack) {
        // TODO: based on the current surrounding blocks and world state
        return 200;
    }

    private boolean canSmelt() {
        if(this.furnaceItemStacks[0] == null) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
            if(itemstack == null) {
                return false;
            } else if(this.furnaceItemStacks[2] == null) {
                return true;
            } else if(!this.furnaceItemStacks[2].isItemEqual(itemstack)) {
                return false;
            } else {
                int result = this.furnaceItemStacks[2].stackSize + itemstack.stackSize;
                return result <= this.getInventoryStackLimit() && result <= this.furnaceItemStacks[2].getMaxStackSize();
            }
        }
    }

    public void smeltItem() {
        if(this.canSmelt()) {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
            if(this.furnaceItemStacks[2] == null) {
                this.furnaceItemStacks[2] = itemstack.copy();
            } else if(this.furnaceItemStacks[2].getItem() == itemstack.getItem()) {
                this.furnaceItemStacks[2].stackSize += itemstack.stackSize;
            }

            // Not supported in nether furnace, but good to know
            /*
            if(this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge) && this.furnaceItemStacks[0].getMetadata() == 1 && this.furnaceItemStacks[1] != null && this.furnaceItemStacks[1].getItem() == Items.bucket) {
                this.furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
            }
            */
            --this.furnaceItemStacks[0].stackSize;
            if(this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }

    }

    public static int getItemBurnTime(ItemStack itemStack) {
        return net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(itemStack);
    }

    public static boolean isItemFuel(ItemStack itemStack) {
        return getItemBurnTime(itemStack) > 0;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void openInventory(EntityPlayer player) {
    }

    public void closeInventory(EntityPlayer player) {
    }

    public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack) {
        return slotIndex != 2 && (slotIndex != 1 || isItemFuel(itemStack));
    }

    public int[] getSlotsForFace(EnumFacing facing) {
        return facing == EnumFacing.DOWN
                ?slotsBottom
                :(facing == EnumFacing.UP?slotsTop:slotsSides);
    }

    public boolean canInsertItem(int slotIndex, ItemStack itemStack, EnumFacing facing) {
        return this.isItemValidForSlot(slotIndex, itemStack);
    }

    public boolean canExtractItem(int slotIndex, ItemStack itemStack, EnumFacing facing) {
        if(facing == EnumFacing.DOWN && slotIndex == 1) {
            Item item = itemStack.getItem();
            if(item != Items.bucket) {
                return false;
            }
        }

        return true;
    }

    public String getGuiID() {
        return "nethercore:furnace";
    }

    public Container createContainer(InventoryPlayer inventory, EntityPlayer player) {
        return new ContainerFurnace(inventory, this);
    }

    public int getField(int fieldId) {
        switch(fieldId) {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    public void setField(int fieldId, int fieldValue) {
        switch(fieldId) {
            case 0:
                this.furnaceBurnTime = fieldValue;
                break;
            case 1:
                this.currentItemBurnTime = fieldValue;
                break;
            case 2:
                this.cookTime = fieldValue;
                break;
            case 3:
                this.totalCookTime = fieldValue;
        }

    }

    public int getFieldCount() {
        return 4;
    }

    public void clear() {
        for(int i = 0; i < this.furnaceItemStacks.length; ++i) {
            this.furnaceItemStacks[i] = null;
        }

    }

    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                ? (T) (facing == EnumFacing.DOWN
                    ? this.handlerBottom
                    : (facing == EnumFacing.UP
                        ? this.handlerTop
                        : this.handlerSide))
                :super.getCapability(capability, facing);
    }
}