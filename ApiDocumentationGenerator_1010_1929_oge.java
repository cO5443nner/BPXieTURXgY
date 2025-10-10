// 代码生成时间: 2025-10-10 19:29:58
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * API文档自动生成器，使用MyBatis框架生成API文档
 */
public class ApiDocumentationGenerator {

    private static final String CONFIGURATION_XML = "mybatis-config.xml"; // 配置文件路径
    private static final String DOCUMENTATION_XML = "api-documentation.xml"; // API文档配置文件路径

    public static void main(String[] args) {
        try {
            // 解析MyBatis配置文件
            List<String> warnings = new ArrayList<>();
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(new InputSource(DOCUMENTATION_XML));

            // 运行MyBatis生成器
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, new DefaultShellCallback(true), warnings);
            myBatisGenerator.generate(null);

            // 输出警告信息
            for (String warning : warnings) {
                System.out.println(warning);
            }

        } catch (XMLParserException e) {
            System.err.println("Error parsing MyBatis configuration: " + e.getMessage());
        } catch (InvalidConfigurationException e) {
            System.err.println("Invalid MyBatis configuration: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error generating API documentation: " + e.getMessage());
        }
    }

    /**
     * 解析XML文档
     *
     * @param xml xml字符串
     * @return Document对象
     * @throws Exception 抛出解析异常
     */
    private static Document parseXml(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }
}
