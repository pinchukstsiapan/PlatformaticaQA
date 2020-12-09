import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupOfBugKillersTest extends BaseTest {

    @Test
    public void simpleTest() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");
        WebElement name = browser.findElement(By.xpath("//strong/a"));

        Assert.assertEquals(name.getText(), "PlatformaticaQA");
    }

    @Test
    public void secondTest() {

        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement button = driver.findElement(By.xpath("//details[contains(@data-action, 'get-repo')]"));
        button.click();

        WebElement input = driver.findElement(By.xpath("(//div[@class='input-group']/input)[1]"));
        Assert.assertEquals(input.getAttribute("value"), "https://github.com/SergeiDemyanenko/PlatformaticaQA.git");
    }

    @Test
    public void newTest() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement button = driver.findElement(By.id("branch-select-menu"));
        button.click();
        Thread.sleep(2000);

        WebElement link = driver.findElement(By.xpath("//footer/a[contains(text(), 'branches')]"));
        link.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://github.com/SergeiDemyanenko/PlatformaticaQA/branches");
    }

    @Test
    public void vladBezpalko() {

        WebDriver browser = getDriver();
        browser.get("https://office.ooma.com/");
        WebElement phone_field = browser.findElement(By.id("phone-label"));
        WebElement password_field = browser.findElement(By.xpath("//*[@id='password']/../*[@class='input-label']"));

        Assert.assertEquals(phone_field.getText(), "ENTER PHONE");
        Assert.assertEquals(password_field.getText(), "ENTER PASSWORD");
    }
}
