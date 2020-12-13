package old;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

@Ignore
public class BraveQATest extends BaseTest {

    @Test
    public void sergeoNevdah() throws InterruptedException {
        WebDriver browser = getDriver();
        browser.get("https://www.wikipedia.org");

        WebElement language = browser.findElement(By.xpath("//a[@id = 'js-link-box-en']/strong"));
        Thread.sleep(2000);
        Assert.assertEquals(language.getText(), "English");
        language.click();

        WebElement simpleSearch = browser.findElement(By.id("searchInput"));
        simpleSearch.sendKeys("New York");
        WebElement searchButton = browser.findElement(By.id("searchButton"));
        searchButton.click();
        Thread.sleep(2000);
    }

    @Test
    public void dmitrySearch() throws InterruptedException {
        WebDriver browser = getDriver();
        browser.get("https://www.wikipedia.org");

        WebElement searchLang = browser.findElement(By.xpath("//select[@name='language']/option[@value='ru']"));
        searchLang.click();

        Thread.sleep(2000);

        Assert.assertEquals(searchLang.isSelected(), true);
        Thread.sleep(2000);

        WebElement input = browser.findElement(By.xpath("//input[@id='searchInput']"));
        input.sendKeys("Обеспечение качества");

        Thread.sleep(2000);

        WebElement btn = browser.findElement(By.xpath("//fieldset/button"));
        btn.click();

        Thread.sleep(2000);

        WebElement hdr = browser.findElement(By.xpath("//h1"));
        Assert.assertEquals(hdr.getText(), "Обеспечение качества");
    }

    @Test
    public void victoriaRet() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://www.ebay.com/");

        WebElement shop = browser.findElement(By.xpath("//*[@id='gh-shop-a']"));
        Assert.assertTrue(shop.isDisplayed());

        WebElement search = browser.findElement(By.xpath("//*[@class='gh-tb ui-autocomplete-input']"));
        search.sendKeys("dress");
        WebElement searchButton = browser.findElement(By.xpath("//*[@id='gh-btn']"));
        searchButton.click();
    }

    @Test
    public void oxanaWiki() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://www.wikipedia.org");

        WebElement name = browser.findElement(By.xpath("//h1/span"));
        Assert.assertEquals(name.getText(), "Wikipedia");
    }

    @Test
    public void alisaGrinko() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://www.selenium.dev/");

        WebElement downloadTab = browser.findElement(By.xpath("//a[.='Downloads']"));
        downloadTab.click();

        WebElement linkJavaAPI = browser.findElement(By.xpath("//td[text()='Java']/following-sibling::td/a[text()='API Docs']"));
        linkJavaAPI.click();

        WebElement framesTab = browser.findElement(By.xpath("//a[.='Frames']"));
        Assert.assertEquals(framesTab.getText(), "FRAMES");
    }

    @Ignore
    @Test
    public void kateHargreaves() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://www.target.com/");

        browser.findElement(By.xpath("//div[@data-test='storeId-store-name']")).click();
        Thread.sleep(1000);
        browser.findElement(By.xpath("//button[contains(@aria-label, 'Set Lakeland')]")).click();

        Assert.assertEquals(browser.findElement(By.xpath("//div[@data-test='storeId-store-name']")).getText(), "Lakeland South");

        browser.findElement(By.xpath("//*[@id='search']")).sendKeys("Ornament hooks");
        Thread.sleep(1000);

        browser.findElement(By.xpath("//*[@data-test='btnSearch']")).click();
        Thread.sleep(3000);

        Assert.assertEquals(browser.findElement(By.xpath("//*[@data-test='numberOfSearchResults']")).getText(), "339 results");
    }

    @Ignore
    @Test
    public void ekaterinaEr() throws InterruptedException {
        WebDriver browser = getDriver();
        browser.get("https://www.vrbo.com/");

        WebElement tripBroad = browser.findElement(By.xpath("//span[contains(text(),'Trip Boards')]"));
        tripBroad.click();

        WebElement nameTrip = browser.findElement(By.xpath("//input[@id='create-tripboard-form__input__empty-tripboards']"));
        nameTrip.sendKeys("Fajardo, Puerto Rico");

        WebElement but = browser.findElement(By.xpath("//span[contains(text(),'Create Trip Board')]"));
        but.click();
        Thread.sleep(3000);

        WebElement name = browser.findElement(By.xpath("//h2[contains(text(),'Fajardo')]"));
        Assert.assertEquals(name.getText(), "Fajardo, Puerto Rico");
    }

    @Test
    public void romanSafarin() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://www.latimes.com/");

        WebElement name = browser.findElement(By.xpath("(//div//a[contains (@aria-label, 'COVID-19')])[1]"));
        Assert.assertEquals(name.getText(), "COVID-19");
    }
    @Test
    public void erikTest() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://www.limoxxi.com/");

        WebElement header = browser.findElement(By.xpath("(//div//h1[contains(text(),'Bay Area')])"));
        Assert.assertEquals(header.getText(), "Bay Area Limo Service");
    }

    @Test
    public void gordeyAppleProductsCheck() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://en.wikipedia.org/wiki/Timeline_of_Apple_Inc._products");

        WebElement name = browser.findElement(By.xpath("(//a[contains (text(), 'iPhone 12 Pro')])[1]"));
        Assert.assertEquals(name.getText(), "iPhone 12 Pro");
    }
}
