package de.felixeckert.medaclient.mods.old;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class Timo implements IMedaMod {
	
	@Override
	public void update() {
		if (Keyboard.isKeyDown(67)) {
			Minecraft.getMinecraft().thePlayer.sendChatMessage("TIMO BROICHAUS AKTIVIERT, DAS KANN NUR KEKKIG WERDEN");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				de.felixeckert.sgedvb.experimente.Timo t = new de.felixeckert.sgedvb.experimente.Timo();
				t.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void render(int orderInRenderering) {}

	@Override
	public String getName() {
		return "TimoMod";
	}

}
