package de.felixeckert.medaclient.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class FPSDisplay implements IMedaModText {
	private static final float[] scaleFloat = new float[] {1f, 1f, 0.5f}; // Sets how much the String should be enlarged by
	private static final String[] colors  = new String[] {
			Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.textColorA"),
			Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.textColorB")
	};
	
	@Override
	public void update() {}

	@Override
	public void render(int orderInRenderering) {
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

		GlStateManager.pushMatrix();
		GlStateManager.scale(scaleFloat[0], scaleFloat[1], scaleFloat[2]);
		fr.drawString(colors[0]+"FPS: "+colors[1]+Minecraft.getMinecraft().getDebugFPS(), 1, 1+(fr.FONT_HEIGHT*orderInRenderering), -1);
		GlStateManager.popMatrix();
	}

	@Override
	public boolean setsOwnDrawOrder() {
		return true;
	}

	@Override
	public int getDrawOrder() {
		return Integer.parseInt(Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.locations.fps"));
	}

}
