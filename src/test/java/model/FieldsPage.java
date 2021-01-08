package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public final class FieldsPage extends BasePage {

    @FindBy(xpath = "//i[text() = 'create_new_folder']")
    private WebElement buttonNew;

    @FindBy(xpath = "//table[@id='pa-all-entities-table']/tbody")
    private WebElement table;

    public FieldsPage(WebDriver driver) {
        super(driver);
    }

    public FieldsEditPage clickNewButton() {
        buttonNew.click();

        return new FieldsEditPage(getDriver());
    }

    public int getRowCount() {
        return 1;
    }

    public String getTitle(int rowNumber) {
        return table.findElement(By.xpath(String.format("//tr[%d]/td[2]/a/div", rowNumber + 1))).getText();
    }
}
