package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

@Ignore
public class GroupOfBugKillersTest extends BaseTest {

    @Test
    public void simpleTest() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");
        WebElement name = browser.findElement(By.xpath("//strong/a"));

        Assert.assertEquals(name.getText(), "PlatformaticaQA");
    }

    @Test
    public void secondTest() {

        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement button = driver.findElement(By.xpath("//details[contains(@data-action, 'get-repo')]"));
        button.click();

        WebElement input = driver.findElement(By.xpath("(//div[@class='input-group']/input)[1]"));
        Assert.assertEquals(input.getAttribute("value"), "https://github.com/SergeiDemyanenko/PlatformaticaQA.git");
    }

    @Test
    public void lenaHW13Test() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://coderoad.ru/48259191/%D0%92-Selenium-%D0%BA%D0%B0%D0%BA-%D0%BF%D1%80%D0%BE%D0%B2%D0%B5%D1%80%D0%B8%D1%82%D1%8C-%D1%87%D1%82%D0%BE-%D0%BF%D1%80%D0%B8-%D0%BD%D0%B0%D0%B6%D0%B0%D1%82%D0%B8%D0%B8-%D0%BD%D0%B0-%D1%8D%D0%BB%D0%B5%D0%BC%D0%B5%D0%BD%D1%82-%D0%BC%D1%8B-%D1%84%D0%B0%D0%BA%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8-%D0%BF%D0%B5%D1%80%D0%B5%D1%88%D0%BB%D0%B8-%D0%BD%D0%B0");
        WebElement name = browser.findElement(By.xpath("//a[contains(text(),'О нас')]"));
        Assert.assertEquals(name.getText(), "О нас");
        name.click();

        Assert.assertEquals(browser.getCurrentUrl(), "https://coderoad.ru/about.html");
    }

    @Test
    public void newTest() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement button = driver.findElement(By.id("branch-select-menu"));
        button.click();
        Thread.sleep(2000);

        WebElement link = driver.findElement(By.xpath("//footer/a[contains(text(), 'branches')]"));
        link.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://github.com/SergeiDemyanenko/PlatformaticaQA/branches");
    }

    @Test
    public void vladBezpalko() {

        WebDriver browser = getDriver();
        browser.get("https://office.ooma.com/");
        WebElement phone_field = browser.findElement(By.id("phone-label"));
        WebElement password_field = browser.findElement(By.xpath("//*[@id='password']/../*[@class='input-label']"));

        Assert.assertEquals(phone_field.getText(), "ENTER PHONE");
        Assert.assertEquals(password_field.getText(), "ENTER PASSWORD");
    }

    @Test
    public void panteleyeva01() {

        WebDriver browser = getDriver();
        browser.get("https://www.colibribookstore.com/");
        WebElement webLabelName = browser.findElement(By.xpath("//img[@class='large']"));
        Assert.assertTrue(webLabelName.isDisplayed());
    }

    @Test
    public void viewHistoryWikipedia() {

        WebDriver browser = getDriver();
        browser.get("https://en.wikipedia.org/wiki/Main_Page");
        WebElement name = browser.findElement(By.id("ca-history"));

        Assert.assertEquals(name.getText(), "View history");
    }

    @Test
    public void clickBtnViewHistoryWikipedia() {

        WebDriver browser = getDriver();
        browser.get("https://en.wikipedia.org/wiki/Main_Page");
        WebElement name = browser.findElement(By.xpath("//*[@id='ca-history']/a"));
        name.click();
        WebElement firstHeading = browser.findElement(By.xpath("//*[@id='firstHeading']"));

        Assert.assertEquals(firstHeading.getText(), "Main Page: Revision history");
    }

    @Test
    public void svetlanaGusachenkoTest() {

        WebDriver driver = getDriver();
        driver.get("https://en.wikipedia.org/wiki/Main_Page");

        WebElement link = driver.findElement(By.id("n-portal"));
        Assert.assertEquals(link.getText(), "Community portal");

        link.click();
        WebElement name = driver.findElement(By.xpath("//*[@id=\"Welcome_to_the_Community_portal!\"]"));
        Assert.assertEquals(name.getText(), "Welcome to the Community portal!");
    }

    @Test
    public void svetlanaGusachenkoTest1() {

        WebDriver driver = getDriver();
        driver.get("https://www.hccts.org/");

        WebElement link = driver.findElement(By.xpath("//span[contains(text(),'Register')]"));
        Assert.assertEquals(link.getText(), "Register Now!");
    }

    @Test
    public void denTest() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://www.udemy.com/");
        Thread.sleep(1100);

        WebElement search = driver.findElement(By.xpath("//input[@placeholder='Search for anything']"));
        search.sendKeys("Java");

        WebElement text = driver.findElement(By.xpath("//label[contains(text(), 'Search')]"));
        Assert.assertEquals(text.getText(), "Search for anything");
        Assert.assertEquals("Online Courses - Learn Anything, On Your Schedule | Udemy", driver.getTitle());

        WebElement submit = driver.findElement(By
                .xpath("//input[@placeholder='Search for anything']/../button[@type='submit']"));
        submit.click();
        Thread.sleep(1000);

        WebElement textResult = driver.findElement(By.xpath("//h1[contains(text(), '10,000 results for “java”')]"));
        Assert.assertEquals(textResult.getText(), "10,000 results for “java”");
    }
}