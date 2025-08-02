// 代码生成时间: 2025-08-03 02:45:48
package com.example.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * 演示如何使用MyBatis框架防止SQL注入的例子。
 * 遵循MyBatis的最佳实践和Java编码规范。
 */
public class PreventSqlInjectionExample {

    // MyBatis 配置文件路径
    private static final String MYBATIS_CONFIG = "mybatis-config.xml";
    // 数据库映射文件路径
    private static final String MAPPER_XML = "UserMapper.xml";

    // 定义接口，与映射文件中的内容匹配
    public interface UserMapper {
        @Select("SELECT * FROM users WHERE username = #{username}")
        List<User> findUserByUsername(@Param("username") String username);
    }

    // User实体类
    public static class User {
        private int id;
        private String username;
        private String password;

        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = null;
        
        try {
            // 读取MyBatis配置文件
            Reader reader = Resources.getResourceAsReader(MYBATIS_CONFIG);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();
            UserMapper mapper = session.getMapper(UserMapper.class);

            // 传入的用户参数
            String username = "admin' --"; // 尝试SQL注入攻击

            List<User> users = mapper.findUserByUsername(username);
            // 打印查询结果
            users.forEach(user -> System.out.println("User: " + user.getUsername()));
        } catch (IOException e) {
            System.err.println("Error reading MyBatis configuration: " + e.getMessage());
        } finally {
            if (sqlSessionFactory != null) {
                sqlSessionFactory.close();
            }
        }
    }
}
