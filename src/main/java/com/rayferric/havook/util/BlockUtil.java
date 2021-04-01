package com.rayferric.havook.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class BlockUtil {
	public static boolean isCollidable(Block block) {
		return block != Blocks.AIR && block != Blocks.BEETROOTS && block != Blocks.CARROTS && block != Blocks.DEADBUSH
				&& block != Blocks.DOUBLE_PLANT && block != Blocks.FLOWING_LAVA && block != Blocks.FLOWING_WATER
				&& block != Blocks.LAVA && block != Blocks.MELON_STEM && block != Blocks.NETHER_WART
				&& block != Blocks.POTATOES && block != Blocks.PUMPKIN_STEM && block != Blocks.RED_FLOWER
				&& block != Blocks.RED_MUSHROOM && block != Blocks.REDSTONE_TORCH && block != Blocks.TALLGRASS
				&& block != Blocks.TORCH && block != Blocks.UNLIT_REDSTONE_TORCH && block != Blocks.YELLOW_FLOWER
				&& block != Blocks.VINE && block != Blocks.WATER && block != Blocks.WEB && block != Blocks.WHEAT;
	}
}
