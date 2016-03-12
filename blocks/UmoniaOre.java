package newo2001.JAMM.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class UmoniaOre extends Block {
	public UmoniaOre() {
		super(Material.rock);
		setBlockName("umonia_ore");
		setBlockTextureName("JAMM:umonia_ore");
		setHardness(4.0f);
		setResistance(15.0f);
		setHarvestLevel("pickaxe", 3);
		setStepSound(soundTypeStone);
		setCreativeTab(CreativeTabs.tabBlock);
	}
}
