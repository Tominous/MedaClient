package de.felixeckert.medaclient.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class Coordinates implements IMedaModText {
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
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		int[] coords = new int[] {(int) player.posX, (int) player.posY, (int) player.posZ};
		
		// Assemble Coordinates
		String out = colors[0]+"X: "+colors[1]+coords[0]+colors[0]+" Y: "+colors[1]+coords[1]+colors[0]+" Z: "+colors[1]+coords[2];
		
		GlStateManager.pushMatrix();
		GlStateManager.scale(scaleFloat[0], scaleFloat[1], scaleFloat[2]);
		fr.drawString(out, 1, 1+(fr.FONT_HEIGHT*orderInRenderering), -1);
		GlStateManager.popMatrix();
	}

	@Override
	public boolean setsOwnDrawOrder() {
		return true;
	}

	@Override
	public int getDrawOrder() {
		return 1;
	}

}
