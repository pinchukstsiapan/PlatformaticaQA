import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class Group_qui_quaerit_reperit_Test extends BaseTest {

    @Test
    public void galinaRuban() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://codepen.io/");
        WebElement signUpBtn = browser.findElement(By.xpath("//a/span[.='Sign Up for Free']"));
        signUpBtn.click();
        WebElement h1 = browser.findElement(By.xpath("//header/p"));
        Assert.assertEquals(h1.getText(), "Welcome to CodePen.");
        WebElement fbSocialSignUp = browser.findElement(By.xpath("//a[@id='login-with-facebook']"));
        Assert.assertEquals(fbSocialSignUp.isDisplayed(), true);
    }

    @Test
    public void oleksandrBurdeinyi() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://www.selenium.dev/");

        WebElement gettingStarted = browser.findElement(By.xpath("//h2[.='Getting Started']"));
        Assert.assertEquals(gettingStarted.getText(), "Getting Started");

    }

    @Ignore
    @Test
    public void stanAretinskiy() {
        WebDriver driver = getDriver();

        // Open US UPS default page
        driver.get("https://www.ups.com/us/en");

        // Click 'Schedule a Pickup' link
        WebElement schedulePickupLink =
                driver.findElement(By.xpath("//a[@class='ups-analytics'][contains(@href, 'pickup.page')]"));
        schedulePickupLink.click();

        // Verify url for 'UPS Pickup Options' page
        String pickupUrl = driver.getCurrentUrl();
        Assert.assertTrue(pickupUrl.contains("www.ups.com/us/en/shipping/services/pickup.page?WT.mc_id="));

        // Verify page title
        WebElement pageHeading = driver.findElement(By.cssSelector("h1"));
        Assert.assertEquals(pageHeading.getText(), "UPS Pickup Options");

        // Verify two article headers
        List<WebElement> articleHeadersLstWe = driver.findElements(By.cssSelector("h2.ups-article-header"));
        List<String> articleHeadersLstStr = new ArrayList<>();
        for(WebElement element : articleHeadersLstWe) {articleHeadersLstStr.add(element.getText());}
        Assert.assertTrue(articleHeadersLstStr.contains("Request a UPS pickup at your business or home"));
        Assert.assertTrue(articleHeadersLstStr.contains("Pick up your packages at thousands of convenient locations"));
    }

    @Test
    public void seidBayramov() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://www.target.com/");
        driver.manage().timeouts().implicitlyWait(1500, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("input[id='search']")).sendKeys("Water");
        driver.findElement(By.cssSelector("button[class='SearchInputButton-sc-1opoijs-0 dvBrqq']")).click();
        driver.manage().timeouts().implicitlyWait(1500, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//div[text()='purified waters']")).click();
        driver.findElement(By.cssSelector("#home")).click();
        Thread.sleep(3000);
    }

    @Ignore
    @Test
    public  void stanMaslov() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://www.walmart.com/");
        WebElement header =  driver.findElement(By.cssSelector("h1"));
        Assert.assertEquals(header.getText(), "Walmart.com - Save Money. Live Better.");
        WebElement logo = driver.findElement(By.cssSelector("[class=\"z_a\"]"));
        Assert.assertEquals(logo.isDisplayed(),true);
    }
}
