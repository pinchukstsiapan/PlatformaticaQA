import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class BugBustersTest extends BaseTest {

    @Test
    public void denys() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://github.com/DennorQA/PlatformaticaQA");

        WebElement insightEl = driver.findElement(By.xpath("//span[text()='Insights']"));
        insightEl.click();
        Thread.sleep(2000);

        WebElement depen = driver.findElement(By.xpath("//a[@href='/DennorQA/PlatformaticaQA/network/dependencies']"));
        Assert.assertEquals(depen.getText(), "Dependency graph");
    }

    @Test
    public void olehOlekshii() {
        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement footer_1 = driver.findElement(By.xpath("/html[1]/body[1]/div[5]/div[1]/ul[1]/li[1]"));

        Assert.assertEquals(footer_1.getText(), "© 2020 GitHub, Inc.");
    }

    @Test
    public void rustamTest(){

        WebDriver driver = getDriver();
        driver.get("https://www.w3schools.com/");

        driver.findElement(By.xpath("(//a[text() ='Learn JavaScript'])[1]")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Tutorial')]")).isDisplayed());
    }
}