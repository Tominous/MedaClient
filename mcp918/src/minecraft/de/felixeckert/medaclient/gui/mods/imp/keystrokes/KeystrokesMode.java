package de.felixeckert.medaclient.gui.mods.imp.keystrokes;

import net.minecraft.client.Minecraft;

public enum KeystrokesMode {
	WASD(Key.W, Key.A, Key.S, Key.D),
	WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB),
	WASD_SPRINT(Key.W, Key.A, Key.S, Key.D, new Key("Sprint", Minecraft.getMinecraft().gameSettings.keyBindSprint, 1, 41, 58, 18)),
	WASD_SPRINT_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB, new Key("Sprint", Minecraft.getMinecraft().gameSettings.keyBindSprint, 1, 61, 58, 18));

	private Key[] keys;
	private int width, height;

	KeystrokesMode(Key... keys) {
		this.keys = keys;
		
		for (Key key : keys) {
			this.width = Math.max(this.width, key.getX() + key.getWidth());
			this.height = Math.max(this.height, key.getY() + key.getHeight());
		}
	}
	
	public Key[] getKeys() {
		return keys;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	static KeystrokesMode getMode(String mode) {
		switch (mode) {
		case "WASD": return WASD;
		case "WASD_MOUSE": return WASD_MOUSE;
		case "WASD_SPRINT": return WASD_SPRINT;
		case "WASD_SPRINT_MOUSE": return WASD_SPRINT_MOUSE;
		}
		
		return WASD;
	}
}
