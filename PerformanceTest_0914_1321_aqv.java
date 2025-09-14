// 代码生成时间: 2025-09-14 13:21:03
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.Reader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;

/**
 * 性能测试脚本，用于测试MyBatis框架的性能
 */
public class PerformanceTest {
    private static AtomicInteger counter = new AtomicInteger(1);
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try {
            // 加载MyBatis配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

            // 执行性能测试
            startTimePerformanceTest(sqlSessionFactory);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 等待所有任务完成
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException ex) {
                executorService.shutdownNow();
            }
        }
    }

    /**
     * 开始执行性能测试
     *
     * @param sqlSessionFactory MyBatis SqlSessionFactory对象
     */
    private static void startTimePerformanceTest(SqlSessionFactory sqlSessionFactory) {
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try (SqlSession session = sqlSessionFactory.openSession()) {
                    // 这里添加具体的MyBatis操作，例如查询操作
                    // 以下代码仅为示例，需要根据实际需求替换
                    // YourMapper mapper = session.getMapper(YourMapper.class);
                    // mapper.selectAll();

                    // 模拟业务操作
                    int currentCount = counter.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
