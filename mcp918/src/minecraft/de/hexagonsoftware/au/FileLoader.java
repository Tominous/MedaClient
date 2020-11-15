package de.hexagonsoftware.au;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.felixeckert.medaclient.util.ExceptionHandler;

public class FileLoader {
    public static String loadFile(String file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
        	ExceptionHandler.getHandler().reportException(e, "Could not load a file: IOException");
        }
        return "";
    }
}
