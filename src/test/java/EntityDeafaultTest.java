import java.util.List;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.BaseTest;

public class EntityDeafaultTest extends BaseTest {

    @Test
    public void editRecord() throws InterruptedException {

        final String title = UUID.randomUUID().toString();

        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(), 'Default')]"));
        tab.click();

        WebElement recordMenu = driver.findElement(By.xpath("//button[contains(@data-toggle, 'dropdown')] "));
        recordMenu.click();
        Thread.sleep(150);

        WebElement editFunction = driver.findElement(By.xpath("//a[text() = 'edit']"));
        System.out.println(editFunction);
        editFunction.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://ref.eteam.work/index.php?action=action_edit&entity_id=7&row_id=2");
        Thread.sleep(1000);

        WebElement fieldString = driver.findElement(By.xpath("//input[@id = 'string']"));
        //fieldString.getAttribute("value")

        //System.out.println(fieldString.getText());

        WebElement fieldText = driver.findElement(By.xpath("//span//textarea[@id = 'text']"));
        //System.out.println(fieldText.getText());
    }
}


