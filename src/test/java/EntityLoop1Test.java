import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.List;

public class EntityLoop1Test extends BaseTest {

    private WebDriverWait getWait(int timeoutSecond) {
        return new WebDriverWait(getDriver(), timeoutSecond);
    }

    private int allRecords() {
        return Integer.parseInt(getWait(1).until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//span[@class='pagination-info']")))
                .getText().split(" ")[5]);
    }

    private void actionForLastRecord(WebDriver driver) throws InterruptedException {
        List<WebElement> record_rows_we = driver.findElements(By.cssSelector("tbody > tr"));
        if (record_rows_we.size() == 0) {
            Assert.fail("No \"Loop 1\" entity records found after creating one record");
        }

        WebElement rows_per_page_we = driver.findElement(By.cssSelector("span.page-size"));
        if (rows_per_page_we.isDisplayed()) {
            rows_per_page_we.click();
            Thread.sleep(500);
            driver.findElement(By.linkText("10")).click();
            int page_to_go_num = (int)Math.ceil(allRecords() / (double)10);
            WebElement page_number = driver.findElement
                    (By.cssSelector("a[aria-label='to page " + page_to_go_num + "']"));
            page_number.click();
            Thread.sleep(500);
        }

        WebElement actions_button_of_created_record =
                driver.findElement(By.xpath("//tr[@data-index='" + (allRecords() - 1) + "']/td/div/button"));
        actions_button_of_created_record.click();
        Thread.sleep(500);
    }

    private void waitUntilLoopStops(WebElement f3_element, int f1_target_num) throws InterruptedException {
        if (f1_target_num == 1000 || f1_target_num == 999) {
            Thread.sleep(1000);
            while (Integer.parseInt(f3_element.getAttribute("value")) < f1_target_num + 2) {
                Thread.sleep(3000);
            }
        } else {
            Assert.fail("Wrong F1 target number provided to initiate loop (accepted values: 1000 or 999)");
        }
    }

    private void assertLoopValues(WebDriver driver, int value, String mode) throws InterruptedException {
        if (mode.equals("view")) {
            driver.findElement(By.linkText(mode)).click();
            Thread.sleep(500);
            int f1 = Integer.parseInt(driver.findElement
                    (By.xpath("//label[(text()='F1')]/following-sibling::div/child::div/child::span")).getText());
            Assert.assertEquals(value, f1);

            int f2 = Integer.parseInt(driver.findElement
                    (By.xpath("//label[(text()='F2')]/following-sibling::div/child::div/child::span")).getText());
            value += 1;
            Assert.assertEquals(value, f2);

            int f3 = Integer.parseInt(driver.findElement
                    (By.xpath("//label[(text()='F3')]/following-sibling::div/child::div/child::span")).getText());
            value += 1;
            Assert.assertEquals(value, f3);
            driver.navigate().back();
        } else {
            if (mode.equals("edit")) {
                driver.findElement(By.linkText(mode)).click();
                Thread.sleep(500);
                int f1 = Integer.parseInt(driver.findElement
                        (By.xpath("//div[@id='_field_container-f1']/child::span/child::input")).getAttribute("value"));
                Assert.assertEquals(value, f1);

                int f2 = Integer.parseInt(driver.findElement
                        (By.xpath("//div[@id='_field_container-f2']/child::span/child::input")).getAttribute("value"));
                value += 1;
                Assert.assertEquals(value, f2);

                int f3 = Integer.parseInt(driver.findElement
                        (By.xpath("//div[@id='_field_container-f3']/child::span/child::input")).getAttribute("value"));
                value += 1;
                Assert.assertEquals(value, f3);
                ProjectUtils.click(driver, driver.findElement(By.xpath("//button[text()='Cancel']/parent::a")));
            } else {
                Assert.fail("Wrong mode provided to assert loop values (accepted values: view or edit)");
            }
        }
    }

    @Test
    public void loop1Stops() throws InterruptedException {

        WebDriver driver = getDriver();
        ProjectUtils.goAndLogin(driver);

        WebElement loop_1 = driver.findElement(By.xpath("//p[contains(text(),'Loop 1')]"));
        ProjectUtils.click(driver, loop_1);

        final int number_1 = 1;
        final int number_2 = 0;

        WebElement new_loop = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        new_loop.click();

        WebElement f1_element = driver.findElement(By.xpath("//div[@id='_field_container-f1']/child::span/child::input"));
        WebElement f3_element = driver.findElement(By.xpath("//div[@id='_field_container-f3']/child::span/child::input"));
        f1_element.sendKeys(String.valueOf(number_1));

        waitUntilLoopStops(f3_element, 1000);
        Assert.assertEquals("1000", f1_element.getAttribute("value"));

        ProjectUtils.click(driver, driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")));
        Thread.sleep(1000);

        actionForLastRecord(driver);
        assertLoopValues(driver, 1000, "view");

        actionForLastRecord(driver);
        driver.findElement(By.linkText("edit")).click();
        Thread.sleep(500);
        WebElement f1_edit = driver.findElement(By.xpath("//div[@id='_field_container-f1']/child::span/child::input"));
        WebElement f3_edit = driver.findElement(By.xpath("//div[@id='_field_container-f3']/child::span/child::input"));
        f1_edit.clear();
        f1_edit.sendKeys(String.valueOf(number_2));
        waitUntilLoopStops(f3_edit, 999);
        ProjectUtils.click(driver, driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")));
        Thread.sleep(1000);

        actionForLastRecord(driver);
        assertLoopValues(driver, 999, "view");

        actionForLastRecord(driver);
        assertLoopValues(driver, 999, "edit");

        actionForLastRecord(driver);
        driver.findElement(By.linkText("delete")).click();
    }
}