package runner;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import runner.type.ProfileType;

import java.util.UUID;

public abstract class ProjectUtils {

    @Deprecated
    public static WebDriver loginProcedure(WebDriver driver) {
        return driver;
    }

    @Deprecated
    public static WebDriver loginProcedure(WebDriver driver, ProfileType profileType) {
        return driver;
    }

    @Deprecated
    public static void reset(WebDriver driver) {
        ProfileType.DEFAULT.reset(driver);
    }

    @Deprecated
    public static void login(WebDriver driver, ProfileType profileType) {
        profileType.login(driver);
    }

    @Deprecated
    public static void login(WebDriver driver, String userName, String pas) {

    }

    public static void click(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()", element);
    }

    public static void scroll(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void sendKeys(WebElement element, String keys) {
        for (int i = 0; i< keys.length(); i++) {
            element.sendKeys(keys.substring(i, i + 1));
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {}
        }
    }

    public static void sendKeys(WebElement element, int keys) throws InterruptedException {
        sendKeys(element, String.valueOf(keys));
    }

    public static void sendKeys(WebElement element, double keys) throws InterruptedException {
        sendKeys(element, String.valueOf(keys));
    }

    public static void inputKeys(WebDriver driver, WebElement element, String keys) throws InterruptedException {
        if (!"input".equals(element.getTagName())) {
            throw new RuntimeException(element + " is not input");
        }

        ProjectUtils.sendKeys(element, keys);
        new WebDriverWait(driver, 10).until(ExpectedConditions.attributeContains(element, "value", keys));
    }

    public static void inputKeys(WebDriver driver, WebElement element, int keys) throws InterruptedException {
        inputKeys(driver, element, String.valueOf(keys));
    }

    public static void inputKeys(WebDriver driver, WebElement element, double keys) throws InterruptedException {
        inputKeys(driver, element, String.valueOf(keys));
    }

    public static String createRandomString(){
        return UUID.randomUUID().toString();
    }
}
