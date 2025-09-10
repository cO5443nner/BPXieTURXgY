// 代码生成时间: 2025-09-11 03:21:29
package com.example.memoryanalysis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;

public class MemoryUsageAnalysis {

    private static final String CONFIGURATION_XML = "mybatis-config.xml";
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "org/mybatis/example/" + CONFIGURATION_XML;
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to get resource " + CONFIGURATION_XML);
        }
    }

    /**
     * 获取 SqlSession
     * 
     * @return SqlSession
     */
    public SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    /**
     * 关闭 SqlSession
     * 
     * @param sqlSession
     */
    public void closeSqlSession(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    /**
     * 打印内存使用情况
     */
    public void printMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        System.out.println("Used Memory: " + usedMemory + " bytes");
        System.out.println("Max Memory: " + maxMemory + " bytes");
    }

    public static void main(String[] args) {
        MemoryUsageAnalysis analysis = new MemoryUsageAnalysis();
        try {
            SqlSession sqlSession = analysis.getSqlSession();
            // Perform database operations here
            analysis.printMemoryUsage();
        } finally {
            analysis.closeSqlSession(null); // No need to pass sqlSession as it is not being used
        }
    }
}
