import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import static org.testng.Assert.assertEquals;

public class GroupBreakingBad_Test extends BaseTest {
    @Test
    public void nataliaVats() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://www.aerotek.com/en/");

        WebElement button = driver.findElement(By.xpath("(//button[contains(text(), 'SEARCH')])[2]"));
        button.click();
        Thread.sleep(3000);

        WebElement input = driver.findElement(By.xpath("//input[contains(@class, 'ph-a11y-location-box')]"));
        assertEquals(input.getAttribute("placeholder"), "Enter City, State or Zip");
    }

    @Test
    public void alexeySemenov() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://www.jizo.com");

        WebElement inputSearch = browser.findElement(By.xpath("//input[@id='searchCity']"));
        inputSearch.sendKeys("Rus");

        Thread.sleep(3000);

        WebElement menuElement = browser.findElement(By.xpath("//*[@id='CityList']/div[1]/div[2]/h4"));
        assertEquals(menuElement.getText().toLowerCase(), "russia");

    }

    @Test
    public void svitlanaVarakuta() {

        WebDriver driver = getDriver();
        driver.get("https://en.wikipedia.org/wiki/List_of_national_parks_of_the_United_States");

        WebElement link = driver.findElement(By.xpath("//div[5]/div[1]/div[2]/nav[1]/div/ul/li[3]/a"));
        link.click();

        assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/w/index.php?title=List_of_national_parks_of_the_United_States&action=history");

    }

    @Test
    public void searchFieldTestPK() throws InterruptedException {

        WebDriver driver = getDriver();
        Actions actions = new Actions(driver);
        driver.get("https://www.wikipedia.org");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//strong[contains(text(),'English')]")).click();
        WebElement search = driver.findElement(By.xpath("//input[@id='searchInput']"));

        actions.moveToElement(search).sendKeys("Selenium WebDriver")
                .sendKeys(Keys.ENTER)
                .build().perform();
        driver.navigate().refresh();
        Thread.sleep(2000);

        assertEquals(getDriver().getTitle(), "Wikipedia, the free encyclopedia");

    }


}
