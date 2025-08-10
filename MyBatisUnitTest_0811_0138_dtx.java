// 代码生成时间: 2025-08-11 01:38:29
package com.example.mybatis.unit;

import static org.junit.Assert.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.entity.User;

public class MyBatisUnitTest {
    // SqlSessionFactory对象用于创建SqlSession
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private UserMapper userMapper;

    @Before
    public void setUp() throws Exception {
        // 加载MyBatis配置文件，创建SqlSessionFactory
        sqlSessionFactory = getSqlSessionFactory();
        // 创建SqlSession对象
        sqlSession = sqlSessionFactory.openSession();
        // 获取UserMapper接口的实现
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void tearDown() throws Exception {
        // 关闭SqlSession
        sqlSession.close();
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        int result = userMapper.insertUser(user);
        assertTrue("Insert operation failed", result > 0);
    }

    @Test
    public void testFindUserById() {
        User user = userMapper.findUserById(1);
        assertNotNull("User not found", user);
        assertEquals("Name does not match", "Existing User", user.getName());
    }

    private SqlSessionFactory getSqlSessionFactory() {
        // 此处应添加读取MyBatis配置文件并创建SqlSessionFactory的逻辑
        // 例如：使用MyBatis提供的Resources类加载配置文件
        try {
            String resource = "mybatis-config.xml";
            org.apache.ibatis.io.Resources resources = new org.apache.ibatis.io.Resources();
            resources.getResourceAsStream(resource);
            return new org.apache.ibatis.session.SqlSessionFactoryBuilder().build(
                resources.getResourceAsReader(resource));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
