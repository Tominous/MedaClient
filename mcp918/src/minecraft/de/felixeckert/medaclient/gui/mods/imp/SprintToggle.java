package de.felixeckert.medaclient.gui.mods.imp;

import org.lwjgl.input.Keyboard;

import de.felixeckert.medaclient.gui.hud.ScreenPosition;
import de.felixeckert.medaclient.gui.mods.ModDraggable;
import de.felixeckert.medaclient.util.MedaSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class SprintToggle extends ModDraggable {
	private ScreenPosition pos = MedaSettings.getSettings().getScreenPosOf("sprintToggle");
	
	boolean toggled = false, pressed = false;
	
	public SprintToggle() {
		this.name = "sprintToggle";
	}
	
	@Override
	public int getWidth() {
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
		String toggledState = toggled ? "Aktiv" : "Inaktiv"; 
		return fr.getStringWidth("[Sprint Toggle ("+toggledState+")]");
	}

	@Override
	public int getHeight() {
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
		return fr.FONT_HEIGHT;
	}
	
	@Override
	public void update() {
		if (!MedaSettings.getSettings().getModEnabled("sprintToggle"))
			return;
		
		// Update Sprint Toggle Variable
		if (Minecraft.getMinecraft().gameSettings.MEDA_SPRINT_TOGGLE.isPressed()) {
			pressed = true;
			if (toggled == false) {
				toggled = true;
			} else {
				toggled = false;
			}
		}	
		if (!Minecraft.getMinecraft().gameSettings.MEDA_SPRINT_TOGGLE.isPressed() && pressed == true && pressed == false) {
			pressed = false;
		}
		
		// Change Sprintstate
		Minecraft.getMinecraft().thePlayer.setSprinting(toggled);
	}

	@Override
	public void render(ScreenPosition pos) {
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj; 
		String toggledState = toggled ? "Aktiv" : "Inaktiv";
		
		fr.drawString("[Sprint Toggle ("+toggledState+")]", pos.getAbsoluteX(), pos.getAbsoluteY(), MedaSettings.getSettings().getColorOf("sprintToggle", "color"));
	}

	@Override
	public void save(ScreenPosition pos) {
		this.pos = pos;
	}

	@Override
	public ScreenPosition load() {
		return pos;
	}
	
	@Override
	public boolean isEnabled() {
		return MedaSettings.getSettings().getModEnabled(name);
	}
}
