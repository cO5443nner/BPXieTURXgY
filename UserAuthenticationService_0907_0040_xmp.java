// 代码生成时间: 2025-09-07 00:40:51
package com.example.auth;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.Reader;
import java.util.Properties;
import com.example.model.User;
import com.example.mapper.UserMapper;

public class UserAuthenticationService {
    // 构建SqlSessionFactory
    private SqlSessionFactory sqlSessionFactory;
    public UserAuthenticationService(Reader reader) {
        Properties properties = new Properties();
        properties.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("url", "jdbc:mysql://localhost:3306/your_database");
        properties.setProperty("username", "your_username");
        properties.setProperty("password", "your_password");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, properties);
    }

    // 用户登录验证
    public boolean authenticateUser(String username, String password) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.findUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取用户信息
    public User getUserInfo(String username) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.findUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
