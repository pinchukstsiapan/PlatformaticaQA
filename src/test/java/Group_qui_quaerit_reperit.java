import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;


public class Group_qui_quaerit_reperit extends BaseTest {

      @Test
      public void IrinaRizvanovaTest() throws InterruptedException{

          WebDriver browser = getDriver();
          browser.get("https://udemy.com");
          WebElement name = browser.findElement(By.xpath("//*[@name='q']"));
          name.sendKeys("Java");
          name.sendKeys(Keys.ENTER);

          Thread.sleep(3000);
          WebElement searchResult = browser.findElement(By.xpath("//h1"));
          String text = searchResult.getText();
          Assert.assertEquals(text,"10,000 results for “java”");



      }


}
