package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

@Ignore
public class GroupSfbaNightSkyTest extends BaseTest {

    @Test
    public void nataliyaStTest() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://www.postallocations.com/ca/castro-valley/castro-valley");

        WebElement name = browser.findElement(By.xpath("//*/div[@id='content']/div[1]/h2"));
        Assert.assertEquals(name.getText(), "CASTRO VALLEY POST OFFICE");
    }

    @Test
    public void klavdiaGTaskETest() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement elementCode = browser.findElement(By.xpath("//span[contains(@data-content,'Code')]"));
        WebElement elementPR = browser.findElement(By.xpath("//span[contains(@data-content,'Pull requests')]"));
        WebElement elementIssues = browser.findElement(By.xpath("//span[contains(@data-content,'Issues')]"));

        Assert.assertEquals(elementCode.getText(),"Code");
        Assert.assertEquals(elementPR.getText(),"Pull requests");
        Assert.assertEquals(elementIssues.getText(),"Issues");

        WebElement elementFork =
                browser.findElement(By.xpath("//li/a[@aria-label='You must be signed in to fork a repository']"));
        WebElement elementWatch =
                browser.findElement(By.xpath("//a[@aria-label='You must be signed in to watch a repository'][1]"));
        WebElement elementStar=
                browser.findElement(By.xpath("//a[@aria-label='You must be signed in to star a repository'][1]"));

        Assert.assertEquals(elementFork.getText(),"Fork");
        Assert.assertEquals(elementWatch.getText(),"Watch");
        Assert.assertEquals(elementStar.getText(),"Star");
    }
}