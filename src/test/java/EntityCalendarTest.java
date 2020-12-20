import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

@Run(run = RunType.Multiple)
public class EntityCalendarTest extends BaseTest {


    public void inputCalendar(WebDriver driver) {


        WebElement calendar = driver.findElement(By.xpath("//p[contains(text(),'Calendar')]"));
        ProjectUtils.click(driver, calendar);
        WebElement newCalendar = driver.findElement(By.xpath("//div[@class='card-icon']/i"));
        newCalendar.click();

        final String string = "New Text Imported";
        final String textArea = "Simple Test";
        final int num = 256;

        WebElement str = driver.findElement(By.xpath("//input[@id='string']"));
        str.sendKeys(string);
        WebElement text = driver.findElement(By.xpath("//textarea[@id='text']"));
        text.sendKeys(textArea);
        WebElement number = driver.findElement(By.xpath("//*[@id='int']"));
        number.sendKeys(String.valueOf(num));
        WebElement dateElement = driver.findElement(By.xpath("//*[@id='date']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(dateElement).build().perform();
        dateElement.click();
        WebElement dateTimeElement = driver.findElement(By.xpath("//*[@id='datetime']"));
        dateTimeElement.click();
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(dateTimeElement).build().perform();
        dateTimeElement.click();
        WebElement save = driver.findElement(By.xpath("//button[normalize-space()='Save']"));
        ProjectUtils.click(driver, save);
    }

    @Test
    public void editCalendar() throws InterruptedException {

        WebDriver driver = getDriver();
        inputCalendar(getDriver());
        WebElement calendar = driver.findElement(By.xpath("//p[contains(text(),'Calendar')]"));
        ProjectUtils.click(driver, calendar);
        WebElement list = driver.findElement(By.xpath("//div[@class='content']//li[2]"));
        list.click();
        WebElement editList = driver.findElement(By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']"));
        editList.click();
        Thread.sleep(1000);
        WebElement clickEdit = driver.findElement(By.xpath("//a[normalize-space()='edit']"));
        clickEdit.click();
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
}
