import java.util.List;
import java.util.UUID;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.BaseTest;

public class EntityDefaultTest extends BaseTest {

    private WebDriver driver;
    private final String title = UUID.randomUUID().toString();

    /**
     * initialize driver field and login
     */
    private void initTest() {
        driver = getDriver();
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");
    }

    @Test
    public void createRecord() {
        initTest();

        //Code to create and test new default using value in this.title
    }


    @Test(dependsOnMethods = "editRecord")
    public void deleteRecord() {
        initTest();

        //Code to delete default using title value in this.title
    }


    @Test
    public void editRecord() throws InterruptedException {
        initTest();

        final String editedTitle = UUID.randomUUID().toString();
        final String editedText = "Edited Text Value";
        final int editedInt = 10;

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(), 'Default')]"));
        tab.click();

        WebElement recordMenu = driver.findElement(By.xpath("//button[contains(@data-toggle, 'dropdown')] "));
        recordMenu.click();

        WebElement editFunction = driver.findElement(By.xpath("//a[text() = 'edit']"));
        System.out.println(editFunction);
        editFunction.click();

        WebElement fieldString = driver.findElement(By.xpath("//input[@id = 'string']"));
        fieldString.clear();
        fieldString.sendKeys(editedTitle);

        WebElement fieldText = driver.findElement(By.xpath("//span//textarea[@id = 'text']"));
        fieldText.clear();
        fieldText.sendKeys(editedText);

        WebElement fieldInt = driver.findElement(By.xpath("//input[@id = 'int']"));
        fieldInt.clear();
        fieldInt.sendKeys(String.valueOf(editedInt));

        ClickSaveButton(driver);

        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr"));

        boolean isFailed = true;
        for (WebElement row: rows) {
            String value = row.findElements(By.cssSelector("td")).get(1).getText();

            if (editedTitle.equals(value)) {
                Assert.assertEquals(row.findElements(By.cssSelector("td")).get(2).getText(), editedText);
                Assert.assertEquals(row.findElements(By.cssSelector("td")).get(3).getText(), String.valueOf(editedInt));
                isFailed = false;
                break;
            }
        }

        if (isFailed) {
            Assert.fail("Didn't find updated Default Entity");
        }

        // Validating changed fields of the record

    }

    /** scroll down to the Save button and click on it */
    private void ClickSaveButton(WebDriver driver) throws InterruptedException {
        WebElement saveButton = driver.findElement(By.xpath("//button[text() = 'Save']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", saveButton);
        saveButton.click();
        Thread.sleep(100);
    }

}

