import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

public class EntityReadonlyTest extends BaseTest {

    @Ignore
    @Test
    public void inputTest() {

        WebDriver driver = getDriver();

        ProjectUtils.loginProcedure(driver);

        driver.findElement(By.xpath("//p[contains(text(), 'Readonly')]")).click();
        driver.findElement(By.xpath("//i[contains(text(), 'create_new_folder')]")).click();

        WebElement inpString = driver.findElement(By.xpath("//input[@id='string']"));
        WebElement inpText = driver.findElement(By.xpath("//textarea[@id='text']"));
        WebElement inpDec = driver.findElement(By.xpath("//input[@id='decimal']"));
        WebElement inpInt = driver.findElement(By.xpath("//input[@id='int']"));
        WebElement inpDate = driver.findElement(By.xpath("//input[@id='date']"));
        WebElement inpDatetime = driver.findElement(By.xpath("//input[@id='datetime']"));
        WebElement btnAddEmbedRec = driver.findElement(By.xpath("//button[contains(text(),'+')]"));

        Assert.assertTrue(Boolean.parseBoolean(inpString.getAttribute("readonly")));
        Assert.assertTrue(Boolean.parseBoolean(inpText.getAttribute("readonly")));
        Assert.assertTrue(Boolean.parseBoolean(inpDec.getAttribute("readonly")));
        Assert.assertTrue(Boolean.parseBoolean(inpInt.getAttribute("readonly")));
        Assert.assertTrue(Boolean.parseBoolean(inpDate.getAttribute("readonly")));
        Assert.assertTrue(Boolean.parseBoolean(inpDatetime.getAttribute("readonly")));
        Assert.assertTrue(Boolean.parseBoolean(btnAddEmbedRec.getAttribute("disabled")));

        driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
        driver.findElement(By.xpath("//i[contains(text(),'format_line_spacing')]")).click();

        WebElement lastRow = driver.findElement(By.xpath("//tr[last()]"));
        Assert.assertEquals(lastRow.getText(),"String\n" +
                                                        "Text\n" +
                                                        "Int\n" +
                                                        "Decimal\n" +
                                                        "Date\n" +
                                                        "Datetime\n" +
                                                        "File\n" +
                                                        "File image\n" +
                                                        "User");
    }
}
