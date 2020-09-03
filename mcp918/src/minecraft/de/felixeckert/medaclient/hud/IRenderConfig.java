package de.felixeckert.medaclient.hud;

public interface IRenderConfig {
	public void save(ScreenPosition pos);
	public ScreenPosition load();
}
