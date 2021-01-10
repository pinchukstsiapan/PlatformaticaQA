package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public final class RecycleBinPage extends MainPage {

    @FindBy(className = "card-body")
    private WebElement body;

    @FindBy(css = "table tbody > tr")
    private List<WebElement> rows;

    @FindBy(xpath = "//div[@class='card-body']//table//tbody//tr//td//a//span//b")
    private WebElement firstDeletedImportValue;

    public RecycleBinPage(WebDriver driver) {
        super(driver);
    }

    public int getRowCount() {
        if (Strings.isStringEmpty(body.getText())) {
            return 0;
        } else {
            return rows.size();
        }
    }

    public String getDeletedEntityTitle(int rowNumber) {
        return rows.get(rowNumber).findElement(By.xpath("//span[contains(text(), 'Title:')]/b")).getText();
    }

    public String getDeletedImportValue() {
        return firstDeletedImportValue.getText();
    }
}
