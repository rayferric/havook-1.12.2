package com.rayferric.havook.proxy;

import com.rayferric.havook.handler.ChatHandler;
import com.rayferric.havook.handler.ClientTickHandler;
import com.rayferric.havook.handler.KeyInputHandler;
import com.rayferric.havook.handler.MouseInputHandler;
import com.rayferric.havook.handler.PlaySoundHandler;
import com.rayferric.havook.handler.PlayerTickHandler;
import com.rayferric.havook.handler.RenderGameOverlayHandler;
import com.rayferric.havook.handler.RenderLivingEventHandler;
import com.rayferric.havook.handler.RenderWorldLastHandler;
import com.rayferric.havook.manager.CommandManager;
import com.rayferric.havook.manager.ConfigManager;
import com.rayferric.havook.manager.FriendManager;
import com.rayferric.havook.manager.KeybindManager;
import com.rayferric.havook.manager.ModManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends ServerProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		if (event.getSide() == Side.CLIENT) {
			ConfigManager.loadConfig();

			CommandManager.loadCommands();
			FriendManager.loadFriends();
			KeybindManager.loadKeybinds();
			ModManager.loadMods();
		}
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		if (event.getSide() == Side.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new ChatHandler());
			MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
			MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
			MinecraftForge.EVENT_BUS.register(new MouseInputHandler());
			MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
			MinecraftForge.EVENT_BUS.register(new PlaySoundHandler());
			MinecraftForge.EVENT_BUS.register(new RenderWorldLastHandler());
			MinecraftForge.EVENT_BUS.register(new RenderGameOverlayHandler());
			MinecraftForge.EVENT_BUS.register(new RenderLivingEventHandler());
		}
	}

	@Override
	public void serverStarting(FMLServerStartingEvent event) {
		super.serverStarting(event);
	}

	@Override
	public void serverStopping(FMLServerStoppingEvent event) {
		super.serverStopping(event);
	}
}
