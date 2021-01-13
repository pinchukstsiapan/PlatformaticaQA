package model;


import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseTablePage<S, E> extends BasePage {

    private static final String ROW_MENU_ = "//button[@data-toggle='dropdown']/../ul/li";

    private static final By ROW_MENU_VIEW = By.xpath(ROW_MENU_ + "[1]");
    private static final By ROW_MENU_EDIT = By.xpath(ROW_MENU_ + "[2]");
    private static final By ROW_MENU_DELETE = By.xpath(ROW_MENU_ + "[3]");

    @FindBy(xpath = "//i[text() = 'create_new_folder']")
    private WebElement buttonNew;

    @FindBy(className = "card-body")
    private WebElement body;

    @FindBy(xpath = "//table[@id='pa-all-entities-table']/tbody/tr")
    private List<WebElement> trs;

    public BaseTablePage(WebDriver driver) {
        super(driver);
    }

    protected abstract E createEditPage();

    public E clickNewFolder() {
        buttonNew.click();
        return createEditPage();
    }

    public int getRowCount() {
        if (Strings.isStringEmpty(body.getText())) {
            return 0;
        } else {
            return trs.size();
        }
    }

    public List<String> getRow(int rowNumber) {
        return trs.get(rowNumber).findElements(By.xpath("//td/a/div")).stream()
                .map(WebElement::getText).collect(Collectors.toList());
    }

    private void clickRowMenu(int rowNumber) {
        trs.get(rowNumber).findElement(By.xpath("//td//div//button")).click();
    }

    public BaseViewPage viewRow(int rowNumber) {
        clickRowMenu(rowNumber);
        getDriver().findElement(ROW_MENU_VIEW).click();

        return new BaseViewPage(getDriver());
    }

    public BaseViewPage viewRow() {
        return viewRow(trs.size() - 1);
    }

    public E editRow(int rowNumber) {
        clickRowMenu(rowNumber);
        getDriver().findElement(ROW_MENU_EDIT).click();

        return createEditPage();
    }

    public E editRow() {
        return editRow(trs.size() - 1);
    }

    public S deleteRow(int rowNumber) {
        clickRowMenu(rowNumber);
        getDriver().findElement(ROW_MENU_DELETE).click();

        return (S)this;
    }

    public S deleteRow() {
        return deleteRow(trs.size() - 1);
    }

}
