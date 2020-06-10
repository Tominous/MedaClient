package de.felixeckert.medaclient.mods;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class KeyStrokes implements IMedaModRender {
	private boolean[] keys;
	private static final float[] scaleInt = new float[] {1f, 1f, 0.5f}; // Sets how much the String should be enlarged by
	private static final String[] colors  = new String[] {
			Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.keys.color")
	};
	private static final int space = Integer.parseInt(Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.keys.space"));
	
	@Override
	public void update() {
		this.keys = new boolean[] {Keyboard.isKeyDown(17), Keyboard.isKeyDown(30), Keyboard.isKeyDown(31), Keyboard.isKeyDown(32)};
	}

	@Override
	public void render(int orderInRenderering) {
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
		
		if (this.keys.length == 0) {
			return;
		}
		
		if (this.keys[0]) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(scaleInt[0], scaleInt[1], scaleInt[2]);
			fr.drawString(colors[0]+"w", space, 1*(fr.FONT_HEIGHT*orderInRenderering), -1);
			GlStateManager.popMatrix();
		}
		if (this.keys[1]) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(scaleInt[0], scaleInt[1], scaleInt[2]);
			fr.drawString(colors[0]+"a", 1, 1*(fr.FONT_HEIGHT*orderInRenderering)+space, -1);
			GlStateManager.popMatrix();
		}
		if (this.keys[2]) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(scaleInt[0], scaleInt[1], scaleInt[2]);
			fr.drawString(colors[0]+"s", space, 1*(fr.FONT_HEIGHT*orderInRenderering)+space, -1);
			GlStateManager.popMatrix();
		}
		if (this.keys[3]) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(scaleInt[0], scaleInt[1], scaleInt[2]);
			fr.drawString(colors[0]+"d", space*2, 1*(fr.FONT_HEIGHT*orderInRenderering)+space, -1);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public int getDrawOrder() {
		String oderString = Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.locations.keys.order");
		return Integer.parseInt(oderString);
	}

	@Override
	public boolean setsOwnDrawOrder() {
		return true;
	}

	@Override
	public float[] getPosition() {
		return null;
	}
	
	public float getHeight() {
		return (space*4)+1;
	}

	@Override
	public String getName() {
		return "keys";
	}
	
}
