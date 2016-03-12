package newo2001.JAMM.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import newo2001.JAMM.tileentities.TileEntityCutter;

public class ContainerCutter extends Container {
	private TileEntityCutter te;
	private Slot slot;

	//SLOTS: Tile Entity 0-1 ........... 0 - 1 Player Hotbar 0-8 ....... 2 - 10 Player Inventory 0-26 ... 11 - 37
	public ContainerCutter(IInventory playerInv, TileEntityCutter te) {
		this.te = te;

		// Tile Entity, Slot 0-1
		this.addSlotToContainer(new Slot(te, 0, 54, 35));
		this.addSlotToContainer(new Slot(te, 1, 107, 35));

		// Player Inventory, Slot 1-27, Slot IDs 11-37
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, x * 18 + 8, 84 + y * 18));

		// Player Inventory, Slot 0-8, Slot IDs 2-10
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

			if (fromSlot >= 0 && fromSlot <= 1) {
				// From TE Inventory to Player Inventory
				if (!this.mergeItemStack(current, 1, 37, true))
					return null;
			} else {
				// From Player Inventory to TE Inventory
				if (!this.mergeItemStack(current, 0, 1, true))
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
}
