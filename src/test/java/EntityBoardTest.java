import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import java.util.UUID;

public class EntityBoardTest extends BaseTest {

    @Ignore
    @Test
    public void inputTest() throws InterruptedException {
        final String text = UUID.randomUUID().toString();
        final int number = (int) (Math.random()*100);
        final double decimal = (int) (Math.random()*20000) / 100.0;
        final String dateField = "05/09/1945";
        final String dateTimeField = "05/09/1945 12:34:56";
        final String pending = "Pending";
        final String userDemo = "user249@tester.com";

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver,30);

        WebElement tabBoard = driver.findElement(By.xpath("//p[contains(text(),'Board')]"));
        ProjectUtils.click(driver, tabBoard);

        WebElement viewList = driver.findElement(By.xpath("//a[contains(@href, '31')]/i[text()='list']"));
        viewList.click();

        WebElement createNew = driver.findElement(By.xpath("//div[@class = 'card-icon']"));
        createNew.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='entity_form_data[text]']")));

        WebElement dropdownStatus = driver.findElement(By.id("string"));
        Select s = new Select(dropdownStatus);
        s.selectByVisibleText(pending);

        WebElement textPlaceholder = driver.findElement(By.xpath("//textarea[@name='entity_form_data[text]']"));
        ProjectUtils.sendKeys(textPlaceholder, text);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='entity_form_data[text]']")));
        wait.until(ExpectedConditions.attributeContains(textPlaceholder, "value", text));

        WebElement intPlaceholder = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        ProjectUtils.sendKeys(intPlaceholder, number);
        wait.until(ExpectedConditions.attributeContains(intPlaceholder, "value", String.valueOf(number)));

        WebElement decimalPlaceholder = driver.findElement(By.id("decimal"));
        ProjectUtils.sendKeys(decimalPlaceholder, decimal);
        wait.until(ExpectedConditions.attributeContains(decimalPlaceholder, "value", String.valueOf(decimal)));

        WebElement date = driver.findElement(By.id("date"));
        ProjectUtils.setAttribute(driver, date, "value", dateField);

        WebElement dateTime = driver.findElement(By.id("datetime"));
        ProjectUtils.setAttribute(driver, dateTime, "value", dateTimeField);

        WebElement dropdownUser = driver.findElement(By.id("user"));
        Select s2 = new Select(dropdownUser);
        s2.selectByVisibleText(userDemo);

        WebElement saveBtn = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, saveBtn);

        String recordTitleXpath = String.format("//div[contains(text(), '%s')]", text);
        WebElement createdRecordText = driver.findElement(By.xpath(recordTitleXpath));
        WebElement createdRecordStringPending = driver.findElement( By.xpath(String.format("%s/../../../td[2]/a/div", recordTitleXpath)));
        WebElement createdRecordInt = driver.findElement(By.xpath(String.format("%s/../../../td[4]/a/div", recordTitleXpath)));
        WebElement createdRecordDecimal = driver.findElement(By.xpath(String.format("%s/../../../td[5]/a/div", recordTitleXpath)));
        WebElement createdRecordDate = driver.findElement(By.xpath(String.format("%s/../../../td[6]/a/div", recordTitleXpath)));
        WebElement createdRecordDateTime = driver.findElement(By.xpath(String.format("%s/../../../td[7]/a/div", recordTitleXpath)));
        WebElement createdNewImage = driver.findElement(By.xpath(String.format("%s/../../../td[8]", recordTitleXpath)));
        WebElement createdRecordUserDemo = driver.findElement(By.xpath(String.format("%s/../../../td[9]", recordTitleXpath)));

        Assert.assertEquals(createdRecordStringPending.getText(), pending, "Created record Pending issue");
        Assert.assertEquals(createdRecordText.getText(), text, "Created record text issue");
        Assert.assertEquals(createdRecordInt.getText(), Integer.toString(number), "Created record number issue");
        Assert.assertEquals(createdRecordDecimal.getText(), Double.toString(decimal), "Created record decimal issue");
        Assert.assertEquals(createdRecordDate.getText(), dateField, "Created date issue");
        Assert.assertEquals(createdRecordDateTime.getText(), dateTimeField, "Created dateTime issue");
        Assert.assertEquals(createdRecordUserDemo.getText(), userDemo, "Created user issue");
    }

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
        ProjectUtils.setAttribute(driver, fieldDate, "value", date);
        WebElement fieldDateTime = driver.findElement(By.id("datetime"));
        ProjectUtils.setAttribute(driver, fieldDateTime, "value", dateTime);
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