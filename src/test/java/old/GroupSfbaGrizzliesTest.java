package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

@Ignore
public class GroupSfbaGrizzliesTest extends BaseTest {

    @Test
    public void klavdiaGoldshteyn() throws InterruptedException {
        WebDriver browser = getDriver();
        browser.get("https://en.wikipedia.org/wiki/Roger_Federer");
        WebElement title = browser.findElement(By.xpath("//h1[@id='firstHeading']"));

        Assert.assertEquals(title.getText(), "Roger Federer");
    }

    @Ignore
    @Test
    public void gayaneMfirst() throws InterruptedException {
        WebDriver browser = getDriver();
        browser.get("https://www.elvisthemusic.com/");

        WebElement link = browser.findElement(By.xpath("//a[contains(@href,'/news/')]"));
        link.click();
        Thread.sleep(2000);

        Assert.assertEquals(browser.getCurrentUrl(), "https://www.elvisthemusic.com/news/");
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

        Assert.assertEquals(text.getText(), "\"umbrella\"");
    }

    @Ignore
    @Test
    public void lanaRogova() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://vpl.bibliocommons.com/v2/");

        WebElement text = browser.findElement(By.xpath("//a[@id='biblionav_account_trigger']"));
        Assert.assertEquals(text.getText(), "Log In / My VPL\nUser Log In / My VPL.");
    }

    @Test
    public void gayaneMthird() throws InterruptedException {
        WebDriver browser = getDriver();
        browser.get("https://www.newyorktheatreguide.com/whats-on/broadway/musicals");

        WebElement input = browser.findElement(By.xpath("//input[@id='edit-search-block-form--2']"));
        input.sendKeys("Cats");

        WebElement icon = browser.findElement(By.xpath("//input[@id='edit-submit']"));
        icon.click();

        Assert.assertEquals(browser.getCurrentUrl(), "https://www.newyorktheatreguide.com/search?s=Cats");
    }

    @Test
    public void kristinaMarachova() {

        WebDriver browser = getDriver();
        browser.get("https://www.thesaurus.com/");

        WebElement name = browser.findElement(By.xpath("//a[contains(@id,'thesaurus-nav-tab')]"));

        Assert.assertEquals(name.getText(), "THESAURUS.COM");
    }

    @Test
    public void lisaJohns() {
        WebDriver browser = getDriver();
        browser.get("https://www.rideboreal.com/");

        WebElement title = browser.findElement(By.xpath("//div/h1[text()='HOME AT BOREAL']"));

        Assert.assertEquals(title.getText(), "HOME AT BOREAL");
    }
}
