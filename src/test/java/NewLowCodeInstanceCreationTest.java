import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class NewLowCodeInstanceCreationTest {

    final static String URL_PORTAL = "https://portal.platformatica.com";
    final static String LOGIN = "tester5@platformatica.net";
    final static String PASS_PORTAL = "FDiWVjFwmn";
    final static String NEW_INSTANCE_NAME = "test015";
    final static String NEW_USER_NAME = "admin";
    final static String URL_EMAIL = "http://mail.ionos.fr";
    final static String PASS_EMAIL = ".v#aTWtFD&Z5c9Z";

    @Ignore
    @Test
    public void createNewInstanceTest() throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(URL_PORTAL);
        Assert.assertEquals("Login", driver.getTitle());

        WebElement nameField = driver.findElement(By.xpath("//div//input[@type='text']"));
        nameField.sendKeys(LOGIN);
        WebElement passwordField = driver.findElement(By.xpath("//div//input[@type='password']"));
        passwordField.sendKeys(PASS_PORTAL);
        WebElement signInButton = driver.findElement(By.xpath("//button[@type='submit']"));
        signInButton.click();
        WebElement newInstance = driver.findElement(By.xpath("//div[@class='card-icon']"));
        newInstance.click();
        Thread.sleep(1000);
        WebElement newInstanceNameField = driver.findElement(By.xpath("//input[@id='name']"));
        newInstanceNameField.sendKeys(NEW_INSTANCE_NAME);
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        saveButton.click();
        WebElement message = driver.findElement(By.xpath("//h4[contains(text(), 'Pass')]/b"));
        String password = message.getText();
        System.out.println(password);
        driver.get("https://" + NEW_INSTANCE_NAME + ".eteam.work");
        WebElement nameField2 = driver.findElement(By.xpath("//div//input[@type='text']"));
        nameField2.sendKeys(NEW_USER_NAME);
        WebElement passwordField2 = driver.findElement(By.xpath("//div//input[@type='password']"));
        passwordField2.sendKeys(password);
        WebElement signInButton2 = driver.findElement(By.xpath("//button[@type='submit']"));
        signInButton2.click();
    }
}
