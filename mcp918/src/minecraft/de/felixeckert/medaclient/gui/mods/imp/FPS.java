package de.felixeckert.medaclient.gui.mods.imp;

import de.felixeckert.medaclient.gui.hud.ScreenPosition;
import de.felixeckert.medaclient.gui.mods.ModDraggable;
import de.felixeckert.medaclient.util.MedaSettings;
import net.minecraft.client.Minecraft;

public class FPS extends ModDraggable {
	private ScreenPosition pos = MedaSettings.getSettings().getScreenPosOf("fps");
	private String dummyText = "FPS: XXX";
	private String baseText = "FPS: ";

	public FPS() {
		this.name = "fps";
	}
	
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
		fr.drawString(baseText, pos.getAbsoluteX()+1, pos.getAbsoluteY(), MedaSettings.getSettings().getColorOf("fps", "baseColor"));
		try {
			fr.drawString(String.valueOf(Minecraft.getMinecraft().getDebugFPS()), (pos.getAbsoluteX()+1)+fr.getStringWidth(baseText), pos.getAbsoluteY(), MedaSettings.getSettings().getColorOf("fps", "valueColor"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		fr.drawString(dummyText, pos.getAbsoluteX()+1, pos.getAbsoluteY(), 0xFF00FF00);
	}

	@Override
	public void save(ScreenPosition pos) {
		this.pos = pos;
		MedaSettings.getSettings().setScreenPosOf("fps", pos);
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
