import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupSfbaNightSky_Test extends BaseTest {

        @Test
        public void nataliyaStTest() throws InterruptedException {

            WebDriver browser = getDriver();
            browser.get("https://www.postallocations.com/ca/castro-valley/castro-valley");
            WebElement name = browser.findElement(By.xpath("//*/div[@id='content']/div[1]/h2"));

            Assert.assertEquals(name.getText(), "CASTRO VALLEY POST OFFICE");

            Thread.sleep(3000);
        }
    }


