import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.BaseTest;
import runner.ProjectUtils;
import java.util.UUID;

public class EntityFieldsViewTest extends BaseTest {

    @Test
    public void viewRecord() {

        final String title = UUID.randomUUID().toString();
        final String comment = "text_1";
        final String number = "123";
        final String decimal = "0.23";

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 6);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//li[@id='pa-menu-item-45']/a")));

        ProjectUtils.click(driver, driver.findElement(By.xpath("//div[@class = 'card-icon']")));

        driver.findElement(By.xpath("//input[@name='entity_form_data[title]']")).sendKeys(title);
        driver.findElement(By.xpath("//textarea[@name='entity_form_data[comments]']")).sendKeys(comment);
        driver.findElement(By.xpath("//input[@name='entity_form_data[int]']")).sendKeys(number);
        driver.findElement(By.xpath("//input[@id='decimal']")).sendKeys(decimal);

        ProjectUtils.click(driver, driver.findElement(By.id("pa-entity-form-save-btn")));

        driver.findElement(By.xpath("//i[text()='menu']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text() = 'view']"))).click();

        WebElement viewTitle = driver.findElement(By.xpath("//label[text()='Comments']/preceding-sibling::div"));
        WebElement viewComment = driver.findElement(By.xpath("//label[text()='Int']/preceding-sibling::div//span"));
        WebElement viewInt = driver.findElement(By.xpath("//label[text()='Decimal']/preceding-sibling::div[1]"));
        WebElement viewDecimal = driver.findElement(By.xpath("//label[text()='Date']/preceding-sibling::div[1]"));

        Assert.assertEquals(viewTitle.getText(), title);
        Assert.assertEquals(viewComment.getText(), comment);
        Assert.assertEquals(viewInt.getText(), number);
        Assert.assertEquals(viewDecimal.getText(), decimal);
    }
}
