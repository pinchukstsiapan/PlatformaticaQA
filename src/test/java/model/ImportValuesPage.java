package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.ProjectUtils;
import java.util.List;

public final class ImportValuesPage extends BaseTablePage<ImportValuesPage, ImportValuesEditPage> {

    @FindBy(xpath = "//i[contains(text(),'delete_outline')]")
    private WebElement recycleBin;

    public ImportValuesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ImportValuesEditPage createEditPage() {
        return new ImportValuesEditPage(getDriver());
    }

    public RecycleBinPage clickRecycleBin () {
        ProjectUtils.click(getDriver(), recycleBin);
        return new RecycleBinPage(getDriver());
    }
}