import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

/** example of how to use TestUtils.takeScreenShot */
public class ScreenshotTest extends BaseTest {

    /**
     * Demonstration of how to use takeScreenShot within the test.
     * saves file into local temporary directory ( /tmp on linux and C:\Users\USERNAME\AppData\Local\Temp on windows
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
        TestUtils.takeScreenShot(browser, "takeScreenshotDemo_01.png");

        // To test screenshot on failure below add assert that would fail. For example, Assert.assertTrue(false);
        Assert.assertTrue(false);
    }

    /** Example of how to take a screenshot after assert fails. this should be in the BaseTest class  */
    // @ AfterMethod
//    public void makeScreenShotAfterTest(ITestResult testResult) {
//        if (ITestResult.FAILURE == testResult.getStatus()) {
//            TestUtils.takeScreenShot(getDriver(), "takeScreenshotDemo_FAILED.png");
//        }
//    }

}
