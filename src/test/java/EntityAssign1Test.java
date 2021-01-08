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

    private void changeUser(WebDriver driver, String user) {

        WebDriverWait wait = new WebDriverWait(driver, 5);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[contains(@href, 'id=37')]")));

        Select changeUser = new Select(driver.findElement(By.className("pa-list-assignee")));

        changeUser.selectByVisibleText(user);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[contains(@style,'bisque')]")));

        driver.navigate().refresh();
    }

    private String[] getAnotherUserCreds() {

        int counter = 0;

        while (counter < 11) {
            counter++;
            try {
                HttpURLConnection con = (HttpURLConnection) new URL("https://ref.eteam.work/next_tester.php").openConnection();
                con.setRequestMethod("GET");
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String response = in.readLine();
                    String[] responseArray = response.split(";");
                    if (!responseArray[0].equals(ProfileType.DEFAULT.getUserName())) {
                        con.disconnect();
                        return responseArray;
                    }
                }
                con.disconnect();
            } catch (IOException ignore) {}
        }
        throw new RuntimeException("Unable to get credentials for another account");
    }

    private void authentication(WebDriver driver, String username, String password) throws InterruptedException {

        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[text()='Log out']")));
        Thread.sleep(400);
        driver.findElement(By.xpath("//input[@name='login_name']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();
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

    @Ignore
    @Test (dependsOnMethods = "assignTest")
    public void editTest() throws InterruptedException {

        String[] anotherUserCreds = getAnotherUserCreds();
        String anotherUserLogin = anotherUserCreds[0];
        String anotherUserPassword = anotherUserCreds[1];

        WebDriver driver = getDriver();

        changeUser(driver, anotherUserLogin);

        WebElement selectedUser = driver.findElement(By.xpath
                (String.format("//option[@selected and text()='%s']", anotherUserLogin)));

        Assert.assertTrue(selectedUser.isDisplayed());

        driver.findElement(By.xpath("//li[@id='pa-menu-item-41']")).click();
        driver.findElement(By.xpath("//h3[contains(text(), 'Tasks for')]"));

        getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Assert.assertTrue(driver.findElements(By.xpath("//tbody/tr")).isEmpty());
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        authentication(driver, anotherUserLogin, anotherUserPassword);

        driver.findElement(By.xpath("//li[@id='pa-menu-item-41']")).click();

        getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Assert.assertTrue(driver.findElement(By.xpath("//tbody/tr")).isDisplayed());
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Ignore
    @Test (dependsOnMethods = "editTest")
    public void deleteTest() throws InterruptedException {

        WebDriver driver = getDriver();

        authentication(driver, ProfileType.DEFAULT.getUserName(), ProfileType.DEFAULT.getPassword());

        changeUser(driver, ProfileType.DEFAULT.getUserName());

        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[text()='delete']")));

        getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Assert.assertTrue(driver.findElements(By.xpath("//tbody/tr")).isEmpty());
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[contains(@href,'recycle_bin')]")));

        Assert.assertTrue(driver.findElement(By.xpath("//tbody/tr")).isDisplayed());

        driver.findElement(By.xpath("//li[@id='pa-menu-item-41']")).click();
        driver.findElement(By.xpath("//h3[contains(text(), 'Tasks for')]"));

        getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Assert.assertTrue(driver.findElements(By.xpath("//tbody/tr")).isEmpty());
    }
}