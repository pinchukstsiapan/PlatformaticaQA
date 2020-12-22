package runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import runner.type.ProfileType;
import runner.type.RunType;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Listeners(TestOrder.class)
public abstract class  BaseTest {

    public static final String HUB_URL = "http://localhost:4444/wd/hub";

    private static boolean remoteWebDriver = false;
    static {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(HUB_URL + "/status").openConnection();
            try {
                con.setRequestMethod("GET");
                remoteWebDriver = con.getResponseCode() == HttpURLConnection.HTTP_OK;
            } finally {
                con.disconnect();
            }
        } catch (IOException ignore) {}

        if (!remoteWebDriver) {
            WebDriverManager.chromedriver().setup();
        }
    }

    public static boolean isRemoteWebDriver() {
        return remoteWebDriver;
    }

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    private RunType runType = RunType.Single;
    private ProfileType profileType = ProfileType.DEFAULT;

    private WebDriver createBrowser() {
        WebDriver result;

        if (isRemoteWebDriver()) {
            try {
                result = new RemoteWebDriver(new URL(HUB_URL), new ChromeOptions());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            result = new ChromeDriver();
        }

        result.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        result.manage().window().maximize();

        LoggerUtils.log("Browser opened");

        return result;
    }

    private void quitBrowser() {
        getDriver().quit();

        LoggerUtils.log("Browser closed");
    }

    private void startTest(WebDriver driver, ProfileType profileType) {
        driver.get(profileType.getUrl());
        ProjectUtils.login(driver, profileType);
        ProjectUtils.reset(driver);
    }

    @BeforeClass
    protected void beforeClass() {
        profileType = TestUtils.getProfileType(this, ProfileType.DEFAULT);
        runType = TestUtils.getRunType(this);

        if (runType == RunType.Multiple) {
            driver = createBrowser();
            startTest(driver, profileType);
        }
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        LoggerUtils.logGreen(String.format("%s.%s()",
            this.getClass().getName(), method.getName()));

        if (runType == RunType.Single) {
            driver = createBrowser();
            startTest(driver, TestUtils.getProfileType(method, profileType));
        } else {
            driver.get(profileType.getUrl());
        }
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult tr) {
        if (runType == RunType.Single) {
            quitBrowser();
        }

        long executionTime = (tr.getEndMillis() - tr.getStartMillis()) / 1000;
        LoggerUtils.logGreen(String.format("%s.%s() Execution time: %ds",
            this.getClass().getName(), method.getName(), executionTime));
    }

    @AfterClass
    protected void afterClass() {
        if (runType == RunType.Multiple) {
            quitBrowser();
        }
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected WebDriverWait getWebDriverWait() {
        if (webDriverWait == null) {
            webDriverWait = new WebDriverWait(driver, 10);
        }

        return webDriverWait;
    }
}
