// 代码生成时间: 2025-08-21 01:55:43
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用MYBATIS框架实现的密码加密解密工具
 */
public class PasswordEncryptionDecryptionTool {

    // 加密解密使用的密钥
    private static final String SECRET_KEY = "mysecretkey";

    /**
     * 加密密码
     * @param password 待加密的密码
     * @return 加密后的密码
     */
    public String encryptPassword(String password) {
        try {
            // 使用AES加密算法进行加密
            return org.apache.commons.codec.binary.Base64.encodeBase64String(
                javax.crypto.Cipher.getInstance("AES/ECB/PKCS5Padding").init(javax.crypto.Cipher.ENCRYPT_MODE,
                new javax.crypto.spec.SecretKeySpec(SECRET_KEY.getBytes(), "AES")).doFinal(password.getBytes()));
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密密码
     * @param encryptedPassword 待解密的密码
     * @return 解密后的密码
     */
    public String decryptPassword(String encryptedPassword) {
        try {
            // 使用AES加密算法进行解密
            return new String(javax.crypto.Cipher.getInstance("AES/ECB/PKCS5Padding").init(javax.crypto.Cipher.DECRYPT_MODE,
                new javax.crypto.spec.SecretKeySpec(SECRET_KEY.getBytes(), "AES")).doFinal(
                org.apache.commons.codec.binary.Base64.decodeBase64(encryptedPassword)));
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取MyBatis SqlSessionFactory
     * @param resourcePath MyBatis配置文件路径
     * @return SqlSessionFactory实例
     * @throws Exception
     */
    public SqlSessionFactory getSqlSessionFactory(String resourcePath) throws Exception {
        Reader reader = Resources.getResourceAsReader(resourcePath);
        return new SqlSessionFactoryBuilder().build(reader);
    }

    // 测试密码加密解密功能
    public static void main(String[] args) {
        PasswordEncryptionDecryptionTool tool = new PasswordEncryptionDecryptionTool();
        String originalPassword = "mypassword123";
        System.out.println("Original Password: " + originalPassword);
        String encryptedPassword = tool.encryptPassword(originalPassword);
        System.out.println("Encrypted Password: " + encryptedPassword);
        String decryptedPassword = tool.decryptPassword(encryptedPassword);
        System.out.println("Decrypted Password: " + decryptedPassword);
    }
}