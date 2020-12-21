import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;
import runner.type.ProfileType;
import java.util.List;
import java.util.UUID;

public class EntityAssignRecordTest extends BaseTest {

    @Test
    public void newRecord() throws InterruptedException {
        WebDriver driver = getDriver();
        String user = TestUtils.getProfileType(this, ProfileType.DEFAULT).getUserName();

        WebElement assignBtn = driver.findElement(By.xpath("//*[@id='menu-list-parent']/ul/li/a[contains(@href,'id=37')]"));
        assignBtn.click();

        WebElement nr = driver.findElement(By.xpath("//*[@class='card-header card-header-rose card-header-icon']/a[contains(@href, 'entity_id=37')]/div"));
        nr.click();

        String testId = UUID.randomUUID().toString();
        WebElement stringField = driver.findElement(By.xpath("//*[@id='string']"));
        stringField.sendKeys(testId);

        WebElement textField = driver.findElement(By.xpath("//*[@id='text']"));
        textField.sendKeys("tratatyshechki");

        WebElement intField = driver.findElement(By.xpath("//*[@id='int']"));
        intField.sendKeys("23");

        WebElement decimalField = driver.findElement(By.xpath("//*[@id='decimal']"));
        decimalField.sendKeys("19.99");

        WebElement dateField = driver.findElement(By.xpath("//*[@id='date']"));
        dateField.click();

        WebElement datetimeField = driver.findElement(By.xpath("//*[@id='datetime']"));
        datetimeField.click();

        WebElement user1Field = driver.findElement(By.xpath(String.format("//option[text()='%s']", user)));
        user1Field.click();

        WebElement saveBtn = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        saveBtn.click();

        WebElement searchField = driver.findElement(By.xpath("//input"));
        searchField.sendKeys(testId);
        Thread.sleep(1000);

        String template = String.format("//select/option[text()='%s']", user);
        List<WebElement> rows = driver.findElements(By.xpath(template));
        Assert.assertEquals(rows.size(), 1);
        for (WebElement row : rows) {
            row.click();
        }

        WebElement myAssignments = driver.findElement(By.xpath("//*[@id='pa-menu-item-41']/a/p"));
        myAssignments.click();

        WebElement searchField2 = driver.findElement(By.xpath("//input"));
        searchField2.sendKeys(testId);
        Thread.sleep(1000);

        template = String.format("//tbody/tr[descendant::div[contains(text(), '%s')] and descendant::td[text()='%s']]", testId, user);
        rows = driver.findElements(By.xpath(template));
        Assert.assertEquals(rows.size(), 1);
    }
}
