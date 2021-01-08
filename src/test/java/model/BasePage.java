package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    private final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
