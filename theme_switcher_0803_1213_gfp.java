// 代码生成时间: 2025-08-03 12:13:16
package com.example.themeswitcher;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides functionality to switch themes for a user.
 */
public class ThemeSwitcher {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // Load MyBatis configuration file
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches the theme for a user with the given userId.
     * 
     * @param userId the ID of the user
     * @param newTheme the new theme to set for the user
     * @return true if the operation was successful, false otherwise
     */
    public boolean switchTheme(int userId, String newTheme) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Start the transaction
            session.startTransaction();

            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("newTheme", newTheme);

            // Call the theme update method from the mapper
            int updatedRows = session.update("ThemeMapper.updateUserTheme", params);

            // Commit the transaction if update was successful
            if (updatedRows > 0) {
                session.commit();
                return true;
            } else {
                session.rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
