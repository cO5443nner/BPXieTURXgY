// 代码生成时间: 2025-08-29 02:30:15
public class FileBackupSyncTool {

    /**
     * Backup a file from source to destination.
     *
     * @param sourcePath The path of the source file.
     * @param destinationPath The path where the file will be backed up.
     * @return true if the backup was successful, false otherwise.
     */
    public boolean backupFile(String sourcePath, String destinationPath) {
        try {
            File sourceFile = new File(sourcePath);
            File destinationFile = new File(destinationPath);

            if (!sourceFile.exists() || !sourceFile.isFile()) {
                throw new FileNotFoundException("Source file does not exist.");
            }

            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Synchronize files between two directories.
     * This method will ensure that all files from the source directory are present in the destination directory.
     *
     * @param sourceDir The path of the source directory.
     * @param destinationDir The path of the destination directory.
     * @return true if the synchronization was successful, false otherwise.
     */
    public boolean syncFiles(String sourceDir, String destinationDir) {
        try {
            File sourceDirectory = new File(sourceDir);
            File destinationDirectory = new File(destinationDir);

            if (!sourceDirectory.isDirectory() || !destinationDirectory.isDirectory()) {
                throw new IllegalArgumentException("Both source and destination must be directories.");
            }

            Files.walk(sourceDirectory.toPath())
                    .forEach(sourcePath -> {
                        try {
                            Path destinationPath = destinationDirectory.toPath().resolve(
                                    sourcePath.toString().substring(sourceDirectory.getAbsolutePath().length() + 1));
                            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        FileBackupSyncTool tool = new FileBackupSyncTool();
        // Example usage:
        // Backup a file
        boolean backupResult = tool.backupFile("path/to/source/file.txt", "path/to/destination/file.txt");
        if (backupResult) {
            System.out.println("Backup was successful.");
        } else {
            System.out.println("Backup failed.");
        }

        // Synchronize files between directories
        boolean syncResult = tool.syncFiles("path/to/source/directory", "path/to/destination/directory");
        if (syncResult) {
            System.out.println("Synchronization was successful.");
        } else {
            System.out.println("Synchronization failed.");
        }
    }
}
