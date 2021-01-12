package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import static runner.ProjectUtils.fill;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class FieldsEditPage extends BaseEditPage<FieldsPage> {

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

    @FindBy(css = "select#user > option")
    private List<WebElement> selectUserAllUsers;

    @FindBy(css = "div[class$=inner-inner]")
    private WebElement selectedUser;

    @FindBy(css = "select#user")
    private WebElement selectUser;

    public FieldsEditPage(WebDriver driver) {
        super(driver);
    }

    public FieldsEditPage fillTitle(String title) {
        fill(getWait(), inputTitle, title);
        return this;
    }

    public FieldsEditPage fillComments(String comments) {
        fill(getWait(), inputComments, comments);
        return this;
    }

    public FieldsEditPage fillInt(String int_) {
        fill(getWait(), inputInt, int_);
        return this;
    }

    public FieldsEditPage fillDecimal(String decimal) {
        fill(getWait(), inputDecimal, decimal);
        return this;
    }

    public FieldsEditPage fillDate(String date) {
        fill(getWait(), inputDate, date);
        return this;
    }

    public FieldsEditPage fillDateTime(String dateTime) {
        fill(getWait(), inputDateTime, dateTime);
        return this;
    }

    public FieldsEditPage sendKeys(String title, String comments, String int_, String decimal, String date, String datetime) {
        fill(getWait(), inputTitle, title);
        fill(getWait(), inputComments, comments);
        fill(getWait(), inputInt, int_);
        fill(getWait(), inputDecimal, decimal);
        fill(getWait(), inputDate, date);
        fill(getWait(), inputDateTime, datetime);

        return this;
    }

    public String getRandomUser() {
        List<WebElement> userList = selectUserAllUsers;
        String randomUser = userList.get(ThreadLocalRandom.current().nextInt(1, userList.size())).getText();

        return randomUser;
    }

    public FieldsEditPage selectUser(String user) {
        WebElement userText = getWait().until(ExpectedConditions.visibilityOf(selectedUser));
        getActions().moveToElement(userText).perform();
        new Select(selectUser).selectByVisibleText(user);

        return this;
    }

    @Override
    protected FieldsPage createPage() {
        return new FieldsPage(getDriver());
    }

}
