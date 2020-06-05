package de.felixeckert.medaclient.utils;

public class MedaLogger {
	String name;
	
	public MedaLogger getLogger(String name) {
		this.name = name;
		return this;
	}
	
	public void info(String msg) {
		System.out.println("[MedaClient] (info) "+msg);
	}
}