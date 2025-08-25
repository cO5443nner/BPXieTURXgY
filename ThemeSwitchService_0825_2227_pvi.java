// 代码生成时间: 2025-08-25 22:27:35
package com.example.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import com.example.mapper.UserMapper;
import com.example.model.User;
import java.io.Reader;
import java.io.IOException;
import java.util.Properties;

/**
 * 服务类，用于实现主题切换功能
 */
public class ThemeSwitchService {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * 构造方法，初始化MyBatis的SqlSessionFactory
     */
    public ThemeSwitchService() {
        try {
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException("Failed to get resource as reader", e);
        }
    }

    /**
     * 根据用户ID和新的主题设置，更新用户的当前主题
     *
     * @param userId 用户ID
     * @param newTheme 新的主题设置
     * @return true 如果更新成功，false 否则
     */
    public boolean updateTheme(int userId, String newTheme) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            int updateCount = mapper.updateTheme(userId, newTheme);
            session.commit();
            return updateCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
