// 代码生成时间: 2025-09-18 12:44:07
package com.example.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * HashCalculatorService is a utility class that provides methods to calculate hash values using
 * various algorithms.
 */
public class HashCalculatorService {

    /**
     * Computes the hash value of the given string using the specified algorithm.
     *
     * @param input The string to calculate the hash for.
     * @param algorithm The name of the algorithm to use (e.g., "MD5", "SHA-256").
     * @return The computed hash value as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the specified algorithm is not available.
     */
    public String computeHash(String input, String algorithm) throws NoSuchAlgorithmException {
        // Get an instance of the MessageDigest class for the specified algorithm
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        // Update the digest using the input string bytes
        digest.update(input.getBytes(StandardCharsets.UTF_8));

        // Compute the hash and convert it to a hexadecimal string
        byte[] hashBytes = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    // Main method for demonstration purposes
    public static void main(String[] args) {
        try {
            HashCalculatorService service = new HashCalculatorService();
            String input = "Hello, World!";
            String md5Hash = service.computeHash(input, "MD5");
            String sha256Hash = service.computeHash(input, "SHA-256");

            System.out.println("MD5 Hash: " + md5Hash);
            System.out.println("SHA-256 Hash: " + sha256Hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: Algorithm not found.");
            e.printStackTrace();
        }
    }
}