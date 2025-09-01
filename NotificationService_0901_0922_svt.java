// 代码生成时间: 2025-09-01 09:22:08
package com.example.notification;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.List;
import java.util.UUID;

/**
 * Service class for managing notifications.
 */
public class NotificationService {

    private final SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor for NotificationService.
     * @param sqlSessionFactory The factory for creating SQL sessions.
     */
    public NotificationService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Send a notification to a specific user.
     * @param userId The ID of the user to send the notification to.
     * @param message The message content of the notification.
     * @return The ID of the created notification.
     */
    public String sendNotification(String userId, String message) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Begin transaction
            session.startTransaction();

            String notificationId = UUID.randomUUID().toString();
            // Assuming a mapper named NotificationMapper exists for database operations
            session.insert("NotificationMapper.insertNotification",
                    new Notification(notificationId, userId, message));

            // Commit transaction
            session.commit();

            return notificationId;
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get a list of notifications for a specific user.
     * @param userId The ID of the user to retrieve notifications for.
     * @return A list of notifications.
     */
    public List<Notification> getNotifications(String userId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Assuming a mapper named NotificationMapper exists for database operations
            return session.selectList("NotificationMapper.selectNotificationsByUser", userId);
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }
}

/**
 * Data class for Notification.
 */
class Notification {
    private String id;
    private String userId;
    private String message;

    public Notification(String id, String userId, String message) {
        this.id = id;
        this.userId = userId;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}
