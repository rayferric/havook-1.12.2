package com.rayferric.havook;

import java.util.logging.Logger;

import com.rayferric.havook.proxy.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Havook.MODID, name = Havook.NAME, version = Havook.VERSION)
public class Havook {
	public static final String MODID = "havook";
	public static final String NAME = "Havook";
	public static final String VERSION = "1.0.0";
	public static final String CLIENT_PROXY = "com.rayferric.havook.proxy.ClientProxy";
	public static final String SERVER_PROXY = "com.rayferric.havook.proxy.ServerProxy";

	public static final Logger LOGGER = Logger.getLogger(Havook.MODID);

	@Mod.Instance
	public static Havook instance;

	@SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
	public static ServerProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
