package de.felixeckert.medaclient.gui.mods;

import de.felixeckert.medaclient.gui.hud.IRender;
import de.felixeckert.medaclient.gui.hud.ScreenPosition;

public abstract class ModDraggable extends Mod implements IRender {
	public final int getLineOffset(ScreenPosition pos, int lNum) {
		return pos.getAbsoluteY() + getLineOffset(lNum);
	}

	private int getLineOffset(int lNum) {
		return (fr.FONT_HEIGHT + 3) + lNum;
	}
}
