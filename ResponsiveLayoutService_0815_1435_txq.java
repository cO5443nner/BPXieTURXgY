// 代码生成时间: 2025-08-15 14:35:52
package com.example.layout;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.TransactionIsolationLevel;
import java.io.Reader;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

public class ResponsiveLayoutService {

    private final SqlSessionFactory sqlSessionFactory;
    private final ReentrantLock lock = new ReentrantLock();

    public ResponsiveLayoutService(String myBatisConfigFile) throws Exception {
        Reader reader = Resources.getResourceAsReader(myBatisConfigFile);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    /**
     * 获取响应式布局设计数据
     * 
     * @return 响应式布局设计数据
     */
    public String getResponsiveLayoutDesign() {
        String layoutDesign = null;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            layoutDesign = session.selectOne("selectResponsiveLayoutDesign");
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layoutDesign;
    }

    /**
     * 更新响应式布局设计数据
     * 
     * @param layoutDesign 响应式布局设计数据
     * @return 更新结果
     */
    public int updateResponsiveLayoutDesign(String layoutDesign) {
        int result = 0;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.setTransactionIsolation(TransactionIsolationLevel.READ_COMMITTED);
            result = session.update("updateResponsiveLayoutDesign", layoutDesign);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 关闭SqlSessionFactory
     */
    public void close() {
        try {
            lock.lock();
            sqlSessionFactory.close();
        } finally {
            lock.unlock();
        }
    }
}
