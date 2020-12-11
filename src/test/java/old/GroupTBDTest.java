package old;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.concurrent.TimeUnit;

@Ignore
public class GroupTBDTest extends BaseTest {

    @Ignore
    @Test
    public void vadimTymeichuk () throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement button = driver.findElement(By.xpath("//div/details/*[@class = 'btn css-truncate']"));
        button.click();
        Thread.sleep(4000);

        WebElement button1 = driver.findElement(By.xpath("//*[@data-filter-placeholder = 'Find a tag']"));
        button1.click();
        Thread.sleep(4000);

        WebElement link = driver.findElement(By.linkText("View all tags"));
        link.click();

        WebElement text = driver.findElement(By.xpath("//div//h3[@class = 'mb-1']"));
        Assert.assertEquals(text.getText(), "There aren’t any releases here");
    }

    @Ignore
    @Test
    public void galinaMelnyk () throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://www.expedia.com/");

        driver.findElement(By.xpath("//*[@id=\"location-field-destination-menu\"]/div[1]/button")).click();

        WebElement searchField = driver.findElement((By.xpath("//*[@id=\"location-field-destination\"]")));
        searchField.sendKeys("San Francisco");
        searchField.sendKeys(Keys.ENTER);
        Thread.sleep(4000);

        driver.findElement(By.cssSelector(".uitk-layout-grid-item-columnspan-medium-2 > button")).click();

        WebElement goingToField = driver.findElement(By.xpath("//*[@id=\"app-layer-base\"]/div/div/div/div[1]/div/section/div/form/div[1]/div/div/button[1]"));
        Assert.assertEquals(goingToField.getText(), "San Francisco (and vicinity), California, United States of America");
    }
}
