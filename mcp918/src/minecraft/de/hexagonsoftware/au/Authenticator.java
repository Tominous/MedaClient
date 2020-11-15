package de.hexagonsoftware.au;

import java.net.PasswordAuthentication;
import java.util.Properties;

public class Authenticator extends java.net.Authenticator {
    private final PasswordAuthentication authentication;

    public Authenticator(String usr, String pw) {
        String userName = usr;
        String password = pw;
        if (userName == null || password == null) {
            authentication = null;
        } else {
            authentication = new PasswordAuthentication(userName, password.toCharArray());
        }
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return authentication;
    }
}
