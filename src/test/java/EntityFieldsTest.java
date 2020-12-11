import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.BaseTest;

import java.util.UUID;

public class EntityFieldsTest extends BaseTest {

    @Test
    public void newRecord() {

        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");

        WebElement tab = driver.findElement(By.xpath("//li[@id='pa-menu-item-45']/a"));
        tab.click();
        WebElement newRecord = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        newRecord.click();

        final String title = TestUtils.getUUID();
        final String comment = "simple text";
        final int number = 10;

        WebElement titleElement = driver.findElement(By.xpath("//input[@name='entity_form_data[title]']"));
        titleElement.sendKeys(title);
        WebElement commentElement = driver.findElement(By.xpath("//textarea[@name='entity_form_data[comments]']"));
        commentElement.sendKeys(comment);
        WebElement numberElement = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        numberElement.sendKeys(String.valueOf(number));

        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, submit);

        // validation of record
        String recordTitleXpath = String.format("//div[contains(text(), '%s')]", title);
        By newRecordTitle = By.xpath(recordTitleXpath);
        By newRecordComment = By.xpath(String.format("%s/../../../td[3]/a/div", recordTitleXpath));
        By newRecordInt = By.xpath(String.format("%s/../../../td[4]/a/div", recordTitleXpath));

        WebElement createdRecordTitle = driver.findElement(newRecordTitle);
        WebElement createdRecordComment = driver.findElement(newRecordComment);
        WebElement createdRecordInt = driver.findElement(newRecordInt);

        Assert.assertTrue(createdRecordTitle.isDisplayed());
        Assert.assertEquals(createdRecordComment.getText(), comment);
        Assert.assertEquals(createdRecordInt.getText(), Integer.toString(number));

        // cleanup, delete created record
        driver.findElement(By.xpath(String.format("%s/../../..//button", recordTitleXpath))).click();
        List<WebElement> deleteButtons =
                driver.findElements(By.xpath(String.format("%s/../../..//a[contains(text(), 'delete')]",
                        recordTitleXpath)));
        for (WebElement button : deleteButtons) {
            if (button.isDisplayed()) {
                button.click();
                break;
            }
        }
    }

}
