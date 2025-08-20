// 代码生成时间: 2025-08-21 06:24:08
package com.example.payment;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * PaymentService class handles the payment process.
 */
public class PaymentService {

    private SqlSessionFactory sqlSessionFactory;

    public PaymentService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Process the payment.
     * @param userId The ID of the user who is making the payment.
     * @param paymentAmount The amount of the payment.
     * @return A map containing the payment status and message.
     */
    public Map<String, Object> processPayment(String userId, double paymentAmount) {
        Map<String, Object> result = new HashMap<>();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Begin transaction
            session.beginTransaction();

            // Check if the user has sufficient balance
            UserAccountMapper userAccountMapper = session.getMapper(UserAccountMapper.class);
            double userBalance = userAccountMapper.getUserBalance(userId);
            if (userBalance < paymentAmount) {
                result.put("status", "failed");
                result.put("message", "Insufficient balance");
                return result;
            }

            // Deduct the payment amount from the user's account
            int updatedRows = userAccountMapper.updateUserBalance(userId, userBalance - paymentAmount);
            if (updatedRows != 1) {
                result.put("status", "failed");
                result.put("message", "Failed to update user balance");
                return result;
            }

            // Insert payment record
            PaymentMapper paymentMapper = session.getMapper(PaymentMapper.class);
            paymentMapper.insertPaymentRecord(userId, paymentAmount);

            // Commit transaction
            session.commit();
            result.put("status", "success");
            result.put("message", "Payment processed successfully");
        } catch (Exception e) {
            // If any exception occurs, rollback the transaction
            e.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("status", "failed");
            result.put("message", e.getMessage());
            return result;
        }
    }
}

/**
 * UserAccountMapper interface for MyBatis.
 */
interface UserAccountMapper {
    double getUserBalance(String userId);
    int updateUserBalance(String userId, double newBalance);
}

/**
 * PaymentMapper interface for MyBatis.
 */
interface PaymentMapper {
    void insertPaymentRecord(String userId, double paymentAmount);
}
