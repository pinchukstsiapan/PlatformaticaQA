import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import org.openqa.selenium.JavascriptExecutor;

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

    @Test
    public void tatyanaPusThirdTest() throws InterruptedException {

        WebDriver driver = getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://3ddd.ru/");

        WebElement button = driver.findElement(By.xpath("//*[text()='3D Модели']"));
        button.click();
        Thread.sleep(1000);

        WebElement link = driver.findElement(By.xpath("//div[@class='cat not-real-a']"));
        link.click();
        Thread.sleep(1000);

        WebElement checkmark = driver.findElement(By.xpath("//*[@id='collapse-0']/ul/li[1]/div"));
        checkmark.click();
        Thread.sleep(1000);

        //This will scroll the web page till end.
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        //Find element by xpath and store in variable "Element"
        WebElement Element = driver.findElement(By.xpath("//a[@class='sky-btn']"));


        //This will scroll the page till the element is found
        Thread.sleep(3000);
        Element.click();

        Thread.sleep(3000);

        Assert.assertEquals(driver.getCurrentUrl(), "https://3ddd.ru/3dmodels?cat=dekor&subcat=3d_panel&page=2");
    }

}
