package de.felixeckert.medaclient.util;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import de.felixeckert.medaclient.MedaClient;
import de.felixeckert.medaclient.gui.hud.ScreenPosition;

public class MedaSettings {
	private String SETTINGS_PATH;
	private JsonObject SETTINGS_FILE;
	
	private boolean SETTINGS_LOADED = false;
	
	public  static final String STD_SETTINGS_PATH = "";
	private static MedaSettings SETTINGS;
	
	private MedaSettings(String PATH) {
		this.SETTINGS_PATH = PATH;
	}
	
	public void loadSettings() {
		String rawData = "{}";
		MedaClient.getLogger().info("Attempting to load settings...");
		try {
			rawData = new String(Files.readAllBytes(Paths.get(new File(this.SETTINGS_PATH).getPath())));
		} catch (IOException e) {
			MedaClient.getLogger().error(String.format("Could not load settings file \"%s\"", this.SETTINGS_PATH));
			ExceptionHandler.getHandler().reportException(e, "Could not load settings file: IOException | further details in log");
			return;
		}
        this.SETTINGS_FILE = new Gson().fromJson(rawData, JsonObject.class);
        this.SETTINGS_LOADED = true;
	}
	
	public void saveSettingsToFile() {
		MedaClient.getLogger().info("Attempting to save settings...");
		
		if (!this.SETTINGS_LOADED) {
			MedaClient.getLogger().error("Can't save settings because no settings were loaded!");
			return;
		}
		
		GsonBuilder gb = new GsonBuilder();
		gb = gb.setPrettyPrinting().setVersion(1.0);
		try {
			JsonWriter writer = new JsonWriter(new FileWriter(this.SETTINGS_PATH));
			writer.setIndent("    ");
			gb.create().toJson(this.SETTINGS_FILE, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			ExceptionHandler.getHandler().reportException(e, "Could not save settings file: IOException | further details in log");
		}
	}
	
	public void changeSettingsFile(String NEW_PATH) {
		this.SETTINGS_PATH   = NEW_PATH;
		this.SETTINGS_LOADED = false;
		saveSettingsToFile();
	}
	
	public int getColorOf(String modName, String id) {
		int color = 0xffffff;
		
		if (!SETTINGS_LOADED)
			loadSettings();
		
		JsonArray mod = null;
		
		try {
			mod = this.SETTINGS_FILE.get("modSettings").getAsJsonObject().get(modName).getAsJsonObject().get(id).getAsJsonArray();
		} catch (Exception e) {
			return color;
		}
		
		int r = mod.get(0).getAsInt();
		int g = mod.get(1).getAsInt();
		int b = mod.get(2).getAsInt();
		int a = mod.size() == 4 ? mod.get(3).getAsInt() : 255;
		
		color = new Color(r, g, b, a).getRGB();
		
		return color;
	}
	
	public ScreenPosition getScreenPosOf(String modName) {
		ScreenPosition pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
		
		if (!SETTINGS_LOADED)
			loadSettings();
		
		JsonArray jsonPos = null;
		
		try {
			jsonPos = this.SETTINGS_FILE.get("modSettings").getAsJsonObject().get(modName).getAsJsonObject().get("pos").getAsJsonArray();
		} catch (NullPointerException e) {
			ExceptionHandler.getHandler().reportException(e, "");
			return pos;
		}
		
		pos = pos.fromAbsolutePosition(jsonPos.get(0).getAsInt(), jsonPos.get(1).getAsInt());
		
		return pos;
	}
	
	public String getModParam(String mod, String param) {
		if (!SETTINGS_LOADED)
			loadSettings();
		
		if (!this.SETTINGS_FILE.get("modSettings").getAsJsonObject().has(mod))
			return "";
			
		JsonObject modJson = this.SETTINGS_FILE.get("modSettings").getAsJsonObject().get(mod).getAsJsonObject();
		
		return modJson.has(param) ? modJson.get(param).getAsString() : "";
	}
	
	public boolean getModEnabled(String modName) {	
		if (!SETTINGS_LOADED)
			loadSettings();
		
		if (!this.SETTINGS_FILE.get("modSettings").getAsJsonObject().has(modName))
			return false;
		
		return this.SETTINGS_FILE.get("modSettings").getAsJsonObject().get(modName).getAsJsonObject().get("enabled").getAsBoolean();
	}
	
	public void setColorOf(String modName, String id, int color) {
		if (!SETTINGS_LOADED)
			loadSettings();
		
		JsonObject json = null;
		
		try {
			json = this.SETTINGS_FILE.get("modSettings").getAsJsonObject().get(modName).getAsJsonObject();
		} catch (NullPointerException e) {
			MedaClient.getLogger().error("An error occured whilst trying to save settings!");
			ExceptionHandler.getHandler().reportException(e, "Could not save settings: NullPointerException");
			return;
		}
		
		Color cColor = new Color(color);
		
		json.remove("pos");
		json.add(id, new Gson().fromJson(Arrays.toString(new int[] {cColor.getRed(), cColor.getGreen(), cColor.getBlue(), cColor.getAlpha()}), JsonArray.class));
	}
	
	public void setScreenPosOf(String modName, ScreenPosition pos) {
		if (!SETTINGS_LOADED)
			loadSettings();
		
		JsonObject json = null;
		
		try {
			json = this.SETTINGS_FILE.get("modSettings").getAsJsonObject().get(modName).getAsJsonObject();
		} catch (NullPointerException e) {
			MedaClient.getLogger().error("An error occured whilst trying to save settings!");
			ExceptionHandler.getHandler().reportException(e, "Could not save settings: NullPointerException");
			return;
		}
		
		json.remove("pos");
		json.add("pos", new Gson().fromJson(Arrays.toString(new int[] {pos.getAbsoluteX(), pos.getAbsoluteY()}), JsonArray.class));
	}

	public static MedaSettings getSettings() {
		if (SETTINGS == null)
			SETTINGS = new MedaSettings(MedaClient.Reference.resources+"/settings.json");
		
		return SETTINGS;
	}
}
