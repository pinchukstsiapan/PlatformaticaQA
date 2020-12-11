package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

@Ignore
public class GroupQa6ersTest extends BaseTest {

    @Test
    public void rinsal() {
        WebDriver driver = getDriver();
        driver.get("https://en.wikipedia.org/wiki/Selenium");
        WebElement text = driver.findElement(By.xpath("//div/a[@title='Isotopes of selenium']"));
        Assert.assertEquals(text.getText(), "Isotopes of selenium");
    }
}