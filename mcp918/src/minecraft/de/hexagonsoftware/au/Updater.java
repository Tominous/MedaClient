package de.hexagonsoftware.au;

import java.awt.*;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import de.felixeckert.medaclient.MedaClient;
import net.minecraft.client.Minecraft;

/*
BSD 3-Clause License

Copyright (c) 2020, Hexagon Software
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/**
 * This is an Auto-Updating Program, which is easy modifiable,
 * it works by downloading a current-version.txt from a given
 * website, which contains a version number. It then compares this
 * to the specified version file (path relative to updater location).
 * If there is a newer version it will download the lastest version,
 * from the specified server. This works via http, if Authentication
 * is required, this most be specified, alongside the credentials.
 * Visit hexagonsoftware.github.io/AutoUpdater for further information
 *
 * @author Felix Eckert
 * */
public class Updater {
    public static final String LOCAL_VERSION = "medaResources/version.txt"; // Name of the Local Version File goes here
    public static final String ONLINE_VERSION_FOLDER = "http://127.0.0.1/au"; // The Adress to the Webserver with the path to the folder containing the version
    public static final boolean AUTH_REQUIRED = false; // Set to true if Password Auth is required
    public static final String AUTH_PWD = ""; // Password for authentication
    public static final String AUTH_USR = ""; // Username for authentication

    public static boolean update(String mcDataDir) throws IOException {
        System.out.println("Hexagon-AutoUpdater is starting...\n");
        // Load the Local Version
        log("Loading local version file...");
        String localVersion = MedaClient.Reference.version; // Use the File Loader to load the local version file
        log("Found local version as: "+localVersion);

        // Load the newest version which is online
        log("Grabbing online version...");
        if (AUTH_REQUIRED) {
            log("Authenticating...");
            Authenticator.setDefault(new de.hexagonsoftware.au.Authenticator(AUTH_USR, AUTH_PWD));
        }
        FileDownloader.download(ONLINE_VERSION_FOLDER+"/version.txt", mcDataDir+"/medaUpdates", "olVersion.txt", true, true);
        String onlineVersion = FileLoader.loadFile(mcDataDir+"/medaUpdates/olVersion.txt");
        log("Found online version as: "+onlineVersion);

        // Convert version string
        Float localVersionFL = Float.parseFloat(localVersion);
        Float onlineVersionFL = Float.parseFloat(onlineVersion);

        // Check if newer Version available
        if (onlineVersionFL > localVersionFL) {
        	 int n = JOptionPane.showConfirmDialog(
        	            null,
        	            "A new Version of MedaClient is available, do you want to download and install it?",
        	            "Hexagon Auto Updater (MedaClient Update)",
        	            JOptionPane.YES_NO_OPTION);
        	
        	 if (n == JOptionPane.NO_OPTION)
        		 return false;
        	 
        	 if (n == JOptionPane.CLOSED_OPTION)
        		 return false;
        	 
            log("New Version Available, downloading!");

            // Download the files.txt which contains a list of files which the update requires.
            log("Grabbing file list...");
            FileDownloader.download(ONLINE_VERSION_FOLDER+"/files.txt", mcDataDir+"/medaUpdates", "files.txt", true, true);

            // Start downloading these files
            log("Starting download...\n");
            UpdateLoader.load(new File(mcDataDir+"/medaUpdates/files.txt"), ONLINE_VERSION_FOLDER, mcDataDir);
            System.out.println();
            return true;
        } else {
            log("No New Version Available, stopping!");
            return false;
        }
    }

    public static void log(String s) {
        System.out.println("> "+s);
    }
}
