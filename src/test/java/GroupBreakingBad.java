import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupBreakingBad extends BaseTest {

    @Test
    public void tatyanaPusFirstTest() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://3ddd.ru/");

        WebElement button = driver.findElement(By.xpath("//*[text()='3D Модели']"));
        button.click();

        Thread.sleep(2000);

        Assert.assertEquals(driver.getCurrentUrl(), "https://3ddd.ru/3dmodels");
    }

    @Test
    public void tatyanaPusSecondTest() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://3ddd.ru/");

        WebElement button = driver.findElement(By.xpath("//a[@href = '/login']"));
        button.click();

        Thread.sleep(2000);

        Assert.assertEquals(driver.getCurrentUrl(), "https://3ddd.ru/login");
    }

}
