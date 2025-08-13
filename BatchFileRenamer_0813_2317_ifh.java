// 代码生成时间: 2025-08-13 23:17:23
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 批量文件重命名工具
 * @author YourName
 */
public class BatchFileRenamer {

    private static final String DIRECTORY = "./"; // 指定目录

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要重命名的文件前缀: ");
        String prefix = scanner.nextLine();
        
        try {
            File directory = new File(DIRECTORY);
            File[] files = directory.listFiles();
            if (files == null || files.length == 0) {
                System.out.println("没有找到任何文件。");
                return;
            }
            
            List<String> renamedFiles = renameFiles(prefix, files);
            System.out.println("重命名的文件列表：");
            for (String fileName : renamedFiles) {
                System.out.println(fileName);
            }
        } catch (Exception e) {
            System.err.println("重命名过程中出现错误: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    /**
     * 批量重命名文件
     * @param prefix 文件前缀
     * @param files 要重命名的文件数组
     * @return 返回重命名后的文件列表
     */
    private static List<String> renameFiles(String prefix, File[] files) {
        List<String> renamedFiles = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                try {
                    Path path = file.toPath();
                    String fileName = prefix + "_" + file.getName();
                    Path newPath = Paths.get(path.getParent().toString(), fileName);
                    Files.move(path, newPath);
                    renamedFiles.add(fileName);
                } catch (IOException e) {
                    System.err.println("无法重命名文件: " + file.getName() + ", 错误: " + e.getMessage());
                }
            }
        }
        return renamedFiles;
    }
}
