package de.felixeckert.medaclient;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import de.felixeckert.medaclient.mods.Coordinates;

////////////////////////////////
// Meda Client by Felix Eckert//
// Minecraft version 1.8.8	  //
////////////////////////////////

import de.felixeckert.medaclient.mods.FPSDisplay;
import de.felixeckert.medaclient.mods.ModRegistry;
import de.felixeckert.medaclient.mods.PingDisplay;
import de.felixeckert.medaclient.mods.SprintToggle;
import de.felixeckert.medaclient.mods.StringInfo;
import de.felixeckert.medaclient.utils.FileUtils;
import de.felixeckert.medaclient.utils.MedaLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.util.ResourceLocation;

// Meda Client Main Class

public class MedaClient {
	private static MedaLogger logger;
	private static ModRegistry modReg;
	private static Properties  config;
	
	public static void init() {
		logger = (new MedaLogger()).getLogger("Meda");
		logger.info("Initializing MedaClient");
		logger.info(DefaultResourcePack.class.getResource(("/" + (new ResourceLocation("pack.png")).getResourcePath())).getPath());
		
		logger.info("Version "+Reference.version + " build " + Reference.build + " " + Reference.stage + " stage ;");
		
		// Konfiguration Laden
		logger.info("Loading Config...");
		try {
			config = FileUtils.readPropertiesFile(Minecraft.getMinecraft().mcDataDir.getAbsolutePath()+"/medaconfig.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}	
		logger.info("Loaded config");
		
		// Register all mods here
		logger.info("Registering MedaMods");
		modReg = new ModRegistry();
		modReg.addMod("StringInfo", new StringInfo());
		modReg.addMod("FPS", new FPSDisplay());
		modReg.addMod("Ping", new PingDisplay());
		modReg.addMod("Coordinates", new Coordinates());
		modReg.addMod("Sprint", new SprintToggle());
		logger.info("Finished mod Registration");
		logger.info("Meda Client Initialized");
		
		postInit();
	}
	
	public static void postInit() {}

	public void update() {
		// Update all mods (Also renders them if they're graphical)
		modReg.updateMods();
	}
	
	// Getter Methods
	public static MedaLogger getLogger() { return logger; }	
	public MedaClient getMedaClient() { return this; }
	public static Properties getConfig() { return config; }
	
	public class Reference {
		// Reference Variables
		// Version Vars
		public static final String version   = "0.2";
		public static final String stage     = "beta";
		public static final String build     = "23";
		public static final String mcVersion = "1.8.8";
		
		// Info Vars
		public static final String developer = "Felix Eckert";
		public static final String name      = "Meda Client";
		public static final String license   = "3 Clause BSD";
	}
}
