package de.felixeckert.medaclient.mods;

import java.util.Iterator;

import de.felixeckert.medaclient.MedaClient.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class StringInfo implements IMedaModText {
	private static final float[] scaleInt = new float[] {1f, 1f, 0.5f};
	
	public StringInfo () {}
	
	public void update() {}
	
	public void render(int orderInRenderering) {
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

		GlStateManager.pushMatrix();
		GlStateManager.scale(scaleInt[0], scaleInt[1], scaleInt[2]);
		fr.drawString(Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.headLine.color")+
				"MedaClient "+Reference.version + " ["+Reference.stage.toUpperCase()+"] Build "+Reference.build+" Patch "+Reference.patch, 
				1, 1+(fr.FONT_HEIGHT*orderInRenderering), -1);
		GlStateManager.popMatrix();
	}

	public boolean setsOwnDrawOrder() {
		return true;
	}

	public int getDrawOrder() {
		String oderString = Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.locations.headLine.order");
		return Integer.parseInt(oderString);
	}

	public String getName() { return "headLine"; }
}
