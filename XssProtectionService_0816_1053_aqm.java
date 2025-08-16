// 代码生成时间: 2025-08-16 10:53:04
import org.apache.commons.text.StringEscapeUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名：XssProtectionService
 * 功能：提供XSS攻击防护的服务
 * 说明：通过清洗输入数据，避免XSS攻击
 */
public class XssProtectionService {

    // SqlSessionFactory实例，用于MyBatis操作
    private SqlSessionFactory sqlSessionFactory;

    public XssProtectionService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 清洗输入数据，防止XSS攻击
     * 
     * @param input 输入数据
     * @return 清洗后的数据
     */
    public String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        return StringEscapeUtils.escapeHtml4(input);
    }

    /**
     * 生成MD5哈希值
     * 
     * @param input 要哈希的输入数据
     * @return MD5哈希值
     * @throws NoSuchAlgorithmException 如果MD5算法不可用
     */
    public String generateMd5Hash(String input) throws NoSuchAlgorithmException {
        if (input == null) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 执行数据库操作
     * 
     * @param operation 数据库操作名称
     * @param params 操作参数
     * @return 操作结果
     */
    public Map<String, Object> executeDatabaseOperation(String operation, Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 根据操作名称执行不同的数据库操作
            if ("insertData".equals(operation)) {
                session.insert("mapperName.insertData", params);
                result.put("status", "success");
            } else if ("updateData".equals(operation)) {
                session.update("mapperName.updateData", params);
                result.put("status", "success");
            } else {
                result.put("status", "failure");
            }
        } catch (Exception e) {
            result.put("status", "failure");
            result.put("error", e.getMessage());
        }
        return result;
    }
}
