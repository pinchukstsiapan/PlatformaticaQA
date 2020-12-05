import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class JavaBears extends BaseTest {

    @Test
    public void victoriaRogozhkina() {

        WebDriver driver = getDriver();
        driver.get("https://developer.mozilla.org/en-US/docs/Web/XPath/Functions");

        WebElement link = driver.findElement(By.xpath("//li/a[contains(text(), 'contains()')]"));
        link.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://developer.mozilla.org/en-US/docs/Web/XPath/Functions/contains");
    }

}
