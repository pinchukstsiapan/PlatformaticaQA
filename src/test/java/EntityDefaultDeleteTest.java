import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.UUID;

public class EntityDefaultDeleteTest extends BaseTest {

    private static final String title = UUID.randomUUID().toString();

    @Test
    public void EntityDefaultDelete() throws InterruptedException {

        WebDriver driver = getDriver();

        WebElement defaultBtn = driver.findElement(By.xpath("//p[contains(text(),' Default ')]"));
        ProjectUtils.click(driver,defaultBtn);


        WebElement newFolderBtn = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        ProjectUtils.click(driver,newFolderBtn);

        WebElement newField = driver.findElement(By.xpath("//input[@name='entity_form_data[string]']"));
        newField.clear();
        newField.sendKeys(title);

        WebElement saveBtn = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver,saveBtn);

        WebElement list = driver.findElement(By.xpath("//*[@class='nav-link active']"));
        list.click();

        WebElement firstColumn = driver.findElement(By.xpath("//*[@id='pa-all-entities-table']/tbody/tr/td[2]/a"));
        Assert.assertEquals(firstColumn.getText(),title);

        WebElement actionBtn = driver.findElement(By.xpath("//button/i[contains(@class, 'material-icons')]/.."));
        actionBtn.click();

        WebElement deleteBtn = driver.findElement(By.xpath("//a[text() = 'delete']"));
        ProjectUtils.click(driver,deleteBtn);

        WebElement recycleBin = driver.findElement(By.xpath("//i[contains(text(),'delete_outline')]"));
        recycleBin.click();

        WebElement deletedField = driver.findElement(By.xpath("//*[contains(text(),'" + title + "')]"));
        Assert.assertEquals(deletedField.getText(), title);
    }
}