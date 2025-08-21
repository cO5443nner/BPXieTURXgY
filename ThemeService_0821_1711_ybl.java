// 代码生成时间: 2025-08-21 17:11:38
package com.example.theme;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.Properties;

public class ThemeService {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor to initialize the ThemeService with SqlSessionFactory.
     * @param sqlSessionFactory The SqlSessionFactory to use for database operations.
     */
    public ThemeService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Switches the theme for a given user.
     * @param userId The ID of the user.
     * @param themeName The name of the theme to switch to.
     * @throws ThemeNotFoundException If the theme does not exist.
     */
    public void switchTheme(int userId, String themeName) throws ThemeNotFoundException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            ThemeMapper themeMapper = session.getMapper(ThemeMapper.class);
            Theme theme = themeMapper.getThemeByName(themeName);
            if (theme == null) {
                throw new ThemeNotFoundException("Theme with name ' " + themeName + " ' not found.");
            }
            UserThemeMapper userThemeMapper = session.getMapper(UserThemeMapper.class);
            userThemeMapper.updateTheme(userId, theme.getThemeId());
        } catch (Exception e) {
            throw new RuntimeException("Error switching theme", e);
        }
    }
}

/**
 * ThemeNotFoundException.java
 * Custom exception for theme not found errors.
 */
class ThemeNotFoundException extends Exception {
    public ThemeNotFoundException(String message) {
        super(message);
    }
}

/**
 * ThemeMapper.java - MyBatis Mapper Interface
 * Defines methods for database interactions related to themes.
 */
package com.example.theme.mapper;

import com.example.theme.model.Theme;
import org.apache.ibatis.annotations.Select;

public interface ThemeMapper {

    @Select("SELECT * FROM themes WHERE name = #{themeName}")
    Theme getThemeByName(String themeName);
}

/**
 * UserThemeMapper.java - MyBatis Mapper Interface
 * Defines methods for database interactions related to user themes.
 */
package com.example.theme.mapper;

import org.apache.ibatis.annotations.Update;

public interface UserThemeMapper {

    @Update("UPDATE user_theme SET theme_id = #{themeId} WHERE user_id = #{userId}")
    void updateTheme(int userId, int themeId);
}

/**
 * Theme.java
 * Represents a theme in the system.
 */
package com.example.theme.model;

public class Theme {
    private int themeId;
    private String name;

    public Theme(int themeId, String name) {
        this.themeId = themeId;
        this.name = name;
    }

    // Getters and setters...
}
