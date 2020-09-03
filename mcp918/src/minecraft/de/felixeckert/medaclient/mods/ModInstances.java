package de.felixeckert.medaclient.mods;

import de.felixeckert.medaclient.hud.HUDManager;
import de.felixeckert.medaclient.mods.imp.FPS;
import de.felixeckert.medaclient.mods.imp.ModTest;

public class ModInstances {
	private static ModTest modTest;
	private static FPS modFPS;
	
	public static void register(HUDManager api) {
		modTest = new ModTest();
		modFPS = new FPS();
		
		api.register(modTest);
		api.register(modFPS);
	}
	
	public static ModTest getModTest() { return modTest; }
	public static FPS getModFPS() { return modFPS; }
}
