package model;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.time.Duration;

public class MainPage extends BasePage {


    @FindBy(id = "navbarDropdownProfile")
    WebElement userProfileButton;

    @FindBy(xpath = "//a[contains(text(), 'Reset')]")
    WebElement resetButton;

    @FindBy(css = "a[href*=recycle] > i")
    private WebElement recycleBinIcon;

    @FindBy(xpath = "//li[@id = 'pa-menu-item-45']")
    private WebElement menuFields;

    @FindBy(xpath = "//p[contains(text(),'Import values')]")
    private WebElement menuImportValues;

    @FindBy(xpath = "//p[contains(text(),'Home')]")
    private WebElement leftMenu;

    @FindBy(xpath = "//p[contains (text(), 'Export')]")
    private WebElement tubExport;

    @FindBy(css = "#menu-list-parent>ul>li>a[href*='id=62")
    private WebElement menuEventsChain2;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public String getCurrentUser() {
        String profileButtonText = getWait().until(ExpectedConditions.visibilityOf(userProfileButton)).getText();
        String currentUser = profileButtonText.split(" ")[1].toLowerCase();

        return currentUser;
    }

    public MainPage resetUserData() {
        click(userProfileButton);
        click(resetButton);
        return this;
    }

    public RecycleBinPage clickRecycleBin (){
        click(recycleBinIcon);
        return new RecycleBinPage(getDriver());
    }

    public FieldsPage clickMenuFields() {
        click(menuFields);
        return new FieldsPage(getDriver());
    }

    public ImportValuesPage clickMenuImportValues() {
        menuImportValues.click();
        return new ImportValuesPage(getDriver());
    }

    public Chain2Page clickMenuEventsChain2() {
        menuEventsChain2.click();
        return new Chain2Page(getDriver());
    }

    public ExportPage clickMenuExport() {
        getActions().moveToElement(leftMenu).perform();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", tubExport);
        getActions().pause(Duration.ofSeconds(3));
        tubExport.click();

        return new ExportPage(getDriver());
    }
}
