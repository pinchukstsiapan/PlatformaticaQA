package model;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    private final WebDriver driver;
    private static WebDriverWait webDriverWait;
    JavascriptExecutor jsExecutor;
    private static Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, 10);
        jsExecutor = (JavascriptExecutor) getDriver();
        actions = new Actions(driver);

        PageFactory.initElements(driver, this);
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected WebDriverWait getWait() {
        return webDriverWait;
    }

    protected JavascriptExecutor getExecutor() {
        return jsExecutor;
    }

    protected Actions getActions() {
        return actions;
    }

    public static void click(WebElement element) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public static void fill(WebElement element, String text) {
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        if (element.toString().toLowerCase().contains("date")) {
            click(element);
        }
        if (!element.getAttribute("value").isEmpty()) {
            element.clear();
        }
        element.sendKeys(text);
        webDriverWait.until(d -> element.getAttribute("value").equals(text));
    }

}
