import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.UUID;

public class EntityCalendarTest extends BaseTest {

    final String titleField = UUID.randomUUID().toString();

    final String titleFieldNew = UUID.randomUUID().toString();

    public void setValue(WebDriver driver,String title, String text, int num, double decimal){

        WebElement titleField = driver.findElement(By.xpath("//input[@name='entity_form_data[string]']"));
        titleField.clear();
        titleField.sendKeys(title);

        WebElement textPlaceholder = driver.findElement(By.xpath("//textarea[@name='entity_form_data[text]']"));
        textPlaceholder.clear();
        textPlaceholder.sendKeys(text);

        WebElement fieldInt = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        fieldInt.clear();
        fieldInt.sendKeys(String.valueOf(num));

        WebElement fieldDecimal = driver.findElement(By.xpath("//input[@name='entity_form_data[decimal]']"));
        fieldDecimal.clear();
        fieldDecimal.sendKeys(String.valueOf(decimal));

        WebElement saveBtn = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver,saveBtn);

        WebElement listBtn = driver.findElement(By.xpath("//ul[@role='tablist']//i[contains(text(),'list')]"));
        listBtn.click();
    }

    @Test
    public  void newRecord() throws InterruptedException {
        WebDriver driver = getDriver();

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Calendar')]"));
        tab.click();

        WebElement createNewFolder = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createNewFolder.click();

        setValue(driver,titleField,"test",55,0);

        WebElement dropdown = driver.findElement(By.xpath("//div[@class='dropdown pull-left']"));
        dropdown.click();

        WebElement editBtn = driver.findElement(By.xpath("//a[contains(text(),'edit')]"));
        editBtn.click();

        setValue(driver,titleFieldNew,"test test test",256,0.1);

        WebElement nameString = driver.findElement(By.xpath("//div[contains(text(),'" + titleFieldNew + "')]"));
        Assert.assertEquals(nameString.getText(),titleFieldNew);

        WebElement nameText = driver.findElement(By.xpath("//div[contains(text(),'test test test')]"));
        Assert.assertEquals(nameText.getText(),"test test test");

        WebElement intField = driver.findElement(By.xpath("//div[contains(text(),'256')]"));
        Assert.assertEquals(intField.getText(),"256");

        WebElement decimalField = driver.findElement(By.xpath("//div[contains(text(),'0.1')]"));
        Assert.assertEquals(decimalField.getText(),"0.1");

        WebElement dropdownDelete = driver.findElement(By.xpath("//div[@class='dropdown pull-left']"));
        dropdownDelete.click();

        WebElement deleteBtn = driver.findElement(By.xpath("//a[contains(text(),'delete')]"));
        deleteBtn.click();

        WebElement RecycleBin = driver.findElement(By.xpath("//i[contains(text(),'delete_outline')]"));
        RecycleBin.click();

        WebElement deleteRecord = driver.findElement(By.xpath("//b[contains(text(),'" + titleFieldNew + "')]"));
        deleteRecord.isDisplayed();

    }
}
