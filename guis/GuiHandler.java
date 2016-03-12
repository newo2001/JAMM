package newo2001.JAMM.guis;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import newo2001.JAMM.container.ContainerCharger;
import newo2001.JAMM.container.ContainerCutter;
import newo2001.JAMM.tileentities.TileEntityCharger;
import newo2001.JAMM.tileentities.TileEntityCutter;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int TILE_ENTITY_CHARGER_GUI = 0;
	public static final int TILE_ENTITY_CUTTER_GUI = 1;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case 0:
				return new ContainerCharger(player.inventory, (TileEntityCharger) world.getTileEntity(x, y, z));
			case 1:
				return new ContainerCutter(player.inventory, (TileEntityCutter) world.getTileEntity(x, y, z));
			default:
				return null;
		}

	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case TILE_ENTITY_CHARGER_GUI:
				return new GuiCharger(player.inventory, (TileEntityCharger) world.getTileEntity(x, y, z));
			case TILE_ENTITY_CUTTER_GUI:
				return new GuiCutter(player.inventory, (TileEntityCutter) world.getTileEntity(x, y, z));
			default:
				return null;
		}
	}
}
