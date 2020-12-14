import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import runner.BaseTest;
public class EntityCalendarTest extends BaseTest {

    @Test
    public void newCalendar() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");

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
    }
}

