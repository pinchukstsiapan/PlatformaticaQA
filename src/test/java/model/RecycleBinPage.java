package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public final class RecycleBinPage extends BasePage {

    @FindBy(xpath = "//div[@class='card-body']//table//tbody//tr//td//a//span//b")
    private WebElement firstDeletedImportValue;

    public RecycleBinPage(WebDriver driver) {
        super(driver);
    }

    public String getDeletedImportValue() {
        return firstDeletedImportValue.getText();
    }
}