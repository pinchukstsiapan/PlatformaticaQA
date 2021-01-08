package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import runner.BaseTest;

public final class MainPage extends BasePage {

    @FindBy(xpath = "//li[@id = 'pa-menu-item-45']")
    private WebElement menuFields;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public FieldsPage clickMenuFields() {
        menuFields.click();

        return new FieldsPage(getDriver());
    }
}
