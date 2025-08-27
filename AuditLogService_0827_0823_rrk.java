// 代码生成时间: 2025-08-27 08:23:25
package com.example.security;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.example.mapper.AuditLogMapper;
import com.example.model.AuditLog;

import java.util.Date;

public class AuditLogService {

    private final SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor for AuditLogService.
     * @param sqlSessionFactory The SqlSessionFactory to use for database operations.
     */
    public AuditLogService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Logs a security audit event to the database.
     * @param user The user performing the action.
     * @param action The action being performed.
     * @param resourceId The ID of the resource affected.
     * @param result The result of the action.
     * @param timestamp The time the action was performed.
     */
    public void logAudit(String user, String action, String resourceId, String result, Date timestamp) {
        // Begin a new transaction
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AuditLogMapper mapper = session.getMapper(AuditLogMapper.class);

            // Create a new AuditLog object
            AuditLog auditLog = new AuditLog();
            auditLog.setUser(user);
            auditLog.setAction(action);
            auditLog.setResourceId(resourceId);
            auditLog.setResult(result);
            auditLog.setTimestamp(timestamp);

            // Insert the audit log into the database
            mapper.insertAuditLog(auditLog);

            // Commit the transaction
            session.commit();
        } catch (Exception e) {
            // Handle any errors that occur during the logging process
            e.printStackTrace();
            // Rollback the transaction in case of error
            // Note: The try-with-resources block will close the session automatically
        }
    }
}
