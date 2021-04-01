package com.rayferric.havook.feature.mod.combat;

import com.rayferric.havook.Havook;
import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModAttribute;
import com.rayferric.havook.feature.mod.ModAttributeBoolean;
import com.rayferric.havook.feature.mod.ModAttributeDouble;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import com.rayferric.havook.manager.FriendManager;
import com.rayferric.havook.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class TriggerBotMod extends Mod {
	public transient ModAttributeDouble speed = new ModAttributeDouble("Speed", 7.0);
	public transient ModAttributeDouble range = new ModAttributeDouble("Range", 4.75);
	public transient ModAttributeBoolean useCooldown = new ModAttributeBoolean("UseCooldown", true);
	public transient ModAttributeBoolean attackPlayers = new ModAttributeBoolean("AttackPlayers", true);
	public transient ModAttributeBoolean attackMobs = new ModAttributeBoolean("AttackMobs", true);
	public transient ModAttributeBoolean attackAnimals = new ModAttributeBoolean("AttackAnimals", false);
	public transient ModAttributeBoolean attackInvisibleEntities = new ModAttributeBoolean("AttackInvisibleEntities", true);
	public transient ModAttributeBoolean useAxeToBreakShield = new ModAttributeBoolean("UseAxeToBreakShield", true);
	public transient ModAttributeBoolean attackWhileMainhandInUse = new ModAttributeBoolean("AttackWhileMainhandInUse", false);

	private transient long time;

	public TriggerBotMod() {
		super("triggerbot", "Trigger Bot", "Automatically attacks the entity you're looking at.",
				ModCategoryEnum.COMBAT);
		addAttrib(speed);
		addAttrib(range);
		addAttrib(useCooldown);
		addAttrib(attackPlayers);
		addAttrib(attackMobs);
		addAttrib(attackAnimals);
		addAttrib(attackInvisibleEntities);
		addAttrib(useAxeToBreakShield);
		addAttrib(attackWhileMainhandInUse);
	}

	@Override
	public void onEnable() {
		time = System.currentTimeMillis();
	}

	@Override
	public void onLocalPlayerUpdate() {
		if ((useCooldown.value ? Minecraft.getMinecraft().player.getCooledAttackStrength(0) < 1
				: (speed.value == 0 || System.currentTimeMillis() - time < 1000.0d / speed.value))
				|| Minecraft.getMinecraft().objectMouseOver == null)
			return;

		long newTime = System.currentTimeMillis();
		Entity entity = Minecraft.getMinecraft().objectMouseOver.entityHit;

		if (entity == null)
			return;
		if (Minecraft.getMinecraft().player.getDistance(entity) > range.value)
			return;
		if (entity instanceof EntityLivingBase && (entity.isDead || ((EntityLivingBase) entity).getHealth() < 0))
			return;
		
		if (entity instanceof EntityPlayer) {
			if (!attackPlayers.value)
				return;
			if (FriendManager.isFriend(entity.getName()))
				return;
			double angle1 = (entity.rotationYaw + 180) % 360;
			double angle2 = Minecraft.getMinecraft().player.rotationYaw % 360;
			if(angle1 < 0)angle1 += 360;
			if(angle2 < 0)angle2 += 360;
			if(isPlayerShielded((EntityPlayer)entity) && 180 - Math.abs(Math.abs(angle1 - angle2) - 180) < 95 && (!useAxeToBreakShield.value || !(Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemAxe)))
				return;
		} else if (entity instanceof IMob) {
			if (!attackMobs.value)
				return;
		} else if (EntityUtil.isAnimal(entity)) {
			if (!attackAnimals.value)
				return;
		} else return;

		if(!isPlayerShielded(Minecraft.getMinecraft().player) && (attackWhileMainhandInUse.value || !isPlayerUsingMainhand(Minecraft.getMinecraft().player))) {
			Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().player, entity);
			Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
			time = newTime;
		}
	}
	
	private static boolean isPlayerShielded(EntityPlayer player) {
		return player.getItemInUseCount() > 0 && (
				player.getHeldItemMainhand().getItem() instanceof ItemShield ||
				(player.getHeldItemOffhand().getItem() instanceof ItemShield && !isPlayerUsingMainhand(player))
		);
	}
	
	private static boolean isPlayerUsingMainhand(EntityPlayer player) {
		ItemStack main = player.getHeldItemMainhand();
		return player.getItemInUseCount() > 0 && (
			(main.getItemUseAction() == EnumAction.EAT && (!player.isCreative() && (player.getFoodStats().needFood() || main.getItem() instanceof ItemAppleGold))) ||
			(main.getItemUseAction() == EnumAction.BOW && canShootBow(player)) ||
			main.getItemUseAction() == EnumAction.DRINK || main.getItemUseAction() == EnumAction.BLOCK
		);
	}
	
	private static boolean canShootBow(EntityPlayer player) {
		if(player.isCreative())return true;
		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack.getItem() == Items.ARROW)return true;
		}
		return false;
	}
}
