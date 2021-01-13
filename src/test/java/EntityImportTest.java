import model.MainPage;
import model.RecycleBinPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.UUID;

public class EntityImportTest extends BaseTest {

    @Test
    public void deleteRecordFromEntityImport() {

        final String str = UUID.randomUUID().toString();

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickMenuImportValues()
                .clickNewFolder()
                .sendKeys(str)
                .clickSaveButton()
                .deleteRow()
                .clickRecycleBin();

        Assert.assertEquals(recycleBinPage.getDeletedImportValue(), str);
    }

    @Ignore
    @Test
    public void doImportButton() throws InterruptedException{

        WebDriver driver = getDriver();
        ProjectUtils.loginProcedure(driver);

        final String str = "Denys_Test_1";
        final String text = "Do_Import_Button";
        final int integer = 1;
        final double decimal = 1.55;
        final String user = "User 1 Demo";

        createRecordInImportValuesEntity(driver, str, text, integer, decimal);

        WebElement ImportEntity =
                driver.findElement(By.xpath("//a[@href='index.php?action=action_list&entity_id=17']"));
        ProjectUtils.click(driver, ImportEntity);
        WebElement createImportFolder = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        createImportFolder.click();
        WebElement doImportButton = driver.findElement(By.xpath("//input[@value='Do import']"));
        doImportButton.click();
        WebElement chooseRecord = driver.findElement(By.xpath("//i[text()='done_all']"));
        chooseRecord.click();
        Thread.sleep(1000);
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);
        WebElement selectImportedRecord = driver.findElement(By.xpath("//button/i[@class='material-icons']"));
        selectImportedRecord.click();
        WebElement viewRecord = driver.findElement(By.xpath("//a[text()='view']"));
        viewRecord.click();

        final String FIELD_XPATH = "(//label[text()='String']/following::div/div/span)";
        WebElement fieldString = driver.findElement(By.xpath(FIELD_XPATH + "[1]"));
        WebElement fieldText = driver.findElement(By.xpath(FIELD_XPATH + "[2]"));
        WebElement fieldInt = driver.findElement(By.xpath(FIELD_XPATH + "[3]"));
        WebElement fieldDecimal = driver.findElement(By.xpath(FIELD_XPATH + "[4]"));
        WebElement fieldUser = driver.findElement(By.xpath("//div[@class='form-group']//p"));

        Assert.assertEquals(fieldString.getText(), str);
        Assert.assertEquals(fieldText.getText(), text);
        Assert.assertEquals(fieldInt.getText(), String.valueOf(integer));
        Assert.assertEquals(fieldDecimal.getText(),String.valueOf(decimal));
        Assert.assertEquals(fieldUser.getText(), user);
    }

    @Ignore
    @Test
    public void customImportButton() throws InterruptedException{

        WebDriver driver = getDriver();
        ProjectUtils.loginProcedure(driver);

        final String str = "Denys_Test_2";
        final String text = "Custom_Import_Button";
        final int integer = 2;
        final double decimal = 2.55;
        final String user = "User 1 Demo";

        createRecordInImportValuesEntity(driver, str, text, integer, decimal);

        WebElement ImportEntity =
                driver.findElement(By.xpath("//a[@href='index.php?action=action_list&entity_id=17']"));
        ProjectUtils.click(driver, ImportEntity);
        WebElement createImportFolder = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        createImportFolder.click();
        WebElement doImportButton = driver.findElement(By.xpath("//input[@value='Custom Import']"));
        doImportButton.click();
        WebElement chooseRecord = driver.findElement(By.xpath("//i[text()='done_all']"));
        chooseRecord.click();
        Thread.sleep(1000);
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);
        WebElement selectImportedRecord = driver.findElement(By.xpath("//button/i[@class='material-icons']"));
        selectImportedRecord.click();
        WebElement viewRecord = driver.findElement(By.xpath("//a[text()='view']"));
        viewRecord.click();

        final String FIELD_XPATH = "(//label[text()='String']/following::div/div/span)";
        WebElement fieldString = driver.findElement(By.xpath(FIELD_XPATH + "[1]"));
        WebElement fieldText = driver.findElement(By.xpath(FIELD_XPATH + "[2]"));
        WebElement fieldInt = driver.findElement(By.xpath(FIELD_XPATH + "[3]"));
        WebElement fieldDecimal = driver.findElement(By.xpath(FIELD_XPATH + "[4]"));
        WebElement fieldUser = driver.findElement(By.xpath("//div[@class='form-group']//p"));

        Assert.assertEquals(fieldString.getText(), "This is a custom TEXT");
        Assert.assertEquals(fieldText.getText(), text);
        Assert.assertEquals(fieldInt.getText(), String.valueOf(integer));
        Assert.assertEquals(fieldDecimal.getText(),String.valueOf(decimal));
        Assert.assertEquals(fieldUser.getText(), user);
    }

    public void createRecordInImportValuesEntity(WebDriver driver, String str, String text, int integ, double decimal){

        WebElement importValuesTab = driver.findElement(By.xpath("//p[contains(text(),'Import values')]"));
        ProjectUtils.click(driver, importValuesTab);
        WebElement createImportValuesIcon = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createImportValuesIcon.click();

        WebElement stringInImportValueField = driver.findElement(By.xpath("//input[@id='string']"));
        stringInImportValueField.sendKeys(str);
        WebElement textInImportValueField = driver.findElement(By.xpath("//textarea[@id='text']"));
        textInImportValueField.sendKeys(text);
        WebElement intInImportValueField = driver.findElement(By.xpath("//input[@id='int']"));
        intInImportValueField.sendKeys(String.valueOf(integ));
        WebElement decimalInImportValueField = driver.findElement(By.xpath("//input[@id='decimal']"));
        decimalInImportValueField.sendKeys(String.valueOf(decimal));
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);
    }
}