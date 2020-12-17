package runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

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

    private WebDriver driver;

    @BeforeMethod
    protected void setUpAll() {

        if (remoteWebDriver) {
            try {
                this.driver = new RemoteWebDriver(new URL(HUB_URL), new ChromeOptions());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.driver = new ChromeDriver();
        }

        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        this.driver.manage().window().maximize();
    }

    @AfterMethod
    protected void setDownAll(Method method, ITestResult tr) {
        driver.quit();
        long executionTime = (tr.getEndMillis() - tr.getStartMillis()) / 1000;
        this.log(String.format("\u001B[33m%s.%s() Execution time: %ds\u001B[0m",
            this.getClass(), method.getName(), executionTime));
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected void log(String message) {
        logger.log(Level.INFO, message);
    }
}
