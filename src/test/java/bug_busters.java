import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class bug_busters extends BaseTest{

    @Test
    public void Denys() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://github.com/DennorQA/PlatformaticaQA");

        WebElement insightEl = driver.findElement(By.xpath("//span[text()='Insights']"));
        insightEl.click();
        Thread.sleep(2000);

        WebElement depen = driver.findElement(By.xpath("//a[@href='/DennorQA/PlatformaticaQA/network/dependencies']"));
        Assert.assertEquals(depen.getText(), "Dependency graph");
        driver.quit();
    }



}