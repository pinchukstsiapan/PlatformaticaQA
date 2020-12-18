package old;

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

@Ignore
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

    @Test
    public void zedTransformaticaLoginUser4() throws InterruptedException {

        String url = "https://ref.eteam.work";
        String email = "user4@tester.com";
        String pass = "CoBX8ym0T7";
        String expected = "Tasks for User 4";

        WebDriver driver = getDriver();
        driver.get(url);
        Thread.sleep(800);

        driver.findElement(By.xpath("//*[contains(@placeholder,'Login name')]")).sendKeys(email);
        Thread.sleep(500);

        driver.findElement(By.xpath("//*[contains(@placeholder,'Password')]")).sendKeys(pass);
        Thread.sleep(500);

        driver.findElement(By.xpath("//*[@type='submit']")).click();
        Thread.sleep(800);

        driver.findElement(By.xpath("//p[contains(text(),'Assignments')]")).click();
        Thread.sleep(500);

        String actual = driver.findElement(By.xpath("//h3[contains(text(),'Tasks for ')]")).getText();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void maxBurdinNootropicsexpert() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://nootropicsexpert.com/");

        WebElement list = browser.findElement(By.xpath("//span[text() = 'List of Nootropics']"));
        list.click();

        WebElement ashwa = browser.findElement(By.xpath("//a[text() = 'Learn more about Ashwagandha']"));
        ashwa.click();

        WebElement book = browser.findElement(By.xpath("//a[text() = 'clicking here']"));
        book.click();

        Thread.sleep(1000);

        WebElement addToBag = browser.findElement(By.xpath("(//button[contains(@class, 'addToBag')])[1]"));
        addToBag.click();

        Thread.sleep(1000);

        Assert.assertEquals(browser.findElement(By.xpath("//span[contains(@class, 'total')]")).getText(), "$37.00");

    }

    @Test
    public void maxBurdinCodecademy() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://www.codecademy.com/");

        WebElement logIn = browser.findElement(By.xpath("(//a[@data-testid = 'header-sign-in'])[1]"));
        logIn.click();
        Thread.sleep(1000);

        WebElement userNameField = browser.findElement(By.xpath("//input[@id='user_login']"));
        userNameField.sendKeys("Test_User_123");

        WebElement passwordField = browser.findElement(By.xpath("//input[@id = 'login__user_password']"));
        passwordField.sendKeys("tEsTpA$$word");

        WebElement logInButton = browser.findElement(By.xpath("//button[@id = 'user_submit']"));
        logInButton.click();

        WebElement setTarget = browser.findElement(By.xpath("//a[@data-testid = 'set-target']"));
        setTarget.click();

        Assert.assertEquals(browser.getCurrentUrl(), "https://www.codecademy.com/account/goals_settings");
    }

    @Test
    public void nataliTverdohlibTask14() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");
        WebElement button = browser.findElement(By.xpath("//span[contains(text(), 'Pull request')]"));
        button.click();

        Thread.sleep(2000);

        Assert.assertEquals(browser.getCurrentUrl(), "https://github.com/SergeiDemyanenko/PlatformaticaQA/pulls");
    }

    @Test
    public void nataliaTaskE1() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");
        WebElement name = browser.findElement(By.xpath("//*[@data-tab-item='i0code-tab']"));

        Assert.assertEquals(name.getText(), "Code");
    }

    @Test
    public void nataliaTaskE2() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");
        WebElement name = browser.findElement(By.xpath("//*[@data-tab-item='i1issues-tab']"));

        Assert.assertEquals(name.getText(), "Issues");
    }

    @Test
    public void nataliaTaskE3() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");
        WebElement name = browser.findElement(By.xpath("//span[@data-content='Pull requests']"));

        Assert.assertEquals(name.getText(), "Pull requests");
    }

    @Test
    public void nataliaTaskE5() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");
        WebElement name = browser.findElement(By.xpath("//a[text()[normalize-space()='Star']]"));

        Assert.assertEquals(name.getText(), "Star");
    }

    @Test
    public void lenaGrinenTest() {

        WebDriver browser = getDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement elementCode = browser.findElement(By.xpath("//span[contains(text(),'Code')]"));
        WebElement elementIssues = browser.findElement(By.xpath("//span[contains(text(),'Issues')]"));
        WebElement elementPR = browser.findElement(By.xpath("//span[contains(text(),'Pull requests')]"));
        WebElement elementWatch = browser.findElement(By.xpath("//body/div[4]/div[1]/main[1]/div[1]/div[1]/ul[1]/li[1]/a[1]"));
        WebElement elementStar = browser.findElement(By.xpath("//span[contains(text(),'Star')]"));
        WebElement elementFork = browser.findElement(By.xpath("//body/div[4]/div[1]/main[1]/div[1]/div[1]/ul[1]/li[3]/a[1]"));

        Assert.assertEquals(elementCode.getText(), "Code");
        Assert.assertEquals(elementIssues.getText(), "Issues");
        Assert.assertTrue(elementPR.getText().contains("Pull requests"));
        Assert.assertEquals(elementWatch.getText(), "Watch");
        Assert.assertEquals(elementStar.getText(), "Star");
        Assert.assertTrue(elementFork.getText().contains("Fork"));

    }

    @Test
    public void vaheTest(){

        WebDriver driver = getDriver();

        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");
        WebElement elementCode = driver.findElement(By.xpath("//li[@class = 'd-flex']/a[@href = '/SergeiDemyanenko/PlatformaticaQA']"));
        WebElement elementIssues = driver.findElement(By.xpath("//li[@class = 'd-flex']/a[@href = '/SergeiDemyanenko/PlatformaticaQA/issues']"));
        WebElement elementPR = driver.findElement(By.xpath("//li[@class = 'd-flex']/a[@href = '/SergeiDemyanenko/PlatformaticaQA/pulls']"));

        WebElement elementWatch = driver.findElement(By.xpath("(//li/a[@href = '/login?return_to=%2FSergeiDemyanenko%2FPlatformaticaQA'])[1]"));
        WebElement elementStar = driver.findElement(By.xpath("(//li/a[@href = '/login?return_to=%2FSergeiDemyanenko%2FPlatformaticaQA'])[2]"));
        WebElement elementFork = driver.findElement(By.xpath("(//li/a[@href = '/login?return_to=%2FSergeiDemyanenko%2FPlatformaticaQA'])[3]"));

        Assert.assertEquals(elementCode.getText(), "Code");
        Assert.assertEquals(elementIssues.getText(), "Issues");
        Assert.assertTrue(elementPR.getText().contains("Pull requests"));

        Assert.assertEquals(elementWatch.getText(), "Watch");
        Assert.assertEquals(elementStar.getText(), "Star");
        Assert.assertEquals(elementFork.getText(), "Fork");

    }

    @Test
    public void getTest2() {
        WebDriver browser = getDriver();
        browser.get("https://www.toolexperts.com/home-inspection-tool-kit-deluxe-with-tool-bag.html?gclid=CjwKCAiAiML-BRAAEiwAuWVggsQWhIb1Vx-epi_iWKBW64c_WVekdNUsb3rVxOBVc6L8orx_IBLfNxoCocAQAvD_BwE");

        WebElement text = browser.findElement(By.xpath("//div/h1"));
        Assert.assertEquals(text.getText(), "Home Inspection Tool Kit - Deluxe");
    }
}

