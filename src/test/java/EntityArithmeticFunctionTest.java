import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

import java.util.List;

@Run(run= RunType.Multiple)
public class EntityArithmeticFunctionTest extends BaseTest {

    public void clickArithmeticFunction(WebDriver driver) {

        WebElement entityArithmeticFunction =
                driver.findElement(By.xpath("//div[@id = 'menu-list-parent']//p[text() = ' Arithmetic Function ']"));
        entityArithmeticFunction.click();
    }

    public void createNewRecord(WebDriver driver) {

        WebElement createNewRecord = driver.findElement(By.xpath("//div[@class='card-icon']"));
        createNewRecord.click();
    }

    public void setValuesF1F2(WebDriver driver, int f1, int f2) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement inputF1 = driver.findElement(By.id("f1"));
        inputF1.clear();
        inputF1.sendKeys(String.valueOf(f1));

        wait.until(driver1 -> inputF1.getAttribute("value").equals(String.valueOf(f1)));

        WebElement inputF2 = driver.findElement(By.id("f2"));
        inputF2.clear();
        inputF2.sendKeys(String.valueOf(f2));

        wait.until(driver1 -> inputF2.getAttribute("value").equals(String.valueOf(f2)));

        WebElement div = driver.findElement(By.id("div"));
        wait.until(driver1 -> div.getAttribute("value").equals(String.valueOf(f1 / f2)));
    }

    public void setValuesF1F2String(WebDriver driver, String f1, String f2) {

        WebElement inputF1 = driver.findElement(By.id("f1"));
        inputF1.clear();
        inputF1.sendKeys(String.valueOf(f1));

        WebElement inputF2 = driver.findElement(By.id("f2"));
        inputF2.clear();
        inputF2.sendKeys(String.valueOf(f2));
    }

    public void clickMenuOfLastRecord(WebDriver driver) {

        List <WebElement> allRecords = driver.findElements(By.xpath("//tr[@data-index]"));
        int length = allRecords.size();

        WebElement lastRecord = driver.findElement(By.xpath("//tr[@data-index][" + length +"]//td[8]"));
        lastRecord.click();
    }

    public void clickViewMode(WebDriver driver) {

        WebElement viewMode = driver.findElement(By.xpath("//ul[@x-placement='bottom-end']//a[text()='view']"));
        ProjectUtils.click(driver, viewMode);
    }

    public void clickEditMode(WebDriver driver) {

        WebElement editMode = driver.findElement(By.xpath("//ul[@x-placement='bottom-end']//a[text()='edit']"));
        ProjectUtils.click(driver, editMode);
    }

    public void clickDelete(WebDriver driver) {

        WebElement deleteRecord = driver.findElement(By.xpath("//ul[@x-placement='bottom-end']//a[text()='delete']"));
        ProjectUtils.click(driver, deleteRecord);
    }

    public void clickSaveButton(WebDriver driver) {

        WebElement saveButton = driver.findElement(By.id("pa-entity-form-save-btn"));
        saveButton.click();
    }

    public void valuesAssertViewMode(WebDriver driver, int f1, int f2) {

        WebElement sum = driver.findElement(By.xpath("//div[3]//span"));
        Assert.assertEquals(sum.getText(), String.valueOf(f1 + f2));

        WebElement sub = driver.findElement(By.xpath("//div[4]//span"));
        Assert.assertEquals(sub.getText(), String.valueOf(f1 - f2));

        WebElement mul = driver.findElement(By.xpath("//div[5]//span"));
        Assert.assertEquals(mul.getText(), String.valueOf(f1 * f2));

        WebElement div = driver.findElement(By.xpath("//div[6]//span"));
        Assert.assertEquals(div.getText(), String.valueOf(f1 / f2));
    }

    public void valuesAssertEditMode(WebDriver driver, int f1, int f2) {

        WebElement sum = driver.findElement(By.id("sum"));
        Assert.assertEquals(sum.getAttribute("value"), String.valueOf(f1 + f2));

        WebElement sub = driver.findElement(By.id("sub"));
        Assert.assertEquals(sub.getAttribute("value"), String.valueOf(f1 - f2));

        WebElement mul = driver.findElement(By.id("mul"));
        Assert.assertEquals(mul.getAttribute("value"), String.valueOf(f1 * f2));

        WebElement div = driver.findElement(By.id("div"));
        Assert.assertEquals(div.getAttribute("value"), String.valueOf(f1 / f2));
    }

    @Test
    public void tc001() {

        WebDriver driver = getDriver();
        clickArithmeticFunction(driver);
        createNewRecord(driver);

        final int f1 = 12;
        final int f2 = 6;

        setValuesF1F2(driver, f1, f2);

        valuesAssertEditMode(driver, f1, f2);

        clickSaveButton(driver);
    }

    @Test
    public void tc002 () {

        WebDriver driver = getDriver();
        clickArithmeticFunction(driver);
        clickMenuOfLastRecord(driver);
        clickViewMode(driver);

        final int f1 = 12;
        final int f2 = 6;

        valuesAssertViewMode(driver, f1, f2);
    }

    @Test
    public void tc003() {

        WebDriver driver = getDriver();
        clickArithmeticFunction(driver);
        clickMenuOfLastRecord(driver);
        clickEditMode(driver);

        final int f1 = 24;
        final int f2 = 8;

        setValuesF1F2(driver, f1, f2);

        valuesAssertEditMode(driver, f1, f2);

        clickSaveButton(driver);
    }

    @Test
    public void tc004() {

        WebDriver driver = getDriver();
        clickArithmeticFunction(driver);
        clickMenuOfLastRecord(driver);
        clickViewMode(driver);

        final int f1 = 24;
        final int f2 = 8;

        valuesAssertViewMode(driver, f1, f2);
    }

    @Test
    public void tc005() {

        WebDriver driver = getDriver();
        clickArithmeticFunction(driver);
        clickMenuOfLastRecord(driver);
        clickEditMode(driver);

        final int f1 = 24;
        final int f2 = 8;

        valuesAssertEditMode(driver, f1, f2);
    }

    @Test
    public void tc006() {

        WebDriver driver = getDriver();
        clickArithmeticFunction(driver);
        clickMenuOfLastRecord(driver);
        clickEditMode(driver);

        final String f1 = "f1";
        final String f2 = "f2";

        setValuesF1F2String(driver, f1, f2);

        clickSaveButton(driver);

        WebElement error = driver.findElement(By.id("pa-error"));
        Assert.assertEquals(error.getText(), "Error saving entity");
    }

    @Ignore
    @Test(priority=2)
    public void recordDelete(){

        WebDriver driver = getDriver();
        clickArithmeticFunction(driver);
        clickMenuOfLastRecord(driver);
        clickDelete(driver);
    }
}