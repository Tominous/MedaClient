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
			Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.keys.color"),
			Minecraft.getMinecraft().getClient().getConfig().getProperty("mods.keys.active.color")
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
			GlStateManager.scale(1f, 1f, 0.5f);
			fr.drawString(colors[0]+"w", space+5, 1*(fr.FONT_HEIGHT*orderInRenderering), -1);
			GlStateManager.popMatrix();
		} else {
			GlStateManager.pushMatrix();
			GlStateManager.scale(1f, 1f, 0.5f);
			fr.drawString(colors[1]+"w", space+5, 1*(fr.FONT_HEIGHT*orderInRenderering), -1);
			GlStateManager.popMatrix();
		}
		if (this.keys[1]) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(1f, 1f, 0.5f);
			fr.drawString(colors[0]+"a", 5, 1*(fr.FONT_HEIGHT*orderInRenderering)+space, -1);
			GlStateManager.popMatrix();
		} else {
			GlStateManager.pushMatrix();
			GlStateManager.scale(1f, 1f, 0.5f);
			fr.drawString(colors[1]+"a", 5, 1*(fr.FONT_HEIGHT*orderInRenderering)+space, -1);
			GlStateManager.popMatrix();
		}
		if (this.keys[2]) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(1f, 1f, 0.5f);
			fr.drawString(colors[0]+"s", space+5, 1*(fr.FONT_HEIGHT*orderInRenderering)+space, -1);
			GlStateManager.popMatrix();
		} else {
			GlStateManager.pushMatrix();
			GlStateManager.scale(1f, 1f, 0.5f);
			fr.drawString(colors[1]+"s", space+5, 1*(fr.FONT_HEIGHT*orderInRenderering)+space, -1);
			GlStateManager.popMatrix();
		}
		if (this.keys[3]) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(1f, 1f, 0.5f);
			fr.drawString(colors[0]+"d", (space*2)+5, 1*(fr.FONT_HEIGHT*orderInRenderering)+space, -1);
			GlStateManager.popMatrix();
		} else {
			GlStateManager.pushMatrix();
			GlStateManager.scale(1f, 1f, 0.5f);
			fr.drawString(colors[1]+"d", (space*2)+5, 1*(fr.FONT_HEIGHT*orderInRenderering)+space, -1);
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
