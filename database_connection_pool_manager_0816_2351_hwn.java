// 代码生成时间: 2025-08-16 23:51:28
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import javax.sql.DataSource;
import java.io.Reader;
import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;

public class DatabaseConnectionPoolManager {

    // 使用单例模式管理数据库连接池
    private static DatabaseConnectionPoolManager instance;
    private SqlSessionFactory sqlSessionFactory;

    private DatabaseConnectionPoolManager() {
        // 初始化数据库连接池
        initSqlSessionFactory();
    }

    public static synchronized DatabaseConnectionPoolManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionPoolManager();
        }
        return instance;
    }

    private void initSqlSessionFactory() {
        try {
            // 读取MyBatis配置文件
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            Reader reader = Resources.newReader(inputStream, "UTF-8");

            // 创建SqlSessionFactoryBuilder
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

            // 使用SqlSessionFactoryBuilder构建SqlSessionFactory对象
            this.sqlSessionFactory = sqlSessionFactoryBuilder.build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    // 创建数据库连接池
    public DataSource createDataSource() {
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydatabase");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        return dataSource;
    }

    // 配置MyBatis环境
    private Environment createEnvironment(DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        return environment;
    }

    // 关闭数据库连接池
    public void closeDataSource(DataSource dataSource) {
        if (dataSource instanceof PooledDataSource) {
            ((PooledDataSource) dataSource).forceCloseAll();
        }
    }
}
