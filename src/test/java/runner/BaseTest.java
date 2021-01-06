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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Listeners(TestOrder.class)
public abstract class  BaseTest {
    private String screenshotDirectoryName = null;

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

        Map<String, Object> chromePreferences = new HashMap<>();
        chromePreferences.put("profile.default_content_settings.geolocation", 2);
        chromePreferences.put("credentials_enable_service", false);
        chromePreferences.put("password_manager_enabled", false);
        chromePreferences.put("safebrowsing.enabled", "true");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", chromePreferences);
        chromeOptions.addArguments("--window-size=1920,1080");

        if (isRemoteWebDriver()) {
            chromeOptions.setHeadless(true);
            chromeOptions.addArguments("--disable-gpu");
            try {
                result = new RemoteWebDriver(new URL(HUB_URL), chromeOptions);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            result = new ChromeDriver(chromeOptions);
        }

        result.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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
        boolean createdDirectory = false;
        if (ITestResult.FAILURE == tr.getStatus()) {
            if (screenshotDirectoryName == null) {
                createdDirectory = true;
                String tempPath = System.getProperty("java.io.tmpdir");
                // on windows last char is '\', on Linux is 'p' so need to add separator
                if (tempPath.charAt(tempPath.length()-1) != File.separatorChar) {
                    tempPath += File.separator;
                }
                screenshotDirectoryName = tempPath
                        + (new SimpleDateFormat("YYYY-MM-dd-kk-mm-").format(new Date()))
                        + UUID.randomUUID().toString();
            }

            ScreenshotUtils.createScreenshotsDir(screenshotDirectoryName);
            if (createdDirectory) {
                LoggerUtils.logYellow("Created directory to save screenshots: " + screenshotDirectoryName);
            }

            ScreenshotUtils.takeScreenShot(getDriver(), String.format("%s%s%s.%s.png",
                    screenshotDirectoryName, File.separator, tr.getInstanceName(), tr.getName()));
        }

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

    @AfterSuite
    protected void afterSuite() {
        if (remoteWebDriver) {
            try {
                if (screenshotDirectoryName != null) {
                    ScreenshotUtils.uploadScreenshotsDir(screenshotDirectoryName);
                }
            } catch (Exception exception) {
                LoggerUtils.logRed(String.format("unable to upload images directory %s to Google drive \n%s",
                        screenshotDirectoryName,
                        DriveUtils.getStackTrace(exception)));
            }
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

    protected String getScreenshotDirectoryName() {
        return screenshotDirectoryName;
    }
}
