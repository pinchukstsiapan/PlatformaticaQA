import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.UUID;

public class EntityBoardViewManipulateTest extends BaseTest {

    final String title = UUID.randomUUID().toString();
    final int number = 380;

    private void createNewRecord() {

        WebDriver driver = getDriver();
        WebElement newRecord = driver.findElement(By.xpath("//div/i[@class='material-icons']"));
        newRecord.click();
        WebElement textElement = driver.findElement(By.id("text"));
        textElement.sendKeys(title);
        WebElement numberElement = driver.findElement(By.id("int"));
        numberElement.sendKeys(String.valueOf(number));
        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, submit);
    }

    @Test
    public void ManipulateTest() {

        WebDriver driver = getDriver();

        forwardManipulate(driver);

        backwardManipulate(driver);

    }

    private void forwardManipulate(WebDriver driver) {

        WebElement board = driver.findElement(By.xpath("//div[@id='menu-list-parent']/ul/li[10]/a"));
        ProjectUtils.click(driver, board);

        createNewRecord();

        WebElement createdRecord = driver.findElement(By.xpath("//div[contains(text(),'" + title + "')]"));

        WebElement from = createdRecord.findElement(By.xpath("./.."));
        WebElement to = driver.findElement(By.xpath("//div[3]/main[@class='kanban-drag']"));
        Actions act = new Actions(driver);
        act.dragAndDrop(from, to).build().perform();

        driver.findElement(By.xpath("//ul[@role='tablist']/li[2]/a")).click();

        WebElement myElement = driver.findElement(By.xpath("//div[contains(text(),'" + title + "')]"));
        WebElement trParent = myElement.findElement(By.xpath("./.."));
        WebElement tdWithStatus = trParent.findElement(By.xpath("//table/tbody/tr/td[2]"));
        Assert.assertEquals(tdWithStatus.findElement(By.tagName("div")).getText(), "Done");
    }

    private void backwardManipulate(WebDriver driver) {

        driver.findElement(By.xpath("//ul[@role='tablist']/li[1]/a")).click();

        WebElement elementStatus = driver.findElement(By.xpath("//main[@class='kanban-drag']//div[contains(text(),'Done')]"));

        WebElement from = elementStatus.findElement(By.xpath("./.."));
        WebElement to = driver.findElement(By.xpath("//div[2]/main[@class='kanban-drag']"));
        Actions act = new Actions(driver);
        act.dragAndDrop(from, to).build().perform();

        driver.findElement(By.xpath("//ul[@role='tablist']/li[2]/a")).click();

        WebElement myElement = driver.findElement(By.xpath("//div[contains(text(),'" + title + "')]"));
        WebElement trParent = myElement.findElement(By.xpath("./.."));
        WebElement tdWithStatus = trParent.findElement(By.xpath("//table/tbody/tr/td[2]"));
        Assert.assertEquals(tdWithStatus.findElement(By.tagName("div")).getText(), "On track");
    }

}
