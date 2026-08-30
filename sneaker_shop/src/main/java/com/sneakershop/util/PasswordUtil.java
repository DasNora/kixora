package com.sneakershop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * PasswordUtil handles secure password hashing using SHA-256 with a random salt.
 *
 * <p>Stored format: "base64(salt):base64(sha256(salt + password))".
 * Even if two users pick the exact same password, their stored hashes will look
 * completely different because every user gets a unique 16-byte salt.</p>
 *
 * <p>Java 8 compatible — uses java.util.Base64 and explicit string operations.</p>
 */
public class PasswordUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Hashes a plain-text password with a fresh random salt.
     * Returns a colon-separated string: "salt:hash" (both Base64 encoded).
     */
    public static String hashPassword(String plainPassword) {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);

        String hashBase64 = computeHash(plainPassword, salt);

        return saltBase64 + ":" + hashBase64;
    }

    /**
     * Verifies a plain-text password against a previously stored "salt:hash" string.
     * Returns true if the password matches.
     */
    public static boolean verifyPassword(String plainPassword, String storedValue) {
        if (storedValue == null || !storedValue.contains(":")) {
            return false;
        }

        String[] parts = storedValue.split(":", 2);
        String saltBase64 = parts[0];
        String expectedHash = parts[1];

        byte[] salt = Base64.getDecoder().decode(saltBase64);
        String computedHash = computeHash(plainPassword, salt);

        return computedHash.equals(expectedHash);
    }

    /**
     * Computes SHA-256(salt bytes + password bytes) and returns it as a Base64 string.
     */
    private static String computeHash(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            md.update(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            byte[] hashBytes = md.digest();
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}