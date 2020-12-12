package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

@Ignore
public class GroupEasternTest extends BaseTest {

    @Ignore
    @Test
    public void viktoriiaPrudka() {

        WebDriver driver = getDriver();
        driver.get("https://wagwalking.com/");

        WebElement button = driver.findElement(By.xpath("//p[text()='Book a walk']"));
        button.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://app.wagwalking.com/signup/welcome?preferred_" +
                "service=1&utm_campaign=service_carousel_walking&utm_medium=homepage&utm_source=web");
    }

    @Ignore
    @Test
    public void irinaKalinichenko() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://www.underarmour.com/en-us/");

        WebElement text = driver.findElement(By.xpath("//span[text()='Knock out your gift list with FREE SHIPPING, orders $60+.']"));
        text.click();
        //  Let us help you find the perfect gift — without getting up.
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.underarmour.com/en-us/c/top-gifts/");
    }

    @Test
    public void olgaLytvynova(){
        WebDriver driver = getDriver();
        driver.get("https://www.hoteltonight.com/");

        WebElement learnMore = driver.findElement(By.xpath("//span[contains(text(),'Learn more.')]"));
        learnMore.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.hoteltonight.com/extenuating-circumstances");
    }

    @Test
    public void gennadyGOR(){
        WebDriver driver = getDriver();
        driver.get("https://webdriver.io/docs/docs/wdio-slack-service.html");
        WebElement button = driver.findElement(By.xpath("//a[@class='edit-page-link button']"));
        Assert.assertEquals(button.getText(), "EDIT");
    }

    @Test
    public void KatyaKishko(){
        WebDriver browser = getDriver();
        browser.get("https://www.target.com/");
        WebElement icon = browser.findElement(By.xpath("//span[@class='styles__AccountName-sc-1kk0q5l-0 hVhJPq']"));
        Assert.assertEquals(icon.getText(), "Sign in");
    }
}
