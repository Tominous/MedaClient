package de.felixeckert.medaclient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.felixeckert.medaclient.util.ExceptionHandler;
import de.hexagonsoftware.au.FileLoader;
import net.minecraft.client.Minecraft;

public class VersionInstaller {
	public static void install() {
		File mcDir = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath().substring(
				0, Minecraft.getMinecraft().mcDataDir.getAbsolutePath().length()-2));
		File versionDir = new File(mcDir + "/versions");
		File medaVersionDir = new File(versionDir+"/MedaClient"+FileLoader.loadFile(mcDir+"/medaUpdates/olVersion.txt").replace('.', '0'));
		File updateDir = new File(mcDir, "medaUpdates");
		
		if (!medaVersionDir.isDirectory()) {
			try {
				System.out.println(medaVersionDir.mkdir());
				Files.copy(new File(updateDir.getAbsolutePath()+"/MedaClient.jar").toPath(), new File(medaVersionDir.getAbsolutePath(),"MedaClient.jar").toPath());
			} catch (IOException e) {
				ExceptionHandler.getHandler().reportException(e, "An error occured whilst Updating MedaClient: IOException");
			}
		} else {
			return;
		}
	}
}
