import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

public class EntityArithmeticInTest extends BaseTest {

    @Test
    private void getNewRecord () throws InterruptedException {

        final int num1 = 3;
        final int num2 = 7;
        final int sum = num1 + num2;
        final int sub = num1 - num2;
        final int mul = num1*num2;
        final int div = num1/num2;

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver,4);

        WebElement elementArithmetic =  driver.findElement(By.xpath("//p[contains(text(), 'Arithmetic Inline')]"));
        ProjectUtils.click(driver, elementArithmetic);

        WebElement clickNewFolder = driver.findElement(By.xpath("//i[contains(text(), 'create_new_folder')]"));
        ProjectUtils.click(driver, clickNewFolder);

        WebElement clickF1 = driver.findElement(By.xpath("//input[@id = 'f1']"));
        ProjectUtils.click(driver, clickF1);
        clickF1.sendKeys(Integer.toString(num1));

        WebElement clickF2 = driver.findElement(By.xpath("//input[@id = 'f2']"));
        ProjectUtils.click(driver, clickF2);
        clickF2.sendKeys(Integer.toString(num2));

        wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath("//input[@id = 'div']"), "0.43"));

        WebElement saveNewRecord = driver.findElement(By.xpath("//*[@id=\"pa-entity-form-save-btn\"]"));
        ProjectUtils.click(driver, saveNewRecord);

        WebElement clickNCheckRecord =
                driver.findElement(By.xpath("//*[@id=\"pa-all-entities-table\"]/tbody/tr/td[2]/a"));
        ProjectUtils.click(driver, clickNCheckRecord);

        WebElement findNCheckF1 =
                driver.findElement(By.xpath("//span[contains(text(), '3')]"));
        ProjectUtils.click(driver, findNCheckF1);

        WebElement findNCheckF2 =
                driver.findElement(By.xpath("//span[contains(text(), '7')]"));
        ProjectUtils.click(driver, findNCheckF1);

        Thread.sleep(5000);

        Assert.assertEquals(findNCheckF1.getText(), "3");
        Assert.assertEquals(findNCheckF2.getText(), "7");

        WebElement findNCheckSum =
                driver.findElement(By.xpath("//div[2]/div/div/div/div[3]/div"));
        Assert.assertEquals(findNCheckSum.getText(), Integer.toString(sum));

        WebElement findNCheckSub =
                driver.findElement(By.xpath("//div[2]/div/div/div/div[4]/div"));
        WebElement findNCheckMul =
                driver.findElement(By.xpath("//div[2]/div/div/div/div[5]/div"));
        WebElement findNCheckDiv =
                driver.findElement(By.xpath("//div[2]/div/div/div/div[6]/div"));

        Assert.assertEquals(findNCheckSub.getText(), Integer.toString(sub));
        Assert.assertEquals(findNCheckMul.getText(), Integer.toString(mul));
        Assert.assertEquals(findNCheckDiv.getText(), Integer.toString(div));
    }

        @Test
        private void setNewData () throws InterruptedException {

            final String F1 = "Hi";
            final String F2 = "Hi";

            WebDriver driver = getDriver();

            WebElement elementArithmetic =  driver.findElement(By.xpath("//p[contains(text(), 'Arithmetic Inline')]"));
            ProjectUtils.click(driver, elementArithmetic);

            WebElement clickNewFolder = driver.findElement(By.xpath("//i[contains(text(), 'create_new_folder')]"));
            ProjectUtils.click(driver, clickNewFolder);

            WebElement clickF1 = driver.findElement(By.xpath("//input[@id = 'f1']"));
            ProjectUtils.click(driver, clickF1);
            clickF1.sendKeys(F1);

            WebElement clickF2 = driver.findElement(By.xpath("//input[@id = 'f2']"));
            ProjectUtils.click(driver, clickF2);
            clickF2.sendKeys(F2);

            Thread.sleep(5000);

            driver.findElement(By.xpath("//*[@id=\"pa-entity-form-save-btn\"]")).click();

            WebElement error = driver.findElement(By.xpath("//*[@id = 'pa-error']"));
            Assert.assertEquals(error.getText(), "Error saving entity");
        }
}