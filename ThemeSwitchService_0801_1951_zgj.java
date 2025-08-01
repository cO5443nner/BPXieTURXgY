// 代码生成时间: 2025-08-01 19:51:41
package com.example.themeswitch;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * ThemeSwitchService class for theme switching functionality.
 * This class uses MyBatis to interact with the database.
 */
public class ThemeSwitchService {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor with SqlSessionFactoryBuilder.
     * @param resourcePath The path to the MyBatis configuration file.
     */
    public ThemeSwitchService(String resourcePath) {
        try {
            Reader reader = Resources.getResourceAsReader(resourcePath);
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing SqlSessionFactory");
        }
    }

    /**
     * Switches the theme for a user.
     * @param userId The ID of the user.
     * @param newTheme The new theme to switch to.
     * @return A boolean indicating the success of the operation.
     */
    public boolean switchTheme(int userId, String newTheme) {
        boolean success = false;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            ThemeMapper themeMapper = session.getMapper(ThemeMapper.class);
            
            // Check if the theme exists in the database
            if (themeMapper.themeExists(newTheme)) {
                Map<String, Object> params = new HashMap<>();
                params.put("userId", userId);
                params.put("theme", newTheme);

                // Update the user's theme
                int rowsAffected = themeMapper.updateUserTheme(params);
                success = rowsAffected > 0;
            } else {
                throw new IllegalArgumentException("Theme does not exist in the database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Interface for MyBatis mapper.
     */
    public interface ThemeMapper {
        int updateUserTheme(Map<String, Object> params);
        boolean themeExists(String theme);
    }
}
