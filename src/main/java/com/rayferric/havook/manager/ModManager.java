package com.rayferric.havook.manager;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import com.rayferric.havook.Havook;
import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModAttribute;
import com.rayferric.havook.feature.mod.ModAttributeBoolean;
import com.rayferric.havook.feature.mod.ModAttributeDouble;
import com.rayferric.havook.feature.mod.ModAttributeString;
import com.rayferric.havook.feature.mod.combat.TriggerBotMod;
import com.rayferric.havook.feature.mod.misc.AutoFishMod;
import com.rayferric.havook.feature.mod.misc.FastPlaceMod;
import com.rayferric.havook.feature.mod.movement.AutoSneakMod;
import com.rayferric.havook.feature.mod.movement.AutoSprintMod;
import com.rayferric.havook.feature.mod.movement.AutoWalkMod;
import com.rayferric.havook.feature.mod.movement.ElytraBoostMod;
import com.rayferric.havook.feature.mod.movement.FlyMod;
import com.rayferric.havook.feature.mod.movement.ParkourMod;
import com.rayferric.havook.feature.mod.movement.SafeWalkMod;
import com.rayferric.havook.feature.mod.render.ActiveListMod;
import com.rayferric.havook.feature.mod.render.ChestESPMod;
import com.rayferric.havook.feature.mod.render.FullBrightMod;
import com.rayferric.havook.feature.mod.render.HealthTags;
import com.rayferric.havook.feature.mod.render.ItemESPMod;
import com.rayferric.havook.feature.mod.render.MobESPMod;
import com.rayferric.havook.feature.mod.render.HitmanRadarMod;
import com.rayferric.havook.feature.mod.render.PlayerESPMod;
import com.rayferric.havook.feature.mod.render.TrajectoriesMod;
import com.rayferric.havook.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class ModManager {
	public static List<Mod> MODS = new ArrayList<Mod>();

	public static void loadMods() {
		MODS.add(new ActiveListMod());
		MODS.add(new AutoFishMod());
		MODS.add(new AutoSneakMod());
		MODS.add(new AutoSprintMod());
		MODS.add(new AutoWalkMod());
		MODS.add(new ChestESPMod());
		MODS.add(new ElytraBoostMod());
		MODS.add(new FastPlaceMod());
		MODS.add(new FlyMod());
		MODS.add(new FullBrightMod());
		MODS.add(new HealthTags());
		MODS.add(new ItemESPMod());
		MODS.add(new MobESPMod());
		MODS.add(new HitmanRadarMod());
		MODS.add(new ParkourMod());
		MODS.add(new PlayerESPMod());
		MODS.add(new SafeWalkMod());
		MODS.add(new TrajectoriesMod());
		MODS.add(new TriggerBotMod());
		JsonArray modsArray = ConfigManager.getJsonObject().getAsJsonArray("mods");
		if(modsArray == null) {
			saveMods();
			return;
		}
		for (int i = 0; i < modsArray.size(); i++) {
			String id = modsArray.get(i).getAsJsonObject().get("id").getAsString();
			Mod targetMod = getModById(id);
			if (targetMod == null)continue;
			if(modsArray.get(i).getAsJsonObject().has("enabled") && modsArray.get(i).getAsJsonObject().get("enabled").getAsBoolean())if(modsArray.get(i).getAsJsonObject().get("enabled").getAsBoolean())targetMod.setEnabled(true);
			for (ModAttribute targetAttribute : targetMod.ATTRIBUTES) {
				String stringValue = null;
				JsonArray attribsArray = modsArray.get(i).getAsJsonObject().getAsJsonArray("attributes");
				for (int j = 0; j < attribsArray.size(); j++) {
					if (attribsArray.get(j).getAsJsonObject().get("name").getAsString().equals(targetAttribute.name)) {
						stringValue = attribsArray.get(j).getAsJsonObject().get("value").getAsString();
						break;
					}
				}
				if (stringValue == null)continue;
				if (targetAttribute instanceof ModAttributeBoolean) {
					if (stringValue.equalsIgnoreCase("true") || stringValue.equalsIgnoreCase("false")) {
						Boolean value = Boolean.parseBoolean(stringValue);
						((ModAttributeBoolean) targetAttribute).value = value;
					} else continue;
				} else if (targetAttribute instanceof ModAttributeDouble) {
					double number;
					try {
						number = Double.parseDouble(stringValue);
					} catch (NullPointerException | NumberFormatException e) {
						continue;
					}
					((ModAttributeDouble) targetAttribute).value = number;
				} else if (targetAttribute instanceof ModAttributeString) {
					((ModAttributeString) targetAttribute).value = stringValue;
				}
			}
		}
		saveMods(); // fix any errors in the config file
	}

	public static void saveMods() {
		Gson gson = new Gson();
		JsonElement element = gson.toJsonTree(MODS, new TypeToken<List<Mod>>() {
		}.getType());
		ConfigManager.getJsonObject().add("mods", element);
		ConfigManager.saveConfig();
	}

	public static Mod getModById(String id) {
		for (Mod mod : MODS) {
			if (mod.id.equalsIgnoreCase(id))
				return mod;
		}
		return null;
	}
}
