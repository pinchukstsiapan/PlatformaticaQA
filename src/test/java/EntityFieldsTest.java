import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.UUID;

public class EntityFieldsTest extends BaseTest {

    @Test
    public void newRecord() throws InterruptedException {

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
        //driver.findElement(By.xpath(String.format("//div[contains(text(), '%s')]", title)));
        driver.findElement(By.xpath("//div[contains(text(), '" + title + "')]"));

        // add delete
    }

    @Test
    public void deleteRecord() throws InterruptedException {

        WebDriver driver = getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://ref.eteam.work");
        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");
        driver.findElement(By.xpath("//li[@id='pa-menu-item-45']")).click();

        int numOfEntities = 500;

        //CREATE
        for (int i = 0; i < numOfEntities; i++) {
            driver.findElement(By.xpath("//i[contains(text(), 'create_new_folder')]")).click();
            driver.findElement(By.xpath("//input[@id='title']")).sendKeys("Title number " + (i+1));
            driver.findElement(By.xpath("//textarea[@id='comments']")).sendKeys("If i left this comment than i did not do my job of deleting");
            driver.findElement(By.xpath("//input[@id='int']")).sendKeys("777");
            driver.findElement(By.xpath("//input[@id='decimal']")).sendKeys("777.7777");
            driver.findElement(By.xpath("//input[@id='date']")).click();
            driver.findElement(By.xpath("//input[@id='datetime']")).click();
//        Need answer - How to click on the image button and embed an image ??
//        driver.findElement(By.xpath("//input[@id='file_image']")).click();
//        js.executeScript("window.scrollBy(0,1000)","");
            WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
            js.executeScript("arguments[0].scrollIntoView();", saveButton);
            saveButton.click();
        }

        //DESTROY
        for (int i = 0; i < numOfEntities; i++) {
            driver.findElement(By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']")).click();
            driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right show']/li[3]")).click();
        }

//        Thread.sleep(3000);


    }
}
