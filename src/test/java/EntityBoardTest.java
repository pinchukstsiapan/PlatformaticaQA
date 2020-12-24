import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @Test
    public void editBoard() throws InterruptedException {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDataEuropean = formatter.format(calendar.getTime());
        final String id = UUID.randomUUID().toString();

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement board = driver.findElement(By.xpath("//p[contains(text(),'Board')]"));
        ProjectUtils.click(driver, board);

        WebElement newFolder = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        newFolder.click();

        WebElement text = driver.findElement(By.xpath("//textarea[@id='text']"));
        text.sendKeys(id);

        WebElement integer = driver.findElement(By.xpath("//input[@id='int']"));
        integer.sendKeys(String.valueOf(20));

        WebElement decimal = driver.findElement(By.xpath("//input[@id='decimal']"));
        decimal.sendKeys(String.valueOf(22.5));

        WebElement date = driver.findElement(By.id("date"));
        date.click();

        WebElement dateTime = driver.findElement(By.id("datetime"));
        dateTime.click();

        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);

        WebElement dashboard = driver.findElement(By.xpath("//ul[@class='pa-nav-pills-small nav nav-pills nav-pills-primary']//i[text()='dashboard']"));
        dashboard.click();

        WebElement list = driver.findElement(By.xpath("//ul[@class='pa-nav-pills-small nav nav-pills nav-pills-primary']//i[text()='list']"));
        list.click();

        WebElement container = driver.findElement(By.xpath("//tbody/tr[1]/td[10]/div[1]/button[1]"));
        container.click();

        WebElement edit = driver.findElement(By.xpath("//a[text()='edit']"));
        ProjectUtils.click(driver, edit);

        WebElement pending = driver.findElement(By.xpath("//div[contains(text(),'Pending')]"));
        pending.click();
        wait.until(ExpectedConditions.elementToBeClickable(pending));

        WebElement onTrack = driver.findElement(By.xpath("//span[contains(text(),'On track')]"));
        onTrack.click();

        WebElement newText= driver.findElement(By.xpath("//textarea[@id='text']"));
        newText.clear();
        newText.sendKeys("my test changed");

        WebElement newInteger = driver.findElement(By.xpath("//input[@id='int']"));
        newInteger.clear();
        newInteger.sendKeys(String.valueOf(50));

        WebElement newDecimal = driver.findElement(By.xpath("//input[@id='decimal']"));
        newDecimal.clear();
        newDecimal.sendKeys(String.valueOf(50.5));

        WebElement saveButton1 = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton1);

        String result = driver.findElement(By.xpath("//tbody/tr[1]/td[3]/a[1]/div[1]")).getText();
        Assert.assertEquals(result, "my test changed");

        WebElement container1 = driver.findElement(By.xpath("//tbody/tr[1]/td[10]/div[1]/button[1]"));
        container1.click();
        wait.until(ExpectedConditions.elementToBeClickable(container1));

        WebElement delete = driver.findElement(By.xpath("//a[text()='delete']"));
        delete.click();

        driver.findElement(By.xpath("//ul[@class='pa-nav-pills-small nav nav-pills nav-pills-primary']//i[text()='list']")).click();
        boolean emptyField = driver.findElements(By.xpath("//tbody/tr[1]/td[10]/div[1]/button[1]")).size() < 1;
        Assert.assertTrue(emptyField);
    }
}




