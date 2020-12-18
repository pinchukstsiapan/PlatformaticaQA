import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.UUID;

public class EntityVisibilityEventsTest extends BaseTest {

    private void setUp() {
        WebDriver driver = getDriver();
        ProjectUtils.loginProcedure(driver);
        WebElement visibilityEventsTab = driver.findElement(
                By.xpath("//div[@id='menu-list-parent']//li/a[contains(@href,'id=86')]"));
        visibilityEventsTab.click();
    }

    private void createField(WebDriver driver, CharSequence content, Boolean enabled) {
        WebElement createButton = driver.findElement(By.xpath("//div/i[contains(text(), 'create_new_folder')]"));
        createButton.click();

        WebElement toggleButton = driver.findElement(By.xpath("//div[@class='togglebutton']"));
        toggleButton.click();

        WebElement testField = driver.findElement(By.xpath("//input[@id='test_field']"));
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(testField));
        testField.sendKeys(content);

        if (!enabled) {
            toggleButton.click();
        }

        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        saveButton.click();
    }

    private void validateFieldVisibility(WebDriver driver, String content, Boolean expected) throws InterruptedException {
        WebElement actionButton = findActionButtonByContent(driver, content);
        actionButton.click();

        Thread.sleep(300); // Wait for CSS animation

        WebElement editButton = actionButton.findElement(
                By.xpath("../ul/li[2]/a[contains(text(), 'edit')]"));
        editButton.click();

        WebElement testField = driver.findElement(
                By.xpath("//div[@id='_field_container-test_field']"));
        Boolean visible = testField.isDisplayed();

        Assert.assertEquals(visible, expected);

        WebElement cancelButton = driver.findElement(
                By.xpath("//button[contains(text(), 'Cancel')]"));
        cancelButton.click();
    }

    private void deleteRecordByTitle(WebDriver driver, String title) throws InterruptedException {
        WebElement actionButton = findActionButtonByContent(driver, title);
        actionButton.click();

        Thread.sleep(300); // Wait for CSS animation

        WebElement deleteButton = actionButton.findElement(
                By.xpath("../ul/li[3]/a[contains(text(), 'delete')]"));
        deleteButton.click();
    }

    private WebElement findActionButtonByContent(WebDriver driver, String content) {
        WebElement searchField = driver.findElement(
                By.xpath("//input[@placeholder='Search']"));
        Assert.assertNotNull(searchField);
        searchField.sendKeys(content);

        final int timeoutSec = 2;
        final String selector = "//div[contains(text(), '" + content + "')]/ancestor::tr//button[contains(., 'menu')]";
        new WebDriverWait(driver, timeoutSec).until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(selector)));

        WebElement actionButton = driver.findElement(By.xpath(selector));
        Assert.assertNotNull(actionButton);
        return actionButton;
    }

    @Test
    public void testFieldVisibility() {
        WebDriver driver = getDriver();
        setUp();

        WebElement createButton = driver.findElement(By.xpath("//div/i[contains(text(), 'create_new_folder')]"));
        createButton.click();

        WebElement toggleButton = driver.findElement(By.xpath("//div[@class='togglebutton']"));

        WebElement testField = driver.findElement(By.xpath("//div[@id='_field_container-test_field']"));

        Assert.assertFalse(testField.isDisplayed());
        toggleButton.click();
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(testField));

        Assert.assertTrue(testField.isDisplayed());
    }

    @Test
    public void triggerFieldState() throws InterruptedException {
        WebDriver driver = getDriver();
        setUp();

        final String fieldEnabled = UUID.randomUUID().toString();
        final String fieldDisabled = UUID.randomUUID().toString();

        createField(driver, fieldEnabled, true);
        createField(driver, fieldDisabled, false);

        validateFieldVisibility(driver, fieldEnabled, true);
        validateFieldVisibility(driver, fieldDisabled, false);

        deleteRecordByTitle(driver, fieldEnabled);
        deleteRecordByTitle(driver, fieldDisabled);
    }
}