package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.ProjectUtils;

import java.util.ArrayList;
import java.util.List;

public class Chain2EditPage extends BasePage {

    @FindBy(css = "input[id*='f']")
    private List<WebElement> fValuesEdit;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    public Chain2EditPage(WebDriver driver) {
        super(driver);
    }

    public Chain2Page clickSaveButton() {
        saveButton.click();
        return new Chain2Page(getDriver());
    }

    public Chain2ErrorPage clickSaveButtonReturnError() {
        saveButton.click();
        return new Chain2ErrorPage(getDriver());
    }

    public List<String> getActualValues() {
        List<WebElement> fValues = fValuesEdit;

        final List<String> actualValues = new ArrayList<>();
        for (WebElement fValue : fValues) {
            actualValues.add(fValue.getAttribute("value"));
        }
        return actualValues;
    }

    public Chain2EditPage editF1Value(String f1Value, List<String> expectedValues) {
        String f10ExpectedValue = expectedValues.get(expectedValues.size() - 1);
        WebElement f1 = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("f1")));
        WebElement f10 = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("f10")));

        f1.clear();
        f1.sendKeys(f1Value);
        getWait().until(ExpectedConditions.attributeToBe(f1, "value", f1Value));
        getWait().until(ExpectedConditions.attributeToBe(f10, "value", f10ExpectedValue));
        return this;
    }

    public Chain2EditPage editValues(List<String> newValues) {
        List<WebElement> f1ToF10 = getWait()
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("input[id*='f']")));

        for (int i = 0; i < newValues.size(); i ++) {
            WebElement fi = f1ToF10.get(i);
            fi.clear();
            ProjectUtils.sendKeys(fi, newValues.get(i));
            getWait().until(ExpectedConditions.attributeToBe(fi, "value", newValues.get(i)));
        }
        return this;
    }
}
