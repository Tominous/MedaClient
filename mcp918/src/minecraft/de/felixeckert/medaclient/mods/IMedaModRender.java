package de.felixeckert.medaclient.mods;

public interface IMedaModRender extends IMedaMod {
	int getDrawOrder();
	boolean setsOwnDrawOrder();
	float[] getPosition();
	float getHeight();
	String getName();
}
