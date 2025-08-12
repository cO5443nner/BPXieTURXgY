// 代码生成时间: 2025-08-12 15:32:52
package com.example.service;

import com.example.mapper.UserMapper;
import com.example.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.Configuration;

import java.io.Reader;
import java.util.Properties;

public class UserAuthenticationService {

    private SqlSessionFactory sqlSessionFactory;

    // Constructor to initialize the SqlSessionFactory
    public UserAuthenticationService() {
        try {
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlSessionFactory", e);
        }
    }

    // Method to authenticate a user
    public boolean authenticateUser(String username, String password) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.findUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                return true; // Authentication successful
            } else {
                return false; // Authentication failed
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            return false; // Handle exceptions and return false in case of failure
        }
    }
}
