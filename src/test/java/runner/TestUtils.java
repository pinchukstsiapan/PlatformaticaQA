package runner;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public abstract class TestUtils {

    public static final String tempPath = System.getProperty("java.io.tmpdir");
    public static final String sessionID = UUID.randomUUID().toString();

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static void takeScreenShot(WebDriver driver, String fileName) {
       if (fileName != null) {
           String separator = "";
           if ( !fileName.contains(File.separator) ) {
               if (tempPath.charAt(tempPath.length()-1) != File.separator.charAt(0)) {
                   separator = File.separator;
               }
               fileName = tempPath + separator + fileName;
           }

           TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
           File screenshotFile = screenshotDriver.getScreenshotAs(OutputType.FILE);
           try {
               FileUtils.copyFile(screenshotFile, new File(fileName));
           } catch (IOException e) {
               writeTextFile(fileName, e.toString());
           }
       }
    }

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
