package de.felixeckert.medaclient.mods;

import de.felixeckert.medaclient.hud.HUDManager;
import de.felixeckert.medaclient.mods.imp.ModTest;

public class ModInstances {
	private static ModTest modTest;
	
	public static void register(HUDManager api) {
		modTest = new ModTest();
		
		api.register(modTest);
	}
	
	public static ModTest getModTest() { return modTest; }
}
