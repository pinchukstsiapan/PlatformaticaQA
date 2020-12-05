import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupSfbaGrizzlies extends BaseTest {

    @Test
    public void klavdiaGoldshteyn() throws InterruptedException {
       WebDriver browser = getDriver();
       browser.get("https://en.wikipedia.org/wiki/Roger_Federer");
       WebElement title = browser.findElement(By.xpath("//h1[@id='firstHeading']"));

       Assert.assertEquals(title.getText(), "Roger Federer");

       Thread.sleep(3000);
   }
}
