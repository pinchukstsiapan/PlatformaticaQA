package runner;

import com.beust.jcommander.Parameter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ScreenshotUtils {

    /** Take screenshot and save it to path/file of your choosing -
     * @param driver WebDriver to control browser
     * @param fullPath full path and name of the file to save.
     */
    public static void takeScreenShot(WebDriver driver, String fullPath) {
       if (fullPath != null) {
           TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
           File screenshotFile = screenshotDriver.getScreenshotAs(OutputType.FILE);
           try {
               FileUtils.copyFile(screenshotFile, new File(fullPath));
           } catch (IOException e) {
               writeTextFile(fullPath, e.toString());
           }
       }
    }

    /** create directory with name dirName inside of the temp */
    public static void createScreenshotsDir(String fullPath) {
        Path dirFullPath = Paths.get(fullPath);
        if (Files.exists(dirFullPath)) {
            LoggerUtils.logYellow(String.format("WARNING: Directory %s already exists!", dirFullPath));
        } else {
            try {
                Files.createDirectories(dirFullPath);
            } catch (IOException ioExceptionObj) {
                LoggerUtils.logRed(String.format("ERROR: while creating directory %s\n%s", fullPath, ioExceptionObj.getMessage()));
            }
        }
    }

    /** to suppress error write error into file with the same name and .txt extension */
    private static void writeTextFile(String fileName, String textToWrite) {
        fileName = fileName.substring(0, fileName.lastIndexOf('.')+1) + "txt";

        try {
            Path path = Paths.get(fileName);
            Files.write(path, textToWrite.getBytes());
        } catch (IOException e) {
            LoggerUtils.logRed(String.format("ERROR: unable to save text file %s . \nError message:\n%s", fileName, textToWrite));
        }
    }

    /** delete directory with name fullPath */
    public static void deleteScreenshotsDir(String fullPath) {
        Path dirFullPath = Paths.get(fullPath);
        try {
            Files.deleteIfExists(dirFullPath);
        } catch (IOException ioExceptionObj) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ioExceptionObj.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            LoggerUtils.logRed(String.format("ERROR: while deleting directory %s\n%s", fullPath, sw.toString() ));
        }
    }

    /** upload directory with name fullPath to Google Drive*/
    public static void uploadScreenshotsDir(String fullDirPath) {
        LoggerUtils.logYellow("Uploading directory with files to google Drive of the PlatformaticaQA@gmail.com");
        LoggerUtils.logYellow("for each file delete it after upload is done");
    }

}
