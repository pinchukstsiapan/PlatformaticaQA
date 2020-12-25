import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import java.util.List;
import java.util.UUID;

public class EntityBoardTest extends BaseTest {

    private static final String TEXT = UUID.randomUUID().toString();
    private static final String NUMBER = String.valueOf ((int)(Math.random()*100));
    private static final String DECIMAL = String.valueOf((int) (Math.random()*20000) / 100.0);
    private final String PENDING = "Pending";
    private final String USER_DEMO = "user249@tester.com";

   public void createRecord() {
       WebDriver driver = getDriver();
       WebDriverWait wait = new WebDriverWait(driver,6);
       WebElement tabBoard = driver.findElement(By.xpath("//p[contains(text(),'Board')]"));
       ProjectUtils.click(driver, tabBoard);

       WebElement viewList = driver.findElement(By.xpath("//a[contains(@href, '31')]/i[text()='list']"));
       viewList.click();

       WebElement createNew = driver.findElement(By.xpath("//div[@class = 'card-icon']"));
       createNew.click();

       Select dropdownStatus = new Select(driver.findElement(By.id("string")));
       dropdownStatus.selectByVisibleText(PENDING);

       WebElement textPlaceholder = driver.findElement(By.id("text"));
       textPlaceholder.click();
       textPlaceholder.sendKeys(TEXT);
       wait.until(ExpectedConditions.attributeContains(textPlaceholder, "value", TEXT));

       WebElement intPlaceholder = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
       intPlaceholder.click();
       intPlaceholder.sendKeys(NUMBER);
       wait.until(ExpectedConditions.attributeContains(intPlaceholder, "value",NUMBER));

       WebElement decimalPlaceholder = driver.findElement(By.id("decimal"));
       decimalPlaceholder.click();
       decimalPlaceholder.sendKeys(DECIMAL);
       wait.until(ExpectedConditions.attributeContains(decimalPlaceholder, "value", DECIMAL));

       Select dropdownUser = new Select(driver.findElement(By.id("user")));
       dropdownUser.selectByVisibleText(USER_DEMO);

       WebElement saveButton = driver.findElement(By.id("pa-entity-form-save-btn"));
       ProjectUtils.click(driver, saveButton);
   }

    @Test(invocationCount = 25)
    public void inputTest() {
        WebDriver driver = getDriver();
        createRecord();

        List<WebElement> tabList = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(tabList.size(), 1, "Issue with unique record");

        WebElement createdRecordStringPending = tabList.get(0).findElement(By.xpath("td[2]/a/div"));
        Assert.assertEquals(createdRecordStringPending.getText(), PENDING, "Created record Pending issue");

        WebElement createdRecordText = tabList.get(0).findElement(By.xpath("td[3]/a/div"));
        Assert.assertEquals(createdRecordText.getText(), TEXT, "Created record text issue");

        WebElement createdRecordInt = tabList.get(0).findElement(By.xpath("td[4]/a/div"));
        Assert.assertEquals(createdRecordInt.getText(), NUMBER, "Created record number issue");

        WebElement createdRecordDecimal = tabList.get(0).findElement(By.xpath("td[5]/a/div"));
        Assert.assertEquals(createdRecordDecimal.getText(), DECIMAL, "Created record decimal issue");

        WebElement createdRecordUserDemo = tabList.get(0).findElement(By.xpath("td[9]"));
        Assert.assertEquals(createdRecordUserDemo.getText(), USER_DEMO, "Created record user default issue");
    }

    @Ignore
    @Test
    public void newBoardRecordCreation() throws InterruptedException {
        final String text = UUID.randomUUID().toString();
        final int number = 320;
        final double decimal = 0.41;
        final String status = "Pending";
        final String date = "25/12/2021";
        final String dateTime = "25/12/2021 00:00:01";
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 6);

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Board')]"));
        ProjectUtils.click(driver, tab);

        WebElement buttonBoard = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        ProjectUtils.click(driver, buttonBoard);
        WebElement menuString = driver.findElement(By.xpath("//button[@data-id='string']"));
        ProjectUtils.click(driver, menuString);

        WebElement optionPending = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//a[@role='option']/span[contains(text(), '" + status + "')]/..")));
        ProjectUtils.click(driver, optionPending);

        WebElement fieldText = driver.findElement(By.id("text"));
        ProjectUtils.sendKeys(fieldText, text);
        WebElement fieldInt = driver.findElement(By.id("int"));
        ProjectUtils.sendKeys(fieldInt, number);
        WebElement fieldDecimal = driver.findElement(By.id("decimal"));
        ProjectUtils.sendKeys(fieldDecimal, decimal);
        WebElement fieldDate = driver.findElement(By.id("date"));
        //ProjectUtils.setAttribute(driver, fieldDate, "value", date);
        WebElement fieldDateTime = driver.findElement(By.id("datetime"));
       // ProjectUtils.setAttribute(driver, fieldDateTime, "value", dateTime);
        WebElement buttonSave = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, buttonSave);

        WebElement viewList = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//a[contains(@href, '31')]/i[text()='list']")));
        ProjectUtils.click(driver, viewList);
        WebElement newRecord = driver.findElement(By.xpath(
                "//table[@id='pa-all-entities-table']/tbody/tr[1]/td[3]/a/div"));
        Assert.assertEquals(newRecord.getText(), text, "No matching created record found.");

        WebElement menuActions = driver.findElement(By.xpath("//i[text() = 'menu']/.."));
        ProjectUtils.click(driver, menuActions);
        WebElement optionDelete = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//ul[@role='menu']/li[3]/a[text()= 'delete']")));
        ProjectUtils.click(driver, optionDelete);
        WebElement recycleBin = driver.findElement(By.xpath("//li/a/i[text()='delete_outline']"));
        ProjectUtils.click(driver, recycleBin);

        WebElement deletedRecord = driver.findElement(By.xpath("//span[contains(text(), 'Text:')]/b"));
        Assert.assertEquals(deletedRecord.getText(), text, "No matching deleted record found.");

        WebElement linkDeletePermanent = driver.findElement(By.xpath("//a[contains(text(), 'delete permanently')]"));
        ProjectUtils.click(driver, linkDeletePermanent);

        WebElement emptyRecycleBin = driver.findElement(By.xpath(
                "//div[contains(text(), 'Good job with housekeeping! Recycle bin is currently empty!')]"));
        Assert.assertNotNull(emptyRecycleBin, "No empty recycle bin message found.");
    }
}