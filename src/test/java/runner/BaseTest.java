package runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import runner.type.ProfileType;
import runner.type.RunType;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class  BaseTest {

    public static final String HUB_URL = "http://localhost:4444/wd/hub";

    private static final Logger logger = Logger.getLogger("BaseLogger");

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

        return result;
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
        if (runType == RunType.Single) {
            driver = createBrowser();
            startTest(driver, TestUtils.getProfileType(method, profileType));
        } else if (!driver.getCurrentUrl().startsWith(profileType.getUrl())) {
            driver.get(profileType.getUrl());
        }
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult tr) {
        if (runType == RunType.Single) {
            getDriver().quit();
        }

        long executionTime = (tr.getEndMillis() - tr.getStartMillis()) / 1000;
        log(String.format("\u001B[33m%s.%s() Execution time: %ds\u001B[0m",
            getClass(), method.getName(), executionTime));
    }

    @AfterClass
    protected void afterClass() {
        if (runType == RunType.Multiple) {
            getDriver().quit();
        }
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected void log(String message) {
        logger.log(Level.INFO, message);
    }
}
