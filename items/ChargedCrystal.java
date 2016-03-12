package newo2001.JAMM.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ChargedCrystal extends Item {
	public ChargedCrystal() {
		maxStackSize = 64;
		setUnlocalizedName("charged_crystal");
		setTextureName("JAMM:charged_crystal");
		setCreativeTab(CreativeTabs.tabMaterials);
	}
}
