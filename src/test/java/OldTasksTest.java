import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class OldTasksTest extends BaseTest {

    @Ignore
    @Test
    public void denXPathTestForPulls() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        Thread.sleep(2000);
        WebElement button = driver.findElement(By.xpath("//a[normalize-space()='Sign in']"));
        button.click();
        WebElement userNameField = driver.findElement(By.xpath("//input[@id='login_field']"));
        userNameField.sendKeys("DenHubPy"); // input real User Name
        WebElement passField = driver.findElement(By.xpath("//input[@id='password']"));
        passField.sendKeys("****************"); // input real Password
        Thread.sleep(2000);
        WebElement signInButton = driver.findElement(By.xpath("//input[@value='Sign in']"));
        signInButton.click();

        WebElement pullRequestsButton = driver.findElement(By.xpath("//a[@href= '/pulls']"));
        pullRequestsButton.click();
    }

    @Ignore
    @Test
    public void denXPathTestForIssues() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        Thread.sleep(2000);
        WebElement button = driver.findElement(By.xpath("//a[normalize-space()='Sign in']"));
        button.click();
        WebElement userNameField = driver.findElement(By.xpath("//input[@id='login_field']"));
        userNameField.sendKeys("DenHubPy"); // input real User Name
        WebElement passField = driver.findElement(By.xpath("//input[@id='password']"));
        passField.sendKeys("****************"); // input real Password
        Thread.sleep(2000);
        WebElement signInButton = driver.findElement(By.xpath("//input[@value='Sign in']"));
        signInButton.click();

        WebElement issuesButton = driver.findElement(By.xpath("//a[@href= '/issues']"));
        issuesButton.click();
    }

    @Ignore
    @Test
    public void denXPathTestForMarketplace() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        Thread.sleep(2000);
        WebElement button = driver.findElement(By.xpath("//a[normalize-space()='Sign in']"));
        button.click();
        WebElement userNameField = driver.findElement(By.xpath("//input[@id='login_field']"));
        userNameField.sendKeys("DenHubPy"); // input real User Name
        WebElement passField = driver.findElement(By.xpath("//input[@id='password']"));
        passField.sendKeys("****************"); // input real Password
        Thread.sleep(2000);
        WebElement signInButton = driver.findElement(By.xpath("//input[@value='Sign in']"));
        signInButton.click();

        WebElement marketplaceButton = driver.findElement(By.xpath("//a[@href= '/marketplace']"));
        marketplaceButton.click();
    }

    @Ignore
    @Test
    public void denXPathTestForNotification() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        Thread.sleep(2000);
        WebElement button = driver.findElement(By.xpath("//a[normalize-space()='Sign in']"));
        button.click();
        WebElement userNameField = driver.findElement(By.xpath("//input[@id='login_field']"));
        userNameField.sendKeys("DenHubPy"); // input real User Name
        WebElement passField = driver.findElement(By.xpath("//input[@id='password']"));
        passField.sendKeys("****************"); // input real Password

        WebElement signInButton = driver.findElement(By.xpath("//input[@value='Sign in']"));
        signInButton.click();

        WebElement notificationButton = driver.findElement(By.xpath("//summary[contains(@class, 'rounded')]"));
        notificationButton.click();

        WebElement unwatchButton = driver.findElement(By.xpath("//notifications-list-subscription-form[contains(@class,'d-flex')]//button[@value='subscribed']"));
        unwatchButton.click();
    }
}