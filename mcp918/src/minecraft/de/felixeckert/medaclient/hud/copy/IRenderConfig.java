package de.felixeckert.medaclient.hud.copy;

public interface IRenderConfig {
	public void save(ScreenPosition pos);
	public ScreenPosition load();
}
