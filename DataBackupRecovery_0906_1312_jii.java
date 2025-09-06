// 代码生成时间: 2025-09-06 13:12:27
// DataBackupRecovery.java
// 这是一个使用JAVA和MYBATIS框架实现的数据备份恢复程序

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DataBackupRecovery {

    private static final String MYBATIS_CONFIG = "mybatis-config.xml";
    private static final String BACKUP_FILE_PATH = "backup/backup.sql";
    private static final String RECOVERY_FILE_PATH = "backup/recovery.sql";
    private static final String BACKUP_DIR = "backup/";
    private static final String DATA_SOURCE_PROPERTIES = "db.properties";
    private static final String BACKUP_SQL = "backup";
    private static final String RECOVERY_SQL = "recovery";

    // 备份数据库
    public void backupDatabase() {
        try (SqlSession session = getSqlSessionFactory().openSession()) {
            session.selectOne(BACKUP_SQL);
            session.commit();
            copyFile(BACKUP_FILE_PATH, BACKUP_DIR + "backup_" + System.currentTimeMillis() + ".sql");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 恢复数据库
    public void recoverDatabase() {
        try (SqlSession session = getSqlSessionFactory().openSession()) {
            session.selectOne(RECOVERY_SQL);
            session.commit();
            copyFile(RECOVERY_DIR + "recovery_" + System.currentTimeMillis() + ".sql", BACKUP_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取SqlSessionFactory
    private SqlSessionFactory getSqlSessionFactory() {
        try {
            return new SqlSessionFactoryBuilder().build(getResourceAsStream(MYBATIS_CONFIG));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取资源文件的输入流
    private InputStream getResourceAsStream(String resource) {
        return getClass().getClassLoader().getResourceAsStream(resource);
    }

    // 文件复制
    private void copyFile(String sourcePath, String destPath) throws IOException {
        Files.copy(Paths.get(sourcePath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void main(String[] args) {
        DataBackupRecovery backupRecovery = new DataBackupRecovery();
        backupRecovery.backupDatabase();
        backupRecovery.recoverDatabase();
    }
}