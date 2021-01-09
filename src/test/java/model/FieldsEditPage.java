package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import runner.ProjectUtils;

public final class FieldsEditPage extends BasePage {

    @FindBy(id = "title")
    private WebElement inputTitle;

    @FindBy(id = "comments")
    private WebElement inputComments;

    @FindBy(id = "int")
    private WebElement inputInt;

    @FindBy(id = "decimal")
    private WebElement inputDecimal;

    @FindBy(id = "date")
    private WebElement inputDate;

    @FindBy(id = "datetime")
    private WebElement inputDateTime;

    @FindBy(css = "div[class$=inner-inner]")
    private WebElement selectedUser;

    @FindBy(css = "select#user")
    private WebElement selectUser;

    @FindBy(css = "button[id*='save']")
    private WebElement saveButton;

    @FindBy(css = "button[id*='draft']")
    private WebElement saveDraftButton;

    public FieldsEditPage(WebDriver driver) {
        super(driver);
    }

    public FieldsEditPage fillTitle(String title) {
        fill(inputTitle, title);

        return this;
    }

    public FieldsEditPage fillComments(String comments) {
        fill(inputComments, comments);

        return this;
    }

    public FieldsEditPage fillInt(String int_) {
        fill(inputInt, int_);

        return this;
    }

    public FieldsEditPage fillDecimal(String decimal_) {
        fill(inputDecimal, decimal_);

        return this;
    }

    public FieldsEditPage fillDate(String date) {
        fill(inputDate, date);

        return this;
    }

    public FieldsEditPage fillDateTime(String dateTime) {
        fill(inputDateTime, dateTime);

        return this;
    }

    public FieldsEditPage sendKeys(String title, String comments, String int_) {
        ProjectUtils.sendKeys(inputTitle, title);
        ProjectUtils.sendKeys(inputComments, comments);
        ProjectUtils.sendKeys(inputInt, int_);

        return this;
    }

    public FieldsEditPage selectUser(String user) {
        WebElement userText = getWait().until(ExpectedConditions.visibilityOf(selectedUser));
        getActions().moveToElement(userText).perform();
        new Select(selectUser).selectByVisibleText(user);

        return this;
    }

    public FieldsPage clickSaveButton() {
        saveButton.click();

        return new FieldsPage(getDriver());
    }

    public FieldsPage clickSaveDraftButton() {
        saveDraftButton.click();

        return new FieldsPage(getDriver());
    }

}
