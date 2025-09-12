// 代码生成时间: 2025-09-13 03:03:33
package com.example.service;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.example.mapper.UserMapper;
import com.example.model.User;

import java.util.Optional;

public class CachingService {

    // MyBatis SqlSessionFactory
    private final SqlSessionFactory sqlSessionFactory;

    public CachingService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Method to fetch a user from the database with caching.
     * 
     * @param userId The ID of the user to fetch.
     * @return An Optional User object, or empty if not found.
     */
    public Optional<User> getUserWithCaching(int userId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            // Try to get the user from the cache first
            Cache cache = session.getConfiguration().getCache(UserMapper.class);
            String cacheKey = String.valueOf(userId);
            if (cache != null && cache.containsKey(cacheKey)) {
                // Retrieve the user from the cache
                return Optional.of((User) cache.getObject(cacheKey));
            } else {
                // If not in cache, fetch from the database
                User user = userMapper.getUserById(userId);
                if (user != null) {
                    // Store the user in the cache
                    cache.putObject(cacheKey, user);
                    return Optional.of(user);
                } else {
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            // Handle any errors that occur
            System.err.println("Error retrieving user: " + e.getMessage());
            return Optional.empty();
        }
    }
}
