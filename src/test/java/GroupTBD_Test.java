import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.concurrent.TimeUnit;

public class GroupTBD_Test extends BaseTest {

    @Ignore
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

    @Test
    public void galinaMelnyk () {
        WebDriver driver = getDriver();
        driver.get("https://www.expedia.com/");

        driver.findElement(By.xpath("//*[@id=\"location-field-destination-menu\"]/div[1]/button")).click();

        WebElement searchField = driver.findElement((By.xpath("//*[@id=\"location-field-destination\"]")));
        searchField.sendKeys("San Francisco");
        searchField.sendKeys(Keys.ENTER);

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        driver.findElement(By.cssSelector(".uitk-layout-grid-item-columnspan-medium-2 > button")).click();

        WebElement goingToField = driver.findElement(By.xpath("//*[@id=\"app-layer-base\"]/div/div/div/div[1]/div/section/div/form/div[1]/div/div/button[1]"));
        Assert.assertEquals(goingToField.getText(), "San Francisco (and vicinity), California, United States of America");

    }
}
