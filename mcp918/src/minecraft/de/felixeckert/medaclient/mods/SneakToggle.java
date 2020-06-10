package de.felixeckert.medaclient.mods;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class SneakToggle implements IMedaModText {
	private static final float[] scaleInt = new float[] {1f, 1f, 0.5f}; // Sets how much the String should be enlarged by
	private static final String[] colors  = new String[] {
			Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.toggles.color")
	};
	private static final int toggleKey = Integer.parseInt(
			Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.toggles.sneak.key"));
	
	private int timesPressed = 0;
	private boolean toggled = false;
	private boolean pressed = false;
	
	public void update() {
		// Update Sneak Toggle Variable
		if (Keyboard.isKeyDown(toggleKey) && pressed == false) {
			pressed = true;
			if (toggled == false) {
				toggled = true;
			} else {
				toggled = false;
			}
		}	
		if (!Keyboard.isKeyDown(toggleKey) && pressed == true) {
			pressed = false;
		}	
			
		// Change Sneakstate
		Minecraft.getMinecraft().thePlayer.setSneaking(toggled);
	}
	
	public void render(int orderInRendering) {
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
		
		GlStateManager.pushMatrix();
		GlStateManager.scale(scaleInt[0], scaleInt[1], scaleInt[2]);
		fr.drawString(colors[0]+"[Sprint Toggle ("+toggledString()+")]", 1, 1+(fr.FONT_HEIGHT*orderInRendering), -1);
		GlStateManager.popMatrix();
	}
	
	private boolean toggled() { return this.toggled; }
	
	private String toggledString() {
		String out = "";
		if (toggled()) {
			out = "Aktiviert";
		} else {
			out = "Deaktiviert";
		}
		return out; 
	}
	
	public boolean setsOwnDrawOrder() { return true; }
	
	public int getDrawOrder() {		
		String oderString = Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.locations.sneak.order");
		return Integer.parseInt(oderString);
	}
	
	public String getName() { return "sneak"; }
}
