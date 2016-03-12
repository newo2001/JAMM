package newo2001.JAMM.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class UmoniaBlock extends Block {
	public UmoniaBlock() {
		super(Material.rock);
		setBlockName("umonia_block");
		setBlockTextureName("JAMM:umonia_block");
		setHardness(7.0f);
		setResistance(30.0f);
		setHarvestLevel("pickaxe", 3);
		setStepSound(soundTypeMetal);
		setCreativeTab(CreativeTabs.tabBlock);
	}
}
