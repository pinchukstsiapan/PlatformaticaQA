package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.ProjectUtils;

public final class ExportEditPage extends BasePage {

    @FindBy(xpath = "//*[@name='entity_form_data[int]']")
    private WebElement fieldInt;

    @FindBy(xpath = "//button[@class='btn btn-warning']")
    private WebElement saveButton;

    @FindBy (xpath = "//div[@id='pa-error']")
    private WebElement getError;

    public ExportEditPage(WebDriver driver) {
        super(driver);
    }

    public ExportEditPage sendKeys(String comments) {
        ProjectUtils.sendKeys(fieldInt, comments);
        return this;
    }
    public ExportPage clickSaveButton() {
        ProjectUtils.click(getDriver(), saveButton);
        return new ExportPage(getDriver());
    }
}
