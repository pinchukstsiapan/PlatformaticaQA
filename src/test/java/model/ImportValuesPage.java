package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.ProjectUtils;
import java.util.List;

public final class ImportValuesPage extends BasePage {

    @FindBy(xpath = "//i[contains(text(),'create_new_folder')]")
    private WebElement createImportValues;

    @FindBy(xpath = "//table[@id='pa-all-entities-table'] // tbody //tr //td[2] //a //div")
    private List<WebElement> stringsOfImportValues;

    @FindBy(xpath = "//table[@id='pa-all-entities-table'] // tbody //tr //td[10] //div //button")
    private List<WebElement> actionsDropDownImportValues;

    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right show'] //li[3]")
    private WebElement actionsDeleteButton;

    @FindBy(xpath = "//i[contains(text(),'delete_outline')]")
    private WebElement recycleBin;

    public ImportValuesPage(WebDriver driver) { super(driver); }

    public CreateImportValuesPage clickCreateImportValues() {
        createImportValues.click();

        return new CreateImportValuesPage(getDriver());
    }

    public ImportValuesPage findImportValue(String lastValue){

        for(int i = 0; i < stringsOfImportValues.size(); i++){
            if(stringsOfImportValues.get(i).getText().equalsIgnoreCase(lastValue)){
                actionsDropDownImportValues.get(i).click();
                actionsDeleteButton.click();
                break;
            }
        }

        return new ImportValuesPage(getDriver());
    }

    public RecycleBinPage clickRecycleBin (){

        ProjectUtils.click(getDriver(), recycleBin);
        return new RecycleBinPage(getDriver());
    }
}