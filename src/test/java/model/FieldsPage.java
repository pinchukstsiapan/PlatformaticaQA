package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public final class FieldsPage extends BasePage {

    @FindBy(xpath = "//i[text() = 'create_new_folder']")
    private WebElement buttonNew;

    @FindBy(className = "card-body")
    private WebElement body;

    @FindBy(xpath = "//table[@id='pa-all-entities-table']/tbody/tr")
    private List<WebElement> trs;

    public FieldsPage(WebDriver driver) {
        super(driver);
    }

    public FieldsEditPage clickNewButton() {
        buttonNew.click();

        return new FieldsEditPage(getDriver());
    }

    public int getRowCount() {
        if (Strings.isStringEmpty(body.getText())) {
            return 0;
        } else {
            return trs.size();
        }
    }

    public String getTitle(int rowNumber) {
        return trs.get(rowNumber).findElement(By.xpath("//td[2]/a/div")).getText();
    }
}
