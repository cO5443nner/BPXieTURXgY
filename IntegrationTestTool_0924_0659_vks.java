// 代码生成时间: 2025-09-24 06:59:53
package com.example.mybatis.tool;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for performing integration tests with MyBatis.
 * It provides a simple way to execute tests against a database.
 */
public class IntegrationTestTool {

    private static SqlSessionFactory sqlSessionFactory;

    // Initialize the SqlSessionFactory by loading MyBatis configuration file
    static {
        try {
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a SqlSession from the factory for test operations.
     * @return a new SqlSession
     */
    public SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    /**
     * Executes a test query using MyBatis and returns the results.
     * @param mapperClass the mapper interface
     * @param statementId the statement identifier in the mapper
     * @param params the parameters for the query
     * @return the result of the query
     */
    public Object testQuery(Class<?> mapperClass, String statementId, Map<String, Object> params) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Object result = session.selectOne(statementId, params);
            session.commit();
            return result;
        } catch (Exception e) {
            // Rollback in case of error
            e.printStackTrace();
            return null; // or throw a custom exception
        }
    }

    /**
     * Executes a test update using MyBatis.
     * @param mapperClass the mapper interface
     * @param statementId the statement identifier in the mapper
     * @param params the parameters for the update
     * @return the number of rows affected
     */
    public int testUpdate(Class<?> mapperClass, String statementId, Map<String, Object> params) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int rowsAffected = session.update(statementId, params);
            session.commit();
            return rowsAffected;
        } catch (Exception e) {
            // Rollback in case of error
            e.printStackTrace();
            return -1; // or throw a custom exception
        }
    }

    // Additional test methods can be added here as needed

    // Example usage of the tool
    public static void main(String[] args) {
        IntegrationTestTool tool = new IntegrationTestTool();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", 1);

        try {
            // Test query
            Object result = tool.testQuery(UsersMapper.class, "selectUserById", params);
            System.out.println("Test query result: " + result);

            // Test update
            int rowsAffected = tool.testUpdate(UsersMapper.class, "updateUser", params);
            System.out.println("Rows affected: " + rowsAffected);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
