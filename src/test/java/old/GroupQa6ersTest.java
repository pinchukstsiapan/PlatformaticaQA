package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

@Ignore
public class GroupQa6ersTest extends BaseTest {

    @Test
    public void rinsal() {
        WebDriver driver = getDriver();
        driver.get("https://en.wikipedia.org/wiki/Selenium");
        WebElement text = driver.findElement(By.xpath("//div/a[@title='Isotopes of selenium']"));
        Assert.assertEquals(text.getText(), "Isotopes of selenium");
    }

    @Test
    public void polinaBeylina() {

        WebDriver browser = getDriver();
        browser.get("https://www.performance-lab.ru/");

        WebElement downloadTab = browser.findElement(By.xpath("//a[@href='/blog']"));
        downloadTab.click();

        WebElement linkToArticle = browser.findElement(By.xpath("//a[@href='https://www.performance-lab.ru/blog/functional-testing/kak-vyyavit-oshibki-sajta-za-1-den']"));
        linkToArticle.click();

        WebElement title = browser.findElement(By.xpath("//h1[1]"));
        Assert.assertEquals(title.getText(), "Как выявить ошибки сайта за 1 день?");
    }
}