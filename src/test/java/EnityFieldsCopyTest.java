import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

import java.util.UUID;

@Run(run = RunType.Single)
public class EnityFieldsCopyTest extends BaseTest {

    @Test
    public void newAlexRecord() throws InterruptedException {

        final String title = UUID.randomUUID().toString();;
        final String comments = "TEST IT";
        final int InTeGeR = 11;

        WebDriver driver = getDriver();

        WebElement tab = driver.findElement(By.xpath("//li[@id = 'pa-menu-item-45']"));
        tab.click();
        WebElement newFile = driver.findElement(By.xpath("//i[text() = 'create_new_folder']"));
        newFile.click();
        Thread.sleep(1000);

        WebElement first = driver.findElement(By.xpath("//input[contains(@name, 'title')]"));
        ProjectUtils.sendKeys(first, title);
        WebElement second = driver.findElement(By.xpath("//textarea[@id = 'comments']"));
        ProjectUtils.sendKeys(second, comments);
        WebElement tri = driver.findElement(By.xpath("//input[contains(@name, 'int')]"));
        ProjectUtils.sendKeys(tri, InTeGeR);

        WebElement button2 = driver.findElement(By.xpath("//button[text() = 'Save']"));
        ProjectUtils.click(driver, button2);

        //validation of record
        WebElement butt3 = driver.findElement(By.xpath("(//button[@data-toggle= 'dropdown'])[1]"));
        butt3.click();
        Thread.sleep(1000);
        WebElement vw = driver.findElement(By.xpath("//a[text() = 'view']"));
        vw.click();
    }
}
