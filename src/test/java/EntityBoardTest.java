
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
    private static final String NUMBER = String.valueOf((int) (Math.random() * 100));
    private static final String DECIMAL = String.valueOf((int) (Math.random() * 20000) / 100.0);
    private static final String PENDING = "Pending";
    private static final String DONE = "Done";
    private static final String ON_TRACK = "On track";
    private static final String APP_USER = "apptester1@tester.com";
    LocalDate today = LocalDate.now();
    String output = today.toString();
    String[] arrOfData = output.split("-", 3);
    private String currentYear = arrOfData[0];
    private String currentMonth = arrOfData[1];

    private void createRecord(WebDriver driver, String status) {

        WebDriverWait wait = new WebDriverWait(driver, 6);
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
        driver.findElement(By.xpath("//td[@data-day = '" +currentMonth + "/" + "15" + "/" + currentYear +"']")).click();
        wait.until(ExpectedConditions.textToBe
                (By.xpath("//td[@data-day = '" +currentMonth + "/" + "15" + "/" + currentYear +"']"), "15"));

        driver.findElement(By.id("date")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class = 'datepicker']"))));
        driver.findElement(By.xpath("//td[@data-day = '" +currentMonth + "/" + "15" + "/" + currentYear +"']")).click();
        wait.until(ExpectedConditions.textToBe
                (By.xpath("//td[@data-day = '" +currentMonth + "/" + "15" + "/" + currentYear +"']"), "15"));

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        WebElement dropdownUser = driver.findElement(By.xpath("//div[contains(text(),'User 1 Demo')]"));
        js.executeScript("arguments[0].scrollIntoView();", dropdownUser);
        ProjectUtils.click(driver, dropdownUser);

        Select appTester1 = new Select(driver.findElement(By.id("user")));
        appTester1.selectByVisibleText(APP_USER);
    }
    private void forwardManipulate1(WebDriver driver) {
        WebElement board = driver.findElement(By.xpath("//div[@id='menu-list-parent']/ul/li[10]/a"));
        ProjectUtils.click(driver, board);

        WebElement createdRecord = driver.findElement(By.xpath("//div[contains(text(),'" + TEXT + "')]"));
        WebElement from = createdRecord.findElement(By.xpath("./.."));
        WebElement to = driver.findElement(By.xpath("//div[2]/main[@class='kanban-drag']"));
        Actions act = new Actions(driver);
        act.dragAndDrop(from, to).build().perform();

        driver.findElement(By.xpath("//ul[@role='tablist']/li[2]/a")).click();
        WebElement myElement = driver.findElement(By.xpath("//div[contains(text(),'" + TEXT + "')]"));
        WebElement trParent = myElement.findElement(By.xpath("./.."));
        WebElement tdWithStatus = trParent.findElement(By.xpath("//table/tbody/tr/td[2]"));
        Assert.assertEquals(tdWithStatus.findElement(By.tagName("div")).getText(), "On track");
    }

    private void forwardManipulate2(WebDriver driver) {

        driver.findElement(By.xpath("//ul[@role='tablist']/li[1]/a")).click();

        WebElement elementStatus = driver.findElement
                (By.xpath("//main[@class='kanban-drag']//div[contains(text(),'On track')]"));
        WebElement from = elementStatus.findElement(By.xpath("./.."));
        WebElement to = driver.findElement(By.xpath("//div[3]/main[@class='kanban-drag']"));
        Actions act = new Actions(driver);
        act.dragAndDrop(from, to).build().perform();

        driver.findElement(By.xpath("//ul[@role='tablist']/li[2]/a")).click();

        WebElement myElement = driver.findElement(By.xpath("//div[contains(text(),'" + TEXT + "')]"));
        WebElement trParent = myElement.findElement(By.xpath("./.."));
        WebElement tdWithStatus = trParent.findElement(By.xpath("//table/tbody/tr/td[2]"));
        Assert.assertEquals(tdWithStatus.findElement(By.tagName("div")).getText(), "Done");
    }

    private void backwardManipulate1(WebDriver driver) {

        driver.findElement(By.xpath("//ul[@role='tablist']/li[1]/a")).click();

        WebElement elementStatus = driver.findElement
                (By.xpath("//main[@class='kanban-drag']//div[contains(text(),'Done')]"));
        WebElement from = elementStatus.findElement(By.xpath("./.."));
        WebElement to = driver.findElement(By.xpath("//div[2]/main[@class='kanban-drag']"));
        Actions act = new Actions(driver);
        act.dragAndDrop(from, to).build().perform();

        driver.findElement(By.xpath("//ul[@role='tablist']/li[2]/a")).click();

        WebElement myElement = driver.findElement(By.xpath("//div[contains(text(),'" + TEXT + "')]"));
        WebElement trParent = myElement.findElement(By.xpath("./.."));
        WebElement tdWithStatus = trParent.findElement(By.xpath("//table/tbody/tr/td[2]"));
        Assert.assertEquals(tdWithStatus.findElement(By.tagName("div")).getText(), "On track");
    }
    private void backwardManipulate2(WebDriver driver) {

        driver.findElement(By.xpath("//ul[@role='tablist']/li[1]/a")).click();

        WebElement elementStatus = driver.findElement
                (By.xpath("//main[@class='kanban-drag']//div[contains(text(),'On track')]"));
        WebElement from = elementStatus.findElement(By.xpath("./.."));
        WebElement to = driver.findElement(By.xpath("//div[1]/main[@class='kanban-drag']"));
        Actions act = new Actions(driver);
        act.dragAndDrop(from, to).build().perform();

        driver.findElement(By.xpath("//ul[@role='tablist']/li[2]/a")).click();

        WebElement myElement = driver.findElement(By.xpath("//div[contains(text(),'" + TEXT + "')]"));
        WebElement trParent = myElement.findElement(By.xpath("./.."));
        WebElement tdWithStatus = trParent.findElement(By.xpath("//table/tbody/tr/td[2]"));
        Assert.assertEquals(tdWithStatus.findElement(By.tagName("div")).getText(), "Pending");
    }

    @Test
    public void inputValidationTest() {

        WebDriver driver = getDriver();

        createRecord(driver, PENDING);

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
                "15" + "/" + currentMonth + "/" + currentYear, "Created record date issue");
        Assert.assertEquals(tabListValues.get(6).getText().substring(0,10),
                "15" + "/" + currentMonth + "/" + currentYear, "Created record dateTime issue");
        Assert.assertEquals(tabListValues.get(8).getText(), APP_USER, "Created record user issue");
    }


    @Test (dependsOnMethods = "inputValidationTest")
    public void viewRecords() {

        WebDriver driver = getDriver();

        WebDriverWait wait = new WebDriverWait(driver, 10);

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

    @Test (dependsOnMethods = {"inputValidationTest", "viewRecords"})
    public void ManipulateTest() {

        WebDriver driver = getDriver();
        forwardManipulate1(driver);
        forwardManipulate2(driver);
        backwardManipulate1(driver);
        backwardManipulate2(driver);
    }

    @Test(dependsOnMethods = {"inputValidationTest","viewRecords", "ManipulateTest"})
    public void editBoard()  {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

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
        text1.sendKeys("my test changed");

        WebElement integer1 = driver.findElement(By.id("int"));
        integer1.clear();
        integer1.sendKeys(String.valueOf(50));

        WebElement decimal1 = driver.findElement(By.id("decimal"));
        decimal1.clear();
        decimal1.sendKeys(String.valueOf(50.5));

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        WebElement dropdownUser = driver.findElement(By.xpath("//div[contains(text(),'apptester1@tester.com')]"));
        js.executeScript("arguments[0].scrollIntoView();", dropdownUser);
        ProjectUtils.click(driver, dropdownUser);

        WebElement dropdownUser166 = driver.findElement(By.xpath("//span[contains(text(),'user166@tester.com')]"));
        js.executeScript("arguments[0].scrollIntoView();", dropdownUser166);
        ProjectUtils.click(driver, dropdownUser166);

        WebElement saveButton1 = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton1);

        String result = driver.findElement(By.xpath("//tbody/tr[1]/td[3]/a[1]/div[1]")).getText();
        Assert.assertEquals(result, "my test changed");
    }

    @Test(dependsOnMethods = {"inputValidationTest","viewRecords", "ManipulateTest", "editBoard"})
    public void recordDeletion() throws InterruptedException {

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

    @Test(dependsOnMethods = {"inputValidationTest", "viewRecords", "ManipulateTest", "editBoard", "recordDeletion"})
    public void recordDeletionRecBin() {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 6);

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

    @Test (dependsOnMethods = {"inputValidationTest", "viewRecords", "ManipulateTest", "editBoard", "recordDeletion",
            "recordDeletionRecBin"})

    public void cancelInputTest() {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 6);

        createRecord(driver, PENDING);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//button[contains(text(), 'Cancel')]")));

        driver.findElement(By.xpath("//a[contains(@href, '31')]/i[text()='list']")).click();

        List<WebElement> tabList = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(tabList.size(), 0, "No records");

    }
}
