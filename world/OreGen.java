package newo2001.JAMM.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import newo2001.JAMM.Main;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class OreGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId) {
		case 0:
			generateOverworld(world, chunkX, chunkZ, random);
			break;
		case -1:
			generateNether(world, chunkX, chunkZ, random);
			break;
		case 1:
			generateEnd(world, chunkX, chunkZ, random);
			break;
		}
	}
	
	private void generateOverworld(World world, int chunkX, int chunkZ, Random random) {
		generateOre(GameRegistry.findBlock(Main.MODID, "blockOreUmonia"), world, random, chunkX, chunkZ, 2, 7, 4, 1, 19, Blocks.stone);
	}
	
	private void generateNether(World world, int chunkX, int chunkZ, Random random) {
		
	}
	
	private void generateEnd(World world, int chunkX, int chunkZ, Random random) {
		
	}
	
	private void generateOre(Block block, World world, Random random, int chunkX, int chunkZ, int minVeinSize, int maxVeinSize, int chance, int minY, int maxY, Block generateIn) {
		for (int i = 0; i < chance; i++) {
			int veinSize = random.nextInt(maxVeinSize-minVeinSize)+minVeinSize;
			int x = random.nextInt(16)+chunkX*16;
			int y = random.nextInt(maxY-minY) + minY;
			int z = random.nextInt(16)+chunkZ*16;
			WorldGenMinable gen = new WorldGenMinable(block, veinSize);
			gen.generate(world, random, x, y, z);
		}
	}

}
