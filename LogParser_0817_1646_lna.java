// 代码生成时间: 2025-08-17 16:46:16
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.*;
import java.util.List;
import java.util.Properties;

// 日志文件解析工具
public class LogParser {

    private SqlSessionFactory sqlSessionFactory;

    // 构造函数，初始化MyBatis SqlSessionFactory
    public LogParser(String resource) throws IOException {
        // 加载MyBatis配置文件
        Properties properties = new Properties();
        properties.load(new FileInputStream(resource));
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(properties);
    }

    // 解析日志文件方法
    public List<String> parseLog(String logFilePath) {
        SqlSession session = null;
        try {
            // 获取SqlSession对象
            session = sqlSessionFactory.openSession();
            // 调用mapper，执行解析操作
            LogMapper logMapper = session.getMapper(LogMapper.class);
            return logMapper.parseLog(logFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            // 错误处理
            return null;
        } finally {
            // 关闭SqlSession
            if (session != null) {
                session.close();
            }
        }
    }

    // 测试方法
    public static void main(String[] args) {
        LogParser logParser = null;
        try {
            logParser = new LogParser(