// 代码生成时间: 2025-09-22 21:37:04
 * This class provides an interface to load and retrieve configuration values.
 */
package com.example.config;

import java.io.InputStream;
import java.util.Properties;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ConfigurationManager {

    // Properties instance to store configuration values
    private Properties properties;

    // Constructor to initialize properties
    public ConfigurationManager() {
        properties = new Properties();
    }

    /*
     * Loads configuration from a given resource file.
     * @param resourcePath The path to the configuration file.
     * @throws Exception If there is an error reading the configuration file.
     */
    public void loadConfiguration(String resourcePath) throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new Exception("Resource not found: " + resourcePath);
            }
        } catch (Exception e) {
            throw new Exception("Error loading configuration.", e);
        }
    }

    /*
     * Retrieves a property value by its key.
     * @param key The key of the property to retrieve.
     * @return The value of the property or null if not found.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /*
     * Demonstrates the usage of MyBatis to create a SqlSessionFactory and get a SqlSession.
     * @param configPath The path to the MyBatis configuration file.
     * @return SqlSessionFactory instance.
     */
    public SqlSessionFactory createSqlSessionFactory(String configPath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configPath);
            if (inputStream != null) {
                return new SqlSessionFactoryBuilder().build(inputStream);
            } else {
                throw new RuntimeException("MyBatis configuration not found: " + configPath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating SqlSessionFactory.", e);
        }
    }

    /*
     * Main method for demonstration purposes.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        try {
            ConfigurationManager configManager = new ConfigurationManager();
            configManager.loadConfiguration("config.properties");
            System.out.println("Database URL: " + configManager.getProperty("db.url"));

            // Creating SqlSessionFactory
            String myBatisConfigPath = "mybatis-config.xml";
            SqlSessionFactory sqlSessionFactory = configManager.createSqlSessionFactory(myBatisConfigPath);
            try (SqlSession session = sqlSessionFactory.openSession()) {
                // Use session to interact with database
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
