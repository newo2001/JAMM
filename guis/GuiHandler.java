package newo2001.JAMM.guis;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import newo2001.JAMM.tileentities.*;
import newo2001.JAMM.container.*;
import newo2001.JAMM.guis.*;

public class GuiHandler implements IGuiHandler {
	
	public static final int TILE_ENTITY_CHARGER_GUI = 0;
	public static final int TILE_ENTITY_ASSEMBLER_GUI = 1;
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == TILE_ENTITY_CHARGER_GUI)
			return new ContainerCharger(player.inventory, (TileEntityCharger) world.getTileEntity(x, y, z));
		else if (id == TILE_ENTITY_ASSEMBLER_GUI)
			return new ContainerAssembler(player.inventory, (TileEntityAssembler) world.getTileEntity(x, y, z));
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == TILE_ENTITY_CHARGER_GUI)
		    return new GuiCharger(player.inventory, (TileEntityCharger) world.getTileEntity(x,y,z));
		else if (id == TILE_ENTITY_ASSEMBLER_GUI)
			return new GuiAssembler(player.inventory, (TileEntityAssembler) world.getTileEntity(x,y,z));
		return null;
	}
}
