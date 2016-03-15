public class ItemUtilities {
	public static void dropContainerItems(ItemStack[] inventory, World world, int x, int y, int z) {
		Random rand = new Random();
		for (int i = 0; i < inventory.size; i++) {
			ItemStack stack = inventory[i]
			if (stack != null && stack.stacksize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
      	float ry = rand.nextFloat() * 0.8F + 0.1F;
      	float rz = rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityItem = new EntityItem(x + rx, y + ry, z + rz, new ItemStack(stack.getItem(), item.stackSize, item.getItemDamage()));
				if (item.hasTagCompound()) {
      		entityItem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
				}
				final float factor = 0.05F;
        entityItem.motionX = rand.nextGaussian() * factor;
        entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
        entityItem.motionZ = rand.nextGaussian() * factor;
        world.spawnEntityInWorld(entityItem);
        stack.stackSize = 0;
			}
		}
	}
}
