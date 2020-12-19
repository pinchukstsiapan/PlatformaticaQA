import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.List;

public class EntityInitTest extends BaseTest {

    @Test
    public void defaultInitRecordCheck() throws InterruptedException {

        final String stringLineDefaultText = "New String";
        final String textLineDefaultText = "New Text";
        final int intLineDefault = 2;
        final double decimalLineDefault = 3.14;
        final String dateLineDefault = "01/01/2020";
        final String dateTimeLineDefault = "31/12/2020 23:59:59";
        final String userDefault = "User 1 Demo";
        final String switchDefaultDisplayed = "1";
        final String listDefault = "Two";

        WebDriver driver = getDriver();
        ProjectUtils.loginProcedure(driver);


        WebElement init = driver.findElement(By.xpath("//p[contains (text(), 'Init')]/parent::a"));
        ProjectUtils.click(driver, init);

        WebElement createNewFolder = driver.findElement(By.xpath("//i[.='create_new_folder']/ancestor::a"));
        ProjectUtils.click(driver, createNewFolder);

        WebElement submitBtn = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, submitBtn);

        List<WebElement> listOfDropDownViewBtns = driver.findElements(By.xpath("//button[@data-toggle='dropdown']/following-sibling::ul/li/a[.='view']"));
        WebElement lastViewBtn = listOfDropDownViewBtns.get(listOfDropDownViewBtns.size() - 1);
        ProjectUtils.click(driver, lastViewBtn);

        WebElement defaultUser = driver.findElement(By.xpath("//div//following-sibling::label[.='User']//following-sibling::p"));

        List<WebElement> listOfDefaultValues = driver.findElements(By.xpath("//span[@class='pa-view-field']"));
        Assert.assertEquals(listOfDefaultValues.get(0).getText(), stringLineDefaultText);
        Assert.assertEquals(listOfDefaultValues.get(1).getText(), textLineDefaultText);
        Assert.assertEquals(listOfDefaultValues.get(2).getText(), intLineDefault + "");
        Assert.assertEquals(listOfDefaultValues.get(3).getText(), decimalLineDefault + "");
        Assert.assertEquals(listOfDefaultValues.get(4).getText(), dateLineDefault);
        Assert.assertEquals(listOfDefaultValues.get(5).getText(), dateTimeLineDefault);
        Assert.assertEquals(listOfDefaultValues.get(6).getText(), switchDefaultDisplayed);
        Assert.assertEquals(listOfDefaultValues.get(7).getText(), listDefault);
        Assert.assertTrue(defaultUser.getText().equals(userDefault));

        driver.navigate().back();

        List<WebElement> listOfDropDownEditBtns = driver.findElements(By.xpath("//button[@data-toggle='dropdown']/following-sibling::ul/li/a[.='edit']"));
        WebElement lastEditBtn = listOfDropDownEditBtns.get(listOfDropDownEditBtns.size() - 1);
        ProjectUtils.click(driver, lastEditBtn);

        WebElement stringData = driver.findElement(By.xpath("//input[@id='string']"));
        Assert.assertEquals(stringData.getAttribute("value"), stringLineDefaultText);

        WebElement textData = driver.findElement(By.xpath("//span//textarea[@id='text']"));
        Assert.assertEquals(textData.getText(), textLineDefaultText);

        WebElement intData = driver.findElement(By.xpath("//input[@id='int']"));
        Assert.assertEquals(intData.getAttribute("value"), intLineDefault + "");

        WebElement decimalData = driver.findElement(By.xpath("//input[@id='decimal']"));
        Assert.assertEquals(decimalData.getAttribute("value"), decimalLineDefault + "");

        WebElement dateData = driver.findElement(By.xpath("//input[@id='date']"));
        Assert.assertEquals(dateData.getAttribute("value"), dateLineDefault);

        WebElement dateTimeData = driver.findElement(By.xpath("//input[@id='datetime']"));
        Assert.assertEquals(dateTimeData.getAttribute("value"), dateTimeLineDefault.toUpperCase());

        WebElement userData = driver.findElement(By.xpath("//div[@class='filter-option-inner-inner' and .='User 1 Demo']"));
        Assert.assertEquals(userData.getText(), userDefault.toUpperCase());

        WebElement switchData = driver.findElement(By.xpath("//div[@class='togglebutton']/label/input"));
        Assert.assertTrue(switchData.isEnabled());

        WebElement listData = driver.findElement(By.xpath("//div[@class='filter-option-inner-inner' and .='Two']"));
        Assert.assertEquals(listData.getText(), listDefault.toUpperCase());

        driver.navigate().back();

        List<WebElement> deleteBtns = driver.findElements(By.xpath("//a[.='delete']"));
        WebElement lastRecordDeleteBtn = deleteBtns.get(deleteBtns.size() - 1);
        ProjectUtils.click(driver, lastRecordDeleteBtn);
   }
}
