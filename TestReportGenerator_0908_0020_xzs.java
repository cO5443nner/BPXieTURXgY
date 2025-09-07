// 代码生成时间: 2025-09-08 00:20:28
package com.example.testreport;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 测试报告生成器，使用MyBatis和FreeMarker框架
 */
public class TestReportGenerator {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * 构造函数，注入SqlSessionFactory
     *
     * @param sqlSessionFactory SqlSessionFactory实例
     */
    public TestReportGenerator(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 生成测试报告
     *
     * @param reportId 报告ID
     * @param writer   报告输出流
     * @throws IOException     如果写入文件时发生IO异常
     * @throws TemplateException 如果模板解析或应用时发生异常
     */
    public void generateReport(String reportId, Writer writer) throws IOException, TemplateException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 加载测试报告模板
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates");
            Template template = cfg.getTemplate("test_report.ftl");

            // 从数据库加载测试报告数据
            Map<String, Object> data = new HashMap<>();
            data.put("reportId", reportId);
            // 假设存在一个Mapper接口和对应的XML文件用于查询测试报告数据
            // TestReportMapper mapper = session.getMapper(TestReportMapper.class);
            // data.put("testResults", mapper.getTestResults(reportId));

            // 应用模板并生成报告
            template.process(data, writer);
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            throw new IOException("Error generating test report", e);
        }
    }
}
