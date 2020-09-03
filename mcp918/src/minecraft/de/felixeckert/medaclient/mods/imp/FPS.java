package de.felixeckert.medaclient.mods.imp;

import de.felixeckert.medaclient.hud.ScreenPosition;
import de.felixeckert.medaclient.mods.ModDraggable;
import net.minecraft.client.Minecraft;

public class FPS extends ModDraggable {
	private static final String[] colors  = new String[] {
			Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.textColorA"),
			Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.textColorB")
	};
	
	private String dummyText = "FPS: XXX";
	private String baseText = "FPS: ";

	private ScreenPosition pos;

	@Override
	public int getWidth() {
		return fr.getStringWidth(dummyText);
	}

	@Override
	public int getHeight() {
		return fr.FONT_HEIGHT;
	}

	@Override
	public void render(ScreenPosition pos) {
		
		fr.drawString(colors[0]+baseText+colors[1]+Minecraft.getMinecraft().getDebugFPS(), pos.getAbsoluteX()+1, pos.getAbsoluteY(), -1);
	}
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		fr.drawString(dummyText, pos.getAbsoluteX()+1, pos.getAbsoluteY(), 0xFF00FF00);
	}

	@Override
	public void save(ScreenPosition pos) {
		this.pos = pos;
	}

	@Override
	public ScreenPosition load() {
		return this.pos;
	}
}
