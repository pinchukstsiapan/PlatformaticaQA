import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.List;

public class EntityTagTest extends BaseTest {

    private static WebElement tagItem(WebDriver driver) {
        By tagListItemLocator = By.xpath("//tr[@data-row_id='5']//span[@class='check']");
        return driver.findElement(tagListItemLocator);
    }

    private static WebElement assignButton(WebDriver driver) {
       return driver.findElement(By.xpath("//button[contains(text(), 'Assign tags')]"));
    }

    private static List<WebElement> assignedTag(WebDriver driver) {
       return driver.findElements(By.xpath("//tr[@data-row_id='5']//span[@class='pa-tag']"));
    }

    @Ignore
    @Test
    public void editTest() {

        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");

        WebElement sidebar = driver.findElement(By.xpath("//div[contains(@class, 'sidebar-wrapper')]"));
        Actions builder = new Actions(driver);
        builder.moveToElement(sidebar).perform();
        WebElement tag = driver.findElement(By.xpath("//li/a/p[contains(text(), 'Tag')]"));
        builder.moveToElement(tag).build().perform();
        tag.click();
        By tagIconLocator = By.xpath("//li/a[contains(@href, 'index.php?action=action_list&list_type=table&entity_id=42&taggable=1')]");

        WebElement tagIcon = driver.findElement(tagIconLocator);
        tagIcon.click();

        By tagCheckboxLocator = By.xpath("//div/label[@class='form-check-label'][contains(text(), 'tag for view')]");
        WebElement tagCheckbox = driver.findElement(tagCheckboxLocator);
        tagCheckbox.click();
        tagItem(driver).click();

        assignButton(driver).click();
        Assert.assertEquals(assignedTag(driver).get(0).getText(), "tag for view");
        tagItem(driver).click();
        assignButton(driver).click();
        Assert.assertEquals(assignedTag(driver).size(), 0);
    }
}
