package de.felixeckert.medaclient.hud;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class HUDConfigScreen extends GuiScreen {
	private final HashMap<IRender, ScreenPosition> renders = new HashMap<>();
	
	private Optional<IRender> selectedRender = Optional.empty();
	private int prevX, prevY;
	
	public HUDConfigScreen(HUDManager hudMan) {
		Collection<IRender> registeredRenders = hudMan.getRegisteredRenderers();
		
		for (IRender render : registeredRenders) {
			if (!render.isEnabled())
				continue;
			
			ScreenPosition pos = render.load();
			if (pos == null) {
				pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
			}
			
			adjustBounds(render, pos);
			this.renders.put(render, pos);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawDefaultBackground();
		
		final float zBackup = this.zLevel;
		this.zLevel = 200;
		
		this.drawHollowRect(0, 0, this.width - 1, this.height -1, 0xFFFF0000);
		
		for (IRender render : renders.keySet()) {
			ScreenPosition pos = renders.get(render);
			render.renderDummy(pos);
			this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), render.getWidth(), render.getHeight(), 0xFF00FFFF);
		}
		
		this.zLevel = zBackup;
	}

	private void drawHollowRect(int x, int y, int w, int h, int color) {
		this.drawHorizontalLine(x, x+w, y, color);
		this.drawHorizontalLine(x, x+w, y + h, color);
		this.drawVerticalLine(x, y + h, y, color);
		this.drawVerticalLine(x + w, y + h, y, color);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			renders.entrySet().forEach((entry) -> {
				entry.getKey().save(entry.getValue());
			});
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}
	
	@Override
	protected void mouseClickMove(int x, int y, int button, long time) {
		if (selectedRender.isPresent()) {
			moveSelectedRenderBy(x - prevX, y - prevY);
		}
		
		this.prevX = x;
		this.prevY = y;
	}

	private void moveSelectedRenderBy(int x, int y) {
		IRender render = selectedRender.get();
		ScreenPosition pos = renders.get(render);
		
		pos.setAbsolute(pos.getAbsoluteX() + x, pos.getAbsoluteY() + y);
		
		adjustBounds(render, pos);
	}

	@Override
	public void onGuiClosed() {
		for (IRender render : renders.keySet()) {
			render.save(renders.get(render));
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
	
	private void adjustBounds(IRender render, ScreenPosition pos) {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		
		int sWidth = res.getScaledWidth();
		int sHeight = res.getScaledHeight();
		
		int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(sWidth - render.getWidth(), 0)));
		int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(sHeight - render.getHeight(), 0)));
		
		pos.setAbsolute(absoluteX, absoluteY);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		this.prevX = x;
		this.prevY = y;
		
		loadMouseOver(x, y);
	}

	private void loadMouseOver(int x, int y) {
		this.selectedRender = renders.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
	}
	
	private class MouseOverFinder implements Predicate<IRender> {
		private int x, y;
		
		public MouseOverFinder(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean test(IRender render) {
			ScreenPosition pos = renders.get(render);
			
			int absX = pos.getAbsoluteX();
			int absY = pos.getAbsoluteY();
			
			if (x >= absX && x <= absX + render.getWidth()) {
				if (y >= absY && y <= absY + render.getHeight()) {
					return true;
				}
			}
			
			return false;
		}
		
	}
}
