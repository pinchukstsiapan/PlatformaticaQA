import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

public class EntityLoop1Test extends BaseTest {

    private WebDriverWait getWait(int timeoutSecond) {
        return new WebDriverWait(getDriver(), timeoutSecond);
    }

    private final By actions_button = By.xpath("//tr[@data-index='0']/td/div/button");

    private void assertLoopValues(WebDriver driver, int value, String mode) {
        getWait(1).until(ExpectedConditions.visibilityOfElementLocated(actions_button)).click();
        if (mode.equals("view")) {
            getWait(1).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(mode))).click();
            int f1 = Integer.parseInt(getWait(1).until(ExpectedConditions.visibilityOfElementLocated
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
                getWait(1).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(mode))).click();
                int f1 = Integer.parseInt(getWait(1).until(ExpectedConditions.visibilityOfElementLocated
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
                ProjectUtils.click(driver, driver.findElement(By.xpath("//button[text()='Cancel']/parent::a")));
            } else {
                Assert.fail("Wrong mode provided to assert loop values (accepted values: view or edit)");
            }
        }
    }
    @Ignore("The test is good, but too long. Let it be ignored until we switch to parallel execution")
    @Test
    public void loop1Stops() {

        WebDriver driver = getDriver();

        WebElement loop_1 = driver.findElement(By.xpath("//p[contains(text(),'Loop 1')]"));
        ProjectUtils.click(driver, loop_1);

        final int number_1 = 1;
        final int number_2 = 0;

        WebElement new_loop = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        new_loop.click();

        WebElement f1_element = driver.findElement(By.xpath("//div[@id='_field_container-f1']/child::span/child::input"));
        WebElement f3_element = driver.findElement(By.xpath("//div[@id='_field_container-f3']/child::span/child::input"));
        f1_element.sendKeys(String.valueOf(number_1));

        getWait(200).until(ExpectedConditions.attributeContains(f3_element, "value", "1002"));
        Assert.assertEquals(f1_element.getAttribute("value"),"1000");

        ProjectUtils.click(driver, driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")));
        assertLoopValues(driver, 1000, "view");

        getWait(1).until(ExpectedConditions.visibilityOfElementLocated(actions_button)).click();
        getWait(1).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("edit"))).click();
        WebElement f1_edit = driver.findElement(By.xpath("//div[@id='_field_container-f1']/child::span/child::input"));
        WebElement f3_edit = driver.findElement(By.xpath("//div[@id='_field_container-f3']/child::span/child::input"));
        getWait(1).until(ExpectedConditions.visibilityOf(f1_edit)).clear();
        f1_edit.sendKeys(String.valueOf(number_2));

        getWait(200).until(ExpectedConditions.attributeContains(f3_edit, "value", "1001"));
        ProjectUtils.click(driver, driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")));

        assertLoopValues(driver, 999, "view");
        assertLoopValues(driver, 999, "edit");

        getWait(1).until(ExpectedConditions.visibilityOfElementLocated(actions_button)).click();
        driver.findElement(By.linkText("delete")).click();
    }
}