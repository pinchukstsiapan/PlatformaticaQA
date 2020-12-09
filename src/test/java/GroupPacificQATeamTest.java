import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.annotations.TestInstance;
import runner.BaseTest;

import java.rmi.registry.Registry;
import java.security.Signature;

public class GroupPacificQATeamTest extends BaseTest {

    @Test
    public void alexanderKazakov() throws InterruptedException {
        WebDriver browser = getDriver();
        browser.get("https://meduza.io");

        WebElement auth = browser.findElement(By.xpath("//button[contains(@class, 'Auth')]"));
        Assert.assertEquals(auth.getText().toLowerCase(), "войти");
        auth.click();
        Thread.sleep(2000);

        WebElement loginWithGoogle = browser.findElement(By.xpath("//div[@class='Modal-content']//button[contains(text(), 'Google')]"));
        Assert.assertEquals("войти через google", loginWithGoogle.getText().toLowerCase());
    }

    @Ignore
    @Test
    public void VeraK() throws InterruptedException {
        /*
            Shop at Samsung.com store for a new Samsung Galaxy A51 unlocked and with no trade-in. Stop at cart
         */
        WebDriver browser = getDriver();
        browser.get("https://www.samsung.com/us/");

        browser.findElement(By.xpath("//a[@class='gnb-promo-close']")).click();
        browser.findElement(By.linkText("Accept")).click();

        Actions actions = new Actions(browser);
        WebElement mobile = browser.findElement(By.xpath("//a[@data-engname='mobile']"));
        actions.moveToElement(mobile).perform();

        Thread.sleep(3000);
        WebElement smartphones = browser.findElement(By.xpath("//span[contains(text(),'Smartphones')]"));
        actions.moveToElement(smartphones).perform();

        Thread.sleep(500);
        WebElement galaxyA = browser.findElement(By.xpath("//a[@href='https://www.samsung.com/us/mobile/phones/galaxy-a/']"));
        actions.moveToElement(galaxyA).click().perform();

        Thread.sleep(1500);
        WebElement galaxyA51 = browser.findElement(By.xpath("//a[contains(@href, '/galaxy-a/galaxy-a51-unlocked-sm')]"));
        JavascriptExecutor js = ((JavascriptExecutor) browser);
        js.executeScript("arguments[0].scrollIntoView();", galaxyA51);
        actions.moveToElement(galaxyA51).click().perform();

        Assert.assertEquals(browser.findElement(By.xpath("//h1[@class='product-details__info-title']")).getText(), "Galaxy A51 (Unlocked)");
        Assert.assertNotNull(browser.findElement(By.xpath("//a[@data-current-selected='Galaxy A51']")));
        Assert.assertNotNull(browser.findElement(By.xpath("//a[@data-current-selected='Unlocked']")));

        WebElement buy = browser.findElement(By.xpath("//a[contains(@href, 'galaxy-a/buy/')]"));
        js.executeScript("arguments[0].scrollIntoView();", buy);
        buy.click();

        Thread.sleep(1000);
        Assert.assertEquals(browser.findElement(By.xpath("//div[@id='galaxy-a51' and contains(@class, 'selected')]//span[@class='price']")).getText(), "From $164.99ᶿ");
        Assert.assertEquals(browser.findElement(By.xpath("//div[@id='unlocked' and contains(@class, 'selected')]//span[@class='price']")).getText(), "From $164.99ᶿ");
        WebElement tradeIn = browser.findElement(By.xpath("//div[@id='tradeinOptionNo']/span"));
        JavascriptExecutor js2 = ((JavascriptExecutor) browser);
        js2.executeScript("arguments[0].scrollIntoView(true);", tradeIn);
        Thread.sleep(2000);
        actions.moveToElement(tradeIn).click().perform();

        WebElement buttonContinue = browser.findElement(By.xpath("//div[@id='homeCTA']/div/div/div"));
        Thread.sleep(1500);
        js2.executeScript("arguments[0].scrollIntoView();", buttonContinue);
        actions.moveToElement(buttonContinue).click().perform();

        browser.findElement(By.xpath("//a[@class='skip']")).click();

        Assert.assertEquals(browser.findElement(By.xpath("//div[@class='estimated-ship-holder']//p[@class='os-price-value']")).getText(), "FREE");
        Assert.assertEquals(browser.findElement(By.xpath("//div[contains(@class, 'total-row')]//p[@class='os-price-value']")).getText(), "$399.99");
        Assert.assertEquals(browser.findElement(By.xpath("//div[contains(@class, 'total-savings-row')]//p[@class='os-price-value']")).getText(), "$24.00");
        Assert.assertEquals(browser.findElement(By.xpath("//div[@class='os-price-holder']/div[2]/p[2]")).getText(), "$399.99");
    }

    @Test
    public void liliyaDan() throws InterruptedException {
        WebDriver browser = getDriver();
        browser.get("https://www.etsy.com/");

        WebElement signIn = browser.findElement(By.xpath("//nav//button"));
        signIn.click();
        Thread.sleep(1500);

        WebElement Register = browser.findElement(By.xpath("//div[@id='wt-modal-container']//button[contains(text(), 'Register')]"));
        Register.click();
        Thread.sleep(1500);

        Assert.assertEquals(browser.findElement(By.xpath("//h1[@id='join-neu-overlay-title']")).getText(), "Create your account");
    }

    @Test
    public void gulyaFirstTest() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://www.udemy.com/");
        Thread.sleep(2000);

        WebElement button = driver.findElement(By.xpath("//a[@data-purpose='header-login']"));
        button.click();
        Thread.sleep(2000);

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.udemy.com/join/login-popup/?locale=en_US&response_type=html&next=https%3A%2F%2Fwww.udemy.com%2F");
    }
}

