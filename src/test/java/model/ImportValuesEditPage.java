package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.ProjectUtils;

public class ImportValuesEditPage extends BasePage {

    @FindBy(xpath = "//input[@id='string']")
    private WebElement inputString;

    @FindBy(xpath = "//button[@id='pa-entity-form-save-btn']")
    private WebElement buttonSave;

    public ImportValuesEditPage(WebDriver driver) {
        super(driver);
    }

    public ImportValuesEditPage sendKeys(String string) {
        ProjectUtils.sendKeys(inputString, string);

        return this;
    }

    public ImportValuesPage clickSaveButton() {
        ProjectUtils.click(getDriver(), buttonSave);

        return new ImportValuesPage(getDriver());
    }
}