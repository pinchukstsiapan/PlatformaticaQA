package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.ProjectUtils;

import java.util.ArrayList;
import java.util.List;
import static runner.ProjectUtils.click;

public class Chain2Page extends BasePage {

    private static final String URL = "https://ref.eteam.work/index.php?action=action_list&entity_id=62";

    @FindBy(css = "div.card-icon>i")
    private WebElement createNewRecordButton;

    @FindBy(xpath = "//table[@id='pa-all-entities-table']/tbody/tr")
    private List<WebElement> chain2Records;

    @FindBy(css = "td>a>div")
    private List<WebElement> cellsInRecords;

    public Chain2Page(WebDriver driver) {
        super(driver);
    }

    public Chain2Page open() {
        getDriver().get(URL);
        return this;
    }

    public Chain2NewPage clickNewRecordButton() {
        createNewRecordButton.click();
        return new Chain2NewPage(getDriver());
    }

    public int getRowsCount() {
        return chain2Records.size();
    }

    public List<String> getActualValues() {
        List<WebElement> cells = cellsInRecords;
        cells.remove(0);
        cells.remove(cells.size() - 1);

        List<String> actualValues = new ArrayList<>();
        for (WebElement cell : cells) {
            actualValues.add(cell.getText());
        }
        return actualValues;
    }

    public Chain2ViewPage viewRecord() {
        WebDriver driver = getDriver();
        click(getWait(), chain2Records.get(0).findElement(By.tagName("button")));
        ProjectUtils.click(driver, getWait().until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//ul[@x-placement='bottom-end']//a[text()='view']"))));
        return new Chain2ViewPage(driver);
    }

    public Chain2EditPage editRecord() {
        WebDriver driver = getDriver();
        click(getWait(), chain2Records.get(0).findElement(By.tagName("button")));
        ProjectUtils.click(driver, getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//ul[@x-placement='bottom-end']//a[text()='edit']"))));
        return new Chain2EditPage(driver);
    }

    public void deleteRecord() {
        WebDriver driver = getDriver();
        click(getWait(), chain2Records.get(0).findElement(By.tagName("button")));
        ProjectUtils.click(driver, getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//ul[@x-placement='bottom-end']//a[text()='delete']"))));
    }

    public WebElement getTableParentElement() {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".card-body")));
    }
}