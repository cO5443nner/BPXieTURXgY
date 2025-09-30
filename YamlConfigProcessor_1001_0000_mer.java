// 代码生成时间: 2025-10-01 00:00:48
package com.example.mybatis.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.InputStream;
import java.util.Map;

/**
 * YamlConfigProcessor is a utility class for processing YAML configuration files.
 * It provides a simple way to load YAML files and retrieve configuration settings.
 */
public class YamlConfigProcessor {

    /**
     * Loads the YAML configuration file from the classpath and returns a map of configurations.
     *
     * @param yamlFilePath The path to the YAML file in the classpath.
     * @return A map of configurations.
     */
    public Map<String, Object> loadYamlConfig(String yamlFilePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(yamlFilePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("YAML file not found: " + yamlFilePath);
            }
            Yaml yaml = new Yaml(new Constructor(Map.class));
            return yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error loading YAML configuration: " + e.getMessage(), e);
        }
    }

    /**
     * Gets a configuration value by key.
     *
     * @param yamlFilePath The path to the YAML file in the classpath.
     * @param key The key for the configuration value.
     * @return The configuration value for the given key.
     */
    public Object getConfigValue(String yamlFilePath, String key) {
        Map<String, Object> config = loadYamlConfig(yamlFilePath);
        return config.get(key);
    }
}
