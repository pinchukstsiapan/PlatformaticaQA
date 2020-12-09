import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupLetsDoItTest extends BaseTest {

    @Test
    public void tatianaChueva() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://docs.oracle.com/en/java");

        WebElement name = browser.findElement(By.xpath("//div[@class='ohc-sidebar hidden-xs'] //a[contains(text(), 'Java')]"));
        Assert.assertEquals(name.getText(), "Java");
    }

    @Ignore
    @Test
    public void secondTestChuevaT() throws InterruptedException{

        WebDriver driver = getDriver();
        driver.get("https://docs.oracle.com/en/java");

        WebElement button = driver.findElement(By.xpath("//div[@class='ohc-sidebar hidden-xs'] //a[contains(text(), 'Cloud')]"));
        button.click();

        WebElement name = driver.findElement(By.xpath("//span[contains(text(),'How can Oracle Cloud Infrastructure products help?')]"));
        Assert.assertEquals(name.getText(), "How can Oracle Cloud Infrastructure products help?");
    }
}
