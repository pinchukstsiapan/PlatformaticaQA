package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.ProjectUtils;

public final class FieldsEditPage extends BasePage {

    @FindBy(xpath = "//input[contains(@name, 'title')]")
    private WebElement inputTitle;

    @FindBy(xpath = "//textarea[@id = 'comments']")
    private WebElement inputComments;

    @FindBy(xpath = "//input[contains(@name, 'int')]")
    private WebElement inputInt;

    @FindBy(xpath = "//button[text() = 'Save']")
    private WebElement buttonSave;

    public FieldsEditPage(WebDriver driver) {
        super(driver);
    }

    public FieldsEditPage sendKeys(String title, String comments, String int_) {
        ProjectUtils.sendKeys(inputTitle, title);
        ProjectUtils.sendKeys(inputComments, comments);
        ProjectUtils.sendKeys(inputInt, int_);

        return this;
    }

    public FieldsPage clickSaveButton() {
        ProjectUtils.click(getDriver(), buttonSave);

        return new FieldsPage(getDriver());
    }
}
