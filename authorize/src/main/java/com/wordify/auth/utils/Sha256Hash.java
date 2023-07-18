package com.wordify.auth.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Hash {
    public static String getSha256Hash(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            byte[] hash = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
