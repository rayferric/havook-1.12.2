package com.rayferric.havook.manager;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import com.rayferric.havook.Havook;

public class FriendManager {
	public static List<String> FRIENDS = new ArrayList<String>();

	public static void loadFriends() {
		JsonArray friendsArray = ConfigManager.getJsonObject().getAsJsonArray("friends");
		if (friendsArray == null) {
			clearFriends();
			return;
		}
		for (int i = 0; i < friendsArray.size(); i++) {
			FRIENDS.add(friendsArray.get(i).getAsString());
		}
	}

	public static void saveFriends() {
		Gson gson = new Gson();
		JsonElement element = gson.toJsonTree(FRIENDS, new TypeToken<List<String>>() {
		}.getType());
		ConfigManager.getJsonObject().add("friends", element);
		ConfigManager.saveConfig();
	}

	public static void clearFriends() {
		FRIENDS.clear();
		saveFriends();
	}

	public static boolean removeFriend(String nick) {
		for (int i = 0; i < FRIENDS.size(); i++) {
			if (FRIENDS.get(i).equalsIgnoreCase(nick)) {
				FRIENDS.remove(i);
				saveFriends();
				return true;
			}
		}
		return false;
	}

	public static boolean addFriend(String nick) {
		for (int i = 0; i < FRIENDS.size(); i++) {
			if (FRIENDS.get(i).equalsIgnoreCase(nick)) {
				return false;
			}
		}
		FRIENDS.add(nick);
		saveFriends();
		return true;
	}

	public static boolean isFriend(String nick) {
		for (String friend : FRIENDS) {
			if (friend.equalsIgnoreCase(nick))
				return true;
		}
		return false;
	}
}
