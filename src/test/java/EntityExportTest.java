import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

public class EntityExportTest extends BaseTest {


    @Test
    public void viewTest() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");
        ProjectUtils.loginProcedure(driver);
        WebDriverWait wait = new WebDriverWait(driver,4);

        WebElement exportButton = driver.findElement(By.xpath("//div[@id= 'menu-list-parent']/ul/li[8]/a"));
        ProjectUtils.click(driver, exportButton);

        WebElement createRecordButton = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createRecordButton.click();

        final String title = "TestV";
        final String text = "New Text";
        final int number = 10;
        final double number1 = 22.22;

        WebElement titleElement = driver.findElement(By.xpath("//input[@id= 'string']"));
        titleElement.sendKeys(title);
        WebElement textElement = driver.findElement(By.xpath("//textarea[@id= 'text']"));
        textElement.sendKeys(text);
        WebElement numberElement = driver.findElement(By.xpath("//input[@id= 'int']"));
        numberElement.sendKeys(String.valueOf(number));
        WebElement number1Element = driver.findElement(By.xpath("//input[@id= 'decimal']"));
        number1Element.sendKeys(String.valueOf(number1));

        WebElement dateElement = driver.findElement(By.xpath("//input[@id= 'date']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(dateElement).build().perform();
        dateElement.click();

        WebElement datetimeElement = driver.findElement(By.xpath("//input[@id= 'datetime']"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(datetimeElement).build().perform();
        datetimeElement.click();

        WebElement saveButton = driver.findElement(By.xpath("//button[@value='1']"));
        ProjectUtils.click(driver,saveButton);
        WebElement actionsButton = driver.findElement(By.xpath("//tbody/tr[1]/td[10]/div[1]/button[1]"));
        actionsButton.click();
        WebElement someLabelButton = driver.findElement(By.xpath("//tbody/tr[last()]//a[contains(text(), 'Some label')]"));
        ProjectUtils.click(driver, someLabelButton);
        WebElement save1Button = driver.findElement(By.xpath("//button[@value='1']"));
        ProjectUtils.click(driver,save1Button);

        //verify Entity:View tickets
        WebElement exportDistButton = driver.findElement(By.xpath("//div[@id= 'menu-list-parent']/ul/li[9]/a"));
        ProjectUtils.click(driver, exportDistButton);
        WebElement verifyView = driver.findElement(By.xpath("//div[contains(text(),'TestV')]"));
        Assert.assertEquals(verifyView.getText(), "TestV");
    }
}


