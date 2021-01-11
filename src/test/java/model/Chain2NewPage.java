package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.ProjectUtils;

public class Chain2NewPage extends BasePage {

    @FindBy(id = "f1")
    private WebElement f1;

    @FindBy(id = "f10")
    private WebElement f10;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    public Chain2NewPage(WebDriver driver) {
        super(driver);
    }

    public Chain2NewPage inputF1Value(String f1Value) {
        ProjectUtils.sendKeys(f1, f1Value);
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(f10, "value"));
        return this;
    }

    public Chain2Page clickSaveButton() {
        saveButton.click();
        return new Chain2Page(getDriver());
    }
}