package runner;

import org.openqa.selenium.*;
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

    public static void click(WebDriverWait wait, WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void jsClick(JavascriptExecutor executor, WebElement element) {
        executor.executeScript("arguments[0].click();", element);
    }

    public static void click(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()", element);
    }

    public static void scroll(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void fill(WebDriverWait wait, WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        if (element.toString().toLowerCase().contains("date")) {
            click(wait, element);
        }
        if (!element.getAttribute("value").isEmpty()) {
            element.clear();
        }
        element.sendKeys(text);
        wait.until(d -> element.getAttribute("value").equals(text));
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

    public static String createUUID() {
        return UUID.randomUUID().toString();
    }
}
