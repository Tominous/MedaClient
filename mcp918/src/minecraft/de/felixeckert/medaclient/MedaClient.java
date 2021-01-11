package de.felixeckert.medaclient;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.felixeckert.medaclient.events.EventManager;
import de.felixeckert.medaclient.events.EventTarget;
import de.felixeckert.medaclient.events.imp.ClientTickEvent;
import de.felixeckert.medaclient.gui.hud.HUDManager;
import de.felixeckert.medaclient.gui.mods.ModInstances;
import de.felixeckert.medaclient.util.ExceptionHandler;
import de.felixeckert.medaclient.util.FileManager;
import de.felixeckert.medaclient.util.FileUtils;
import de.felixeckert.medaclient.util.MedaSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.util.ResourceLocation;

/**
 * Meda Client 0.7 [BETA]
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
		logger.info("Initialising MedaClient");
		logger.info("Version "+Reference.version + " build " + Reference.build + " " + Reference.stage + " stage patch "+Reference.patch);
		
		logger.info("Initialising ExceptionHandler");
		List<String> exceptionFlags = ExceptionHandler.getHandler().getFlags();
		exceptionFlags.remove("reportAll");
		exceptionFlags.add("reportIOE");
		exceptionFlags.add("reportIOEAsMsgBox");
		exceptionFlags.add("reportIOEToConsole");
		exceptionFlags.add("reportNPE");
		exceptionFlags.add("reportNPEToConsole");
		exceptionFlags.add("reportNPEAsMsgBox");
		
		// Konfiguration Laden
		logger.info("Loading Configs...");
		try {
			config = FileUtils.readPropertiesFile(Minecraft.getMinecraft().mcDataDir.getAbsolutePath()+"/medaconfig.properties");
		} catch (IOException e) {
			ExceptionHandler.getHandler().reportException(e, "Could not load Config: IOException");
		}
		logger.info("Loaded medaconfig properties file!");
		logger.info("Loading Mod Configs...");
		FileManager.init();
		logger.info("Loaded configs!");
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
		logger.info("Shutting down MedaClient...");
		if (discordInt != null)
			discordInt.shutdown();
		
		MedaSettings.getSettings().saveSettingsToFile();
		logger.info("MedaClient shutdown!");
	}
	
	public void update() {
		// Update all mods (Also renders them if they're graphical)
		modReg.updateMods();
	}
	
	// Getter Methods
	public static Logger getLogger() { return logger; }	
	public MedaClient getMedaClient() { return INSTANZ; }
	public static Properties getConfig() { return config; }
	public DiscordIntegration getDiscordInt() { return discordInt; }
	
	/**
	 * Handle Client Updates
	 * */
	@EventTarget
	public void onTick(ClientTickEvent e) {
		ModInstances.getModSprintToggle().update();
		if (Minecraft.getMinecraft().gameSettings.MEDA_GUI_MOD_POS.isPressed()) {
			modReg.openConfigScreen();
		} else if (Minecraft.getMinecraft().gameSettings.MEDA_RELOAD_SETTINGS.isPressed()) {
			MedaSettings.getSettings().loadSettings();
		}
	}
	
	public static class Reference {
		// Reference Variables
		// Version Vars
		public static final String version   = "0.7";
		public static final String patch     = "1";
		public static final String stage     = "beta";
		public static final String build     = "1";
		public static final String mcVersion = "1.8.8";
		
		// Resource Directory
		public static final String resources = Minecraft.getMinecraft().mcDataDir.getAbsolutePath()+"/medaResources";
		
		// Info Vars
		public static final String developer = "Felix Eckert";
		public static final String name      = "Meda Client";
		public static final String license   = "3 Clause BSD";
	}
}
