// 代码生成时间: 2025-10-08 18:17:47
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.Reader;
import java.util.Properties;
import org.apache.ibatis.io.Resources;

/**
 * TransactionExecutorEngine is a class that handles the execution of transactions using MyBatis framework.
 * It encapsulates the setup of the SqlSessionFactory and provides a method to execute transactions.
 */
public class TransactionExecutorEngine {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Constructs a TransactionExecutorEngine with a SqlSessionFactory.
     * @param myBatisConfigPath The path to the MyBatis configuration file.
     */
    public TransactionExecutorEngine(String myBatisConfigPath) {
        try {
            // Read the MyBatis configuration file
            Reader reader = Resources.getResourceAsReader(myBatisConfigPath);
            // Build the SqlSessionFactory
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            // Handle exceptions during SqlSessionFactory construction
            e.printStackTrace();
        }
    }

    /**
     * Executes a transaction using the provided transaction handler.
     * @param transactionHandler The handler that executes the transaction logic.
     * @return The result of the transaction execution.
     * @throws Exception If any error occurs during transaction execution.
     */
    public Object executeTransaction(TransactionHandler transactionHandler) throws Exception {
        SqlSession sqlSession = null;
        try {
            // Start a new SqlSession
            sqlSession = sqlSessionFactory.openSession();
            // Begin the transaction
            sqlSession.beginTransaction();
            
            // Execute the transaction logic using the handler
            Object result = transactionHandler.execute(sqlSession);
            
            // Commit the transaction if no exceptions occur
            sqlSession.commit();
            // Return the result of the transaction
            return result;
        } catch (Exception e) {
            if (sqlSession != null) {
                // Rollback the transaction in case of exception
                sqlSession.rollback();
            }
            // Rethrow the exception to be handled by the caller
            throw e;
        } finally {
            if (sqlSession != null) {
                // Close the SqlSession
                sqlSession.close();
            }
        }
    }

    /**
     * A functional interface for transaction handlers.
     */
    @FunctionalInterface
    public interface TransactionHandler {
        Object execute(SqlSession sqlSession) throws Exception;
    }
}
