package com.rayferric.havook.handler;

import com.rayferric.havook.Havook;
import com.rayferric.havook.gui.LongMessageSenderGui;
import com.rayferric.havook.util.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class ClientTickHandler {
	private static GuiScreen nextGui = null;

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if (event.phase != Phase.START)
			return;

		if (nextGui != null) {
			Minecraft.getMinecraft().displayGuiScreen(nextGui);
			nextGui = null;
		}
	}

	public static void queueGui(GuiScreen gui) {
		nextGui = gui;
	}
}
