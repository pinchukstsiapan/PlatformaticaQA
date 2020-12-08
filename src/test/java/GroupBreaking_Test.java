import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class GroupBreaking_Test {
        @Ignore
        @Test
        public void NataliyaPlatonova() throws InterruptedException{
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.hotspringspool.com/");
            WebElement button = driver.findElement(By.xpath("//a[@href='https://reservations.hotspringspool.com/#/roomsBooking']"));
            button.click();
            String getActualTitle = driver.getTitle();
            Assert.assertEquals(getActualTitle,"Glenwood Hot Springs | Reservations");
        }
    }
