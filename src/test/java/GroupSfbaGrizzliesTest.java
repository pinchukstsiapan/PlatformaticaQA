import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupSfbaGrizzliesTest extends BaseTest {

    @Test
    public void klavdiaGoldshteyn() throws InterruptedException {
       WebDriver browser = getDriver();
       browser.get("https://en.wikipedia.org/wiki/Roger_Federer");
       WebElement title = browser.findElement(By.xpath("//h1[@id='firstHeading']"));

       Assert.assertEquals(title.getText(), "Roger Federer");
    }

    @Test
    public void gayaneMfirst() throws InterruptedException {
        WebDriver browser = getDriver();
        browser.get("https://www.elvisthemusic.com/");

        WebElement link = browser.findElement(By.xpath("//a[contains(@href,'/news/')]"));
        link.click();
        Thread.sleep(2000);

        Assert.assertEquals(browser.getCurrentUrl(),"https://www.elvisthemusic.com/news/");
    }

    @Ignore
    @Test
    public void gayaneMsecond() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://www.amazon.com/");

        WebElement input = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
        input.sendKeys("umbrella");
        Thread.sleep(2000);
        WebElement submit = driver.findElement(By.xpath("//span[@id='nav-search-submit-text']/input[@type='submit']"));
        submit.click();
        Thread.sleep(2000);
        WebElement text = driver.findElement(By.xpath("//div//span[3][contains(text(), 'brel')]"));

        Assert.assertEquals(text.getText(),"\"umbrella\"");
    }

    @Ignore
    @Test
    public void lanaRogova() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://vpl.bibliocommons.com/v2/");

        WebElement text = browser.findElement(By.xpath("//a[@id='biblionav_account_trigger']"));
        Assert.assertEquals(text.getText(), "Log In / My VPL\nUser Log In / My VPL.");
    }
}
