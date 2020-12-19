import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    public void newRecordPV () {

        WebDriver driver = getDriver();
        ProjectUtils.loginProcedure(driver);

        WebElement placeholder = driver.findElement(By.xpath("//p[text()=' Placeholder ']"));
        placeholder.click();

        WebElement newRec = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        newRec.click();

        WebElement stringField = driver.findElement(By.xpath("//input[@name='entity_form_data[string]']"));
        String string_ph = stringField.getAttribute("placeholder");
        stringField.sendKeys(string_ph);

        WebElement textField = driver.findElement(By.xpath("//textarea[@name='entity_form_data[text]']"));
        String text_ph = textField.getAttribute("placeholder");
        textField.sendKeys(text_ph);

        WebElement intField = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        String int_ph = intField.getAttribute("placeholder");
        intField.sendKeys(int_ph);

        WebElement decimalField = driver.findElement(By.xpath("//input[@name='entity_form_data[decimal]']"));
        String decimal_ph = decimalField.getAttribute("placeholder");
        decimalField.sendKeys(decimal_ph);

        WebElement save = driver.findElement(By.xpath("//div/button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, save);

        driver.findElement(By.xpath(String.format("//div[contains(text(), '%s')]", string_ph)));
        driver.findElement(By.xpath(String.format("//div[contains(text(), '%s')]", text_ph)));
        driver.findElement(By.xpath(String.format("//div[contains(text(), '%s')]", int_ph)));
        driver.findElement(By.xpath(String.format("//div[contains(text(), '%s')]", decimal_ph)));

        WebElement actions1 = driver.findElement(By.xpath(String.format("//tr[@data-index='0']//button/i[text()='menu']")));
        ProjectUtils.click(driver, actions1);
        WebElement delete = driver.findElement(By.xpath(String.format("//tr[@data-index='0']//div//li/a[text()='delete']")));
        ProjectUtils.click(driver, delete);
    }
}
