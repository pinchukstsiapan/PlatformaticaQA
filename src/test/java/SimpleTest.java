import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleTest {

    @Test
    public void simpleTest() {

        System.setProperty("webdriver.chrome.driver", "d:/Work/Projects/chromedriver.exe");
        WebDriver browser = new ChromeDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");
        WebElement name = browser.findElement(By.cssSelector("div strong a"));

        Assert.assertEquals(name.getText(), "PlatformaticaQA");

        browser.close();
    }
}
