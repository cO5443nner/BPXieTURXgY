// 代码生成时间: 2025-08-01 15:53:14
package com.example.themeswitch;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class ThemeSwitchService {

    private static final String MYBATIS_CONFIG = "mybatis-config.xml";
    private static final String USER_THEMES = "userThemes.xml";
    private SqlSessionFactory sqlSessionFactory;

    public ThemeSwitchService() {
        try {
            // 加载 MyBatis 配置文件
            String resource = MYBATIS_CONFIG;
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 切换用户主题
     * @param userId 用户ID
     * @param themeName 主题名称
     * @return 切换结果
     */
    public boolean switchTheme(int userId, String themeName) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            ThemeMapper mapper = session.getMapper(ThemeMapper.class);
            boolean success = mapper.updateTheme(userId, themeName) > 0;
            session.commit();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

// ThemeMapper.java
package com.example.themeswitch;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ThemeMapper {

    @Update("UPDATE user_theme SET theme_name = #{themeName} WHERE user_id = #{userId}")
    int updateTheme(@Param("userId") int userId, @Param("themeName\) String themeName);
}

// user_theme.xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">\
<mapper namespace="com.example.themeswitch.ThemeMapper">\
  <update id="updateTheme">\    UPDATE user_theme
    SET theme_name = #{themeName}
    WHERE user_id = #{userId}
  </update>
</mapper>