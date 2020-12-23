import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

public class EntityDefaultEditTest extends BaseTest {

    @Test
    public void defaultEdit() {

        WebDriver driver = getDriver();

        WebElement entities = driver.findElement(By.xpath("//p[contains(text(),'Entities')]"));
        entities.click();
        WebElement defaultMenu = driver.findElement(By.xpath("//p[contains(text(),'Default')]"));
        defaultMenu.click();
        WebElement createNewfolder = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createNewfolder.click();

        WebElement inputString = driver.findElement(By.xpath("//input[@id='string']"));
        inputString.clear();
        inputString.sendKeys("edited default string value");

        WebElement inputText = driver.findElement(By.xpath("//textarea[@id='text']"));
        inputText.clear();
        inputText.sendKeys("edited default text value");

        WebElement inputInt = driver.findElement(By.xpath("//input[@id='int']"));
        inputInt.clear();
        inputInt.sendKeys("34");

        WebElement inputDecimal = driver.findElement(By.xpath("//input[@id='decimal']"));
        inputDecimal.clear();
        inputDecimal.sendKeys("110.54");

        WebElement inputDate = driver.findElement(By.xpath("//input[@id='date']"));
        inputDate.clear();
        inputDate.sendKeys("12/12/2020");

        WebElement inputDateTime = driver.findElement(By.xpath("//input[@id='datetime']"));
        inputDateTime.clear();
        inputDateTime.sendKeys("21/11/2020 13:59:00");

        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);

        WebElement recordMenu = driver.findElement(By.xpath("//i[.='menu']"));
        recordMenu.click();

        WebElement editFunction = driver.findElement(By.xpath("//a[text()='edit']"));
        ProjectUtils.click(driver, editFunction);

        inputString = driver.findElement(By.xpath("//input[@id='string']"));
        Assert.assertEquals(inputString.getAttribute("value"), "edited default string value");

        inputText = driver.findElement(By.xpath("//textarea[@id='text']"));
        Assert.assertEquals(inputText.getAttribute("value"), "edited default text value");

        inputInt = driver.findElement(By.xpath("//input[@id='int']"));
        Assert.assertEquals(inputInt.getAttribute("value"), "34");

        inputDecimal = driver.findElement(By.xpath("//input[@id='decimal']"));
        Assert.assertEquals(inputDecimal.getAttribute("value"), "110.54");

        inputDate = driver.findElement(By.xpath("//input[@id='date']"));
        Assert.assertEquals(inputDate.getAttribute("value"), "12/12/2020");

        inputDateTime = driver.findElement(By.xpath("//input[@id='datetime']"));
        Assert.assertEquals(inputDateTime.getAttribute("value"), "21/11/2020 13:59:00");
    }
}