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

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static void makeScreenShot(WebDriver driver, String fileName) {
        TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
        File screenshotFile = screenshotDriver.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, new File(fileName));
        } catch (IOException e) {

            writeTextFile(fileName, e.toString() );
        }
    }

    public static void writeTextFile(String fileName, String textToWrite) {
        fileName = fileName.substring(0, fileName.lastIndexOf('.')+1) + "txt";

        try {
            Path path = Paths.get(fileName);
            Files.write(path, textToWrite.getBytes());
        } catch (IOException e) {
            System.out.printf("ERROR: unable to save text file %s . \nError message:\n%s%n", fileName, textToWrite);
        }
    }

}
