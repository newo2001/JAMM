package newo2001.JAMM.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import newo2001.JAMM.tileentities.TileEntityCharger;

public class ContainerCharger extends Container {
	private TileEntityCharger te;
	private Slot slot;

	/* SLOTS:
	 * 
	 * Tile Entity 0 ........... 0 - 0 Player Hotbar 0-8 ....... 1 - 9 Player Inventory 0-26 ... 10 - 36 */
	public ContainerCharger(IInventory playerInv, TileEntityCharger te) {
		this.te = te;

		// Tile Entity, Slot 0
		this.addSlotToContainer(new Slot(te, 0, 62 + 1 * 18, 17 + 1 * 18));

		// Player Inventory, Slot 1-27, Slot IDs 10-36
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, x * 18 + 8, 84 + y * 18));

		// Player Inventory, Slot 0-8, Slot IDs 1-9
		for (int x = 0; x < 9; x++)
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot) {
		ItemStack previous = null;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			te.markForUpdate();
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot == 0) {
				// From TE Inventory to Player Inventory
				if (!this.mergeItemStack(current, 1, 37, false))
					return null;
			} else {
				// From Player Inventory to TE Inventory
				if (!this.mergeItemStack(current, 0, 1, false))
					return null;
			}

			if (current.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (current.stackSize == previous.stackSize)
				return null;
			slot.onPickupFromSlot(player, current);
		}
		return previous;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
		boolean success = false;
		int index = startIndex;

		if (useEndIndex)
			index = endIndex - 1;

		Slot slot;
		ItemStack stackinslot;

		if (stack.isStackable()) {
			while (stack.stackSize > 0 && (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex)) {
				slot = (Slot) this.inventorySlots.get(index);
				stackinslot = slot.getStack();

				if (stackinslot != null && stackinslot.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == stackinslot.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, stackinslot)) {
					int l = stackinslot.stackSize + stack.stackSize;
					int maxsize = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());

					if (l <= maxsize) {
						stack.stackSize = 0;
						stackinslot.stackSize = l;
						slot.onSlotChanged();
						success = true;
					} else if (stackinslot.stackSize < maxsize) {
						stack.stackSize -= stack.getMaxStackSize() - stackinslot.stackSize;
						stackinslot.stackSize = stack.getMaxStackSize();
						slot.onSlotChanged();
						success = true;
					}
				}

				if (useEndIndex) {
					--index;
				} else {
					++index;
				}
			}
			te.markForUpdate();
		}

		if (stack.stackSize > 0) {
			if (useEndIndex) {
				index = endIndex - 1;
			} else {
				index = startIndex;
			}

			while (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex && stack.stackSize > 0) {
				slot = (Slot) this.inventorySlots.get(index);
				stackinslot = slot.getStack();

				// Forge: Make sure to respect isItemValid in the slot.
				if (stackinslot == null && slot.isItemValid(stack)) {
					if (stack.stackSize <= 1) {
						slot.putStack(stack.copy());
						stack.stackSize = 0;
						success = true;
						break;
					} else {
						ItemStack newstack = stack.copy();
						newstack.stackSize = 1;
						slot.putStack(newstack);
						stack.stackSize--;
						success = true;
					}
				}
				if (useEndIndex) {
					index--;
				} else {
					index++;
				}
				slot.onSlotChanged();
			}
		}
		te.markForUpdate();
		return success;
	}
}
