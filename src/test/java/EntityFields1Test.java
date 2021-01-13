import model.FieldsPage;
import model.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Run(run = RunType.Multiple)
public class EntityFields1Test extends BaseTest {

    @Test
    public void newRecord() {

        final String title = UUID.randomUUID().toString();
        ;
        final String comments = "TEST IT";
        final String int_ = "11";
        final String decimal = "0";

        final List<String> record = Arrays.asList(title, comments, int_, decimal, "", "");

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickMenuFields()
                .clickNewFolder()
                .sendKeys(title, comments, int_, decimal, "", "")
                .clickSaveButton();

        Assert.assertEquals(fieldsPage.getRowCount(), 1);
        Assert.assertEquals(fieldsPage.getRow(0), record);
    }

    @Ignore
    @Test(dependsOnMethods = "newRecord")
    public void editRecord() throws InterruptedException {

        final String newTitle = UUID.randomUUID().toString();
        final String newComments = "EDIT fields";
        final int newInt = 12;

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 4);

        WebElement tab = driver.findElement(By.xpath("//li[@id = 'pa-menu-item-45']"));
        tab.click();
        WebElement tab1 = driver.findElement(By.xpath("//div[@class = 'dropdown pull-left']"));
        tab1.click();
        WebElement edit = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'edit')]")));
        edit.click();

        WebElement first = driver.findElement(By.xpath("//input[contains(@name, 'title')]"));
        first.clear();
        ProjectUtils.sendKeys(first, newTitle);
        WebElement second = driver.findElement(By.xpath("//textarea[@id = 'comments']"));
        second.clear();
        ProjectUtils.sendKeys(second, newComments);
        WebElement tri = driver.findElement(By.xpath("//input[contains(@name, 'int')]"));
        tri.clear();
        ProjectUtils.sendKeys(tri, newInt);

        WebElement button2 = driver.findElement(By.xpath("//button[text() = 'Save']"));
        ProjectUtils.click(driver, button2);

        WebElement butt3 = driver.findElement(By.xpath("//div[contains(text() , '" + newTitle + "')]"));

        Assert.assertEquals(butt3.getText(), newTitle);
    }
}
