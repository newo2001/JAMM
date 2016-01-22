package newo2001.JAMM;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import newo2001.JAMM.*;
import newo2001.JAMM.blocks.*;
import newo2001.JAMM.container.*;
import newo2001.JAMM.guis.*;
import newo2001.JAMM.tileentities.*;
import newo2001.JAMM.world.*;
import newo2001.JAMM.items.*;
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
	
	@Instance(value=MODID)
    public static Main instance = new Main();
	
	final Random random = new Random();
	
	public static Block blockOreUmonia;
	public static Item itemIngotUmonia;
	public static Block blockUmonia;
	public static OreGen oreGen = new OreGen();
	public static Block blockCharger;
	public static Item itemIngotChargedUmonia;
	public static Block blockAssembler;
	public static Item itemNuggetUmonia;
	public static Block blockOreSome;
	public static Item itemIngotSome;
	public static Item itemNuggetSome;
	public static Block blockSome;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		System.out.println("[" + MODID + "] Pre initializing...");
		
		UmoniaOre blockOreUmonia = new UmoniaOre();
		UmoniaIngot itemIngotUmonia = new UmoniaIngot();
		UmoniaBlock blockUmonia = new UmoniaBlock();
		Charger blockCharger = new Charger();
		ChargedUmonia itemIngotChargedUmonia = new ChargedUmonia();
		Assembler blockAssembler = new Assembler();
		UmoniaNugget itemNuggetUmonia = new UmoniaNugget();
		SomeOre blockOreSome = new SomeOre();
		SomeIngot itemIngotSome = new SomeIngot();
		SomeNugget itemNuggetSome = new SomeNugget();
		SomeBlock blockSome = new SomeBlock();
		
		GameRegistry.registerBlock(blockOreUmonia, "blockOreUmonia");
		GameRegistry.registerBlock(blockUmonia, "blockUmonia");
		GameRegistry.registerItem(itemIngotUmonia, "itemIngotUmonia");
		GameRegistry.registerBlock(blockCharger, "blockCharger");
		GameRegistry.registerItem(itemIngotChargedUmonia, "itemIngotChargedUmonia");
		GameRegistry.registerBlock(blockAssembler, "blockAssembler");
		GameRegistry.registerItem(itemNuggetUmonia, "itemNuggetUmonia");
		GameRegistry.registerBlock(blockOreSome, "blockOreSome");
		GameRegistry.registerItem(itemIngotSome, "itemIngotSome");
		GameRegistry.registerItem(itemNuggetSome, "itemNuggetSome");
		GameRegistry.registerBlock(blockSome, "blockSome");
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		System.out.println("[" + MODID + "] Initializing...");
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		MinecraftForge.EVENT_BUS.register(new newo2001.JAMM.EventHandler());
		FMLCommonHandler.instance().bus().register(new newo2001.JAMM.EventHandler());
		
		GameRegistry.registerTileEntity(TileEntityCharger.class, "tileEntityCharger");
		GameRegistry.registerTileEntity(TileEntityAssembler.class, "tileEntityAssembler");
		
		ItemStack stackIngotUmonia = new ItemStack(GameRegistry.findItem(MODID, "itemIngotUmonia"));
		ItemStack stackBlockUmonia = new ItemStack(GameRegistry.findItem(MODID, "blockUmonia"));
		ItemStack stackOreUmonia = new ItemStack(GameRegistry.findItem(MODID, "blockOreUmonia"));
		ItemStack stackNuggetUmonia = new ItemStack(GameRegistry.findItem(MODID, "itemNuggetUmonia"));
		ItemStack stackBlockSome = new ItemStack(GameRegistry.findItem(MODID, "blockSome"));
		ItemStack stackIngotSome = new ItemStack(GameRegistry.findItem(MODID, "itemIngotSome"));
		ItemStack stackNuggetSome = new ItemStack(GameRegistry.findItem(MODID, "itemNuggetSome"));
		ItemStack stackOreSome = new ItemStack(GameRegistry.findItem(MODID, "blockOreSome"));
		
		//Shaped recipes
		GameRegistry.addShapedRecipe(
			stackBlockUmonia,
			"xxx",
			"xxx",
			"xxx",
			'x',stackIngotUmonia
		);
		GameRegistry.addShapedRecipe(
			stackIngotUmonia,
			"xxx",
			"xxx",
			"xxx",
			'x',stackNuggetUmonia
		);
		
		//Shapeless recipes
		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem(MODID, "itemIngotUmonia"), 9), stackBlockUmonia);
		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem(MODID, "itemNuggetUmonia"), 9), stackIngotUmonia);
		
		//Smelting recipes
		GameRegistry.addSmelting(stackOreUmonia, stackIngotUmonia, 2.0f);
		
		GameRegistry.registerWorldGenerator(oreGen, 1);
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event) {
		System.out.println("[" + MODID + "] Post initializing...");
	}
}
