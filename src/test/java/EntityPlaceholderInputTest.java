import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.UUID;

public class EntityPlaceholderInputTest extends BaseTest {

    @Test
    public void newRecord() throws InterruptedException {

        final String title = UUID.randomUUID().toString();

        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver,"user1@tester.com", "ah1QNmgkEO");

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
    public void newRecordPV () throws InterruptedException {

        int pageNumber;

        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work/");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");

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

        WebElement dateField = driver.findElement(By.xpath("//input[@name='entity_form_data[date]']"));
        String date_ph = dateField.getAttribute("placeholder");
        dateField.sendKeys(date_ph);

        WebElement datetimeField = driver.findElement(By.xpath("//input[@name='entity_form_data[datetime]']"));
        String datetime_ph = datetimeField.getAttribute("placeholder");
        datetimeField.sendKeys(datetime_ph);

        WebElement save = driver.findElement(By.xpath("//div/button[@id='pa-entity-form-save-btn']"));
        Actions actions = new Actions(driver);

        actions.moveToElement(save).click().perform();

//        Thread.sleep(6000);
//        save.click();

        WebElement numOfPagesS = driver.findElement(By.xpath("//span[@class='pagination-info']"));

        int numOfRows =  Integer.parseInt(numOfPagesS.getText().substring(19, 22));
        int rowsPerPage =  Integer.parseInt(numOfPagesS.getText().substring(13, 15));

        if (numOfRows%rowsPerPage == 0) {
            pageNumber = numOfRows/rowsPerPage;
        } else {
            pageNumber = numOfRows/rowsPerPage + 1;
        }

        // id of the newly created record (the last record)
        int id = numOfRows-1;

        WebElement page = driver.findElement(By.xpath("//a[@class='page-link'][@aria-label='to page " + pageNumber + "']"));
        page.click();

        Thread.sleep(2000);
        driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", id, string_ph)));
        driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", id, text_ph)));
        driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", id, int_ph)));
        driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", id, decimal_ph)));

        WebElement actions1 = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//button/i[text()='menu']", id)));
        Thread.sleep(2000);
        actions1.click();
        WebElement delete = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div//li/a[text()='delete']", id)));
        Thread.sleep(2000);
        delete.click();
    }
}
