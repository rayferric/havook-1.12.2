package com.rayferric.havook.manager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.rayferric.havook.Havook;

public class ConfigManager {
	private static JsonObject jsonObject;

	public static void loadConfig() {
		try {
			File file = new File("config/" + Havook.MODID + ".json");
			FileReader reader = new FileReader(file);
			char[] charBuffer = new char[(int) file.length()];
			reader.read(charBuffer);
			reader.close();
			jsonObject = new JsonParser().parse(String.valueOf(charBuffer)).getAsJsonObject();
		} catch (Exception e) {
			Havook.LOGGER.warning(e.getMessage());
			jsonObject = new JsonObject();
		}
	}

	public static void saveConfig() {
		Gson gson = new GsonBuilder().create();
		try {
			FileWriter writer = new FileWriter("config/" + Havook.MODID + ".json");
			writer.write(gson.toJson(jsonObject));
			writer.close();
		} catch (IOException e) {
			Havook.LOGGER.warning(e.getMessage());
		}
	}

	public static JsonObject getJsonObject() {
		return jsonObject;
	}
}
