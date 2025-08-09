// 代码生成时间: 2025-08-09 16:20:33
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.type.TypeReference;

public class JsonDataTransformer {

    private SqlSessionFactory sqlSessionFactory;

    public JsonDataTransformer() {
        try {
            // 初始化MyBatis SqlSessionFactory
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将JSON字符串转换为Java对象
     *
     * @param jsonStr JSON字符串
     * @param valueType 目标Java对象类型
     * @param <T> Java对象类型参数
     * @return 转换后的Java对象
     */
    public <T> T transformJsonToJavaObject(String jsonStr, TypeReference<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonStr, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将Java对象转换为JSON字符串
     *
     * @param javaObject Java对象
     * @return 转换后的JSON字符串
     */
    public String transformJavaObjectToJson(Object javaObject) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(javaObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取SqlSession
     *
     * @return SqlSession实例
     */
    public SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    public static void main(String[] args) {
        JsonDataTransformer transformer = new JsonDataTransformer();

        // 示例：将JSON字符串转换为Java对象
        String jsonStr = "{"name":"John","age":30}";
        Map<String, Object> javaObject = transformer.transformJsonToJavaObject(jsonStr, new TypeReference<Map<String, Object>>(){});
        System.out.println("Java Object: " + javaObject);

        // 示例：将Java对象转换为JSON字符串
        Map<String, Object> javaObjectMap = Map.of("name", "John", "age", 30);
        String jsonStrResult = transformer.transformJavaObjectToJson(javaObjectMap);
        System.out.println("JSON String: " + jsonStrResult);
    }
}
