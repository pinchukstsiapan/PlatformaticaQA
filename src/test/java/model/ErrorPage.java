package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ErrorPage extends BasePage {

    @FindBy(css = "div[id*=error]")
    private WebElement errorMessage;

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    public boolean isErrorMessageDisplayed() {
        return getWait().until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed();
    }

    public String getErrorMessage() {
        return getWait().until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

}
