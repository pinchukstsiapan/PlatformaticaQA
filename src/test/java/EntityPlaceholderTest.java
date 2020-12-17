import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.UUID;

public class EntityPlaceholderTest extends BaseTest {

    @Test
    public void inputTest() throws InterruptedException {

        WebDriver driver = ProjectUtils.loginProcedure(getDriver());

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Placeholder')]/preceding-sibling::i"));
        ProjectUtils.click(driver, tab);
        WebElement icon = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        ProjectUtils.click(driver,icon);

        final String title = UUID.randomUUID().toString();
        final String comments = "my pretty simple text";
        final int number = 37;
        final double value = 18.73;

        WebElement titleElement = driver.findElement(By.xpath("//input[@placeholder='String placeholder']"));
        titleElement.sendKeys(title);
        WebElement commentsElement= driver.findElement(By.xpath("//textarea[@placeholder='Text placeholder']"));
        commentsElement.sendKeys((comments));
        WebElement numberElement = driver.findElement(By.xpath("//input[@id='int']"));
        numberElement.sendKeys(String.valueOf(number));
        WebElement valueElement = driver.findElement(By.xpath("//input[@id='decimal']"));
        valueElement.sendKeys(String.valueOf(value));
        WebElement userSelection = driver.findElement(By.xpath("//div[contains(text(),'Demo')]"));
        ProjectUtils.click(driver,userSelection);
        WebElement newUser= driver.findElement(By.xpath("//span[text()='User 2']"));
        ProjectUtils.click(driver,newUser);

        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver,submit);

        String recordTitleXpath = String.format("//div[contains(text(), '%s')]", title);
        By newRecordComments = By.xpath(String.format(" %s/../../../td[3]/a/div", recordTitleXpath));
        By newRecordInt = By.xpath(String.format(" %s/../../../td[4]/a/div", recordTitleXpath));
        By newRecordVal = By.xpath(String.format(" %s/../../../td[5]/a/div", recordTitleXpath));
        WebElement createdRecordComments = driver.findElement(newRecordComments);
        WebElement createdRecordInt = driver.findElement(newRecordInt);
        WebElement createdRecordVal = driver.findElement(newRecordVal);

        Assert.assertEquals(createdRecordComments.getText(),comments);
        Assert.assertEquals(createdRecordInt.getText(),Integer.toString(number));
        Assert.assertEquals(createdRecordVal.getText(),Double.toString(value));
    }
}
