import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class EntityBoardTest extends BaseTest {

    @Test
    public void inputTest() throws InterruptedException {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDataEuropean = formatter.format(calendar.getTime());
        final String text = UUID.randomUUID().toString();
        final int number = 12;
        final double decimal = 10.25;
        final String pending = "Pending";
        final String user1Demo = "User 1 Demo";

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver,30);

        WebElement tabBoard = driver.findElement(By.xpath("//p[contains(text(),'Board')]"));
        ProjectUtils.click(driver, tabBoard);

        WebElement viewList = driver.findElement(By.xpath("//a[contains(@href, '31')]/i[text()='list']"));
        viewList.click();

        WebElement createNew = driver.findElement(By.xpath("//div[@class = 'card-icon']"));
        createNew.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='entity_form_data[text]']")));

        WebElement stringDropdown = driver.findElement(By.xpath("//div[text()= 'Pending']/.."));
        stringDropdown.click();

        WebElement stringPending = driver.findElement(By.xpath("//div[text()= 'Pending']"));
        stringPending.click();

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
        date.click();

        WebElement dateTime = driver.findElement(By.id("datetime"));
        dateTime.click();

        WebElement saveBtn = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, saveBtn);

        String recordTitleXpath = String.format("//div[contains(text(), '%s')]", text);
        By newRecordStringPending = By.xpath(String.format("%s/../../../td[2]/a/div", recordTitleXpath));
        By newRecordInt = By.xpath(String.format("%s/../../../td[4]/a/div", recordTitleXpath));
        By newRecordDecimal = By.xpath(String.format("%s/../../../td[5]/a/div", recordTitleXpath));
        By newRecordDate = By.xpath(String.format("%s/../../../td[6]/a/div", recordTitleXpath));
        By newRecordDateTime = By.xpath(String.format("%s/../../../td[7]/a/div", recordTitleXpath));
        By newRecordUser1Demo = By.xpath(String.format("%s/../../../td[9]", recordTitleXpath));

        By createdRecordText = By.xpath(recordTitleXpath);
        WebElement newCreatedRecordText = wait.until(ExpectedConditions.visibilityOfElementLocated(createdRecordText));

        WebElement createdRecordStringPending = driver.findElement(newRecordStringPending);
        WebElement createdRecordInt = driver.findElement(newRecordInt);
        WebElement createdRecordDecimal = driver.findElement(newRecordDecimal);
        WebElement createdRecordDate = driver.findElement(newRecordDate);
        WebElement createdRecordDateTime = driver.findElement(newRecordDateTime);
        WebElement createdRecordUser1Demo = driver.findElement(newRecordUser1Demo);

        Assert.assertEquals(newCreatedRecordText.getText(), text, "Created record text issue");
        Assert.assertEquals(createdRecordStringPending.getText(), pending, "Created record Pending issue");
        Assert.assertEquals(createdRecordInt.getText(), Integer.toString(number), "Created record number issue");
        Assert.assertEquals(createdRecordDecimal.getText(), Double.toString(decimal), "Created record decimal issue");
        Assert.assertEquals(createdRecordDate.getText(), currentDataEuropean, "Created date issue");
        Assert.assertEquals(createdRecordUser1Demo.getText(), user1Demo, "Created user issue");
    }

}