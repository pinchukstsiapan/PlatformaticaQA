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
     * demonstration of how to use makeScreenShot within the test.
     * When running this locally make sure you have  /tmp  directory on Linux/Mac
     * or  c:\tmp  directory on windows assuming you are using disk C: to run your tests
     */
    @Test
    public void takeScreenshotDemo() {

        WebDriver browser = getDriver();
        browser.get("https://www.wikipedia.org");

        WebElement searchLang = browser.findElement(By.xpath("//select[@name='language']/option[@value='ru']"));
        searchLang.click();

        WebElement input = browser.findElement(By.xpath("//input[@id='searchInput']"));
        input.sendKeys("Обеспечение качества");

        Assert.assertTrue(true);
        TestUtils.makeScreenShot(browser, "/tmp/takeScreenshotDemo_01.png");

        // To test screenshot on failure add below assert that would fail. For example, Assert.assertTrue(false);
    }

    /***
     * Example of how to take a screenshot after assert fails.
     */
    @AfterMethod
    public void makeScreenShotAfterTest(ITestResult testResult) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            TestUtils.makeScreenShot(getDriver(), "/tmp/takeScreenshotDemo_FAILED.png");
        }
    }

}
