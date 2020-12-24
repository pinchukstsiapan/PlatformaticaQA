import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;
import org.openqa.selenium.remote.internal.WebElementToJsonConverter;
import org.testng.annotations.Ignore;
import runner.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

@Run(run = RunType.Multiple)
public class EntityChevronTest extends BaseTest {

    private static final String TITLE = UUID.randomUUID().toString();
    private static final String INT_NUMBER = "123";
    private static final String DOUBLE_NUMBER = "4.56";
    private static final String STATUS_NEW = "Fulfillment";
    private static final String STATUS_EDITED = "Pending";

    /* Need refactoring */
    @Ignore
    @Test
    public void findChevron() throws InterruptedException {

        WebDriver driver = getDriver();

        WebElement clickChevron = driver.findElement(By.xpath("//p[contains(text(),'Chevron')]"));
        ProjectUtils.click(driver, clickChevron);

        WebElement clickCreateRecord = driver.findElement(By.xpath("//div[@class = 'card-icon']//i"));
        ProjectUtils.click(driver, clickCreateRecord);

        WebElement addString = driver.findElement(By.xpath("//div[@class = 'filter-option-inner-inner']"));
        ProjectUtils.click(driver, addString);

        WebElement clickString = driver.findElement(By.xpath("//div[contains(text(),'Pending')]"));
        ProjectUtils.click(driver, clickString);

        WebElement checkFulfillment = driver.findElement(By.xpath("//span[contains(text(),'Fulfillment')]"));
        ProjectUtils.click(driver, checkFulfillment);

        WebElement fillTextField = driver.findElement(By.xpath("//textarea[@id = 'text']"));
        fillTextField.sendKeys("This is the sign");

        WebElement fillInt = driver.findElement(By.xpath("//input[@id = 'int']"));
        fillInt.sendKeys("100");

        WebElement fillDec = driver.findElement(By.xpath("//input[@id = 'decimal']"));
        fillDec.sendKeys("0.01");

        WebElement fillDate = driver.findElement(By.xpath("//input[@id = 'date']"));
        ProjectUtils.click(driver, fillDate);

        WebElement fillTime = driver.findElement(By.xpath("//input[@id = 'datetime']"));
        ProjectUtils.click(driver, fillTime);

        WebElement buttonSaveClick = driver.findElement(By.xpath("//button[@class = 'btn btn-warning']"));
        ProjectUtils.click(driver, buttonSaveClick);

        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'Fulfillment')]")).getText(),
                "Fulfillment");

        WebDriverWait wait = new WebDriverWait(driver, 6);

        WebElement findFulfillmentAgain = driver.findElement(By.xpath("//td//div[contains(text(), 'Fulfillment')]"));
        ProjectUtils.click(driver, findFulfillmentAgain);

        WebElement recheckFulfillment = driver.findElement(By.xpath("//a[@class = 'pa-chev-active']"));
        String ExpectedSign = "Fulfillment";
        Assert.assertEquals(ExpectedSign, recheckFulfillment.getText());
    }

    @Test
    private void addRecord() throws InterruptedException {

        WebDriver driver = getDriver();

        goChevronPage(driver);

        WebElement addLink = driver.findElement(By.xpath("//i[contains(text(), 'create_new_folder')]"));
        ProjectUtils.click(driver, addLink);

        WebElement stringFieldMenu = driver.findElement(By.xpath("//button[@data-id='string']"));
        ProjectUtils.click(driver, stringFieldMenu);
        WebElement stringField = driver.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", STATUS_NEW)));
        ProjectUtils.click(driver, stringField);

        driver.findElement(By.id("text")).sendKeys(TITLE);
        driver.findElement(By.id("int")).sendKeys(INT_NUMBER);
        driver.findElement(By.id("decimal")).sendKeys(DOUBLE_NUMBER);
        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, submit);

        WebElement row = findRow(driver);
        Assert.assertNotNull(row, "New record hasn't been found in the list");
    }

    @Test(dependsOnMethods = "addRecord")
    public void editStatus()  throws InterruptedException {

        WebDriver driver = getDriver();

        goChevronPage(driver);

        WebElement row = findRow(driver);
        Assert.assertNotNull(row, "Title hasn't been found in the filtered list");

        WebElement editLink = row.findElement(By.xpath(".//a[contains(@href, 'action_edit')]"));
        ProjectUtils.click(driver, editLink);

        WebElement stringFieldMenu = driver.findElement(By.xpath("//button[@data-id='string']"));
        ProjectUtils.click(driver, stringFieldMenu);

        WebElement pendingMenu = driver.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", STATUS_EDITED)));
        ProjectUtils.click(driver, pendingMenu);

        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, submit);

        WebElement linkPending = driver.findElement(By.xpath(String.format("//div[contains(@class,'card-body')]/div/a[contains(text(), '%s')]", STATUS_EDITED)));
        ProjectUtils.click(driver, linkPending);

        WebElement rowEdited = findRow(driver);
        Assert.assertNotNull(rowEdited, "Edited title hasn't been found in the filtered list");

        WebElement viewMenu = rowEdited.findElement(By.xpath(".//a[contains(@href, 'action_view')]"));
        ProjectUtils.click(driver, viewMenu);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='crumbs']//a[@class='pa-chev-active']")).getText(), STATUS_EDITED, "New status is not equal");
    }

    private WebElement findRow(WebDriver driver) {

        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (WebElement cell : cells) {
                if (cell.getText().equals(TITLE)) {
                    return row;
                }
            }
        }
        return null;
    }

    private void goChevronPage(WebDriver driver) {
        ProjectUtils.click(driver, driver.findElement(By.xpath("//p[contains(text(),'Chevron')]")));
    }
}