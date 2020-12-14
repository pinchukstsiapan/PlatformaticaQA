import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class ProjectUtils {

    public static String URL = "https://ref.eteam.work";

    static {
        renewCredentials();
    }

    private static String login;
    private static String password;

    public static void renewCredentials() {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(URL + "/next_tester.php").openConnection();
            try {
                con.setRequestMethod("GET");
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
        			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        			String response = in.readLine();
                    String[] responseArray = response.split(";");

                    login = responseArray[0];
                    password = responseArray[1];
                }
            } finally {
                con.disconnect();
            }
        } catch (IOException ignore) {}
    }

    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }

    public static WebDriver goAndLogin(WebDriver driver) {
        driver.get(URL);
        login(driver);

        return driver;
    }

    public static void login(WebDriver driver) {
        login(driver, getLogin(), getPassword());
    }

    public static void login(WebDriver driver, String login, String pas) {
        WebElement loginElement = driver.findElement(By.xpath("//input[@name='login_name']"));
        loginElement.sendKeys(login);
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
