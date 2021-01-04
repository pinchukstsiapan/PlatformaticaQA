import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

public class COPYEntityInitTest extends BaseTest {

    @Test
    public void checkDefaultValuesInViewModeCOPY() throws InterruptedException {

        WebDriver driver = getDriver();

        WebElement clickInit = driver.findElement(By.xpath("//a[@href='index.php?action=action_list&entity_id=65']"));
        ProjectUtils.click(driver, clickInit);

        WebElement clickPlusNewRecord = driver.findElement(By.xpath("//i[contains(text(), 'create_new_folder')]"));
        ProjectUtils.click(driver, clickPlusNewRecord);

        WebElement clickOnSaveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, clickOnSaveButton);

        WebElement openRecord = driver.findElement(By.xpath("//i[contains(text(), 'menu')]/.."));
        ProjectUtils.click(driver, openRecord);

        WebElement dropDownMenuButton = driver.findElement(By.xpath("//li/a[text() = 'view']"));
        ProjectUtils.click(driver, dropDownMenuButton);

        Assert.assertEquals(driver.findElement(By.xpath("//span[text() = 'New String']")).getText(), "New String");
        Assert.assertEquals(driver.findElement(By.xpath("//span[text() = 'New Text']")).getText(), "New Text");
        Assert.assertEquals(driver.findElement(By.xpath("//span[text() = '2']")).getText(), "2");
        Assert.assertEquals(driver.findElement(By.xpath("//span[text() = '3.14']")).getText(), "3.14");
        Assert.assertEquals(driver.findElement(By.xpath("//span[text() = '01/01/2020']")).getText(), "01/01/2020");
        Assert.assertEquals(driver.findElement(By.xpath("//span[text() = '31/12/2020 23:59:59']")).getText(),
                "31/12/2020 23:59:59");
        Assert.assertEquals(driver.findElement(By.xpath("//p[text() = 'User 1 Demo']")).getText(), "User 1 Demo");
        Assert.assertEquals(driver.findElement(By.xpath("//span[text() = '1']")).getText(), "1");
        Assert.assertEquals(driver.findElement(By.xpath("//span[text() = 'Two']")).getText(), "Two");
    }
}
