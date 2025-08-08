// 代码生成时间: 2025-08-08 18:59:31
package com.example.converter;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 文档格式转换器
 * 使用MYBATIS框架实现文档格式的转换功能
 */
public class DocumentConverter {

    private static final String MYBATIS_CONFIG = "mybatis-config.xml";
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 构造函数，初始化SQL会话工厂
     */
    public DocumentConverter() {
        String resource = MYBATIS_CONFIG;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 关闭SQL会话工厂
     */
    public void close() {
        if (sqlSessionFactory != null) {
            sqlSessionFactory.close();
        }
    }

    /**
     * 转换文档格式
     * @param documentId 文档ID
     * @param targetFormat 目标格式
     * @return 转换结果
     */
    public String convertDocumentFormat(String documentId, String targetFormat) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("documentId", documentId);
            params.put("targetFormat", targetFormat);

            // 调用MyBatis映射器执行转换操作
            String result = session.selectOne("convertDocumentFormat", params);

            if (result == null) {
                throw new IllegalArgumentException("转换失败，未找到文档");
            }

            return result;
        } catch (Exception e) {
            // 处理异常情况
            e.printStackTrace();
            throw new RuntimeException("文档格式转换失败", e);
        }
    }

    // 测试用例
    public static void main(String[] args) {
        DocumentConverter converter = new DocumentConverter();
        try {
            String result = converter.convertDocumentFormat("123", "PDF");
            System.out.println("转换结果: " + result);
        } finally {
            converter.close();
        }
    }
}
