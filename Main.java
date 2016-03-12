package newo2001.JAMM;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import newo2001.JAMM.blocks.Charger;
import newo2001.JAMM.blocks.Cutter;
import newo2001.JAMM.blocks.UmoniaBlock;
import newo2001.JAMM.blocks.UmoniaOre;
import newo2001.JAMM.guis.GuiHandler;
import newo2001.JAMM.items.ChargedCrystal;
import newo2001.JAMM.items.ChargedUmonia;
import newo2001.JAMM.items.UmoniaIngot;
import newo2001.JAMM.items.UmoniaNugget;
import newo2001.JAMM.items.UmoniaWire;
import newo2001.JAMM.items.UnstableUmoniaIngot;
import newo2001.JAMM.tileentities.TileEntityCharger;
import newo2001.JAMM.tileentities.TileEntityCutter;
import newo2001.JAMM.world.OreGen;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {

	public static final String MODID = "JAMM";
	public static final String VERSION = "1.7.10";
	public static final String MODVER = "0.0.1";

	@Instance(value = MODID)
	public static Main instance = new Main();

	final Random random = new Random();

	public static Block blockOreUmonia;
	public static Item itemIngotUmonia;
	public static Block blockUmonia;
	public static OreGen oreGen = new OreGen();
	public static Block blockCharger;
	public static Item itemIngotChargedUmonia;
	public static Item itemNuggetUmonia;
	public static Item itemIngotUnstableUmonia;
	public static Item itemChargedCrystal;
	public static Item itemWireUmonia;
	public static Block blockCutter;

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		System.out.println("[" + MODID + "] Pre initializing...");

		UmoniaOre blockOreUmonia = new UmoniaOre();
		UmoniaIngot itemIngotUmonia = new UmoniaIngot();
		UmoniaBlock blockUmonia = new UmoniaBlock();
		Charger blockCharger = new Charger();
		ChargedUmonia itemIngotChargedUmonia = new ChargedUmonia();
		UmoniaNugget itemNuggetUmonia = new UmoniaNugget();
		UnstableUmoniaIngot itemIngotUnstableUmonia = new UnstableUmoniaIngot();
		ChargedCrystal itemChargedCrystal = new ChargedCrystal();
		UmoniaWire itemWireUmonia = new UmoniaWire();
		Cutter blockCutter = new Cutter();

		GameRegistry.registerBlock(blockOreUmonia, "umonia_ore");
		GameRegistry.registerBlock(blockUmonia, "umonia_block");
		GameRegistry.registerItem(itemIngotUmonia, "umonia_ingot");
		GameRegistry.registerBlock(blockCharger, "charger");
		GameRegistry.registerItem(itemIngotChargedUmonia, "charged_umonia_ingot");
		GameRegistry.registerItem(itemNuggetUmonia, "umonia_nugget");
		GameRegistry.registerItem(itemIngotUnstableUmonia, "unstable_umonia_ingot");
		GameRegistry.registerItem(itemChargedCrystal, "charged_crystal");
		GameRegistry.registerItem(itemWireUmonia, "umonia_wire");
		GameRegistry.registerBlock(blockCutter, "cutter");
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		System.out.println("[" + MODID + "] Initializing...");
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		MinecraftForge.EVENT_BUS.register(new newo2001.JAMM.EventHandler());
		FMLCommonHandler.instance().bus().register(new newo2001.JAMM.EventHandler());

		GameRegistry.registerTileEntity(TileEntityCharger.class, "tileEntityCharger");
		GameRegistry.registerTileEntity(TileEntityCutter.class, "tileEntityCutter");

		ItemStack stackIngotUmonia = new ItemStack(GameRegistry.findItem(MODID, "umonia_ingot"));
		ItemStack stackBlockUmonia = new ItemStack(GameRegistry.findItem(MODID, "umonia_block"));
		ItemStack stackOreUmonia = new ItemStack(GameRegistry.findItem(MODID, "umonia_ore"));
		ItemStack stackNuggetUmonia = new ItemStack(GameRegistry.findItem(MODID, "umonia_nugget"));
		ItemStack stackIngotChargedUmonia = new ItemStack(GameRegistry.findItem(MODID, "charged_umonia_ingot"));
		ItemStack stackChargedCrystal = new ItemStack(GameRegistry.findItem(MODID, "charged_crystal"));
		ItemStack stackIngotUnstableUmonia = new ItemStack(GameRegistry.findItem(MODID, "unstable_umonia_ingot"));
		ItemStack stackEmerald = new ItemStack(Items.emerald);
		ItemStack stackWireUmonia = new ItemStack(GameRegistry.findItem(MODID, "umonia_wire"));

		// Shaped recipes
		GameRegistry.addShapedRecipe(stackBlockUmonia, "xxx", "xxx", "xxx", 'x', stackIngotUmonia);
		GameRegistry.addShapedRecipe(stackIngotUmonia, "xxx", "xxx", "xxx", 'x', stackNuggetUmonia);

		// Shapeless recipes
		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem(MODID, "umonia_ingot"), 9), stackBlockUmonia);
		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem(MODID, "umonia_nugget"), 9), stackIngotUmonia);

		// Smelting recipes
		GameRegistry.addSmelting(stackOreUmonia, stackIngotUmonia, 2.0f);

		// Charger recipes
		TileEntityCharger.addRecipe(stackIngotUmonia, stackIngotChargedUmonia);
		TileEntityCharger.addRecipe(stackEmerald, stackChargedCrystal, 60, 5000);

		// Cutter recipes
		TileEntityCutter.addRecipe(stackIngotUmonia, stackWireUmonia);

		// World Generators
		GameRegistry.registerWorldGenerator(oreGen, 1);
	}

	@EventHandler
	public void PostInit(FMLPostInitializationEvent event) {
		System.out.println("[" + MODID + "] Post initializing...");
	}
}
