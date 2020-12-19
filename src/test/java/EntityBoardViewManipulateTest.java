import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.UUID;

public class EntityBoardViewManipulateTest extends BaseTest {

    @Ignore
    @Test
    public void manipulateTest() {

        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");

        WebElement board = driver.findElement(By.xpath("//*[@id='menu-list-parent']/ul/li[10]/a"));
        ProjectUtils.click(driver, board);

        WebElement newRecord = driver.findElement(By.xpath("//div/i[@class='material-icons']"));
        newRecord.click();

        final String title = UUID.randomUUID().toString();
        final int number = 380;

        WebElement textElement = driver.findElement(By.id("text"));
        textElement.sendKeys(title);
        WebElement numberElement = driver.findElement(By.id("int"));
        numberElement.sendKeys(String.valueOf(number));
        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        submit.click();

        WebElement from = driver.findElement(By.xpath("//*[@id='kanban']/div/div[1]/main/div[1]"));
        WebElement to = driver.findElement(By.xpath("//*[@id='kanban']/div/div[3]"));
        Actions act = new Actions(driver);
        act.dragAndDrop(from, to).build().perform();

        WebElement listMode = driver.findElement(By.xpath("//a[@href='index.php?action=action_list&list_type=table&entity_id=31']"));
        listMode.click();

        WebElement myElement = driver.findElement(By.xpath("//div[contains(text(),'" + title + "')]"));
        WebElement tdParent = myElement.findElement(By.xpath("./.."));

        WebElement tdWithStatus = tdParent.findElement(By.xpath("//*[@id='pa-all-entities-table']/tbody/tr/td[2]"));
        Assert.assertEquals(tdWithStatus.findElement(By.tagName("div")).getText(), "Done");
    }

}
