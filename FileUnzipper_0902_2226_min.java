// 代码生成时间: 2025-09-02 22:26:48
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 文件解压工具，使用Java和MyBatis框架实现
 */
public class FileUnzipper {

    private static final String CONFIGURATION_XML = "mybatis-config.xml";
    private static final String MAPPER_XML = "unzip-mapper.xml";
    private static final String MAPPER_INTERFACE = "com.example.UnzipMapper";
    private static final String ZIP_FILE_PATH = "/path/to/your/zipfile.zip";
    private static final String DESTINATION_DIR = "/path/to/destination/directory";

    private SqlSessionFactory sqlSessionFactory;

    /**
     * 初始化MyBatis配置和SqlSessionFactory
     */
    public void initMyBatis() throws IOException {
        // 加载MyBatis配置文件
        InputStream inputStream = Resources.getResourceAsStream(CONFIGURATION_XML);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 解压文件
     * @param zipFilePath zip文件路径
     * @param destinationDir 解压目标目录
     */
    public void unzip(String zipFilePath, String destinationDir) {
        try {
            // 确保目标目录存在
            Files.createDirectories(Paths.get(destinationDir));

            // 解压文件
            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
                ZipEntry zipEntry = zis.getNextEntry();
                while (zipEntry != null) {
                    String fileName = zipEntry.getName();
                    File newFile = newFile(destinationDir, fileName);

                    if (zipEntry.isDirectory()) {
                        if (!newFile.isDirectory() && !newFile.mkdirs()) {
                            throw new IOException("Failed to create directory: " + newFile);
                        }
                    } else {
                        File parent = newFile.getParentFile();
                        if (!parent.exists() && !parent.mkdirs()) {
                            throw new IOException("Failed to create directory: " + parent);
                        }

                        // 复制文件到目标目录
                        try (FileOutputStream fos = new FileOutputStream(newFile)) {
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = zis.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                        }
                    }
                    zipEntry = zis.getNextEntry();
                }
                zis.closeEntry();
            }

            System.out.println("File extracted successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件
     * @param destinationDir 目标目录
     * @param fileName 文件名
     * @return 文件对象
     */
    private File newFile(String destinationDir, String fileName) {
        return new File(destinationDir + File.separator + fileName);
    }

    /**
     * 主方法
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        FileUnzipper unzipper = new FileUnzipper();
        try {
            unzipper.initMyBatis();
            unzipper.unzip(ZIP_FILE_PATH, DESTINATION_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
