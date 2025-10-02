// 代码生成时间: 2025-10-03 03:11:25
import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class UnzipUtility {

    /**
     * Unzips a zip file into a specified directory.
     *
     * @param zipFilePath The path to the zip file.
     * @param destDirectory The destination directory where the zip file will be unzipped.
     * @throws IOException If an I/O error occurs.
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zis.getNextEntry();
            // Iterate through each entry in the zip file
            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                File newFile = newFile(destDirectory, fileName);

                // Create directories for sub directories in zip
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    // Create the parent directory structure
                    newFile.getParentFile().mkdirs();

                    // Extract file with buffering
                    try (BufferedOutputStream dest = new BufferedOutputStream(new FileOutputStream(newFile))) {
                        byte[] buffer = new byte[1024];
                        int read;
                        while ((read = zis.read(buffer)) != -1) {
                            dest.write(buffer, 0, read);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException e) {
            throw new IOException("Error while unzipping the file: " + zipFilePath, e);
        }
    }

    /**
     * Creates a file with the specified directory and fileName.
     *
     * @param destinationDir The directory where the file will be created.
     * @param zipEntryName The name of the file to create.
     * @return The created file.
     * @throws IOException If an I/O error occurs.
     */
    private File newFile(String destinationDir, String zipEntryName) throws IOException {
        File destDir = new File(destinationDir);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        String[] zipEntryNameArr = zipEntryName.split("/");
        String newFileName = zipEntryNameArr[zipEntryNameArr.length - 1];
        return new File(destDir, newFileName);
    }

    public static void main(String[] args) {
        UnzipUtility unzipUtility = new UnzipUtility();
        String zipFilePath = "path/to/your/zipfile.zip"; // Replace with the actual zip file path
        String destDirectory = "path/to/destination"; // Replace with the destination directory path

        try {
            unzipUtility.unzip(zipFilePath, destDirectory);
            System.out.println("Unzipping completed successfully.");
        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}