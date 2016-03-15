public class Caster extends TileEntity implements IInventory {
  
  private static Map<Fluid, ItemStack> recipes = new HashMap<Fluid, ItemStack>();
  private static Map<Fluid, int> recipesTime = new HashMap<Fluid, int>();
  private int progress;
  private ItemStack[] inventory;
  
  public Caster() {
    super();
  }
  
  @Override
  public int getSizeInventory() {
    return 2;
  }
  
  @Override
  public void updateEntity() {
    //GetFluidFromContainer() is probably not the correct method
    if (getStackInSlot(0) == null || getStackInSlot(0).stacksize == 0 || !recipes.contains(getStackInSlot(0).getItem().getFluidFromContainer())
      return;
    if (progress < getCastTime())
      progress++;
    if (progress >= getCastTime()) {
      //GetFluidFromContainer() is probably not the correct method
      getStackInSlot(1) = recipes.get(getStackInSlot(0).getItem().getFluidFromContainer())
      getStackInSlot(0) = new ItemStack(Items.bucket);
    }
  }
  
  public static void addRecipe(Fluid fluid, ItemStack result) {
    addRecipe(fluid, result, 100);
  }
  
  public static void addRecipe(Fluid fluid, ItemStack result, time) {
    recipes.put(fluid, result);
    recipesTime.put(fluid, time);
  }
}
