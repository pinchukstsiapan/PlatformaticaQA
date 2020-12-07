import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class BraveQA extends BaseTest {

    @Test
    public void sergeoNevdah() throws InterruptedException {
        WebDriver browser = getDriver();
        browser.get("https://www.wikipedia.org");

        WebElement language = browser.findElement(By.xpath("//strong[contains(text(),'English')]"));
        Thread.sleep(2000);
        Assert.assertEquals(language.getText(), "English");
        language.click();
    }


    @Test
    public void dmitrySearch() throws InterruptedException {
        WebDriver browser = getDriver();
        browser.get("https://www.wikipedia.org");

        WebElement searchLang = browser.findElement(By.xpath("//select[@name='language']/option[@value='ru']"));
        searchLang.click();

        Thread.sleep(2000);

        Assert.assertEquals(searchLang.isSelected() , true);
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
    public void VictoriaRet() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://www.ebay.com/");

        WebElement shop = browser.findElement(By.xpath("//*[@id='gh-shop-a']"));
        Assert.assertTrue(shop.isDisplayed());

        WebElement search = browser.findElement(By.xpath("//*[@class='gh-tb ui-autocomplete-input']"));
        search.sendKeys("dress");
        WebElement searchButton = browser.findElement(By.xpath("//*[@id='gh-btn']"));
        searchButton.click();

        Thread.sleep(3000);

    }
}
