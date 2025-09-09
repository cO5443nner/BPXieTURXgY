// 代码生成时间: 2025-09-10 07:00:52
import org.apache.ibatis.annotations.Param;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Password Encryption Decryption Tool using Java and MyBatis framework.
 * This tool provides methods to encrypt and decrypt passwords.
 */
public class PasswordEncryptionDecryptionTool {

    // AES Encryption Algorithm
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    private SecretKeySpec secretKeySpec;

    public PasswordEncryptionDecryptionTool(@Param("key") String key) {
        // Initialize the SecretKeySpec with the provided key
        this.secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
    }

    /**
     * Encrypt the given password using AES algorithm.
     *
     * @param password the password to encrypt
     * @return encrypted password in base64 encoded string
     * @throws Exception if encryption fails
     */
    public String encrypt(String password) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypt the given encrypted password.
     *
     * @param encryptedPassword the encrypted password to decrypt
     * @return decrypted password
     * @throws Exception if decryption fails
     */
    public String decrypt(String encryptedPassword) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    /**
     * Generate a random AES key and return it in base64 encoded string.
     *
     * @return base64 encoded AES key
     * @throws Exception if key generation fails
     */
    public static String generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128, SecureRandom.getInstanceStrong());
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
