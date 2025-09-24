// 代码生成时间: 2025-09-24 19:37:25
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import javax.sql.DataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DatabaseConnectionPoolManager class is responsible for managing a database connection pool.
 * It provides the functionality to initialize the pool and obtain connections from it.
 */
public class DatabaseConnectionPoolManager {

    private SqlSessionFactory sqlSessionFactory;
    private DataSource dataSource;

    /**
     * Initializes the database connection pool.
     *
     * @param myBatisConfigPath The path to the MyBatis configuration file.
     * @param databaseUrl       The JDBC URL of the database.
     * @param username          The username to connect to the database.
     * @param password          The password to connect to the database.
     */
    public void initPool(String myBatisConfigPath, String databaseUrl, String username, String password) {
        try {
            // Create a pooled data source
            dataSource = new PooledDataSource(databaseUrl, username, password);

            // Build the SqlSessionFactory
            Reader reader = Resources.getResourceAsReader(myBatisConfigPath);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

            // Configure transaction factory and environment
            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Environment environment = new Environment("development", transactionFactory, dataSource);
            Configuration configuration = new Configuration();
            configuration.setEnvironment(environment);

            // Register the configuration with the SqlSessionFactory
            sqlSessionFactory.getConfiguration().addMapper(MyMapper.class);
        } catch (Exception e) {
            // Handle initialization errors
            e.printStackTrace();
        }
    }

    /**
     * Obtains a connection from the pool.
     *
     * @return A connection from the database connection pool.
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            // Handle connection errors
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Closes the connection pool and releases all resources.
     */
    public void closePool() {
        if (dataSource instanceof PooledDataSource) {
            ((PooledDataSource) dataSource).forceCloseAll();
        }
    }
}
