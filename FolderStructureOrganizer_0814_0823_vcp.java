// 代码生成时间: 2025-08-14 08:23:50
package com.example.organizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FolderStructureOrganizer {

    // 主方法，程序入口
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java FolderStructureOrganizer <sourceDirectory>");
            System.exit(1);
        }
        String sourceDirectory = args[0];
        try {
            File sourceFolder = new File(sourceDirectory);
            if (!sourceFolder.exists()) {
                throw new IllegalArgumentException("Source directory does not exist.");
            }
            if (!sourceFolder.isDirectory()) {
                throw new IllegalArgumentException("The provided path is not a directory.");
            }

            organizeFolderStructure(sourceFolder);
        } catch (IOException e) {
            System.err.println("An error occurred while organizing the folder structure: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // 方法：整理文件夹结构
    public static void organizeFolderStructure(File folder) throws IOException {
        // 使用try-with-resources确保资源被关闭
        try (Stream<Path> paths = Files.walk(folder.toPath())) {
            List<File> files = paths
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            // 按类型组织文件
            files.forEach(file -> {
                String extension = getFileExtension(file.getName());
                File destFolder = new File(folder, extension);
                if (!destFolder.exists() && !destFolder.mkdir()) {
                    throw new IOException("Failed to create directory for extension: " + extension);
                }
                moveFileToDirectory(file, destFolder);
            });
        }
    }

    // 方法：获取文件扩展名
    private static String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1) return "others"; // 没有扩展名的文件
        return fileName.substring(lastIndexOfDot + 1);
    }

    // 方法：将文件移动到指定目录
    private static void moveFileToDirectory(File file, File destFolder) throws IOException {
        File destFile = new File(destFolder, file.getName());
        if (!file.renameTo(destFile)) {
            throw new IOException("Failed to move file: " + file.getName());
        }
    }
}
