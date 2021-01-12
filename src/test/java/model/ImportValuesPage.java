package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.ProjectUtils;
import java.util.List;

public final class ImportValuesPage extends BasePage {

    @FindBy(xpath = "//i[contains(text(),'create_new_folder')]")
    private WebElement buttonNew;

    @FindBy(xpath = "//table[@id='pa-all-entities-table']//tbody//tr//td[10]//div//button")
    private List<WebElement> actionsDropDownImportValues;

    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right show']//li[3]")
    private WebElement actionsDeleteButton;

    @FindBy(xpath = "//i[contains(text(),'delete_outline')]")
    private WebElement recycleBin;

    public ImportValuesPage(WebDriver driver) {
        super(driver);
    }

    public ImportValuesEditPage clickNewButton() {
        buttonNew.click();
        return new ImportValuesEditPage(getDriver());
    }

    public ImportValuesPage deleteRecord() {

        actionsDropDownImportValues.get(0).click();
        actionsDeleteButton.click();

        return new ImportValuesPage(getDriver());
    }

    public RecycleBinPage clickRecycleBin () {
        ProjectUtils.click(getDriver(), recycleBin);
        return new RecycleBinPage(getDriver());
    }
}