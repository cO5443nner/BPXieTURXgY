// 代码生成时间: 2025-09-10 20:02:58
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import java.io.IOException;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncryptionDecryptionTool {

    private SqlSessionFactory sqlSessionFactory;

    public PasswordEncryptionDecryptionTool() {
        try {
            // 加载MyBatis配置文件
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 加密密码
    public String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            // 将加密后的字节数组转换成16进制的字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 解密密码
    public String decryptPassword(String encryptedPassword) {
        // 由于SHA-256是不可逆的加密算法，这里仅模拟解密过程
        // 实际应用中，应使用数据库存储的盐值进行加盐解密
        try {
            // 将16进制字符串转换回字节数组
            byte[] bytes = new byte[encryptedPassword.length() / 2];
            for (int i = 0; i < bytes.length; i++) {
                int index = i * 2;
                bytes[i] = (byte) Integer.parseInt(encryptedPassword.substring(index, index + 2), 16);
            }
            // 将字节数组解码为字符串
            String password = new String(bytes);
            return password;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取SqlSessionFactory
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public static void main(String[] args) {
        PasswordEncryptionDecryptionTool tool = new PasswordEncryptionDecryptionTool();
        String password = "myPassword123";
        String encryptedPassword = tool.encryptPassword(password);
        System.out.println("Encrypted Password: " + encryptedPassword);
        String decryptedPassword = tool.decryptPassword(encryptedPassword);
        System.out.println("Decrypted Password: " + decryptedPassword);
    }
}
