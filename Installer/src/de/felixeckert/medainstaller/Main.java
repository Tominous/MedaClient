package de.felixeckert.medainstaller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
	public static void main(String[] main) {
		MainWindow win = new MainWindow();
		win.setup();
		System.out.println("[MedaInstaller] Starte Installer");
		System.out.println("[MedaInstaller] Prüfe nach Versionen...");
		System.out.println("[MedaInstaller] Suche nach Versions Ordner...");
		if (new File(System.getProperty("user.dir").toString()+"/MedaClient").isDirectory()) {
			System.out.println("[MedaInstaller] Versions Ordner Gefunden!");
		} else {
			System.out.println("[MedaInstaller] (error) Kein Versions Ordner! Breche ab...");
			System.exit(0);
		}
		
		System.out.println("[MedaInstaller] Suche nach Jar Datei...");
		if (new File(System.getProperty("user.dir").toString()+"/MedaClient/MedaClient.jar").isFile()) {
			System.out.println("[MedaInstaller] Jar Datei Gefunden!");
		} else {
			System.out.println("[MedaInstaller] (error) Keine Jar Datei gefunden! Breche ab...");
			System.exit(0);
		}
		
		String homeDir = System.getProperty("user.home");
		File versionFolder = new File(homeDir+"/AppData/Roaming/.minecraft/versions");
		File medaVersionFolder = new File(versionFolder.getAbsolutePath().toString()+"/MedaClient");
		
		System.out.println("[MedaInstaller] Prüfe nach Ordnern...");
		if (!versionFolder.isDirectory()) {
			System.out.println("[MedaInstaller] (error) Kein Minecraft Versions Ordner! Breche ab...");
			System.exit(0);
		}
		if (!medaVersionFolder.isDirectory()) {
			System.out.println("[MedaInstaller] Kein Meda Versions Ordner! Erstelle...");
			medaVersionFolder.mkdir();
		}
		
		boolean copyConfig = false;
		boolean copyJson   = false;
		
		System.out.println("[MedaInstaller] Prüfe nach Konfigurations Datei...");
		if (new File(homeDir+"/AppData/Roaming/.minecraft/medaconfig.properties").isFile()) {
			System.out.println("[MedaInstaller] Konfigurations Datei vorhanden!");
		} else {
			System.out.println("[MedaInstaller] Keine Konfigurations Datei vorhanden!");
			copyConfig = true;
		}
		
		System.out.println("[MedaInstaller] Prüfe nach Jar Datei...");
		if (new File(homeDir+"/AppData/Roaming/.minecraft/versions/MedaClient/MedaClient.jar").isFile()) {
			System.out.println("[MedaInstaller] Jar Datei bereits vorhanden, lösche!");
			new File(homeDir+"/AppData/Roaming/.minecraft/versions/MedaClient/MedaClient.jar").delete();
		}
		
		if (new File(homeDir+"/AppData/Roaming/.minecraft/versions/MedaClient/MedaClient.json").isFile()) {
			System.out.println("[MedaInstaller] JSON Datei vorhanden!");
		} else {
			System.out.println("[MedaInstaller] Keine JSON Datei vorhanden!");
			copyJson = true;
		}
		
		System.out.println("[MedaInstaller] Kopiere Dateien...");
		System.out.println("[MedaInstaller] Kopiere Jar Datei...");
		try {
			Files.copy(new File(System.getProperty("user.dir").toString()+"/MedaClient/MedaClient.jar").toPath(), new File(medaVersionFolder.toString()+"/MedaClient.jar").toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (copyConfig) {
			System.out.println("[MedaInstaller] Kopiere Konfigurationsdatei...");
			try {
				Files.copy(new File(System.getProperty("user.dir").toString()+"/MedaClient/medaconfig.properties").toPath(), new File(homeDir+"/AppData/Roaming/.minecraft/medaconfig.properties").toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (copyConfig) {
			System.out.println("[MedaInstaller] Kopiere JSON Datei...");
			try {
				Files.copy(new File(System.getProperty("user.dir").toString()+"/MedaClient/MedaClient.json").toPath(), new File(homeDir+"/AppData/Roaming/.minecraft/versions/MedaClient/MedaClient.json").toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("[MedaInstaller] Installation Abgeschlossen!...");
	}
}
