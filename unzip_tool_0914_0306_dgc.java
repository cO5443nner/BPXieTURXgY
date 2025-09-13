// 代码生成时间: 2025-09-14 03:06:30
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A utility class for unzipping files using Java.
 */
public class UnzipTool {

    public static void unzip(String zipFilePath, String destDirectory) {
        try {
            // Check if the zip file exists
            File zipFile = new File(zipFilePath);
            if (!zipFile.exists()) {
                throw new IllegalArgumentException("Zip file does not exist: " + zipFilePath);
            }

            // Create destination directory if it doesn't exist
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdir();
            }

            // Create a ZipInputStream to read the zip file
            try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
                ZipEntry entry = zipIn.getNextEntry();
                // Iterate over the entries in the zip file and extract them
                while (entry != null) {
                    String filePath = destDirectory + File.separator + entry.getName();
                    if (!entry.isDirectory()) {
                        // If the entry is a file, extract it
                        extractFile(zipIn, filePath);
                    } else {
                        // If the entry is a directory, create it
                        new File(filePath).mkdirs();
                    }
                    zipIn.closeEntry();
                    entry = zipIn.getNextEntry();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts a file from the zip input stream to the specified location.
     *
     * @param zipIn the ZipInputStream to read from
     * @param filePath the path to extract the file to
     * @throws IOException if an I/O error occurs
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: UnzipTool <zip file path> <destination directory>
);
            return;
        }

        try {
            unzip(args[0], args[1]);
            System.out.println("Unzipped successfully
);
        } catch (Exception e) {
            System.out.println("Error during unzipping: " + e.getMessage());
        }
    }
}