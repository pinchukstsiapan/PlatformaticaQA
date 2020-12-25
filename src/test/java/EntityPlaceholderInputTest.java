import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.UUID;

public class EntityPlaceholderInputTest extends BaseTest {

    @Test
    public void newRecord() throws InterruptedException {

        final String title = UUID.randomUUID().toString();

        WebDriver driver = getDriver();

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Placeholder')]"));
        tab.click();

        WebElement createNewFolder = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createNewFolder.click();

        WebElement titleElement = driver.findElement(By.xpath("//input[@name='entity_form_data[string]']"));
        titleElement.sendKeys(title);

        WebElement textPlaceholder = driver.findElement(By.xpath("//textarea[@name='entity_form_data[text]']"));
        textPlaceholder.sendKeys("test ");

        WebElement fieldInt = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        fieldInt.sendKeys(String.valueOf(55));

        WebElement fieldDecimal = driver.findElement(By.xpath("//input[@name='entity_form_data[decimal]']"));
        fieldDecimal.sendKeys(String.valueOf(0));

        WebElement fieldData = driver.findElement(By.id("date"));
        fieldData.click();

        WebElement saveBtn = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver,saveBtn);

        WebElement orderBtn = driver.findElement(By.xpath("//i[contains(text(),'format_line_spacing')]"));
        orderBtn.click();

        driver.findElement(By.xpath("//div[contains(text(),'" + title + "')]"));
    }


    @Test
    public void newRecordPV() {

        WebDriver driver = getDriver();

        WebElement placeholder = driver.findElement(By.xpath("//p[text()=' Placeholder ']"));
        ProjectUtils.click(driver, placeholder);

        WebElement newRec = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        ProjectUtils.click(driver, newRec);

        WebElement stringValue = driver.findElement(By.xpath("//input[@name='entity_form_data[string]']"));
        String string_ph = stringValue.getAttribute("placeholder");
        stringValue.sendKeys(string_ph);

        WebElement textValue = driver.findElement(By.xpath("//textarea[@name='entity_form_data[text]']"));
        String text_ph = textValue.getAttribute("placeholder");
        textValue.sendKeys(text_ph);

        WebElement intValue = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        String int_ph = intValue.getAttribute("placeholder");
        intValue.sendKeys(int_ph);

        WebElement decimalValue = driver.findElement(By.xpath("//input[@name='entity_form_data[decimal]']"));
        String decimal_ph = decimalValue.getAttribute("placeholder");
        decimalValue.sendKeys(decimal_ph);

        WebElement saveButton = driver.findElement(By.xpath("//div/button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);

        Assert.assertEquals(driver.findElement(By.xpath("//tr/td[2]/a/div")).getText(), string_ph);
        Assert.assertEquals(driver.findElement(By.xpath("//tr/td[3]/a/div")).getText(), text_ph);
        Assert.assertEquals(driver.findElement(By.xpath("//tr/td[4]/a/div")).getText(), int_ph);
        Assert.assertEquals(driver.findElement(By.xpath("//tr/td[5]/a/div")).getText(), decimal_ph);

        WebElement actions = driver.findElement(By.xpath("//tr[@data-index='0']//button/i[text()='menu']"));
        ProjectUtils.click(driver, actions);
        WebElement delete = driver.findElement(By.xpath("//tr[@data-index='0']//div//li/a[text()='delete']"));
        ProjectUtils.click(driver, delete);
    }
}
