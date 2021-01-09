package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public final class MainPage extends BasePage {

    @FindBy(xpath = "//li[@id = 'pa-menu-item-45']")
    private WebElement menuFields;

    @FindBy(xpath = "//p[contains(text(),'Import values')]")
    private WebElement menuImportValues;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public FieldsPage clickMenuFields() {
        menuFields.click();

        return new FieldsPage(getDriver());
    }

    public ImportValuesPage clickMenuImportValues() {
        menuImportValues.click();

        return new ImportValuesPage(getDriver());
    }
}