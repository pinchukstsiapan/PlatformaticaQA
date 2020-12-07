import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupJavaBears extends BaseTest {

    @Test
    public void victoriaRogozhkina() {

        WebDriver driver = getDriver();
        driver.get("https://developer.mozilla.org/en-US/docs/Web/XPath/Functions");

        WebElement link = driver.findElement(By.xpath("//li/a[contains(text(), 'contains()')]"));
        link.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://developer.mozilla.org/en-US/docs/Web/XPath/Functions/contains");
    }
    @Test
    public void Alex_Mack() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://en.wikipedia.org/wiki/Golden_Gate");

        WebElement title = driver.findElement(By.xpath("//span[@id='History']"));
        Assert.assertEquals(title.getText(), "History");

        Thread.sleep(3000);
    }
}
