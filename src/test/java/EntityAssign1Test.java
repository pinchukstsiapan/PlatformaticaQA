import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.ProfileType;
import runner.type.Run;
import runner.type.RunType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Run(run = RunType.Multiple)
public class EntityAssign1Test extends BaseTest {

    private static final String STRING_INP = UUID.randomUUID().toString();
    private static final ProfileType PROFILE_TYPE = ProfileType.DEFAULT;
    private static final String FIRST_USER_NAME =  PROFILE_TYPE.getUserName();
    private static final String FIRST_USER_PASS =  PROFILE_TYPE.getPassword();

    private void changeUser(WebDriver driver, String user) {

        WebDriverWait wait = new WebDriverWait(driver, 5);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[contains(@href, 'id=37')]")));

        Select changeUser = new Select(driver.findElement(By.className("pa-list-assignee")));

        changeUser.selectByVisibleText(user);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[contains(@style,'bisque')]")));

        driver.navigate().refresh();
    }

    @Test
    public void assignTest() {

        WebDriver driver = getDriver();

        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[contains(@href, 'id=37')]")));
        driver.findElement(By.xpath("//i[contains(text(), 'create_new')]")).click();
        driver.findElement(By.id("string")).sendKeys(STRING_INP);
        driver.findElement(By.id("text")).sendKeys("test text");
        driver.findElement(By.id("int")).sendKeys(String.valueOf((int)(Math.random() * 100)));
        driver.findElement(By.id("decimal")).sendKeys(String.valueOf(Math.random()));
        driver.findElement(By.id("date")).click();
        driver.findElement(By.xpath("//input[@id='datetime']")).click();
        ProjectUtils.click(driver, driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")));
        WebElement userSelect = driver.findElement(By.xpath
                (String.format("//option[text()='%s']", ProfileType.DEFAULT.getUserName())));
        userSelect.click();

        driver.navigate().refresh();

        WebElement selectedUser = driver.findElement(By.xpath
                (String.format("//option[@selected and text()='%s']", ProfileType.DEFAULT.getUserName())));

        Assert.assertTrue(selectedUser.isDisplayed());

        driver.findElement(By.xpath("//li[@id='pa-menu-item-41']")).click();
        driver.findElement(By.xpath("//h3[contains(text(), 'Tasks for')]"));

        Assert.assertTrue(driver.findElement(By.xpath("//tbody/tr")).isDisplayed());
    }

    @Test (dependsOnMethods = "assignTest")
    public void editTest() {

        WebDriver driver = getDriver();
        ProfileType.renewCredentials();

        changeUser(driver, PROFILE_TYPE.getUserName());

        WebElement selectedUser = driver.findElement(By.xpath
                (String.format("//option[@selected and text()='%s']", ProfileType.DEFAULT.getUserName())));

        Assert.assertTrue(selectedUser.isDisplayed());

        driver.findElement(By.xpath("//li[@id='pa-menu-item-41']")).click();
        driver.findElement(By.xpath("//h3[contains(text(), 'Tasks for')]"));

        Assert.assertFalse(driver.findElement(By.className("col-md-12")).getText().contains(STRING_INP));

        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[text()='Log out']")));
        PROFILE_TYPE.login(driver);

        driver.findElement(By.xpath("//li[@id='pa-menu-item-41']")).click();

        //change to True
        Assert.assertFalse(driver.findElement(By.className("col-md-12")).getText().contains(STRING_INP));
    }

    @Test (dependsOnMethods = "editTest")
    public void deleteTest() throws InterruptedException {

        WebDriver driver = getDriver();

        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[text()='Log out']")));
        Thread.sleep(400);
        driver.findElement(By.xpath("//input[@name='login_name']")).sendKeys(FIRST_USER_NAME);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(FIRST_USER_PASS);
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();

//        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[text()='Log out']")));
//        PROFILE_TYPE.login(driver);

        changeUser(driver, FIRST_USER_NAME);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[text()='delete']")));

        Assert.assertFalse(driver.findElement(By.className("col-md-12")).getText().contains(STRING_INP));

        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[contains(@href,'recycle_bin')]")));

        Assert.assertTrue(driver.findElement(By.xpath("//tbody/tr")).isDisplayed());

        driver.findElement(By.xpath("//li[@id='pa-menu-item-41']")).click();

        Assert.assertFalse(driver.findElement(By.className("col-md-12")).getText().contains(STRING_INP));
    }
}