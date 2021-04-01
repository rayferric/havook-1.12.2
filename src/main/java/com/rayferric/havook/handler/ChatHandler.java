package com.rayferric.havook.handler;

import com.rayferric.havook.util.ChatUtil;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatHandler {
	@SubscribeEvent
	public void onChat(ClientChatEvent event) {
		event.setCanceled(!ChatUtil.sendChatMessage(event.getMessage(), true));
	}
}
