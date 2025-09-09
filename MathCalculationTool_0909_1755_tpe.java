// 代码生成时间: 2025-09-09 17:55:29
package com.example.mathtool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;

public class MathCalculationTool {

    // MyBatis' SqlSessionFactory
    private SqlSessionFactory sqlSessionFactory;

    // Constructor to initialize SqlSessionFactory
    public MathCalculationTool() {
        String resource = "mybatis-config.xml";
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(getClass().getClassLoader().getResourceAsStream(resource));
    }

    // Addition method
    public BigDecimal add(BigDecimal number1, BigDecimal number2) {
        return number1.add(number2);
    }

    // Subtraction method
    public BigDecimal subtract(BigDecimal number1, BigDecimal number2) {
        return number1.subtract(number2);
    }

    // Multiplication method
    public BigDecimal multiply(BigDecimal number1, BigDecimal number2) {
        return number1.multiply(number2);
    }

    // Division method
    public BigDecimal divide(BigDecimal number1, BigDecimal number2) {
        if (number2.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return number1.divide(number2, 10, RoundingMode.HALF_UP);
    }

    // Close the SqlSessionFactory to release resources
    public void close() {
        if (sqlSessionFactory != null) {
            SqlSessionManager.closeSqlSession(sqlSessionFactory);
        }
    }

    // Main method for demonstration
    public static void main(String[] args) {
        MathCalculationTool tool = new MathCalculationTool();
        try {
            BigDecimal num1 = new BigDecimal("10.5");
            BigDecimal num2 = new BigDecimal("3.7");

            // Perform calculations
            BigDecimal sum = tool.add(num1, num2);
            BigDecimal difference = tool.subtract(num1, num2);
            BigDecimal product = tool.multiply(num1, num2);
            BigDecimal quotient = tool.divide(num1, num2);

            // Output the results
            System.out.println("Addition: " + sum);
            System.out.println("Subtraction: " + difference);
            System.out.println("Multiplication: " + product);
            System.out.println("Division: " + quotient);
        } catch (ArithmeticException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            tool.close();
        }
    }
}
