// 代码生成时间: 2025-08-07 04:34:39
package com.example.security;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.List;

/**
 * Service class for access control operations.
 * Provides methods to check user permissions and perform access control.
 */
public class AccessControlService {

    private final SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor to inject the SqlSessionFactory.
     * @param sqlSessionFactory The SqlSessionFactory instance.
     */
    public AccessControlService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Checks if the user has the specified permission.
     * @param userId The user ID to check permissions for.
     * @param permission The permission to check.
     * @return True if the user has the permission, false otherwise.
     */
    public boolean hasPermission(int userId, String permission) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // MyBatis Mapper Interface
            PermissionMapper permissionMapper = session.getMapper(PermissionMapper.class);
            // Query to check user permission
            List<String> userPermissions = permissionMapper.findPermissionsByUser(userId);
            return userPermissions.contains(permission);
        } catch (Exception e) {
            // Log and handle the exception
            e.printStackTrace();
            return false;
        }
    }
}

/**
 * MyBatis Mapper Interface for permission operations.
 */
interface PermissionMapper {

    /**
     * Finds all permissions for a given user ID.
     * @param userId The user ID to retrieve permissions for.
     * @return A list of permissions.
     */
    List<String> findPermissionsByUser(int userId);
}
