package de.felixeckert.medaclient.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class FileUtils {
	public static Properties readPropertiesFile(String fileName) throws IOException {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(fis);
		} catch(FileNotFoundException fnfe) {
			ExceptionHandler.getHandler().reportException(fnfe, "Could not read properties file: File not found");
		} catch(IOException ioe) {
			ExceptionHandler.getHandler().reportException(ioe, "Could not read properties file: IOException");
		} finally {
			fis.close();
		}
		
		return prop;
	}
}
