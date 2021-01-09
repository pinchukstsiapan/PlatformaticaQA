package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    private final WebDriver driver;
    private static WebDriverWait webDriverWait;
    private static Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, 10);
        actions = new Actions(driver);

        PageFactory.initElements(driver, this);
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected WebDriverWait getWait() {
        return webDriverWait;
    }

    protected Actions getActions() {
        return actions;
    }

    public static void fill(WebElement element, String text) {
        webDriverWait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
        webDriverWait.until(d -> element.getAttribute("value").equals(text));
    }

}
