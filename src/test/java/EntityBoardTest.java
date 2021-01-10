
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Run(run = RunType.Multiple)
public class EntityBoardTest extends BaseTest {

    private static final String TEXT = UUID.randomUUID().toString();
    private static final String NUMBER = Integer.toString((int) (Math.random() * 100));
    private static final String DECIMAL = Double.toString(35.06);
    private static final String PENDING = "Pending";
    private static final String DONE = "Done";
    private static final String ON_TRACK = "On track";
    private static final String APP_USER = "apptester1@tester.com";
    private static final LocalDate TODAY = LocalDate.now();
    private static final String OUTPUT = TODAY.toString();
    private static final String[] ARR_OF_DATA = OUTPUT.split("-", 3);
    private static final String CURRENT_YEAR = ARR_OF_DATA[0];
    private static final String CURRENT_MONTH = ARR_OF_DATA[1];
    Random generator = new Random();
    private final String RANDOM_DAY = String.format("%02d", generator.nextInt(27) + 1);

    private void createRecord
            (WebDriver driver, String text, String status, String number, String decimal, String RANDOM_DAY, String user) {

        WebDriverWait wait = getWebDriverWait();
        ProjectUtils.click(driver, driver.findElement(By.xpath("//p[contains(text(),'Board')]")));

        driver.findElement(By.xpath("//div[@class = 'card-icon']")).click();

        Select drop = new Select(driver.findElement(By.id("string")));
        drop.selectByVisibleText(status);

        WebElement textPlaceholder = driver.findElement(By.id("text"));
        textPlaceholder.click();
        textPlaceholder.sendKeys(TEXT);
        wait.until(ExpectedConditions.attributeContains(textPlaceholder, "value", TEXT));

        WebElement intPlaceholder = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        intPlaceholder.click();
        intPlaceholder.sendKeys(NUMBER);
        wait.until(ExpectedConditions.attributeContains(intPlaceholder, "value", NUMBER));

        WebElement decimalPlaceholder = driver.findElement(By.id("decimal"));
        decimalPlaceholder.click();
        decimalPlaceholder.sendKeys(DECIMAL);
        wait.until(ExpectedConditions.attributeContains(decimalPlaceholder, "value", DECIMAL));

        driver.findElement(By.id("datetime")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class = 'datepicker-days']"))));
        driver.findElement(By.xpath(String.format
                ("//td[@data-day = '%1$s%2$s%3$s%2$s%4$s']", CURRENT_MONTH, "/", RANDOM_DAY, CURRENT_YEAR))).click();

        driver.findElement(By.id("date")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class = 'datepicker']"))));
        driver.findElement(By.xpath(String.format
                ("//td[@data-day = '%1$s%2$s%3$s%2$s%4$s']", CURRENT_MONTH, "/", RANDOM_DAY, CURRENT_YEAR))).click();

        WebElement dropdownUser = driver.findElement(By.xpath("//div[contains(text(),'User 1 Demo')]"));
        ProjectUtils.scroll(driver, dropdownUser);
        ProjectUtils.click(driver, dropdownUser);

        Select appTester1 = new Select(driver.findElement(By.id("user")));
        appTester1.selectByVisibleText(APP_USER);
    }

    @Test
    public void inputValidationTest() {

        WebDriver driver = getDriver();

        createRecord(driver, TEXT, PENDING, NUMBER, DECIMAL, RANDOM_DAY, APP_USER);

        ProjectUtils.click(driver, driver.findElement(By.id("pa-entity-form-draft-btn")));

        driver.findElement(By.xpath("//a[contains(@href, '31')]/i[text()='list']")).click();

        List<WebElement> tabList = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(tabList.size(), 1, "Issue with unique record");

        List<WebElement> tabListValues = driver.findElements(By.xpath("//tbody/tr/td"));
        Assert.assertEquals(tabListValues.get(1).getText(), PENDING, "Created record Pending issue");
        Assert.assertEquals(tabListValues.get(2).getText(), TEXT, "Created record text issue");
        Assert.assertEquals(tabListValues.get(3).getText(), NUMBER, "Created record number issue");
        Assert.assertEquals(tabListValues.get(4).getText(), DECIMAL, "Created record decimal issue");
        Assert.assertEquals(tabListValues.get(5).getText(),
                RANDOM_DAY + "/" + CURRENT_MONTH + "/" + CURRENT_YEAR, "Created record date issue");
        Assert.assertEquals(tabListValues.get(6).getText().substring(0, 10),
                RANDOM_DAY + "/" + CURRENT_MONTH + "/" + CURRENT_YEAR, "Created record dateTime issue");
        Assert.assertEquals(tabListValues.get(8).getText(), APP_USER, "Created record user issue");
    }

    @Test(dependsOnMethods = "inputValidationTest")
    public void viewRecords() {

        WebDriver driver = getDriver();

        WebDriverWait wait = new WebDriverWait(driver, 3);

        WebElement board = driver.findElement(By.xpath("//p[contains(text(),'Board')]"));
        ProjectUtils.click(driver, board);

        WebElement list = driver.findElement(By.xpath("//a[contains(@href, '31')]/i[text()='list']"));
        wait.until(ExpectedConditions.elementToBeClickable(list));
        ProjectUtils.click(driver, list);

        WebElement container = driver.findElement(By.xpath("//i[text() = 'menu']"));
        ProjectUtils.click(driver, container);

        WebElement optionVew = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text() = 'view']")));
        ProjectUtils.click(driver, optionVew);

        List<WebElement> record = driver.findElements(By.xpath("//div[@class = 'card-body']//span"));
        Assert.assertEquals(record.get(0).getText(), PENDING, "Created record Pending issue");
        Assert.assertEquals(record.get(1).getText(), TEXT, "Created record text issue");
        Assert.assertEquals(record.get(2).getText(), NUMBER, "Created record number issue");
        Assert.assertEquals(record.get(3).getText(), DECIMAL, "Created record decimal issue");
        Assert.assertEquals(driver.findElement
                (By.xpath("//div[@class = 'form-group']/p")).getText(), APP_USER, "Created record user issue");
    }

    private void dragAndDropAndVerify(String status, String path) {

        WebDriver driver = getDriver();

        WebElement elementStatus = driver.findElement(By.xpath(path));
        WebElement from = elementStatus.findElement(By.xpath("./.."));
        WebElement to = driver.findElement(By.xpath("//div[contains(text(),'" + status + "')]/../.."));
        Actions act = new Actions(driver);
        act.dragAndDrop(from, to).build().perform();

        driver.findElement(By.xpath("//ul[@role='tablist']/li[2]/a")).click();
        WebElement myElement = driver.findElement(By.xpath("//table[@id='pa-all-entities-table']//tr/td[2]/a"));
        Assert.assertEquals(myElement.findElement(By.tagName("div")).getText(), status);

    }

    @Test(dependsOnMethods = {"inputValidationTest", "viewRecords"})
    public void manipulateTest1() {

        WebDriver driver = getDriver();

        WebElement board = driver.findElement(By.xpath("//div[@id='menu-list-parent']/ul/li[10]/a"));
        ProjectUtils.click(driver, board);

        dragAndDropAndVerify("On track", "//div[contains(text(),'" + TEXT + "')]");

    }

    @Test(dependsOnMethods = {"manipulateTest1"})
    public void manipulateTest2() {

        WebDriver driver = getDriver();

        WebElement board = driver.findElement(By.xpath("//div[@id='menu-list-parent']/ul/li[10]/a"));
        ProjectUtils.click(driver, board);

        dragAndDropAndVerify("Done", "//main[@class='kanban-drag']//div[contains(text(),'On track')]");

    }

    @Test(dependsOnMethods = {"manipulateTest2"})
    public void manipulateTest3() {

        WebDriver driver = getDriver();

        WebElement board = driver.findElement(By.xpath("//div[@id='menu-list-parent']/ul/li[10]/a"));
        ProjectUtils.click(driver, board);

        dragAndDropAndVerify("On track", "//main[@class='kanban-drag']//div[contains(text(),'Done')]");

    }

    @Test(dependsOnMethods = {"manipulateTest3"})
    public void manipulateTest4() {

        WebDriver driver = getDriver();

        WebElement board = driver.findElement(By.xpath("//div[@id='menu-list-parent']/ul/li[10]/a"));
        ProjectUtils.click(driver, board);

        dragAndDropAndVerify("Pending", "//main[@class='kanban-drag']//div[contains(text(),'On track')]");

    }

    @Test(dependsOnMethods = {"manipulateTest4"})
    public void editBoard() throws InterruptedException {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 3);

        WebElement board = driver.findElement(By.xpath("//p[contains(text(),'Board')]"));
        ProjectUtils.click(driver, board);

        WebElement list = driver.findElement(By.xpath("//a[contains(@href, '31')]/i[text()='list']"));
        wait.until(ExpectedConditions.elementToBeClickable(list));
        ProjectUtils.click(driver, list);

        WebElement container = driver.findElement(By.xpath("//i[text() = 'menu']/.."));
        ProjectUtils.click(driver, container);

        WebElement edit = driver.findElement(By.xpath("//a[text()='edit']"));
        wait.until(ExpectedConditions.elementToBeClickable(edit));
        ProjectUtils.click(driver, edit);

        WebElement pending = driver.findElement(By.xpath("//div[contains(text(),'Pending')]"));
        wait.until(ExpectedConditions.elementToBeClickable(pending));
        pending.click();

        WebElement onTrack = driver.findElement(By.xpath("//span[contains(text(),'On track')]"));
        ProjectUtils.click(driver, onTrack);

        WebElement text1 = driver.findElement(By.id("text"));
        text1.clear();
        ProjectUtils.sendKeys(text1, "my test changed");
        wait.until(ExpectedConditions.visibilityOf(text1));


        WebElement integer1 = driver.findElement(By.id("int"));
        integer1.clear();
        ProjectUtils.sendKeys(integer1, 50);

        WebElement decimal1 = driver.findElement(By.id("decimal"));
        decimal1.clear();
        ProjectUtils.sendKeys(decimal1, 50.5);

        WebElement dropdownUser = driver.findElement(By.xpath("//div[contains(text(),'apptester1@tester.com')]"));
        ProjectUtils.scroll(driver, dropdownUser);
        ProjectUtils.click(driver, dropdownUser);

        WebElement dropdownUser166 = driver.findElement(By.xpath("//span[contains(text(),'user166@tester.com')]"));
        ProjectUtils.scroll(driver, dropdownUser166);
        ProjectUtils.click(driver, dropdownUser166);

        WebElement saveButton1 = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton1);

        String result = driver.findElement(By.xpath("//tbody/tr[1]/td[3]/a[1]/div[1]")).getText();
        Assert.assertEquals(result, "my test changed");
    }

    @Test(dependsOnMethods = {"editBoard"})
    public void recordDeletion() {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 6);

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Board')]"));
        ProjectUtils.click(driver, tab);

        WebElement viewList = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//a[contains(@href, '31')]/i[text()='list']")));
        ProjectUtils.click(driver, viewList);
        WebElement newRecord = driver.findElement(By.xpath(
                "//table[@id='pa-all-entities-table']/tbody/tr[1]/td[3]/a/div"));
        Assert.assertEquals(newRecord.getText(), "my test changed", "No matching created record found.");

        WebElement menuActions = driver.findElement(By.xpath("//i[text() = 'menu']"));
        ProjectUtils.click(driver, menuActions);
        WebElement optionDelete = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//ul[@role='menu']/li[3]/a[text()= 'delete']")));
        ProjectUtils.click(driver, optionDelete);
        boolean emptyField = driver.findElements(By.xpath("//tbody/tr[1]/td[10]/div[1]/button[1]")).size() < 1;
        Assert.assertTrue(emptyField);
    }

    @Test(dependsOnMethods = {"recordDeletion"})
    public void recordDeletionRecBin() {

        WebDriver driver = getDriver();

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Board')]"));
        ProjectUtils.click(driver, tab);

        WebElement recycleBin = driver.findElement(By.xpath("//li/a/i[text()='delete_outline']"));
        ProjectUtils.click(driver, recycleBin);

        WebElement deletedRecord = driver.findElement(By.xpath("//span[contains(text(), 'Text:')]/b"));
        Assert.assertEquals(deletedRecord.getText(), "my test changed", "No matching deleted record found.");

        WebElement linkDeletePermanent = driver.findElement(By.xpath("//a[contains(text(), 'delete permanently')]"));
        ProjectUtils.click(driver, linkDeletePermanent);

        WebElement emptyRecycleBin = driver.findElement(By.xpath(
                "//div[contains(text(), 'Good job with housekeeping! Recycle bin is currently empty!')]"));
        Assert.assertNotNull(emptyRecycleBin, "No empty recycle bin message found.");
    }

    @Test(dependsOnMethods = {"recordDeletionRecBin"})

    public void cancelInputTest() {

        WebDriver driver = getDriver();

        createRecord(driver, TEXT, PENDING, NUMBER, DECIMAL, RANDOM_DAY, APP_USER);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//button[contains(text(), 'Cancel')]")));

        driver.findElement(By.xpath("//a[contains(@href, '31')]/i[text()='list']")).click();

        List<WebElement> tabList = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(tabList.size(), 0, "No records");

    }
}
