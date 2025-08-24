// 代码生成时间: 2025-08-24 08:34:07
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.session.TransactionIsolationLevel;

/**
 * 文件备份和同步工具
 */
public class FileBackupAndSyncTool {

    // 配置数据库连接信息
    private static final String CONFIGURATION_FILE = "mybatis-config.xml";
    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSessionManager sqlSessionManager;

    // 构造函数
    public FileBackupAndSyncTool() throws IOException {
        // 初始化MyBatis工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(CONFIGURATION_FILE));
        sqlSessionManager = new SqlSessionManager(sqlSessionFactory);
    }

    /**
     * 备份文件
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件路径
     * @throws IOException 文件操作异常
     */
    public void backupFile(String sourcePath, String targetPath) throws IOException {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);

        if (!sourceFile.exists()) {
            throw new IOException("源文件不存在");
        }

        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new IOException("文件备份失败", e);
        }
    }

    /**
     * 同步文件到目标路径
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件路径
     * @throws IOException 文件操作异常
     */
    public void syncFile(String sourcePath, String targetPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);

        if (!Files.exists(source)) {
            throw new IOException("源文件不存在");
        }

        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    // MyBatis会话管理
    public static SqlSession getSqlSession() {
        return sqlSessionManager.newSqlSession();
    }

    public static void closeSqlSessionFactory() {
        if (sqlSessionFactory != null) {
            sqlSessionFactory = null;
        }
    }

    public static void main(String[] args) {
        try {
            FileBackupAndSyncTool tool = new FileBackupAndSyncTool();

            // 备份文件
            String sourcePath = "D:/source.txt";
            String backupPath = "D:/backup/backup.txt";
            tool.backupFile(sourcePath, backupPath);

            // 同步文件
            String targetPath = "D:/destination.txt";
            tool.syncFile(sourcePath, targetPath);

            System.out.println("文件备份和同步成功");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件备份和同步失败");
        } finally {
            closeSqlSessionFactory();
        }
    }
}
