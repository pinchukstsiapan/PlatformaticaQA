package model;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import java.time.Duration;

public final class MainPage extends BasePage {

    @FindBy(xpath = "//li[@id = 'pa-menu-item-45']")
    private WebElement menuFields;

    @FindBy(xpath = "//p[contains(text(),'Import values')]")
    private WebElement menuImportValues;

    @FindBy(xpath = "//p[contains(text(),'Home')]")
    private WebElement leftMenu;

    @FindBy(xpath = "//p[contains (text(), 'Export')]")
    private WebElement tubExport;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public FieldsPage clickMenuFields() {
        menuFields.click();

        return new FieldsPage(getDriver());
    }

    public ImportValuesPage clickMenuImportValues() {
        menuImportValues.click();

        return new ImportValuesPage(getDriver());
    }

    public ExportPage clickTubExport() {
        Actions action = new Actions(getDriver());
        action.moveToElement(leftMenu).perform();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", tubExport);
        action.pause(Duration.ofSeconds(3));
        tubExport.click();

        return new ExportPage(getDriver());
    }
}