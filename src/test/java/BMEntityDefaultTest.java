import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.sql.Driver;
import java.util.UUID;

public class BMEntityDefaultTest extends BaseTest {

    @Test
    public void defaultChange() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");
        ProjectUtils.login(driver, "user132@tester.com", "mhBHKTYWEs");

        WebElement entities = driver.findElement(By.xpath("//p[contains(text(),'Entities')]"));
        entities.click();
        WebElement defaultMenu = driver.findElement(By.xpath("//p[contains(text(),'Default')]"));
        defaultMenu.click();
        WebElement createNewfolder = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createNewfolder.click();

        //input new values
        WebElement inputString = driver.findElement(By.xpath("//input[@id='string']"));
        inputString.clear();
        inputString.sendKeys("edited default string value");

        WebElement inputText = driver.findElement(By.xpath("//textarea[@id='text']"));
        inputText.clear();
        inputText.sendKeys("edited default text value");

        WebElement inputInt = driver.findElement(By.xpath("//input[@id='int']"));
        inputInt.clear();
        inputInt.sendKeys("34");

        WebElement inputDecimal = driver.findElement(By.xpath("//input[@id='decimal']"));
        inputDecimal.clear();
        inputDecimal.sendKeys("110.54");

        WebElement inputDate = driver.findElement(By.xpath("//input[@id='date']"));
        inputDate.clear();
        inputDate.sendKeys("12/12/2020");

        WebElement inputDateTime = driver.findElement(By.xpath("//input[@id='datetime']"));
        inputDateTime.clear();
        inputDateTime.sendKeys("21/11/2020 13:59:00");


        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        saveButton.click();

        WebElement recordMenu = driver.findElement(By.xpath("//button[contains(@data-toggle, 'dropdown')] "));
        recordMenu.click();

        WebElement editFunction = driver.findElement(By.xpath("//a[text() = 'edit']"));
        System.out.println(editFunction);
        editFunction.click();

        inputString = driver.findElement(By.xpath("//input[@id='string']"));
        Assert.assertEquals(inputString.getAttribute("value"),"edited default string value");





    }

}
