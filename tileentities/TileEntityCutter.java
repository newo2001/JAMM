package newo2001.JAMM.tileentities;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityCutter extends TileEntity implements IInventory, IEnergyReceiver {

	private ItemStack[] inventory;
	private String customName = "Cutter";
	private int energyStored;
	private int progress;
	private boolean isActive;
	private static ArrayList<Item> recipesInput = new ArrayList<Item>();
	private static ArrayList<Integer> recipesTime = new ArrayList<Integer>();
	private static ArrayList<Integer> recipesEnergy = new ArrayList<Integer>();
	private static ArrayList<ItemStack> recipesResult = new ArrayList<ItemStack>();
	private static ArrayList<Integer> recipesInputDamage = new ArrayList<Integer>();

	public TileEntityCutter() {
		inventory = new ItemStack[getSizeInventory()];
	}

	public String getCustomName() {
		return customName;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection direction) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection direction) {
		return energyStored;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection direction) {
		return 50000;
	}

	@Override
	public int receiveEnergy(ForgeDirection direction, int amount, boolean simulate) {
		if (getMaxEnergyStored(direction) - (energyStored) <= 0) {
			return 0;
		} else if (getMaxEnergyStored(direction) - (energyStored + amount) <= 0) {
			int val = getMaxEnergyStored(direction) - energyStored;
			if (!simulate) {
				energyStored += val;
				markForUpdate();
			}
			return val;
		}
		if (!simulate) {
			energyStored += amount;
			markForUpdate();
		}
		return amount;
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= getSizeInventory())
			return null;
		return inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (getStackInSlot(index) != null) {
			ItemStack itemstack;

			if (getStackInSlot(index).stackSize <= count) {
				itemstack = this.getStackInSlot(index);
				setInventorySlotContents(index, null);
				markDirty();
				return itemstack;
			} else {
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).stackSize <= 0) {
					this.setInventorySlotContents(index, null);
				} else {
					this.setInventorySlotContents(index, getStackInSlot(index));
				}

				markDirty();
				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack stack = getStackInSlot(index);
		setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= getSizeInventory())
			return;

		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();

		if (stack != null && stack.stackSize == 0)
			stack = null;

		inventory[index] = stack;
		markForUpdate();
		markDirty();
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? customName : "container.tileEntityCutter";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return customName != null && !customName.equals("");
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0 && recipesInput.contains(stack.getItem())) {
			return true;
		} else if (slot == 1 && recipesResult.contains(stack.getItem()))
			return true;
		return false;
	}

	public int getCutTime() {
		if (getStackInSlot(0) == null || !isActive || !(recipesInput.contains(getStackInSlot(0).getItem())))
			return -1;
		return recipesTime.get(recipesInput.indexOf(getStackInSlot(0).getItem()));
	}

	@SideOnly(Side.CLIENT)
	public int getPixelsEnergyBar() {
		float var1 = (getMaxEnergyStored(ForgeDirection.UNKNOWN) / 57f);
		return (int) Math.ceil(energyStored / var1);
	}

	@SideOnly(Side.CLIENT)
	public int getPixelsProgressBar() {
		float var1 = (getCutTime() / 57f);
		return (int) Math.ceil(progress / var1);
	}

	public int getProgress() {
		return progress;
	}

	public int getEnergyPerTick() {
		if (!isActive || !(recipesInput.contains(getStackInSlot(0).getItem())))
			return 0;
		return recipesEnergy.get(recipesInput.indexOf(getStackInSlot(0).getItem()));
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}

	public void markForUpdate() {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity() {
		if (getStackInSlot(0) == null) {
			progress = 0;
			return;
		}
		Item item = getStackInSlot(0).getItem();
		if (recipesInput.contains(item) && recipesInputDamage.contains(getStackInSlot(0).getItemDamage())) {
			if (!isActive)
				isActive = true;
			if (energyStored > getEnergyPerTick() && progress < getCutTime()) {
				energyStored -= getEnergyPerTick();
				progress++;
			}
			if (progress >= getCutTime()) {
				//See if item is valid for result slot
				if (getStackInSlot(1) == null || getStackInSlot(1).getItem() == recipesResult.get(recipesInput.indexOf(item)).getItem()) {
					//See if there is enough space in the result slot to put the item(s)
					if (getStackInSlot(1) == null) {
						boolean isActive = false;
						progress = 0;
						setInventorySlotContents(1, recipesResult.get(recipesInput.indexOf(item)));
						decrStackSize(0, 1);
						markForUpdate();
					} else if (getStackInSlot(1).stackSize + recipesResult.get(recipesInput.indexOf(getStackInSlot(0).getItem())).stackSize <= getInventoryStackLimit()) {
						boolean isActive = false;
						progress = 0;
						ItemStack stack = recipesResult.get(recipesInput.indexOf(item)).copy();
						stack.stackSize += getStackInSlot(1).stackSize;
						setInventorySlotContents(1, stack);
						decrStackSize(0, 1);
						markForUpdate();
					}
				}
			}
		} else {
			if (isActive) {
				isActive = false;
				markForUpdate();
			}
		}
	}

	public static void addRecipe(ItemStack input, ItemStack result) {
		addRecipe(input, result, 100, 60);
	}

	public static void addRecipe(ItemStack input, ItemStack result, int time, int energy) {
		recipesInput.add(input.getItem());
		recipesInputDamage.add(input.getItemDamage());
		recipesResult.add(result);
		recipesTime.add(time);
		recipesEnergy.add(energy);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		if (getStackInSlot(0) != null) {
			NBTTagCompound stackTag = new NBTTagCompound();
			stackTag.setByte("Slot", (byte) 0);
			getStackInSlot(0).writeToNBT(stackTag);
			list.appendTag(stackTag);
		}

		nbt.setTag("Items", list);
		if (this.hasCustomInventoryName())
			nbt.setString("CustomName", getCustomName());

		nbt.setInteger("progress", progress);
		nbt.setInteger("energyStored", energyStored);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Items", 10);

		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
		}

		if (nbt.hasKey("CustomName", 8))
			setCustomName(nbt.getString("CustomName"));

		progress = nbt.getInteger("progress");
		energyStored = nbt.getInteger("energyStored");
	}

	public void setCustomName(String displayName) {
		this.customName = displayName;
	}

}
