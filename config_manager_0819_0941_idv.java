// 代码生成时间: 2025-08-19 09:41:23
// config_manager.java

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件管理器
 * 该类负责管理MyBatis配置文件，提供获取SqlSessionFactory的接口
 */
public class ConfigManager {

    private static final String CONFIG_FILE = "mybatis-config.xml";
    private static SqlSessionFactory sqlSessionFactory;

    /**
     * 获取SqlSessionFactory实例
     * @return SqlSessionFactory
     * @throws IOException 当配置文件无法加载时抛出异常
     */
    public synchronized static SqlSessionFactory getSqlSessionFactory() throws IOException {
        if (sqlSessionFactory == null) {
            String resource = ConfigManager.class.getClassLoader().getResource(CONFIG_FILE).toString();
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        return sqlSessionFactory;
    }

    /**
     * 关闭SqlSessionFactory
     */
    public static void closeSqlSessionFactory() {
        if (sqlSessionFactory != null) {
            sqlSessionFactory.close();
        }
    }

    /**
     * 测试配置文件管理器的功能
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        try {
            SqlSessionFactory factory = getSqlSessionFactory();
            SqlSession session = factory.openSession();
            try {
                // TODO: 这里可以添加具体的测试代码，例如执行查询等
                // session.selectList("selectUsers", "users");
                System.out.println("SqlSessionFactory and SqlSession created successfully.");
            } finally {
                session.close();
            }
        } catch (IOException e) {
            System.err.println("Error loading MyBatis configuration: " + e.getMessage());
        }
    }
}