package com.rayferric.havook.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.rayferric.havook.Havook;
import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.manager.ModManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PlayerTickHandler {
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (event.phase != Phase.START)
			return;

		EntityPlayer player = event.player;
		if (player == Minecraft.getMinecraft().player) {
			for (Mod mod : ModManager.MODS) {
				if (mod.isEnabled()) {
					mod.onLocalPlayerUpdate();
				}
			}
		}
	}
}
