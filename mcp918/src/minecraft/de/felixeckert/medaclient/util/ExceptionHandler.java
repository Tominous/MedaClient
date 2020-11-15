package de.felixeckert.medaclient.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

public class ExceptionHandler {
	public static ExceptionHandler INSTANCE;
	private static final ExceptionHandler BACKUP_INSTANCE = new ExceptionHandler("reportAll", "reportToConsole");
	
	private List<String> flags = new LinkedList<>();
	
	public ExceptionHandler(String... flags) {
		for (String flag : flags) {
			this.flags.add(flag);
		}
	}
	
	public void reportException(Exception e, String title) {
		if (title.matches(""))
			title = e.getClass().getName();
		
		if (flags.contains("ignoreAll")) {
			return;
		}
		
		if (flags.contains("reportAll")) {
			if (flags.contains("reportToConsole")) {
				e.printStackTrace();
			} else if (flags.contains("reportAsMsgBox")) {
				reportAsMsgBox(e, title);
			}
		} 
		if (flags.contains("reportIOE")) {
			if ((e instanceof IOException)) {
				if (flags.contains("reportIOEToConsole")) {
					e.printStackTrace();
				} else if (flags.contains("reportIOEAsMsgBox")) {
					reportAsMsgBox(e, title);
				}
			}
		}
		if (flags.contains("reportNPE")) {
			if ((e instanceof NullPointerException)) {
				if (flags.contains("reportNPEToConsole")) {
					e.printStackTrace();
				} else if (flags.contains("reportNPEAsMsgBox")) {
					reportAsMsgBox(e, title);
				}
			}
		}
	}
	
	private void reportAsMsgBox(Exception e, String title) {
		StringWriter sWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(sWriter);
		
		e.printStackTrace(writer);
		writer.flush();
		
		JOptionPane.showMessageDialog(
				null, sWriter.toString(), title, JOptionPane.ERROR_MESSAGE);
	}

	public List<String> getFlags() {
		return this.flags;
	}
	
	public static ExceptionHandler getHandler() {
		return INSTANCE != null ? INSTANCE : BACKUP_INSTANCE;
	}
}
