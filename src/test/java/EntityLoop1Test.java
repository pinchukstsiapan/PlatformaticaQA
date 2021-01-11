import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

public class EntityLoop1Test extends BaseTest {

    private static final By ACTIONS_BUTTON = By.xpath("//tr[@data-index='0']/td/div/button");

    private void waitUntilEnd(WebElement element, String value){
        do {
            getWebDriverWait();
        }
        while (!element.getAttribute("value").equals(value));
    }

    private void assertLoopValues(WebDriver driver, int value, String mode) {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(ACTIONS_BUTTON)).click();
        if (mode.equals("view")) {
            ProjectUtils.click(driver, driver.findElement(By.linkText(mode)));
            int f1 = Integer.parseInt(getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("//label[(text()='F1')]/following-sibling::div/child::div/child::span"))).getText());
            Assert.assertEquals(f1, value);

            int f2 = Integer.parseInt(driver.findElement
                    (By.xpath("//label[(text()='F2')]/following-sibling::div/child::div/child::span")).getText());
            value += 1;
            Assert.assertEquals(f2, value);

            int f3 = Integer.parseInt(driver.findElement
                    (By.xpath("//label[(text()='F3')]/following-sibling::div/child::div/child::span")).getText());
            value += 1;
            Assert.assertEquals(f3, value);
            driver.navigate().back();
        } else {
            if (mode.equals("edit")) {
                ProjectUtils.click(driver, driver.findElement(By.linkText(mode)));
                int f1 = Integer.parseInt(getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//div[@id='_field_container-f1']/child::span/child::input"))).getAttribute("value"));
                Assert.assertEquals(f1, value);

                int f2 = Integer.parseInt(driver.findElement
                        (By.xpath("//div[@id='_field_container-f2']/child::span/child::input")).getAttribute("value"));
                value += 1;
                Assert.assertEquals(f2, value);

                int f3 = Integer.parseInt(driver.findElement
                        (By.xpath("//div[@id='_field_container-f3']/child::span/child::input")).getAttribute("value"));
                value += 1;
                Assert.assertEquals(f3, value);
                ProjectUtils.click(driver, driver.findElement(By.xpath("//button[contains(text(),'Cancel')]/parent::a")));
            } else {
                Assert.fail("Wrong mode provided to assert loop values (accepted values: view or edit)");
            }
        }
    }
    @Ignore("The test is good, but too long. Let it be ignored until we switch to parallel execution")
    @Test
    public void loop1Stops() throws InterruptedException {
        WebDriver driver = getDriver();

        WebElement loop_1 = driver.findElement(By.xpath("//p[contains(text(),'Loop 1')]"));
        ProjectUtils.click(driver, loop_1);

        final int number_1 = 1;
        final int number_2 = 0;

        WebElement new_loop = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        new_loop.click();

        WebElement f1_element = driver.findElement(By.xpath("//div[@id='_field_container-f1']/child::span/child::input"));
        WebElement f3_element = driver.findElement(By.xpath("//div[@id='_field_container-f3']/child::span/child::input"));
        ProjectUtils.inputKeys(driver, f1_element, number_1);

        waitUntilEnd(f3_element, "1002");
        Assert.assertEquals(f1_element.getAttribute("value"),"1000");

        ProjectUtils.click(driver, driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")));
        assertLoopValues(driver, 1000, "view");

        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(ACTIONS_BUTTON)).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.linkText("edit"))).click();
        WebElement f1_edit = driver.findElement(By.xpath("//div[@id='_field_container-f1']/child::span/child::input"));
        WebElement f3_edit = driver.findElement(By.xpath("//div[@id='_field_container-f3']/child::span/child::input"));
        getWebDriverWait().until(ExpectedConditions.visibilityOf(f1_edit)).clear();
        ProjectUtils.inputKeys(driver, f1_edit, number_2);

        waitUntilEnd(f3_edit, "1001");
        ProjectUtils.click(driver, driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")));

        assertLoopValues(driver, 999, "view");
        assertLoopValues(driver, 999, "edit");

        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(ACTIONS_BUTTON)).click();
        driver.findElement(By.linkText("delete")).click();

        WebElement loop1_table = getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//div[contains(@class,'card-body')]")));
        Assert.assertTrue(loop1_table.getText().isEmpty());
    }
}