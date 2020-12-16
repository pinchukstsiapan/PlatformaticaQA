import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import runner.BaseTest;

public class EntityCalendarTest extends BaseTest {

    @Test
    public void editCalendar() throws InterruptedException {

        WebDriver driver = getDriver();

        driver.get("https://ref.eteam.work");
        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");

        WebElement calen = driver.findElement(By.xpath("//p[contains(text(),'Calendar')]"));
        ProjectUtils.click(driver, calen);

        WebElement list = driver.findElement(By.xpath("//div[@class='content']//li[2]"));
        list.click();

        WebElement editList = driver.findElement(By.xpath("//tbody/tr[4]/td[10]/div[1]/button[1]/i[1]"));
        editList.click();

        WebElement clickEdit = driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right show']//a[contains(text(),'edit')]"));
        clickEdit.click();

        WebElement textarea = driver.findElement(By.xpath("//textarea[@id='text']"));
        textarea.sendKeys("Privet eto prosto test");

        WebElement save = driver.findElement(By.xpath("//button[normalize-space()='Save']"));
        ProjectUtils.click(driver, save);

    }
}
