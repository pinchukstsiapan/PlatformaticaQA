import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.TestUtils;
import runner.type.ProfileType;
import runner.type.Run;
import runner.type.RunType;
import java.util.List;
import java.util.UUID;

@Run(run = RunType.Multiple)
public class EntityBoardTest extends BaseTest {

    private static final String TEXT = UUID.randomUUID().toString();
    private static final String NUMBER = String.valueOf((int) (Math.random() * 100));
    private static final String DECIMAL = String.valueOf((int) (Math.random() * 20000) / 100.0);
    private static final String PENDING = "Pending";
    private final String USER_DEFAULT = "user249@tester.com";

    private void createRecord() {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 6);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//p[contains(text(),'Board')]")));

        driver.findElement(By.xpath("//a[contains(@href, '31')]/i[text()='list']")).click();

        driver.findElement(By.xpath("//div[@class = 'card-icon']")).click();

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

        ProjectUtils.click(driver, driver.findElement(By.id("pa-entity-form-save-btn")));
    }

    @Test
    public void inputValidationTest() {
        WebDriver driver = getDriver();
        createRecord();

        List<WebElement> tabList = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(tabList.size(), 1, "Issue with unique record");

        List<WebElement> tabListValues = driver.findElements(By.xpath("//tbody/tr/td"));
        Assert.assertEquals(tabListValues.get(1).getText(), PENDING, "Created record Pending issue");
        Assert.assertEquals(tabListValues.get(2).getText(), TEXT, "Created record text issue");
        Assert.assertEquals(tabListValues.get(3).getText(), NUMBER, "Created record number issue");
        Assert.assertEquals(tabListValues.get(4).getText(), DECIMAL, "Created record decimal issue");
    }

    @Test(dependsOnMethods = "inputValidationTest")
    public void editBoard() {

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

        WebElement saveButton1 = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton1);

        String result = driver.findElement(By.xpath("//tbody/tr[1]/td[3]/a[1]/div[1]")).getText();
        Assert.assertEquals(result, "my test changed");
    }

    @Test(dependsOnMethods = {"inputValidationTest", "editBoard"})
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

        WebElement menuActions = driver.findElement(By.xpath("//i[text() = 'menu']/.."));
        ProjectUtils.click(driver, menuActions);
        WebElement optionDelete = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//ul[@role='menu']/li[3]/a[text()= 'delete']")));
        ProjectUtils.click(driver, optionDelete);
        // ProjectUtils.click(driver, driver.findElement(By.xpath("//i[text()='list']")));
        boolean emptyField = driver.findElements(By.xpath("//tbody/tr[1]/td[10]/div[1]/button[1]")).size() < 1;
        Assert.assertTrue(emptyField);

    }

    @Test(dependsOnMethods = {"inputValidationTest", "editBoard", "recordDeletion"})
    public void recordDeletionRecBin() throws InterruptedException {
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
}