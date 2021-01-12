package runner.type;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import runner.BaseTest;
import runner.LoggerUtils;
import runner.ProjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public enum ProfileType {

    DEFAULT("https://ref.eteam.work", "https://ref.eteam.work/next_tester.php"),
    MARKETPLACE("https://portal.platformatica.com", "https://portal.platformatica.com/next_tester.php?persona=customer");

    private static Properties credentials;
    static {
        renewCredentials();
    }

    private final String userNameKey = getName() + ".username";
    private final String passwordKey = getName() + ".password";

    private final String url;
    private final String credentialUrl;

    ProfileType(String url, String credentialUrl) {
        this.url = url;
        this.credentialUrl = credentialUrl;
    }

    public void setUserName(String userName) {
        credentials.setProperty(userNameKey, userName);
    }

    public String getUserName() {
        return credentials.getProperty(userNameKey);
    }

    public void setPassword(String password) {
        credentials.setProperty(passwordKey, password);
    }

    public String getPassword() {
        return credentials.getProperty(passwordKey);
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    public void get(WebDriver driver) {
        driver.get(url);
    }

    public void login(WebDriver driver) {
        if (Strings.isStringEmpty(this.getUserName()) || Strings.isStringEmpty(this.getPassword())) {
            throw new RuntimeException("Username or Password is empty");
        }

        login(driver, this.getUserName(), this.getPassword());
    }

    public void login(WebDriver driver, String userName, String password) {
        WebElement loginElement = driver.findElement(By.xpath("//input[@name='login_name']"));
        loginElement.sendKeys(userName);
        WebElement pasElement = driver.findElement(By.xpath("//input[@name='password']"));
        pasElement.sendKeys(password);
        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();
    }

    public void reset(WebDriver driver) {
        ProjectUtils.click(driver, driver.findElement(By.id("navbarDropdownProfile")));
        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[contains(text(), 'Reset')]")));
    }

    public static void renewCredentials() {
        ProfileType.credentials = getCredentials();
    }

    private static Properties getCredentials() {
        Properties properties = new Properties();
        try {
            if (!BaseTest.isRemoteWebDriver()) {
                InputStream inputStream = ProfileType.class.getClassLoader().getResourceAsStream("local.properties");
                if (inputStream == null) {
                    LoggerUtils.logRed("Copy and paste the local.properties.TEMPLATE file to local.properties");
                    System.exit(1);
                }

                properties.load(inputStream);
                return properties;
            }
        } catch (IOException ignore) {}

        if (BaseTest.isRemoteWebDriver() || Boolean.parseBoolean(properties.getProperty("serverCredentials"))) {
            for (ProfileType profile : ProfileType.values()) {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(profile.credentialUrl).openConnection();
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
}
