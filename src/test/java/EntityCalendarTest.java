import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

@Run(run = RunType.Multiple)
public class EntityCalendarTest extends BaseTest {

    @Test
    public void newCalendar() throws InterruptedException {

        WebDriver driver = getDriver();

        WebElement calendar = driver.findElement(By.xpath("//p[contains(text(),'Calendar')]"));
        ProjectUtils.click(driver, calendar);
        WebElement newCalendar = driver.findElement(By.xpath("//div[@class='card-icon']/i"));
        newCalendar.click();

        final String string = UUID.randomUUID().toString();
        final int number = 25;
        final double number1 = 56.23;

        WebElement titleElement = driver.findElement(By.xpath("//input[@name='entity_form_data[string]']"));
        titleElement.sendKeys(string);
        WebElement numberElement = driver.findElement(By.xpath("//*[@id=\"int\"]"));
        numberElement.sendKeys(String.valueOf(number));
        WebElement number1Element = driver.findElement(By.xpath("//*[@id=\"decimal\"]"));
        number1Element.sendKeys(String.valueOf(number1));
        WebElement dateElement = driver.findElement(By.xpath("//*[@id=\"date\"]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(dateElement).build().perform();
        dateElement.click();
        WebElement dateTimeElement = driver.findElement(By.xpath("//*[@id=\"datetime\"]"));
        dateTimeElement.click();
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(dateTimeElement).build().perform();
        dateTimeElement.click();
        WebElement submit = driver.findElement(By.xpath("//*[@id=\"pa-entity-form-save-btn\"]"));
        ProjectUtils.click(driver, submit);

        Thread.sleep(3000);
        WebElement listElement = driver.findElement(By.xpath("//div[2]/div[1]//div[1]/div/ul/li[2]/a"));
        listElement.click();

        driver.findElement(By.xpath("//div[contains(text(), '" + string + "')]"));
    }

    @Test(dependsOnMethods = "newCalendar")
    public void editCalendar() throws InterruptedException {

        WebDriver driver = getDriver();
        WebElement calendar = driver.findElement(By.xpath("//p[contains(text(),'Calendar')]"));
        ProjectUtils.click(driver, calendar);
        WebElement list = driver.findElement(By.xpath("//div[@class='content']//li[2]"));
        list.click();
        WebElement editList = driver.findElement(By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']"));
        editList.click();
        Thread.sleep(1000);
        WebElement clickEdit = driver.findElement(By.xpath("//a[normalize-space()='edit']"));
        clickEdit.click();
        WebElement str = driver.findElement(By.xpath("//input[@id='string']"));
        str.clear();
        str.sendKeys("New Zapis");
        WebElement text = driver.findElement(By.xpath("//textarea[@id='text']"));
        text.clear();
        text.sendKeys("Ne znayu chto delat");
        WebElement number = driver.findElement(By.xpath("//*[@id='int']"));
        number.sendKeys("585");
        WebElement save = driver.findElement(By.xpath("//button[normalize-space()='Save']"));
        ProjectUtils.click(driver, save);
        WebElement resultEdit = driver.findElement(By.xpath("//tr//td[3]"));
        Assert.assertEquals(resultEdit.getText(), "Ne znayu chto delat");
    }


    final String titleField = UUID.randomUUID().toString();

    final String titleFieldNew = UUID.randomUUID().toString();

    public void setValue(WebDriver driver, String title, String text, int num, double decimal) {

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
        ProjectUtils.click(driver, saveBtn);

        WebElement listBtn = driver.findElement(By.xpath("//ul[@role='tablist']//i[contains(text(),'list')]"));
        listBtn.click();
    }

    @Test
    public void newRecord() throws InterruptedException {
        WebDriver driver = getDriver();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Calendar')]"));
        tab.click();

        WebElement createNewFolder = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createNewFolder.click();

        setValue(driver, titleField, "test", 55, 0);

        WebElement dropdown = driver.findElement(By.xpath("//div[@class='dropdown pull-left']"));
        dropdown.click();

        WebElement editBtn = driver.findElement(By.xpath("//a[contains(text(),'edit')]"));
        ProjectUtils.click(driver, editBtn);

        setValue(driver, titleFieldNew, "test test test", 256, 0.1);

        WebElement nameString = driver.findElement(By.xpath("//div[contains(text(),'" + titleFieldNew + "')]"));
        Assert.assertEquals(nameString.getText(), titleFieldNew);

        WebElement nameText = driver.findElement(By.xpath("//div[contains(text(),'test test test')]"));
        Assert.assertEquals(nameText.getText(), "test test test");

        WebElement intField = driver.findElement(By.xpath("//div[contains(text(),'256')]"));
        Assert.assertEquals(intField.getText(), "256");

        WebElement decimalField = driver.findElement(By.xpath("//div[contains(text(),'0.1')]"));
        Assert.assertEquals(decimalField.getText(), "0.1");

        WebElement dropdownDelete = driver.findElement(By.xpath("//div[@class='dropdown pull-left']"));
        dropdownDelete.click();

        WebElement deleteBtn = driver.findElement(By.xpath("//a[contains(text(),'delete')]"));
        ProjectUtils.click(driver,deleteBtn);

        WebElement RecycleBin = driver.findElement(By.xpath("//i[contains(text(),'delete_outline')]"));
        RecycleBin.click();

        WebElement deleteRecord = driver.findElement(By.xpath("//b[contains(text(),'" + titleFieldNew + "')]"));
        wait.until(driver1 -> deleteRecord.isDisplayed());
    }
}
