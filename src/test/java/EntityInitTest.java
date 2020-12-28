import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;
import java.util.List;


@Run(run = RunType.Multiple)
public class EntityInitTest extends BaseTest {

    final String userDefault = "User 1 Demo";
    final String[] defaultValuesArr = {"New String", "New Text", "2", "3.14", "01/01/2020", "31/12/2020 23:59:59", "1", "Two"};

    public void createDefaultInitRecord() {
        WebDriver driver = getDriver();

        WebElement init = driver.findElement(By.xpath("//p[contains (text(), 'Init')]/parent::a"));
        ProjectUtils.click(driver, init);

        WebElement createNewFolder = driver.findElement(By.xpath("//i[.='create_new_folder']/ancestor::a"));
        ProjectUtils.click(driver, createNewFolder);

        WebElement submitBtn = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, submitBtn);
    }

    public void viewFunctionClick() {
        WebDriver driver = getDriver();
        WebElement lastViewBtn;
        List<WebElement> listOfDropDownViewBtns = driver.findElements(By.xpath("//button[@data-toggle='dropdown']/following-sibling::ul/li/a[.='view']"));
        if (listOfDropDownViewBtns.size() == 1) {
            lastViewBtn = listOfDropDownViewBtns.get(0);
        } else {
            lastViewBtn = listOfDropDownViewBtns.get(listOfDropDownViewBtns.size() - 1);
        }
        ProjectUtils.click(driver, lastViewBtn);
    }

    public void editFunctionClick() {
        WebDriver driver = getDriver();
        WebElement lastEditBtn;
        List<WebElement> listOfDropDownEditBtns = driver.findElements(By.xpath("//button[@data-toggle='dropdown']/following-sibling::ul/li/a[.='edit']"));
        if (listOfDropDownEditBtns.size() == 1) {
            lastEditBtn = listOfDropDownEditBtns.get(0);
        } else {
            lastEditBtn = listOfDropDownEditBtns.get(listOfDropDownEditBtns.size() - 1);
        }
        ProjectUtils.click(driver, lastEditBtn);
    }


    @Test
    public void checkDefaultValuesInViewMode() {

        WebDriver driver = getDriver();
        createDefaultInitRecord();
        viewFunctionClick();

        WebElement defaultUser = driver.findElement(By.xpath("//div//following-sibling::label[.='User']//following-sibling::p"));

        List<WebElement> listOfDefaultValues = driver.findElements(By.xpath("//span[@class='pa-view-field']"));
        Assert.assertEquals(listOfDefaultValues.size(), defaultValuesArr.length);

        for (int i = 0; i < listOfDefaultValues.size(); i++) {
            Assert.assertEquals(listOfDefaultValues.get(i).getText(), defaultValuesArr[i]);
        }

        Assert.assertTrue(defaultUser.getText().equals(userDefault));
        driver.navigate().back();
    }

    @Test(dependsOnMethods = "checkDefaultValuesInViewMode")

    public void checkDefaultValuesInEditMode() {
        WebDriver driver = getDriver();
        checkDefaultValuesInViewMode();
        editFunctionClick();

        WebElement stringData = driver.findElement(By.xpath("//input[@id='string']"));
        Assert.assertEquals(stringData.getAttribute("value"), defaultValuesArr[0]);

        WebElement textData = driver.findElement(By.xpath("//span//textarea[@id='text']"));
        Assert.assertEquals(textData.getText(), defaultValuesArr[1]);

        WebElement intData = driver.findElement(By.xpath("//input[@id='int']"));
        Assert.assertEquals(intData.getAttribute("value"), defaultValuesArr[2]);

        WebElement decimalData = driver.findElement(By.xpath("//input[@id='decimal']"));
        Assert.assertEquals(decimalData.getAttribute("value"), defaultValuesArr[3]);

        WebElement dateData = driver.findElement(By.xpath("//input[@id='date']"));
        Assert.assertEquals(dateData.getAttribute("value"), defaultValuesArr[4]);

        WebElement dateTimeData = driver.findElement(By.xpath("//input[@id='datetime']"));
        Assert.assertEquals(dateTimeData.getAttribute("value"), defaultValuesArr[5]);

        WebElement userData = driver.findElement(By.xpath("//div[@class='filter-option-inner-inner' and .='User 1 Demo']"));
        Assert.assertEquals(userData.getText(), userDefault.toUpperCase());

        WebElement switchData = driver.findElement(By.xpath("//div[@class='togglebutton']/label/input"));
        Assert.assertTrue(switchData.isEnabled());

        WebElement listData = driver.findElement(By.xpath("//div[@class='filter-option-inner-inner' and .='Two']"));
        Assert.assertEquals(listData.getText(), defaultValuesArr[defaultValuesArr.length - 1].toUpperCase());
    }
}
