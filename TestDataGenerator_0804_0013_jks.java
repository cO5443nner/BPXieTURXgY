// 代码生成时间: 2025-08-04 00:13:43
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.exceptions.PersistenceException;
import java.io.Reader;
import java.util.Random;

/**
 * TestDataGenerator class is used to generate test data using MyBatis framework.
 * It follows Java best practices for maintainability and extensibility.
 */
public class TestDataGenerator {

    private static final String CONFIGURATION_XML = "mybatis-config.xml";
    private static final String INSERT_STATEMENT = "insertTestData";
    private static final String SELECT_STATEMENT = "selectAllData";
    private static final int MAX_DATA = 100;
    private static final Random random = new Random();

    public static void main(String[] args) {
        try (SqlSessionFactory sqlSessionFactory = getSqlSessionFactory()) {
            SqlSession session = sqlSessionFactory.openSession();
            try {
                TestDataMapper mapper = session.getMapper(TestDataMapper.class);
                // Generate and insert test data
                for (int i = 0; i < MAX_DATA; i++) {
                    String data = generateRandomData();
                    mapper.insertTestData(data);
                }
                // Select all data to verify insert operation
                mapper.selectAllData();
            } catch (Exception e) {
                session.rollback();
                throw new RuntimeException(e);
            } finally {
                session.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SQL session factory", e);
        }
    }

    /**
     * Returns a configured SqlSessionFactory.
     */
    private static SqlSessionFactory getSqlSessionFactory() throws Exception {
        Reader reader = Resources.getResourceAsReader(CONFIGURATION_XML);
        return new SqlSessionFactoryBuilder().build(reader);
    }

    /**
     * Generates random test data.
     */
    private static String generateRandomData() {
        // Generate a random alphanumeric string of length 10
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append((char) (random.nextInt(26) + 'A'));
        }
        return sb.toString();
    }
}

/**
 * TestDataMapper interface for MyBatis mapper configuration.
 */
interface TestDataMapper {
    void insertTestData(String data);
    void selectAllData();
}