// 代码生成时间: 2025-08-06 16:53:11
package com.example.monitor;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.session.AutoCommitSQLException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.Session;
import org.apache.ibatis.session.SqlSessionManager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * System Performance Monitor using Java and MyBatis
 */
public class SystemPerformanceMonitor {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSessionManager sqlSessionManager;

    // Constructor
    public SystemPerformanceMonitor() {
        try {
            // Load MyBatis configuration file
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);

            // Create a SqlSessionFactory and SqlSessionManager
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            this.sqlSessionManager = new SqlSessionManager(sqlSessionFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to monitor system performance
    public void monitorPerformance() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // Auto commit is disabled
            sqlSession.getConfiguration().setExecutorType(ExecutorType.SIMPLE);
            sqlSession.commit();
            // Retrieve system performance data using MyBatis
            SystemPerformanceMapper systemPerformanceMapper = sqlSession.getMapper(SystemPerformanceMapper.class);
            SystemPerformanceData performanceData = systemPerformanceMapper.fetchSystemPerformanceData();

            // Process and display system performance data
            displaySystemPerformanceData(performanceData);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    // Method to display system performance data
    private void displaySystemPerformanceData(SystemPerformanceData performanceData) {
        if (performanceData != null) {
            System.out.println("CPU Usage: " + performanceData.getCpuUsage() + "%");
            System.out.println("Memory Usage: " + performanceData.getMemoryUsage() + " MB");
            System.out.println("Disk Usage: " + performanceData.getDiskUsage() + " GB");
        } else {
            System.out.println("No system performance data available.");
        }
    }

    // Main method to run the system performance monitor
    public static void main(String[] args) {
        SystemPerformanceMonitor monitor = new SystemPerformanceMonitor();
        monitor.monitorPerformance();
    }
}

// System Performance Data class
class SystemPerformanceData {
    private double cpuUsage;
    private long memoryUsage;
    private double diskUsage;

    // Getters and setters
    public double getCpuUsage() {
        return cpuUsage;
    }
    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }
    public long getMemoryUsage() {
        return memoryUsage;
    }
    public void setMemoryUsage(long memoryUsage) {
        this.memoryUsage = memoryUsage;
    }
    public double getDiskUsage() {
        return diskUsage;
    }
    public void setDiskUsage(double diskUsage) {
        this.diskUsage = diskUsage;
    }
}

// System Performance Mapper interface
interface SystemPerformanceMapper {
    SystemPerformanceData fetchSystemPerformanceData();
}
