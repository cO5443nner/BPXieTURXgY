// 代码生成时间: 2025-08-25 03:15:02
package com.example.configmanager;

import java.io.InputStream;
import java.util.Properties;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 * ConfigFileManager is a utility class used to manage configuration files.
 * It uses MyBatis framework to work with database configurations.
 */
public class ConfigFileManager {

    private static SqlSessionFactory sqlSessionFactory;
    private static Properties configProperties;

    static {
        // Initialize properties from a properties file
        try {
            configProperties = new Properties();
            InputStream inputStream = Resources.getResourceAsStream(
                "config.properties"); // Assuming config.properties is in the classpath
            configProperties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Could not load configuration properties.", e);
        }

        // Initialize MyBatis SqlSessionFactory
        try {
            String resource = configProperties.getProperty("mybatis.config");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
        } catch (Exception e) {
            throw new RuntimeException("Error initializing MyBatis SqlSessionFactory.", e);
        }
    }

    /**
     * Retrieves a database session from MyBatis.
     * @return SqlSession instance.
     */
    private static SqlSession getSqlSession() {
        try {
            return sqlSessionFactory.openSession();
        } catch (PersistenceException e) {
            throw new RuntimeException("Could not get database session.", e);
        }
    }

    /**
     * Reads a configuration value from the database.
     * @param key The configuration key.
     * @return The configuration value.
     */
    public static String getConfigValue(String key) {
        try (SqlSession session = getSqlSession()) {
            ConfigMapper configMapper = session.getMapper(ConfigMapper.class);
            return configMapper.getConfigValue(key);
        }
    }

    /**
     * Updates a configuration value in the database.
     * @param key The configuration key.
     * @param value The new configuration value.
     */
    public static void updateConfigValue(String key, String value) {
        try (SqlSession session = getSqlSession()) {
            ConfigMapper configMapper = session.getMapper(ConfigMapper.class);
            configMapper.updateConfigValue(key, value);
            session.commit();
        } catch (PersistenceException e) {
            throw new RuntimeException("Could not update configuration value.", e);
        }
    }
}
