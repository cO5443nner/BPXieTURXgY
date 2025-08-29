// 代码生成时间: 2025-08-29 22:49:12
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 * ConfigManager is a utility class to manage configuration files using MyBatis.
 * It provides methods to load and interact with configuration data.
 */
public class ConfigManager {

    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession sqlSession;

    // Loads the MyBatis configuration file and initializes the SqlSessionFactory.
    static {
        try {
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            sqlSession = sqlSessionFactory.openSession();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get resource as reader", e);
        }
    }

    // Closes the SqlSession and SqlSessionFactory when the application is shutting down.
    public static void shutdown() {
        if (sqlSession != null) {
            sqlSession.close();
        }
        if (sqlSessionFactory != null) {
            sqlSessionFactory.close();
        }
    }

    /**
     * Gets a configuration value by key.
     * @param key The key to look up in the configuration.
     * @return The value associated with the key or null if not found.
     */
    public static String getConfigValue(String key) {
        try {
            ConfigMapper mapper = sqlSession.getMapper(ConfigMapper.class);
            return mapper.getConfigValue(key);
        } catch (PersistenceException e) {
            // Handle persistence exceptions, possibly logging them.
            System.err.println("Error retrieving configuration value: " + e.getMessage());
            return null;
        }
    }
}

// Mapper interface for configuration operations.
interface ConfigMapper {
    String getConfigValue(String key);
}