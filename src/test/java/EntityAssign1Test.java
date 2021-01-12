import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.ProfileType;
import runner.type.Run;
import runner.type.RunType;

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
                ("//option[text()='" + ProfileType.DEFAULT.getUserName() + "']"));
        userSelect.click();

        driver.navigate().refresh();

        WebElement selectedUser = driver.findElement(By.xpath
                ("//option[@selected and text()='" + ProfileType.DEFAULT.getUserName() + "']"));

        Assert.assertTrue(selectedUser.isDisplayed());

        driver.findElement(By.xpath("//li[@id='pa-menu-item-41']")).click();
        driver.findElement(By.xpath("//h3[contains(text(), 'Tasks for')]"));

        Assert.assertTrue(driver.findElement(By.xpath("//tbody/tr")).isDisplayed());
    }

    @Test (dependsOnMethods = "assignTest")
    public void editTest() {

        String anotherUser = "user" + (int)(Math.random() * (249 - 100 + 1) + 100) + "@tester.com";

        WebDriver driver = getDriver();

        changeUser(driver, anotherUser);

        WebElement selectedUser = driver.findElement(By.xpath("//option[@selected and text()='" + anotherUser + "']"));

        Assert.assertTrue(selectedUser.isDisplayed());

        driver.findElement(By.xpath("//li[@id='pa-menu-item-41']")).click();
        driver.findElement(By.xpath("//h3[contains(text(), 'Tasks for')]"));

        getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Assert.assertTrue(driver.findElements(By.xpath("//tbody/tr")).isEmpty());
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test (dependsOnMethods = "editTest")
    public void deleteTest() {

        WebDriver driver = getDriver();

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
