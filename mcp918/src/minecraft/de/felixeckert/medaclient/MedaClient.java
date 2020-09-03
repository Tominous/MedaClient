package de.felixeckert.medaclient;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.felixeckert.medaclient.events.EventManager;
import de.felixeckert.medaclient.events.EventTarget;
import de.felixeckert.medaclient.events.imp.ClientTickEvent;
import de.felixeckert.medaclient.hud.HUDManager;
import de.felixeckert.medaclient.mods.ModInstances;
import de.felixeckert.medaclient.utils.FileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.util.ResourceLocation;

/**
 * Meda Client 0.5.0 [BETA]
 * Copyright (c) 2020 Felix Eckert.
 * */
public class MedaClient {
	private static final Logger logger = LogManager.getLogger();
	private static MedaClient INSTANZ;
	private static HUDManager modReg;
	private static Properties  config;
	
	private static DiscordIntegration discordInt;
	
	public MedaClient() {
		this.INSTANZ = this;
	}
	
	public void init() {
		logger.info("Initializing MedaClient");
		logger.info(DefaultResourcePack.class.getResource(("/" + (new ResourceLocation("pack.png")).getResourcePath())).getPath());
		
		logger.info("Version "+Reference.version + " build " + Reference.build + " " + Reference.stage + " stage patch "+Reference.patch);
		
		// Konfiguration Laden
		logger.info("Loading Config...");
		try {
			config = FileUtils.readPropertiesFile(Minecraft.getMinecraft().mcDataDir.getAbsolutePath()+"/medaconfig.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}	
		logger.info("Loaded config");
		logger.info("Initializing DiscordRPC");
		discordInt = new DiscordIntegration();
		discordInt.Start();
		logger.info("Finished Initializing DiscordRPC");
		
		EventManager.register(this);
		
		postInit();
	}
	
	public static void postInit() {}

	public static void start() {
		modReg = HUDManager.getInstance();
		ModInstances.register(modReg);
	}
	
	public static void shutdown() {
		discordInt.shutdown();
	}
	
	public void update() {
		// Update all mods (Also renders them if they're graphical)
		//modReg.updateMods();
	}
	
	// Getter Methods
	public static Logger getLogger() { return logger; }	
	public MedaClient getMedaClient() { return INSTANZ; }
	public static Properties getConfig() { return config; }
	public DiscordIntegration getDiscordInt() { return discordInt; }
	
	@EventTarget
	public void onTick(ClientTickEvent e) {
		if (Minecraft.getMinecraft().gameSettings.MEDA_GUI_MOD_POS.isPressed()) {
			modReg.openConfigScreen();
		}
	}
	
	public class Reference {
		// Reference Variables
		// Version Vars
		public static final String version   = "0.5.0";
		public static final String patch     = "0";
		public static final String stage     = "beta";
		public static final String build     = "1";
		public static final String mcVersion = "1.8.8";
		
		// Info Vars
		public static final String developer = "Felix Eckert";
		public static final String name      = "Meda Client";
		public static final String license   = "3 Clause BSD";
	}
}
