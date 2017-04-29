package com.renevo.nethercore.tileentity;

import com.renevo.nethercore.Util;
import com.renevo.nethercore.blocks.BlockNetherFurnace;
import com.renevo.nethercore.inventory.ContainerNetherFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;

public class TileEntityNetherFurnace extends TileEntityLockable implements ITickable, ISidedInventory {
    private static final int[] slotsTop = new int[]{0};
    private static final int[] slotsBottom = new int[]{1};
    private ItemStack[] furnaceItemStacks = new ItemStack[2];
    private int furnaceBurnTime;
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;
    private String furnaceCustomName;
    private IItemHandler handlerTop;
    private IItemHandler handlerBottom;
    private IItemHandler handlerSides;

    public TileEntityNetherFurnace() {
        this.handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
        this.handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
        this.handlerSides = new EmptyHandler();

        // faking these until we remove them
        this.furnaceBurnTime = 0;
        this.currentItemBurnTime = 0;
    }

    @Override
    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    @Override public boolean isEmpty() {
        return furnaceItemStacks[0].isEmpty() && furnaceItemStacks[1].isEmpty() && furnaceItemStacks[2].isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return this.furnaceItemStacks[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int amount) {
        if (this.furnaceItemStacks[slotIndex] != null) {
            ItemStack itemstack;
            if (this.furnaceItemStacks[slotIndex].getCount() <= amount) {
                itemstack = this.furnaceItemStacks[slotIndex];
                this.furnaceItemStacks[slotIndex] = null;
                return itemstack;
            } else {
                itemstack = this.furnaceItemStacks[slotIndex].splitStack(amount);
                if (this.furnaceItemStacks[slotIndex].getCount() == 0) {
                    this.furnaceItemStacks[slotIndex] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int slotIndex) {
        if (this.furnaceItemStacks[slotIndex] != null) {
            ItemStack itemstack = this.furnaceItemStacks[slotIndex];
            this.furnaceItemStacks[slotIndex] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, @Nonnull ItemStack itemStack) {
        boolean canInsert =
            itemStack.isItemEqual(this.furnaceItemStacks[slotIndex]) && ItemStack.areItemStackTagsEqual(itemStack, this.furnaceItemStacks[slotIndex]);
        this.furnaceItemStacks[slotIndex] = itemStack;
        if (itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }

        if (slotIndex == 0 && !canInsert) {
            this.totalCookTime = this.getCookTime(itemStack);
            this.cookTime = 0;
            this.markDirty();
        }

    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.furnaceCustomName : "container." + Util.prefix("nether_furnace");
    }

    @Override
    public boolean hasCustomName() {
        return this.furnaceCustomName != null && this.furnaceCustomName.length() > 0;
    }

    public void setCustomInventoryName(String name) {
        this.furnaceCustomName = name;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        NBTTagList nbttaglist = tagCompound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            byte j = nbttagcompound.getByte("Slot");
            if (j >= 0 && j < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[j] = new ItemStack(nbttagcompound);
            }
        }

        this.furnaceBurnTime = tagCompound.getShort("BurnTime");
        this.cookTime = tagCompound.getShort("CookTime");
        this.totalCookTime = tagCompound.getShort("CookTimeTotal");
        this.currentItemBurnTime = this.furnaceBurnTime;
        if (tagCompound.hasKey("CustomName", 8)) {
            this.furnaceCustomName = tagCompound.getString("CustomName");
        }

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        NBTTagCompound result = super.writeToNBT(tagCompound);

        tagCompound.setShort("BurnTime", (short) this.furnaceBurnTime);
        tagCompound.setShort("CookTime", (short) this.cookTime);
        tagCompound.setShort("CookTimeTotal", (short) this.totalCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
            if (this.furnaceItemStacks[i] != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        tagCompound.setTag("Items", nbttaglist);
        if (this.hasCustomName()) {
            tagCompound.setString("CustomName", this.furnaceCustomName);
        }

        return result;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) == this
            && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
            (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    @Override
    public void update() {
        boolean burning = this.isBurning();
        boolean updated = false;
        int burnTime = this.furnaceBurnTime;
        int burnSpeed = this.getBurnSpeed();

        this.currentItemBurnTime = (this.furnaceItemStacks[0] != null ? 300 : 0);
        double burnMultiplier = Math.floor(((double) burnSpeed / 5d) * 300d);
        this.furnaceBurnTime = this.currentItemBurnTime > 0 ? (int) burnMultiplier : 0;

        if (!this.world.isRemote) {
            if (this.furnaceBurnTime != burnTime) {
                updated = true;
            }
            if (this.isBurning() && this.furnaceItemStacks[0] != null) {
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
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (burning != this.isBurning()) {
                updated = true;
                BlockNetherFurnace.setState(this.isBurning(), this.world, this.pos);
            }
        }

        if (updated) {
            this.markDirty();
        }

    }

    private int getBurnSpeed() {
        // sets the base speed, 1 if in nether, otherwise, none and requires lava on one side
        int speed = this.world.provider.doesWaterVaporize() ? 1 : 0;

        IBlockState eastBlockState = this.world.getBlockState(this.pos.east());
        IBlockState westBlockState = this.world.getBlockState(this.pos.west());
        IBlockState northBlockState = this.world.getBlockState(this.pos.north());
        IBlockState southBlockState = this.world.getBlockState(this.pos.south());

        // TODO: These should be based on fluids as well when possible
        if (eastBlockState.getBlock() == Blocks.LAVA || eastBlockState.getBlock() == Blocks.FLOWING_LAVA) {
            speed++;
        }

        if (westBlockState.getBlock() == Blocks.LAVA || westBlockState.getBlock() == Blocks.FLOWING_LAVA) {
            speed++;
        }

        if (northBlockState.getBlock() == Blocks.LAVA || northBlockState.getBlock() == Blocks.FLOWING_LAVA) {
            speed++;
        }

        if (southBlockState.getBlock() == Blocks.LAVA || southBlockState.getBlock() == Blocks.FLOWING_LAVA) {
            speed++;
        }

        return speed;
    }

    public int getCookTime(ItemStack itemStack) {
        return 200 * 4; // 800 base, with 5x you can get 1.25 the speed of a standard furnace
    }

    private boolean canSmelt() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
            if (itemstack == null) {
                return false;
            } else if (this.furnaceItemStacks[1] == null) {
                return true;
            } else if (!this.furnaceItemStacks[1].isItemEqual(itemstack)) {
                return false;
            } else {
                int result = this.furnaceItemStacks[1].getCount() + itemstack.getCount();
                return result <= this.getInventoryStackLimit() && result <= this.furnaceItemStacks[1].getMaxStackSize();
            }
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
            if (this.furnaceItemStacks[1] == null) {
                this.furnaceItemStacks[1] = itemstack.copy();
            } else if (this.furnaceItemStacks[1].getItem() == itemstack.getItem()) {
                this.furnaceItemStacks[1].grow(itemstack.getCount());
            }

            this.furnaceItemStacks[0].shrink(-1);
            if (this.furnaceItemStacks[0].getCount() <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack) {
        return slotIndex != 2;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing facing) {
        if (facing == EnumFacing.DOWN) {
            return slotsBottom;
        }

        if (facing == EnumFacing.UP) {
            return slotsTop;
        }

        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slotIndex, ItemStack itemStack, EnumFacing facing) {
        return this.isItemValidForSlot(slotIndex, itemStack);
    }

    @Override
    public boolean canExtractItem(int slotIndex, ItemStack itemStack, EnumFacing facing) {
        return true;
    }

    @Override
    public String getGuiID() {
        // not sure what this does
        return Util.resource("nether_furnace");
    }

    @Override
    public Container createContainer(InventoryPlayer inventory, EntityPlayer player) {
        return new ContainerNetherFurnace(inventory, this);
    }

    @Override
    public int getField(int fieldId) {
        switch (fieldId) {
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

    @Override
    public void setField(int fieldId, int fieldValue) {
        switch (fieldId) {
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

    @Override
    public int getFieldCount() {
        return 4;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
            this.furnaceItemStacks[i] = null;
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (facing == null || capability != CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return super.getCapability(capability, facing);
        }

        switch (facing) {
            case DOWN:
                return (T) this.handlerBottom;
            case UP:
                return (T) this.handlerTop;
            default:
                return (T) this.handlerSides;
        }
    }
}