package runner;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import runner.type.ProfileType;

public abstract class ProjectUtils {

    @Deprecated
    public static WebDriver loginProcedure(WebDriver driver) {
        return driver;
    }

    @Deprecated
    public static WebDriver loginProcedure(WebDriver driver, ProfileType profileType) {
        return driver;
    }

    public static void reset(WebDriver driver) {
        click(driver, driver.findElement(By.id("navbarDropdownProfile")));
        click(driver, driver.findElement(By.xpath("//a[contains(text(), 'Reset')]")));
    }

    public static void login(WebDriver driver, ProfileType profileType) {
        if (Strings.isStringEmpty(profileType.getUserName()) || Strings.isStringEmpty(profileType.getPassword())) {
            throw new RuntimeException("Username or Password is empty");
        }

        WebElement loginElement = driver.findElement(By.xpath("//input[@name='login_name']"));
        loginElement.sendKeys(profileType.getUserName());
        WebElement pasElement = driver.findElement(By.xpath("//input[@name='password']"));
        pasElement.sendKeys(profileType.getPassword());
        WebElement button = driver.findElement(By.xpath("//button[text()='Sign in']"));
        button.click();
    }

    @Deprecated
    public static void login(WebDriver driver, String userName, String pas) {

    }

    /*
    *  The method helps to avoid - Element is not clickable at point (x,x). Other element would receive the click
    */
    public static void click(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()", element);
    }
}
