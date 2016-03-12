package newo2001.JAMM.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import newo2001.JAMM.Main;
import newo2001.JAMM.guis.GuiHandler;
import newo2001.JAMM.tileentities.TileEntityCutter;

public class Cutter extends Block implements ITileEntityProvider {
	public Cutter() {
		super(Material.iron);
		setBlockName("cutter");
		setBlockTextureName("JAMM:cutter");
		setHardness(4.0f);
		setResistance(30.0f);
		setHarvestLevel("pickaxe", 1);
		setStepSound(soundTypeMetal);
		setCreativeTab(CreativeTabs.tabDecorations);
		isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCutter();
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventID, int param) {
		super.onBlockEventReceived(world, x, y, z, eventID, param);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity == null ? false : tileentity.receiveClientEvent(eventID, param);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int state) {
		super.breakBlock(world, x, y, z, block, state);
		world.removeTileEntity(x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			((TileEntityCutter) world.getTileEntity(x, y, z)).setCustomName(stack.getDisplayName());
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			player.openGui(Main.instance, GuiHandler.TILE_ENTITY_CUTTER_GUI, world, x, y, z);
		}
		return true;
	}
}
