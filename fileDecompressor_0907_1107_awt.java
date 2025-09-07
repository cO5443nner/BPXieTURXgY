// 代码生成时间: 2025-09-07 11:07:05
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件解压工具类，使用Apache Commons Compress库来解压缩文件。
 */
public class FileDecompressor {

    private static final String BASE_PATH = "C:\	emp\"; // 设置解压文件的基本路径

    /**
     * 解压tar.gz文件
     *
     * @param sourceFilePath 压缩文件路径
     * @throws IOException 抛出IO异常
     * @throws ArchiveException 抛出压缩文件格式异常
     */
    public void decompressTarGz(String sourceFilePath) throws IOException, ArchiveException {
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            throw new IOException("The source file does not exist.");
        }

        File destinationDir = new File(BASE_PATH + sourceFile.getName() + "_decompressed");
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        try (InputStream fis = new FileInputStream(sourceFile);
             InputStream gzipIS = new GzipCompressorInputStream(new BufferedInputStream(fis));
             InputStream tarIS = new ArchiveStreamFactory()
                     .createArchiveInputStream("tar", gzipIS)) {

            TarArchiveInputStream tarIn = (TarArchiveInputStream) tarIS;
            TarArchiveEntry tarEntry = tarIn.getNextTarEntry();

            while (tarEntry != null) {
                String fileName = tarEntry.getName();
                File outputFile = new File(destinationDir + File.separator + fileName);
                if (tarEntry.isDirectory()) {
                    outputFile.mkdirs();
                } else {
                    outputFile.getParentFile().mkdirs();
                    try (OutputStream out = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = tarIn.read(buffer)) != -1) {
                            out.write(buffer, 0, len);
                        }
                    }
                }
                tarEntry = tarIn.getNextTarEntry();
            }
        } catch (IOException | ArchiveException e) {
            throw new IOException("An error occurred while decompressing the file.", e);
        }
    }

    public static void main(String[] args) {
        FileDecompressor decompressor = new FileDecompressor();
        try {
            decompressor.decompressTarGz("C:\path\	o\your\file.tar.gz"); // 设置压缩文件路径
            System.out.println("Decompression completed successfully.");
        } catch (IOException | ArchiveException e) {
            System.err.println("Failed to decompress the file: " + e.getMessage());
        }
    }
}
