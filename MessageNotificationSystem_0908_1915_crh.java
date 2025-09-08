// 代码生成时间: 2025-09-08 19:15:21
package com.example.notification;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.mapping.Environment;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 消息通知系统，使用MYBATIS框架与数据库交互
 */
public class MessageNotificationSystem {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // 加载MYBATIS配置文件
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息通知
     * 
     * @param message 消息内容
     * @param userId 接收者用户ID
     */
    public void sendMessage(String message, int userId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MessageMapper mapper = session.getMapper(MessageMapper.class);
            try {
                mapper.insertMessage(new Message(message, userId));
                session.commit();
            } catch (Exception e) {
                session.rollback();
                throw e;
            }
        }
    }
}

/**
 * MYBATIS映射器接口
 */
interface MessageMapper {

    /**
     * 插入消息记录
     * 
     * @param message 消息记录对象
     */
    void insertMessage(Message message);
}

/**
 * 消息实体类
 */
class Message {
    private String content;
    private int userId;

    public Message(String content, int userId) {
        this.content = content;
        this.userId = userId;
    }

    // Getter和Setter方法
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
