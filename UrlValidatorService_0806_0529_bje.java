// 代码生成时间: 2025-08-06 05:29:13
package com.example.urlvalidator;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlValidatorService {

    private static final Logger logger = LoggerFactory.getLogger(UrlValidatorService.class);

    private SqlSessionFactory sqlSessionFactory;

    public UrlValidatorService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 验证URL链接的有效性
     * @param urlString URL字符串
     * @return 是否有效
     */
    public boolean validateUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            try {
                HttpGet request = new HttpGet(urlString);
                CloseableHttpResponse response = httpClient.execute(request);
                try {
                    HttpEntity entity = response.getEntity();
                    int statusCode = response.getStatusLine().getStatusCode();
                    // 200 OK, 404 Not Found
                    return statusCode >= 200 && statusCode < 300;
                } finally {
                    response.close();
                }
            } finally {
                httpClient.close();
            }
        } catch (MalformedURLException e) {
            logger.error("Invalid URL format: {}", urlString, e);
            return false;
        } catch (IOException e) {
            logger.error("Error accessing URL: {}", urlString, e);
            return false;
        }
    }

    public static void main(String[] args) {
        // Example usage
        SqlSessionFactory sqlSessionFactory = null; // Initialize with actual SqlSessionFactory
        UrlValidatorService urlValidatorService = new UrlValidatorService(sqlSessionFactory);
        String testUrl = "http://www.example.com";
        boolean isValid = urlValidatorService.validateUrl(testUrl);
        System.out.println("URL is valid: " + isValid);
    }
}
