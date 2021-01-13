package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Chain2ErrorPage extends BasePage {

    @FindBy(id = "pa-error")
    private WebElement actualError;

    public Chain2ErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getActualError() {
        return getWait().until(ExpectedConditions.visibilityOf(actualError)).getText();
    }
}
