package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public final class ExportPage extends BasePage {

    @FindBy(xpath = "//*[contains (text(), 'create_new_folder')]")
    private WebElement newExportButton;
    @FindBy(xpath = "//div[@id='pa-error']")
    private WebElement getError;

    public ExportPage(WebDriver driver) {
        super(driver);
    }

    public ExportEditPage clickNewExportButton() {
        newExportButton.click();
        return new ExportEditPage(getDriver());
    }
    public ExportEditPage getErrorMassage(){
        Assert.assertEquals("Error saving entity", getError.getText());
        return new ExportEditPage(getDriver());
    }

}
