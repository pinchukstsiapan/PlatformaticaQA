import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.concurrent.TimeUnit;

public class GroupTBD extends BaseTest {

    @Test
    public void vadimTymeichuk () {

        WebDriver driver = getDriver();

        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement button = driver.findElement(By.xpath("//div/details/*[@class = 'btn css-truncate']"));
        button.click();

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        WebElement button1 = driver.findElement(By.xpath("//*[@data-filter-placeholder = 'Find a tag']"));
        button1.click();

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        WebElement link = driver.findElement(By.linkText("View all tags"));
        link.click();

        WebElement text = driver.findElement(By.xpath("//div//h3[@class = 'mb-1']"));
        Assert.assertEquals(text.getText(), "There aren’t any releases here");
    }
}
