// 代码生成时间: 2025-08-17 03:51:47
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.mapping.MapperRegistry;
import org.apache.ibatis.session.Configuration.SqlFragmentBuilder;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseMigrationTool {

    // 方法：初始化MyBatis环境
    private SqlSessionFactory getSqlSessionFactory() throws Exception {
        String resource = "mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        Configuration configuration = new Configuration();
        configuration.addMapper(MigrationMapper.class);
        Environment environment = new Environment("development", new JdbcTransactionFactory(),
                configuration.getDatabase());
        configuration.setEnvironment(environment);
        return new DefaultSqlSessionFactory(configuration);
    }

    // 方法：执行数据库迁移
    public void migrateDatabase() {
        try (SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
             SqlSession sqlSession = sqlSessionFactory.openSession()) {

            // 事务管理
            TransactionFactory transactionFactory = sqlSessionFactory.getConfiguration().getEnvironment().getTransactionFactory();
            if (!(transactionFactory instanceof JdbcTransactionFactory)) {
                throw new RuntimeException("JDBC Transaction Factory required");
            }
            Connection connection = ((JdbcTransactionFactory) transactionFactory).getTransaction(sqlSession.getConnection()).getConnection();

            // 执行迁移脚本
            MigrationMapper mapper = sqlSession.getMapper(MigrationMapper.class);
            mapper.migrateDatabase();

            // 提交事务
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Database migration failed: " + e.getMessage());
        }
    }

    // 主方法：程序入口点
    public static void main(String[] args) {
        DatabaseMigrationTool tool = new DatabaseMigrationTool();
        tool.migrateDatabase();
    }
}

// 迁移Mapper接口
interface MigrationMapper {
    void migrateDatabase();
}

// 注意：请确保mybatis-config.xml和数据库驱动已正确配置