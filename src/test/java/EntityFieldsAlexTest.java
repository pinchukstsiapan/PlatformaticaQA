import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

import java.util.List;
import java.util.UUID;

@Run(run = RunType.Multiple)
public class EntityFieldsAlexTest extends BaseTest {

    private static final By FIELDS_BUTTON = By.id("pa-menu-item-45");
    private static final By CREATE_newRecord = By.xpath("//i[text() = 'create_new_folder']");
    private static final By TITLE_FIELD = By.xpath("//input[contains(@name, 'title')]");
    private static final By COMMENTS_FIELD = By.xpath("//textarea[@id = 'comments']");
    private static final By INTEGER_FIELD = By.xpath("//input[contains(@name, 'int')]");
    private static final By SAVE_BTN = By.xpath("//button[text() = 'Save']");
    private static final By ROW = By.xpath("//tbody/tr");
    private static final By DROP_DOWN = By.xpath("//div[@class = 'dropdown pull-left']");
    private static final By EDIT_BTN = By.xpath("//a[contains(@href, 'edit')]");
    private static final By TITLE_LOCATOR = By.xpath("//td[2]/a/div");
    private static final By COMMENTS_LOCATOR = By.xpath("//td[3]/a/div");
    private static final By INTEGER_LOCATOR = By.xpath("//td[4]/a/div");
    private static final By DELETE_BTN = By.xpath("//a[contains(@href, 'delete')]");
    private static final By RECYCLE_BIN = By.xpath("//i[contains(text(), 'delete_outline')]");

    private static final String TITLE = UUID.randomUUID().toString();
    private static final String COMMENT = "NEW RECORD";
    private static final String INTEGER = "11";
    private static final String NEW_TITLE = UUID.randomUUID().toString();
    private static final String NEW_COMMENT = "EDIT RECORD";
    private static final String NEW_INTEGER = "1996";

    @Test
    public void createRecord() {

        WebDriver driver = getDriver();

        driver.findElement(FIELDS_BUTTON).click();
        driver.findElement(CREATE_newRecord).click();

        ProjectUtils.sendKeys(driver.findElement(TITLE_FIELD), TITLE);
        ProjectUtils.sendKeys(driver.findElement(COMMENTS_FIELD), COMMENT);
        ProjectUtils.sendKeys(driver.findElement(INTEGER_FIELD), INTEGER);

        ProjectUtils.click(driver, driver.findElement(SAVE_BTN));

        List<WebElement> rowList = driver.findElements(ROW);
        Assert.assertEquals(rowList.size(), 1);
        Assert.assertEquals(rowList.get(0).findElement(TITLE_LOCATOR).getText(), TITLE);
        Assert.assertEquals(rowList.get(0).findElement(COMMENTS_LOCATOR).getText(), COMMENT);
        Assert.assertEquals(rowList.get(0).findElement(INTEGER_LOCATOR).getText(), INTEGER);

    }


    @Test(dependsOnMethods = "createRecord")
    public void editRecord() {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 4);

        driver.findElement(FIELDS_BUTTON).click();
        driver.findElement(DROP_DOWN).click();
        wait.until(ExpectedConditions.elementToBeClickable(EDIT_BTN)).click();

        driver.findElement(TITLE_FIELD).clear();
        ProjectUtils.sendKeys(driver.findElement(TITLE_FIELD), NEW_TITLE);
        driver.findElement(COMMENTS_FIELD).clear();
        ProjectUtils.sendKeys(driver.findElement(COMMENTS_FIELD), NEW_COMMENT);
        driver.findElement(INTEGER_FIELD).clear();
        ProjectUtils.sendKeys(driver.findElement(INTEGER_FIELD), NEW_INTEGER);

        ProjectUtils.click(driver, driver.findElement(SAVE_BTN));

        List<WebElement> rowList = driver.findElements(ROW);
        Assert.assertEquals(rowList.size(), 1);
        Assert.assertEquals(rowList.get(0).findElement(TITLE_LOCATOR).getText(), NEW_TITLE);
        Assert.assertEquals(rowList.get(0).findElement(COMMENTS_LOCATOR).getText(), NEW_COMMENT);
        Assert.assertEquals(rowList.get(0).findElement(INTEGER_LOCATOR).getText(), NEW_INTEGER);
    }

    @Test(dependsOnMethods = "editRecord")
    public void deleteRecord() {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 4);

        driver.findElement(FIELDS_BUTTON).click();
        driver.findElement(DROP_DOWN).click();
        wait.until(ExpectedConditions.elementToBeClickable(DELETE_BTN)).click();

        driver.findElement(RECYCLE_BIN).click();
        List<WebElement> rowInRecycleBin = driver.findElements(ROW);
        Assert.assertEquals(rowInRecycleBin.size(), 1);
    }
}

