import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class GroupJavaBears extends BaseTest {

    @Test
    public void victoriaRogozhkina() {

        WebDriver driver = getDriver();
        driver.get("https://developer.mozilla.org/en-US/docs/Web/XPath/Functions");

        WebElement link = driver.findElement(By.xpath("//li/a[contains(text(), 'contains()')]"));
        link.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://developer.mozilla.org/en-US/docs/Web/XPath/Functions/contains");
    }
    @Test
    public void Alex_Mack() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://en.wikipedia.org/wiki/Golden_Gate");

        WebElement title = driver.findElement(By.xpath("//span[@id='History']"));
        Assert.assertEquals(title.getText(), "History");
    }

    @Test
    public void Alex_Mack2() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://dota2.ru/");

        WebElement base = driver.findElement(By.xpath("//header//ul//a[contains(text(), 'База')]"));
        base.click();

        WebElement heroes = driver.findElement(By.xpath("//header//ul//a[contains(text(), 'Герои')]"));
        heroes.click();
        Thread.sleep(2000);

        WebElement riki = driver.findElement(By.xpath("//div//a[contains(@href, '/heroes/riki')]"));
        riki.click();
        Thread.sleep(2000);

        WebElement guides = driver.findElement(By.xpath("//h2//a[contains(text(), 'Гайды по герою')]"));
        guides.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://dota2.ru/guides/riki/");
    }
}
