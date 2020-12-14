package runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public abstract class ScreenshotUtils {

    public static final String sessionID = UUID.randomUUID().toString();

    public static final String screenshotDirectoryName;

    private static final String tempPath;

    static {
        tempPath = System.getProperty("java.io.tmpdir");
        screenshotDirectoryName = tempPath + (new SimpleDateFormat("YYYY-MM-dd-kk-mm-").format(new Date())) + sessionID;
    }


    /** Take screenshot and save it to path/file of your choosing -
     * @param driver WebDriver to control browser
     * @param fileName name of the file to save. If path is missing then save to temporary directory.
     */
    public static void takeScreenShot(WebDriver driver, String fileName) {
       if (fileName != null) {
           String fullPath = convertFileToFullPath(fileName);
           TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
           File screenshotFile = screenshotDriver.getScreenshotAs(OutputType.FILE);
           try {
               FileUtils.copyFile(screenshotFile, new File(fullPath));
           } catch (IOException e) {
               writeTextFile(fullPath, e.toString());
           }
       }
    }

    /** Convert file name in to full temp path */
    private static String convertFileToFullPath(String fileName) {
        String fullPath = null;
        if (fileName != null) {
            fullPath = screenshotDirectoryName + File.separator + fileName;
        }
        return fullPath;
    }

    /** create directory with name dirName inside of the temp */
    public static void createScreenshotsDir() {
        Path dirFullPath = Paths.get(screenshotDirectoryName);
        if(Files.exists(dirFullPath)) {
            System.out.println(String.format("WARNING: Directory %s already exists!", dirFullPath));
        } else {
            try {
                Files.createDirectories(dirFullPath);
            } catch (IOException ioExceptionObj) {
                System.out.println("ERROR: while creating directory " + dirFullPath + ioExceptionObj.getMessage());
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

}
