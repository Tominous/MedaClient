package de.felixeckert.medaclient.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;

public class PingDisplay implements IMedaModText {
	private static final float[] scaleInt = new float[] {1f, 1f, 0.5f}; // Sets how much the String should be enlarged by
	private static final String[] colors  = new String[] {
			Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.textColorA"),
			Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.textColorB")
	};
	
	@Override
	public void update() {}

	@Override
	public void render(int orderInRenderering) {
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
		int ping = 0;
		
		//if (!Minecraft.getMinecraft().isSingleplayer()) {
			NetworkPlayerInfo npi = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getGameProfile().getId());
			//ping = npi.func_178835_l();
		//}
		GlStateManager.pushMatrix();
		GlStateManager.scale(scaleInt[0], scaleInt[1], scaleInt[2]);
		fr.drawString(colors[0]+"Ping: "+colors[1]+ping, 1, 1+(fr.FONT_HEIGHT*orderInRenderering), -1);
		GlStateManager.popMatrix();
	}

	@Override
	public boolean setsOwnDrawOrder() {
		return true;
	}

	@Override
	public int getDrawOrder() {
		return 3;
	}

}
