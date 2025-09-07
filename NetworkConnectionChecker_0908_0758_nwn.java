// 代码生成时间: 2025-09-08 07:58:54
package com.example.networkchecker;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

/**
 * NetworkConnectionChecker is a utility class used to check the connection status of a given URL.
 * It uses HttpURLConnection to establish a connection and check the response code.
 */
public class NetworkConnectionChecker {

    /**
     * Checks if the connection to the specified URL is successful.
     *
     * @param urlString The URL to check the connection status for.
     * @return true if the connection is successful, false otherwise.
     */
    public boolean checkConnection(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 5 seconds timeout
            connection.connect();
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK; // 200 OK
        } catch (IOException e) {
            // Log the exception and return false indicating the connection failed
            System.err.println("Error checking connection: " + e.getMessage());
            return false;
        }
    }

    /**
     * Main method to test the NetworkConnectionChecker.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        NetworkConnectionChecker checker = new NetworkConnectionChecker();
        String testUrl = "http://www.example.com"; // Replace with the URL to test
        boolean isConnected = checker.checkConnection(testUrl);
        System.out.println("Connection to " + testUrl + " is " + (isConnected ? "successful" : "failed"));
    }
}