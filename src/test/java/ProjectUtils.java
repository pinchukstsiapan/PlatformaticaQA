import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import runner.BaseTest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public abstract class ProjectUtils {

    public enum Profile {

        DEFAULT("https://ref.eteam.work"),
        MARKETPLACE("https://ref.eteam.work");

        private static Properties credentials;
        static {
            renewCredentials();
        }

        private final String userNameKey = getName() + ".username";
        private final String passwordKey = getName() + ".password";

        private final String url;

        Profile(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public String getUserName() {
            return credentials.getProperty(userNameKey);
        }

        public String getPassword() {
            return credentials.getProperty(passwordKey);
        }

        public String getCredentialUrl() {
            return getUrl() + "/next_tester.php";
        }

        public String getName() {
            return this.name().toLowerCase();
        }

        public WebDriver loginProcedure(WebDriver driver) {
            return ProjectUtils.loginProcedure(driver, this);
        }
    }

    public static void renewCredentials() {
        Profile.credentials = getCredentials();
    }

    private static Properties getCredentials() {
        Properties properties = new Properties();
        try {
            if (!BaseTest.isRemoteWebDriver()) {
                properties.load(ProjectUtils.class.getClassLoader().getResourceAsStream("local.properties"));
                return properties;
            }
        } catch (IOException ignore) {}

        if (BaseTest.isRemoteWebDriver() || Boolean.parseBoolean(properties.getProperty("serverCredentials"))) {
            for (Profile profile : Profile.values()) {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(profile.getCredentialUrl()).openConnection();
                    try {
                        con.setRequestMethod("GET");
                        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String response = in.readLine();
                            String[] responseArray = response.split(";");

                            properties.setProperty(profile.userNameKey, responseArray[0]);
                            properties.setProperty(profile.passwordKey, responseArray[1]);
                        }
                    } finally {
                        con.disconnect();
                    }

                } catch (IOException ignore) {}
            }
        }

        return properties;
    }

    public static WebDriver loginProcedure(WebDriver driver) {
        return loginProcedure(driver, Profile.DEFAULT);
    }

    public static WebDriver loginProcedure(WebDriver driver, Profile profile) {
        driver.get(profile.getUrl());
        login(driver, profile);
        reset(driver);

        return driver;
    }

    public static void reset(WebDriver driver) {
        click(driver, driver.findElement(By.id("navbarDropdownProfile")));
        click(driver, driver.findElement(By.xpath("//a[contains(text(), 'Reset')]")));
    }

    public static void login(WebDriver driver, Profile profile) {
        if (Strings.isStringEmpty(profile.getUserName()) || Strings.isStringEmpty(profile.getPassword())) {
            throw new RuntimeException("Username or Password is empty");
        }

        login(driver, profile.getUserName(), profile.getPassword());
    }

    @Deprecated
    public static void login(WebDriver driver, String userName, String pas) {
        WebElement loginElement = driver.findElement(By.xpath("//input[@name='login_name']"));
        loginElement.sendKeys(userName);
        WebElement pasElement = driver.findElement(By.xpath("//input[@name='password']"));
        pasElement.sendKeys(pas);
        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();
    }

    /*
    *  The method helps to avoid - Element is not clickable at point (x,x). Other element would receive the click
    */
    public static void click(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()", element);
    }
}
