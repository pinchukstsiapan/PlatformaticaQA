import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import runner.BaseTest;

/**
 * example of how to use TestUtils.makeScreenShot
 */
public class ScreenshotTest extends BaseTest {

    /***
     * demonstration of how to use makeScreenShot within the test
     */
    @Test
    public void takeScreenshotDemo() {

        WebDriver browser = getDriver();
        browser.get("https://www.wikipedia.org");

        WebElement searchLang = browser.findElement(By.xpath("//select[@name='language']/option[@value='ru']"));
        searchLang.click();

        WebElement input = browser.findElement(By.xpath("//input[@id='searchInput']"));
        input.sendKeys("Обеспечение качества");

        // imitate successful assert (happy path)
        Assert.assertNotEquals(input.getText(), "ZZZZZZZZZ");

        // just take screenshot after successful assert (happy path)
        TestUtils.makeScreenShot(browser, "/tmp/dmitryTestRun_01.png");

        // imitate failed assert to test makeScreenShotAfterTest method
        //Assert.assertEquals(input.getText(), "Обеспечение качества");
    }

    /***
     * Example of how to take a screenshot after assert fails.
     */
    @AfterMethod
    public void makeScreenShotAfterTest(ITestResult testResult) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            TestUtils.makeScreenShot(getDriver(), "/tmp/dmitryTestRun_FAILED.png");
        }
    }

}
