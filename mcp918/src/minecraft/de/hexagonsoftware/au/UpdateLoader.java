package de.hexagonsoftware.au;

import java.io.File;
import java.io.IOException;

import net.minecraft.client.Minecraft;

public class UpdateLoader {
    public static void load(File files, String web_adress, String mcDataDir) throws IOException {
        String temp = FileLoader.loadFile(files.getAbsolutePath());
        String[] fileList = temp.split("\\n");

        int fileAmount = fileList.length;

        int i = 1;

        FileDownloader fd = new FileDownloader(false, true);
        for (String s : fileList) {
            System.out.print("Progress: "+i+"/"+fileAmount+" | FILE: "+web_adress+"/"+s+"\r");
            fd.download(web_adress+"/"+s, mcDataDir+"/medaUpdates", s);
            i++;
        }
    }
}
