package de.felixeckert.medaclient.gui.mods.imp;

import de.felixeckert.medaclient.gui.hud.ScreenPosition;
import de.felixeckert.medaclient.gui.mods.ModDraggable;
import de.felixeckert.medaclient.util.MedaSettings;

public class XYZ extends ModDraggable {
	private ScreenPosition pos = MedaSettings.getSettings().getScreenPosOf(name);
	
	public XYZ() {
		this.name = "xyz";
	}
	
	@Override
	public int getWidth() {
		return fr.getStringWidth("X: "+(int)mc.thePlayer.posX+" Y: "+(int)mc.thePlayer.posY+" Z: "+(int)mc.thePlayer.posZ);
	}

	@Override
	public int getHeight() {
		return fr.FONT_HEIGHT;
	}

	@Override
	public void render(ScreenPosition pos) {
		int x = (int)mc.thePlayer.posX;
		int y = (int)mc.thePlayer.posY;
		int z = (int)mc.thePlayer.posZ;
		
		fr.drawString("X: ", pos.getAbsoluteX()+1, pos.getAbsoluteY(), MedaSettings.getSettings().getColorOf(name, "baseColor"));
		fr.drawString(String.valueOf(x), pos.getAbsoluteX()+fr.getStringWidth("X: "), pos.getAbsoluteY(), MedaSettings.getSettings().getColorOf(name, "valueColor"));
		fr.drawString(" Y: ", pos.getAbsoluteX()+fr.getStringWidth("X: "+x), pos.getAbsoluteY(), MedaSettings.getSettings().getColorOf(name, "baseColor"));
		fr.drawString(String.valueOf(y), pos.getAbsoluteX()+fr.getStringWidth("X: "+x+" Y: "), pos.getAbsoluteY(), MedaSettings.getSettings().getColorOf(name, "valueColor"));
		fr.drawString(" Z: ", pos.getAbsoluteX()+fr.getStringWidth("X: "+x+" Y: "+y), pos.getAbsoluteY(), MedaSettings.getSettings().getColorOf(name, "baseColor"));
		fr.drawString(String.valueOf(z), pos.getAbsoluteX()+fr.getStringWidth("X: "+x+"Y: "+y+" Z: "), pos.getAbsoluteY(), MedaSettings.getSettings().getColorOf(name, "valueColor"));
	}
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		fr.drawString("X: "+(int)mc.thePlayer.posX+" Y: "+(int)mc.thePlayer.posY+" Z: "+(int)mc.thePlayer.posZ, pos.getAbsoluteX()+1, pos.getAbsoluteY(), 0xFF00FF00);
	}

	@Override
	public void save(ScreenPosition pos) {
		this.pos = pos;
		MedaSettings.getSettings().setScreenPosOf(name, pos);
	}

	@Override
	public ScreenPosition load() {
		return this.pos;
	}
	
	@Override
	public boolean isEnabled() {
		return MedaSettings.getSettings().getModEnabled(name);
	}
}
