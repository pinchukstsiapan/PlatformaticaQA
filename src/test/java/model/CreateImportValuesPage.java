package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.ProjectUtils;

public class CreateImportValuesPage extends BasePage{

    @FindBy(xpath = "//input[@id='string']")
    private WebElement stringInImportValueField;

    @FindBy(xpath = "//button[@id='pa-entity-form-save-btn']")
    private WebElement saveButton;

    private String currentValue;

    public CreateImportValuesPage(WebDriver driver) {
        super(driver);
    }

    public String getCurrentValue(){
        return currentValue;
    }

    public ImportValuesPage createRecordInEntityImport(WebDriver driver) {

        currentValue = ProjectUtils.createRandomString();
        stringInImportValueField.sendKeys(currentValue);
        ProjectUtils.click(driver, saveButton);

        return new ImportValuesPage(getDriver());
    }
}