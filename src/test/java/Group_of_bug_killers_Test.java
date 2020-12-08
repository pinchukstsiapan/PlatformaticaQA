import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class Group_of_bug_killers_Test extends BaseTest {

    @Test
    public void simpleTest() throws InterruptedException {

        WebDriver browser = getDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");
        WebElement name = browser.findElement(By.xpath("//strong/a"));

        Assert.assertEquals(name.getText(), "PlatformaticaQA");

        Thread.sleep(3000);
    }

    @Test
    public void secondTest() {

        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement button = driver.findElement(By.xpath("//details[contains(@data-action, 'get-repo')]"));
        button.click();

        WebElement input = driver.findElement(By.xpath("(//div[@class='input-group']/input)[1]"));
        Assert.assertEquals(input.getAttribute("value"), "https://github.com/SergeiDemyanenko/PlatformaticaQA.git");
    }

    @Test
    public void newTest() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");

        WebElement button = driver.findElement(By.id("branch-select-menu"));
        button.click();

        Thread.sleep(2000);

        WebElement link = driver.findElement(By.xpath("//footer/a[contains(text(), 'branches')]"));
        link.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://github.com/SergeiDemyanenko/PlatformaticaQA/branches");
    }

    @Test
    public void myTest() {

        WebDriver browser = getDriver();
        browser.get("https://github.com/SergeiDemyanenko/PlatformaticaQA");
        WebElement name = browser.findElement(By.xpath("//h2[contains(text(),'Languages')]"));

        Assert.assertEquals(name.getText(), "Languages");
    }


    @Ignore
    @Test
    public void aliakseiFranchuk () {
        WebDriver alexa = getDriver();
        alexa.get("https://www.tut.by/");

        WebElement searchField = alexa.findElement(By.xpath("//*[@id = 'search_from_str']"));
        searchField.sendKeys("Выборы август 2020");
        searchField.click();

        WebElement submitButton = alexa.findElement(By.xpath("//input[@class = 'button big']"));
        submitButton.click();

        Assert.assertEquals(alexa.getCurrentUrl(), "https://search.tut.by/?status=1&ru=1&encoding=1&page=0&how=rlv&query=%D0%92%D1%8B%D0%B1%D0%BE%D1%80%D1%8B+%D0%B0%D0%B2%D0%B3%D1%83%D1%81%D1%82+2020");

    }
}
