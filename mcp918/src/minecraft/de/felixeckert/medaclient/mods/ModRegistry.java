package de.felixeckert.medaclient.mods;

import java.util.HashMap;

import net.minecraft.client.Minecraft;

public class ModRegistry {
	private HashMap<String, IMedaMod> mods;
	
	public ModRegistry() {
		this.mods = new HashMap<String, IMedaMod>();
	}
	
	public void addMod(String name, IMedaMod mod) {
		this.mods.put(name, mod);
		Minecraft.getMinecraft().getClient().getLogger().info("Added MedaMod: "+mod.getClass());
	}
	
	public void updateMods() {
		for (IMedaMod mod : mods.values()) {
			if (Minecraft.getMinecraft().getClient().getConfig().getProperty("mods."+mod.getName()+".active").matches("true")) {
				mod.update();
				if (mod instanceof IMedaModText) {
					if (((IMedaModText) mod).setsOwnDrawOrder()) {
						mod.render(((IMedaModText) mod).getDrawOrder());
					}
				} else if (mod instanceof IMedaModRender) {
					if (((IMedaModRender) mod).setsOwnDrawOrder()) {
						mod.render(((IMedaModRender) mod).getDrawOrder());
					}
				}
			}
		}
	}
}
