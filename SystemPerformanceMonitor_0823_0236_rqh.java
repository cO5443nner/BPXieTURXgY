// 代码生成时间: 2025-08-23 02:36:38
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * SystemPerformanceMonitor class uses MyBatis to interact with the database and
 * monitor system performance.
 */
public class SystemPerformanceMonitor {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor to initialize the SqlSessionFactory.
     * @param sqlSessionFactory The SqlSessionFactory to use for database operations.
     */
    public SystemPerformanceMonitor(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Retrieves system performance data from the database.
     * @return A map containing system performance metrics.
     */
    public Map<String, Object> getSystemPerformanceData() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SystemPerformanceMapper mapper = session.getMapper(SystemPerformanceMapper.class);
            return mapper.getSystemPerformance();
        } catch (Exception e) {
            // Handle any exceptions that occur during database operations.
            e.printStackTrace();
            return null;
        }
    }
}

/**
 * Mapper interface for system performance data retrieval.
 */
@Mapper
public interface SystemPerformanceMapper {

    /**
     * Retrieves system performance metrics from the database.
     * @return A map containing system performance metrics.
     */
    @Select("SELECT cpu_usage, memory_usage, disk_usage FROM system_performance")
    Map<String, Object> getSystemPerformance();
}

/**
 * Properties class for database configuration.
 */
class DatabaseConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/system_monitor";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Properties getProperties() {
        Properties props = new HashMap<>();
        props.put("driver", DRIVER);
        props.put("url", URL);
        props.put("username", USER);
        props.put("password", PASSWORD);
        return props;
    }
}

/**
 * Main class to run the SystemPerformanceMonitor.
 */
public class Main {
    public static void main(String[] args) {
        // Instantiate the SqlSessionFactory using the configuration from DatabaseConfig.
        Properties props = DatabaseConfig.getProperties();
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(props);

        // Create an instance of SystemPerformanceMonitor.
        SystemPerformanceMonitor monitor = new SystemPerformanceMonitor(sqlSessionFactory);

        // Retrieve and print system performance data.
        Map<String, Object> performanceData = monitor.getSystemPerformanceData();
        if (performanceData != null) {
            performanceData.forEach((key, value) -> System.out.println(key + ": " + value));
        } else {
            System.out.println("Failed to retrieve system performance data.");
        }
    }
}
