package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static runner.ProjectUtils.click;

public abstract class BaseEditPage<T> extends BasePage {

    @FindBy(css = "button[id*='save']")
    private WebElement saveButton;

    @FindBy(css = "button[id*='draft']")
    private WebElement saveDraftButton;

    public BaseEditPage(WebDriver driver) {
        super(driver);
    }

    protected abstract T createPage();

    public T clickSaveButton() {
        click(getWait(), saveButton);
        return createPage();
    }

    public T clickSaveDraftButton() {
        click(getWait(), saveDraftButton);
        return createPage();
    }

    public ErrorPage clickSaveButtonErrorExpected() {
        click(getWait(), saveButton);
        return new ErrorPage(getDriver());
    }

    public ErrorPage clickSaveDraftButtonErrorExpected() {
        click(getWait(), saveButton);
        return new ErrorPage(getDriver());
    }

}
