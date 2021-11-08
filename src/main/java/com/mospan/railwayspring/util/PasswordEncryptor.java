package com.mospan.railwayspring.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {
    private PasswordEncryptor() {
    }

    public static String hashPassword(String password) {
        MessageDigest md;
        String hexString = "";
        try {
            md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);

            hexString = number.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return hexString;
    }


}
