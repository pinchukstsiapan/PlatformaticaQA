package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public FieldsPage clickMenuFields() {
        click(menuFields);

        return new FieldsPage(getDriver());
    }

    public ImportValuesPage clickMenuImportValues() {
        menuImportValues.click();

        return new ImportValuesPage(getDriver());
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

}
