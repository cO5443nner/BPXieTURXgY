// 代码生成时间: 2025-09-02 04:57:28
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 哈希值计算工具类
 *
 * @author Your Name
 * @version 1.0
 */
public class HashCalculator {

    /**
     * 计算给定字符串的哈希值（使用SHA-256算法）
     *
     * @param input 待计算哈希值的字符串
     * @return 哈希值的Base64编码字符串
     * @throws NoSuchAlgorithmException 如果SHA-256算法不可用
     */
    public String calculateHash(String input) throws NoSuchAlgorithmException {
        // 使用SHA-256算法创建MessageDigest实例
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        // 对输入字符串进行哈希计算
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        // 将哈希值转换为Base64编码字符串
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * 主方法，用于测试哈希值计算功能
     *
     * @param args 命令行参数（在此未使用）
     */
    public static void main(String[] args) {
        try {
            HashCalculator calculator = new HashCalculator();
            String input = "Hello, MyBatis!";
            String hash = calculator.calculateHash(input);
            System.out.println("Input: " + input);
            System.out.println("Hash: " + hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: SHA-256 algorithm is not available.");
            e.printStackTrace();
        }
    }
}