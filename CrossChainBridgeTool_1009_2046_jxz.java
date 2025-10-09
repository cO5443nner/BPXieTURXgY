// 代码生成时间: 2025-10-09 20:46:52
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

// CrossChainBridgeTool类提供了跨链桥接的基本功能
public class CrossChainBridgeTool {
    // 数据源配置文件
    private static final String CONFIGURATION_FILE = "mybatis-config.xml";

    // 数据源对象
    private DataSource dataSource;

    // 构造函数，初始化数据源
    public CrossChainBridgeTool(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 初始化MyBatis的SqlSessionFactory
    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIGURATION_FILE);
        if (inputStream == null) {
            throw new IOException("MyBatis configuration file not found");
        }
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    // 获取数据库连接
    public Connection getDatabaseConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 执行跨链操作
    public boolean executeCrossChainOperation(String operation, Object... params) {
        try (SqlSession session = getSqlSessionFactory().openSession()) {
            // 根据操作类型获取相应的Mapper
            Mapper mapper = session.getMapper(CrossChainMapper.class);
            // 执行操作
            boolean result = mapper.performOperation(operation, params);
            session.commit();
            return result;
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return false;
        }
    }

    // MyBatis Mapper接口，定义跨链操作
    @Mapper
    public interface CrossChainMapper {
        // 执行具体的跨链操作
        @Select("{call execute_cross_chain_operation(#{operation}, #{params})}")
        boolean performOperation(String operation, Object... params);
    }
}
