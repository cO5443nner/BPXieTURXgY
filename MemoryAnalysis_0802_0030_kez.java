// 代码生成时间: 2025-08-02 00:30:07
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * 内存使用情况分析程序，使用MYBATIS框架连接数据库。
 */
public class MemoryAnalysis {

    /**
     * 获取数据库SQL会话工厂。
     * @return SqlSessionFactory
     */
    private SqlSessionFactory getSqlSessionFactory() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
            return new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分析内存使用情况。
     */
    public void analyzeMemoryUsage() {
        // 获取内存信息
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        // 输出内存使用信息
        System.out.println("Heap Memory Used: " + heapMemoryUsage.getUsed());
        System.out.println("Heap Memory Committed: " + heapMemoryUsage.getCommitted());
        System.out.println("Heap Memory Max: " + heapMemoryUsage.getMax());
        System.out.println("Non-Heap Memory Used: " + nonHeapMemoryUsage.getUsed());
        System.out.println("Non-Heap Memory Committed: " + nonHeapMemoryUsage.getCommitted());
        System.out.println("Non-Heap Memory Max: " + nonHeapMemoryUsage.getMax());
    }

    /**
     * 主函数，程序入口。
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        MemoryAnalysis memoryAnalysis = new MemoryAnalysis();

        // 分析内存使用情况
        memoryAnalysis.analyzeMemoryUsage();
    }
}
