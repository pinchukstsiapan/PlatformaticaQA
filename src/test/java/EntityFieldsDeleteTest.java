import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import runner.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;
import java.util.UUID;

@Run(run = RunType.Multiple)
public class EntityFieldsDeleteTest extends BaseTest {

    private static final String title = UUID.randomUUID().toString();
    private static final String comments = UUID.randomUUID().toString();
    private static  final int number = 15;
    private static final double decimal = 23.4;

    @Test
    public void createNewFields() {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 3);

        ProjectUtils.reset(driver);

        WebElement goFields = driver.findElement(By.xpath("//li[@id='pa-menu-item-45']/a"));
        goFields.click();

        WebElement newRecord = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        ProjectUtils.click(driver, newRecord);

        WebElement titleElement = driver.findElement(By.id("title"));
        titleElement.sendKeys(title);

        WebElement commentElement = driver.findElement(By.id("comments"));
        commentElement.sendKeys(comments);

        WebElement numberElement = driver.findElement(By.id("int"));
        numberElement.sendKeys(String.valueOf(number));

        WebElement decimalElement = driver.findElement(By.id("decimal"));
        decimalElement.sendKeys(String.valueOf(decimal));

        WebElement dateElement = driver.findElement(By.id("date"));
        ProjectUtils.click(driver, dateElement);

        WebElement dateTimeElement = driver.findElement(By.id("datetime"));
        ProjectUtils.click(driver, dateTimeElement);

        WebElement saveElement = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, saveElement);

        WebElement resultTitleElement = driver.findElement(By.xpath("//div[contains(text(),'" + title + "')]"));
        WebElement resultCommentsElement = driver.findElement(By.xpath("//div[contains(text(),'" + comments + "')]"));
        WebElement resultNumberElement = driver.findElement(By.xpath("//div[contains(text(),'" + number + "')]"));
        WebElement resultDecimalElement = driver.findElement(By.xpath("//div[contains(text(),'" + decimal + "')]"));

        Assert.assertEquals(resultTitleElement.getText(), title);
        Assert.assertEquals(resultCommentsElement.getText(), comments);

        WebElement clickListElement = driver.findElement(By.xpath("//a[@class='nav-link active']"));
        ProjectUtils.click(driver, clickListElement);

        WebElement menuButtonElement = driver.findElement(By.xpath("//i[contains(text(),'menu')]"));
        ProjectUtils.click(driver, menuButtonElement);

        WebElement deleteRecordElement = driver.findElement(By.xpath("//a[contains(@href,'delete')]"));
        ProjectUtils.click(driver, deleteRecordElement);

        WebElement recycleBinElement = driver.findElement(By.xpath("//i[contains(text(),'delete_outline')]"));
        ProjectUtils.click(driver, recycleBinElement);

        WebElement currentTitleDeleteElement = driver.findElement(By.xpath("//b[text()='" + title + "']"));
        wait.until(ExpectedConditions.elementToBeClickable(currentTitleDeleteElement));

        Assert.assertEquals(currentTitleDeleteElement.getText(), title);

    }

    @Test
    public void createNewFields1 () {
        WebDriver driver = getDriver();
        ProjectUtils.reset(driver);
        WebElement goFields = driver.findElement(By.xpath("//li[@id='pa-menu-item-45']/a"));
        goFields.click();

        WebElement newRecord = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        ProjectUtils.click(driver, newRecord);

        WebElement titleElement = driver.findElement(By.id("title"));
        titleElement.sendKeys(title);
        WebElement commentElement = driver.findElement(By.id("comments"));
        commentElement.sendKeys(comments);

        WebElement numberElement = driver.findElement(By.id("int"));
        numberElement.sendKeys(String.valueOf(number));

        WebElement decimalElement = driver.findElement(By.id("decimal"));
        decimalElement.sendKeys(String.valueOf(decimal));

        WebElement dateElement = driver.findElement(By.id("date"));
        ProjectUtils.click(driver, dateElement);

        WebElement dateTimeElement = driver.findElement(By.id("datetime"));
        ProjectUtils.click(driver, dateTimeElement);

        WebElement saveElement = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, saveElement);

        WebElement resultTitleElement = driver.findElement(By.xpath("//div[contains(text(),'" + title + "')]"));
        WebElement resultCommentsElement = driver.findElement(By.xpath("//div[contains(text(),'" + comments + "')]"));
        WebElement resultNumberElement = driver.findElement(By.xpath("//div[contains(text(),'" + number + "')]"));
        WebElement resultDecimalElement = driver.findElement(By.xpath("//div[contains(text(),'" + decimal + "')]"));

        Assert.assertEquals(resultTitleElement.getText(), title);
        Assert.assertEquals(resultCommentsElement.getText(), comments);
    }

    private void deleteFields () {
        WebDriver driver = getDriver();
        WebElement goFields = driver.findElement(By.xpath("//li[@id='pa-menu-item-45']/a"));
        goFields.click();

        WebElement clickListElement = driver.findElement(By.xpath("//a[@class='nav-link active']"));
        ProjectUtils.click(driver, clickListElement);

        WebElement menuButtonElement = driver.findElement(By.xpath("//i[contains(text(),'menu')]"));
        ProjectUtils.click(driver, menuButtonElement);

        WebElement deleteRecordElement = driver.findElement(By.xpath("//a[contains(@href,'delete')]"));
        ProjectUtils.click(driver, deleteRecordElement);
    }

    @Test(dependsOnMethods = "createNewFields1")
    public void checkFieldsRecycleBin () {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        deleteFields();

        WebElement goFields = driver.findElement(By.xpath("//li[@id='pa-menu-item-45']/a"));
        goFields.click();

        WebElement recycleBinElement = driver.findElement(By.xpath("//i[contains(text(),'delete_outline')]"));
        ProjectUtils.click(driver, recycleBinElement);

        WebElement currentTitleDeleteElement = driver.findElement(By.xpath("//b[text()='" + title + "']"));
        wait.until(ExpectedConditions.elementToBeClickable(currentTitleDeleteElement));

        Assert.assertEquals(currentTitleDeleteElement.getText(), title);
    }
}