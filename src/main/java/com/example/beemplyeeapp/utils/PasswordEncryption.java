package com.example.beemplyeeapp.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class PasswordEncryption {
    private PasswordEncryption() {
    }

    private static final String SECRET_KEY = "encrypitionLicenta";
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/GCM/NoPadding";

    // Fixed IV for demonstration purposes (must be unique for each encryption)
    private static final byte[] FIXED_IV = new byte[12];

    public static String encryptPassword(String password) {
        try {
            SecretKeySpec secretKeySpec = generateKey();
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, FIXED_IV);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decryptPassword(String encryptedPassword) {
        try {
            SecretKeySpec secretKeySpec = generateKey();
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, FIXED_IV);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            return null;
        }
    }

    private static SecretKeySpec generateKey() {
        byte[] keyBytes = new byte[0];
        try {
            keyBytes = Arrays.copyOf(MessageDigest.getInstance("SHA-256")
                    .digest(SECRET_KEY.getBytes()), 16);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}