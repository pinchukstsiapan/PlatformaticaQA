package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

@Ignore
public class TeachersTest extends BaseTest {

    @Test
    public void testSergeiD() {
        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement elementCode = driver.findElement(By.xpath("//li[@class = 'd-flex']/a[@href = '/SergeiDemyanenko/PlatformaticaQA']"));
        WebElement elementIssues = driver.findElement(By.xpath("//li[@class = 'd-flex']/a[@href = '/SergeiDemyanenko/PlatformaticaQA/issues']"));
        WebElement elementPR = driver.findElement(By.xpath("//li[@class = 'd-flex']/a[@href = '/SergeiDemyanenko/PlatformaticaQA/pulls']"));

        final String BUTTON_XPATH = "(//li/a[@href = '/login?return_to=%2FSergeiDemyanenko%2FPlatformaticaQA'])";
        WebElement elementWatch = driver.findElement(By.xpath(BUTTON_XPATH + "[1]"));
        WebElement elementStar = driver.findElement(By.xpath(BUTTON_XPATH + "[2]"));
        WebElement elementFork = driver.findElement(By.xpath(BUTTON_XPATH + "[3]"));

        Assert.assertEquals(elementCode.getText(), "Code");
        Assert.assertEquals(elementIssues.getText(), "Issues");
        Assert.assertTrue(elementPR.getText().contains("Pull requests"));

        Assert.assertEquals(elementWatch.getText(), "Watch");
        Assert.assertEquals(elementStar.getText(), "Star");
        Assert.assertEquals(elementFork.getText(), "Fork");
    }
}
