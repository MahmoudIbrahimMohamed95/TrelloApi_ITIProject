package fileUtils;

import org.apache.commons.io.FileUtils;
import readers.Log;
import readers.PropertyReader;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyFile;

public class FileAndTerminalManager {

    public static void createDirectory(File file) {
        try {
            if (!file.exists())
            {
                file.mkdirs();
                Log.info("Directory created: " ,file.getAbsolutePath());
            }
        }
        catch (Exception e) {
            Log.error("Failed to create directory: " + file.getAbsolutePath(), e.getMessage());
        }
    }
    public static void renameFile(String oldName, String newName) {
        try {
            var targetFile = new File(oldName);
            String targetDirectory = targetFile.getParentFile().getAbsolutePath();
            File newFile = new File(targetDirectory + File.separator + newName);
            if (!targetFile.getPath().equals(newFile.getPath())) {
                copyFile(targetFile, newFile);
                FileUtils.deleteQuietly(targetFile);
                Log.info("Target File Path: \"" + oldName + "\", file was renamed to \"" + newName + "\".");
            } else {
                Log.info(("Target File Path: \"" + oldName + "\", already has the desired name \"" + newName + "\"."));
            }
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }
    public static void forceDelete(File file) {
        try {
            FileUtils.forceDeleteOnExit(file);
            Log.info("File deleted: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.error("Failed to delete file: " + file.getAbsolutePath(), e.getMessage());
        }
    }
    public static void cleanDirectory(File file)
    {
        try {
            FileUtils.deleteQuietly(file); //in use > skip
        }
        catch (Exception e) {
            Log.error("Failed to clean directory: " + file.getAbsolutePath(), e.getMessage());
        }
    }
    public static void executeTerminalCommand(String... commandParts) {
        try {
            Process process = Runtime.getRuntime().exec(commandParts); //allure generate -o reports --single-file --clean
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                Log.error("Command failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            Log.error("Failed to execute terminal command: " + String.join(" ", commandParts), e.getMessage());
        }
    }
    //check if the file exists
    public static boolean isFileExists( String fileName) {
        String filePath = PropertyReader.getProperty("user.dir") + "/src/test/resources/downloads/" ;
        File file = new File(filePath+ fileName);
        return file.exists();
    }
}
