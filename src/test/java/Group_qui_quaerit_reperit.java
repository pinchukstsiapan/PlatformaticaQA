import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class Group_qui_quaerit_reperit extends BaseTest {

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
}
