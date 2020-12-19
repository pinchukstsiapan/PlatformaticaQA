package runner;

import com.beust.jcommander.Parameter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
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
            System.out.printf("WARNING: Directory %s already exists!%n", dirFullPath);
        } else {
            try {
                Files.createDirectories(dirFullPath);
            } catch (IOException ioExceptionObj) {
                System.out.println("ERROR: while creating directory " + fullPath + "\n" + ioExceptionObj.getMessage());
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
            System.out.printf("ERROR: unable to save text file %s . \nError message:\n%s%n", fileName, textToWrite);
        }
    }

    /** delete directory with name fullPath */
    public static void deleteScreenshotsDir(String fullPath) {
        Path dirFullPath = Paths.get(fullPath);
        try {
            Files.deleteIfExists(dirFullPath);
            System.out.println("Deleted directory used to save screenshots: " + fullPath);
        } catch (IOException ioExceptionObj) {
            System.out.println("ERROR: while deleting directory " + fullPath + "\n" + ioExceptionObj.getMessage());
        }
    }

    /** upload directory with name fullPath to Google Drive*/
    public static void uploadScreenshotsDir(String fullDirPath) {
        System.out.println("Uploading directory with files to google Drive of the PlatformaticaQA@gmail.com");
        //for each file delete it after upload is done
    }

}
