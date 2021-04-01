package com.rayferric.havook.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.util.math.Vec3d;

public class EntityUtil {
	public static boolean isAnimal(Entity entity) {
		if (entity instanceof EntityAgeable || entity instanceof EntityAmbientCreature
				|| entity instanceof EntityWaterMob || entity instanceof EntityGolem)
			return true;
		return false;
	}

	public static Vec3d getInterpolatedPos(Entity entity, float partialTicks) {
		Vec3d from = new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);
		return entity.getPositionVector().subtract(from).scale(partialTicks).add(from);
	}
}
