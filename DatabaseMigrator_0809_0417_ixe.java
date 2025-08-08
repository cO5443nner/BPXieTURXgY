// 代码生成时间: 2025-08-09 04:17:59
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.Reader;
import java.util.Properties;

/**
 * DatabaseMigrator is a utility class for performing database migrations using MyBatis.
 * It provides a simple way to run migration scripts against a database.
 */
public class DatabaseMigrator {

    private static final String MYBATIS_CONFIG = "mybatis-config.xml";
    private static final String MIGRATION_SCRIPT = "migration-script.sql";

    private SqlSessionFactory sqlSessionFactory;

    public DatabaseMigrator() {
        try {
            // Load MyBatis configuration
            String resource = MYBATIS_CONFIG;
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing MyBatis SqlSessionFactory", e);
        }
    }

    /**
     * Executes the database migration script.
     *
     * @param connectionString The connection string for the database.
     * @param user The username for the database.
     * @param password The password for the database.
     */
    public void migrate(String connectionString, String user, String password) {
        Properties props = new Properties();
        props.put("jdbc.url", connectionString);
        props.put("jdbc.username", user);
        props.put("jdbc.password", password);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Begin transaction
            session.startTransaction();
            try {
                // Run the migration script
                Reader scriptReader = Resources.getResourceAsReader(MIGRATION_SCRIPT);
                session.getConfiguration().getMappedStatement("runMigrationScript").bind(session, scriptReader);
                session.commit();
            } catch (Exception e) {
                // Rollback transaction in case of error
                session.rollback();
                throw new RuntimeException("Error executing migration script", e);
            } finally {
                // Close the script reader
                if (scriptReader != null) {
                    try {
                        scriptReader.close();
                    } catch (Exception e) {
                        // Handle script reader close error
                    }
                }
            }
        }
    }

    /**
     * Main method for testing the DatabaseMigrator.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        DatabaseMigrator migrator = new DatabaseMigrator();
        try {
            // Replace with your actual database connection details
            String connectionString = "jdbc:mysql://localhost:3306/your_database?useSSL=false";
            String user = "your_username";
            String password = "your_password";
            migrator.migrate(connectionString, user, password);
            System.out.println("Database migration completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
