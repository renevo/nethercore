package com.renevo.nethercore.tileentity;

import com.renevo.nethercore.Util;
import com.renevo.nethercore.blocks.BlockNetherFurnace;
import com.renevo.nethercore.inventory.ContainerNetherFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
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
import net.minecraftforge.items.wrapper.EmptyHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityNetherFurnace extends TileEntityLockable implements ITickable, ISidedInventory {
    private static final int[] slotsTop = new int[]{0};
    private static final int[] slotsBottom = new int[]{1};
    private ItemStack[] furnaceItemStacks = new ItemStack[2];
    private int furnaceBurnTime;
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;
    private String furnaceCustomName;
    IItemHandler handlerTop;
    IItemHandler handlerBottom;
    IItemHandler handlerSides;

    public TileEntityNetherFurnace() {
        this.handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
        this.handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
        this.handlerSides = new EmptyHandler();

        // faking these until we remove them
        this.furnaceBurnTime = 0;
        this.currentItemBurnTime = 0;
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
        boolean canInsert = itemStack != null && itemStack.isItemEqual(this.furnaceItemStacks[slotIndex]) && ItemStack.areItemStackTagsEqual(itemStack, this.furnaceItemStacks[slotIndex]);
        this.furnaceItemStacks[slotIndex] = itemStack;
        if(itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        if(slotIndex == 0 && !canInsert) {
            this.totalCookTime = this.getCookTime(itemStack);
            this.cookTime = 0;
            this.markDirty();
        }

    }

    public String getName() {
        return this.hasCustomName()?this.furnaceCustomName: "container." + Util.prefix("nether_furnace");
    }

    public boolean hasCustomName() {
        return this.furnaceCustomName != null && this.furnaceCustomName.length() > 0;
    }

    public void setCustomInventoryName(String name) {
        this.furnaceCustomName = name;
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
        this.currentItemBurnTime = this.furnaceBurnTime;
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
        boolean burning = this.isBurning();
        boolean updated = false;
        int burnTime = this.furnaceBurnTime;
        int burnSpeed = this.getBurnSpeed();

        this.currentItemBurnTime = (this.furnaceItemStacks[0] != null ? 300 : 0);
        double burnMultiplier = Math.floor(((double)burnSpeed / 5d) * 300d);
        this.furnaceBurnTime = this.currentItemBurnTime > 0 ? (int)burnMultiplier : 0;

        if(!this.worldObj.isRemote) {
            if (this.furnaceBurnTime != burnTime) {
                updated = true;
            }
            if(this.isBurning() && this.furnaceItemStacks[0] != null) {
                if (this.isBurning() && this.canSmelt()) {

                    this.cookTime += burnSpeed;

                    if (this.cookTime >= this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.furnaceItemStacks[0]);
                        this.smeltItem();
                        updated = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if(!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
            }

            if(burning != this.isBurning()) {
                updated = true;
                BlockNetherFurnace.setState(this.isBurning(), this.worldObj, this.pos);
            }
        }

        if(updated) {
            this.markDirty();
        }

    }

    private int getBurnSpeed() {
        int speed = 1;

        if (!this.worldObj.provider.doesWaterVaporize()) {
            return 0;
        }

        IBlockState eastBlockState = this.worldObj.getBlockState(this.pos.east());
        IBlockState westBlockState = this.worldObj.getBlockState(this.pos.west());
        IBlockState northBlockState = this.worldObj.getBlockState(this.pos.north());
        IBlockState southBlockState = this.worldObj.getBlockState(this.pos.south());

        if (eastBlockState.getBlock() == Blocks.lava || eastBlockState.getBlock() == Blocks.flowing_lava) {
            speed++;
        }

        if (westBlockState.getBlock() == Blocks.lava || westBlockState.getBlock() == Blocks.flowing_lava) {
            speed++;
        }

        if (northBlockState.getBlock() == Blocks.lava || northBlockState.getBlock() == Blocks.flowing_lava) {
            speed++;
        }

        if (southBlockState.getBlock() == Blocks.lava || southBlockState.getBlock() == Blocks.flowing_lava) {
            speed++;
        }

        return speed;
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
            } else if(this.furnaceItemStacks[1] == null) {
                return true;
            } else if(!this.furnaceItemStacks[1].isItemEqual(itemstack)) {
                return false;
            } else {
                int result = this.furnaceItemStacks[1].stackSize + itemstack.stackSize;
                return result <= this.getInventoryStackLimit() && result <= this.furnaceItemStacks[1].getMaxStackSize();
            }
        }
    }

    public void smeltItem() {
        if(this.canSmelt()) {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
            if(this.furnaceItemStacks[1] == null) {
                this.furnaceItemStacks[1] = itemstack.copy();
            } else if(this.furnaceItemStacks[1].getItem() == itemstack.getItem()) {
                this.furnaceItemStacks[1].stackSize += itemstack.stackSize;
            }

            --this.furnaceItemStacks[0].stackSize;
            if(this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void openInventory(EntityPlayer player) {
    }

    public void closeInventory(EntityPlayer player) {
    }

    public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack) {
        return slotIndex != 2;
    }

    public int[] getSlotsForFace(EnumFacing facing) {
        if (facing == EnumFacing.DOWN) {
            return slotsBottom;
        }

        if (facing == EnumFacing.UP) {
            return slotsTop;
        }

        return new int[0];
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
        return new ContainerNetherFurnace(inventory, this);
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
                        : this.handlerSides))
                :super.getCapability(capability, facing);
    }
}