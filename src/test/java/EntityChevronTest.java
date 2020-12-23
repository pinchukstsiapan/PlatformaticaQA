import org.openqa.selenium.remote.internal.WebElementToJsonConverter;
import runner.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.ProjectUtils;

import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;


public class EntityChevronTest extends BaseTest {

    final private String TITLE = UUID.randomUUID().toString();
    final private String INT_NUMBER = "123";
    final private String DOUBLE_NUMBER = "4.56";
    final private String STATUS_NEW = "Fulfillment";
    final private String STATUS_EDITED = "Pending";

    @Test
    public void findChevron() throws InterruptedException {

        WebDriver driver = ProjectUtils.loginProcedure(getDriver());

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
    private void addRecord () throws InterruptedException {

        WebDriver driver = getDriver();

        goMenuPage("Chevron");

        WebElement addLink = driver.findElement(By.xpath("//i[contains(text(), 'create_new_folder')]"));
        ProjectUtils.click(driver, addLink);

        WebElement stringFieldMenu = driver.findElement(By.xpath("//button[@data-id='string']"));
        ProjectUtils.click(driver, stringFieldMenu);

        WebElement stringField = driver.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", STATUS_NEW)));
        ProjectUtils.click(driver, stringField);

        WebElement textField = driver.findElement(By.xpath("//textarea[@id = 'text']"));
        textField.sendKeys(TITLE);

        WebElement intField = driver.findElement(By.xpath("//input[@id = 'int']"));
        intField.sendKeys(INT_NUMBER);

        WebElement decimalField = driver.findElement(By.xpath("//input[@id = 'decimal']"));
        decimalField.sendKeys(DOUBLE_NUMBER);

        driver.findElement(By.xpath("//input[@id = 'date']")).click();

        driver.findElement(By.xpath("//input[@id = 'datetime']")).click();

        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, submit);

        goMenuPage("Chevron");

        WebElement row = findRowByTitle(TITLE);
        Assert.assertNotNull(row, "New record hasn't been found in the list");
    }

    @Test(dependsOnMethods = "addRecord")
    public void editRecord ()  throws InterruptedException {

        WebDriver driver = getDriver();

        addRecord();

        goMenuPage("Chevron");

        WebElement editMenu = driver.findElement(By.xpath("//a[contains(@href, 'action_edit')][1]"));
        ProjectUtils.click(driver, editMenu);

        WebElement stringFieldMenu = driver.findElement(By.xpath("//button[@data-id='string']"));
        ProjectUtils.click(driver, stringFieldMenu);

        WebElement pendingMenu = driver.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", STATUS_EDITED)));
        ProjectUtils.click(driver, pendingMenu);

        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, submit);

        goMenuPage("Chevron");

        WebElement linkPending = driver.findElement(By.xpath(String.format("//div[contains(@class,'card-body')]/div/a[contains(text(), '%s')]", STATUS_EDITED)));
        ProjectUtils.click(driver, linkPending);

        WebElement row = findRowByTitle(TITLE);
        Assert.assertNotNull(row, "Title hasn't been found in the filtered list");

        WebElement viewMenu = row.findElement(By.xpath("//a[contains(@href, 'action_view')]"));
        ProjectUtils.click(driver, editMenu);

        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='crumbs']//a[@class='pa-chev-active']")).getText(), STATUS_EDITED, "New status is not equal");
    }

    private WebElement findRowByTitle(String title) {

        List<WebElement> rows = getDriver().findElement(By.xpath("//table[@id = 'pa-all-entities-table']")).findElements(By.xpath(".//tbody/tr"));

        for (WebElement row : rows) {
            WebElement cell = row.findElement(By.xpath(String.format(".//td//div[contains(text(),'%s')]", title)));
            if (cell != null) {
                return row;
            }
        }

        return null;
    }

    private void goMenuPage(String pageName) {
        ProjectUtils.click(getDriver(), getDriver().findElement(By.xpath(String.format("//p[contains(text(),'%s')]", pageName))));
    }
}














