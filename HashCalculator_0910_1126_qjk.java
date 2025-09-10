// 代码生成时间: 2025-09-10 11:26:25
package com.example.hashcalculator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class HashCalculator {

    private final SqlSessionFactory sqlSessionFactory;

    // Constructor
    public HashCalculator(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    // Calculate hash for a given string
    public String calculateHash(String input) {
        try {
            // Get a digest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Perform the hash calculation
            byte[] hashedBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            // Convert bytes to hex string
            StringBuilder hexString = new StringBuilder(2 * hashedBytes.length);
            for (int i = 0; i < hashedBytes.length; i++) {
                String hex = Integer.toHexString(0xff & hashedBytes[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to calculate hash: Algorithm not found", e);
        }
    }

    // Main method to test the hash calculator
    public static void main(String[] args) {
        try (SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"))); {
            HashCalculator calculator = new HashCalculator(sqlSessionFactory);
            String input = "Hello, MyBatis!";
            String hash = calculator.calculateHash(input);
            System.out.println("Input: " + input);
            System.out.println("Hash: " + hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
