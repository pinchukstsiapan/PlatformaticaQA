import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupPacificQATeam extends BaseTest {

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
}
