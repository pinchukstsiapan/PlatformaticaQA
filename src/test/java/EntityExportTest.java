import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Run(run = RunType.Multiple)
public class EntityExportTest extends BaseTest {
    public String exportString = "My String";
    public String exportText = "New text with 1234";
    public String exportInt = "1234";
    public String exportDec = "23.34";
    public String User = "User 1";
    public String tablString = "abc";
    public String tableTex = "abc123";
    public String tableInt = "124";
    public String tableDec = "34.56";
    private static final By BY_STRING = By.id("string");
    private static final By BY_TEXT = By.id("text");
    private static final By BY_INT = By.id("int");
    private static final By BY_DECIMAL = By.id("decimal");
    private static final By BY_DATE = By.id("date");
    private static final By BY_DATETIME = By.id("datetime");
    private static final String EDITED_STRING = "EditedString";
    private static final String EDITED_TEXT = "EditedText";
    private static final String EDITED_INT = "11";
    private static final String EDITED_DECIMAL = "11.11";
    private static final String EDITED_DATE = "02/01/2021";
    private static final String EDITED_DATETIME = "03/01/2021 13:13:13";

    SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
    public String Data = data.format(new Date());

    SimpleDateFormat dataTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public String DataTime = dataTime.format(new Date());

    private void sendKeys(WebDriver driver, By by, String text) {
        WebElement element = driver.findElement(by);
        element.clear();
        element.sendKeys(text);
    }

    private String getValue(WebDriver driver, By by) {
        return driver.findElement(by).getAttribute("value");
    }

    private void createFieldForm(WebDriver driver) {
        ProjectUtils.click(driver, driver.findElement(By.xpath("//p[contains(text(), ' Export ')]/..")));
        driver.findElement(By.xpath("//div/i")).click();

        sendKeys(driver, BY_STRING, exportString);
        sendKeys(driver, BY_TEXT, exportText);
        sendKeys(driver, BY_INT, exportInt);
        sendKeys(driver, BY_DECIMAL, exportDec);

        driver.findElement(By.id("date")).click();
        driver.findElement(By.id("date")).clear();
        driver.findElement(By.id("date")).sendKeys(Data);

        driver.findElement(By.id("datetime")).click();
        driver.findElement(By.id("datetime")).clear();
        driver.findElement(By.id("datetime")).sendKeys(DataTime);

        driver.findElement(By.xpath("//div[@id='_field_container-user']/div/button/div/div/div")).click();
        Select selDr = new Select(driver.findElement(By.id("user")));
        selDr.selectByVisibleText(User);
    }

    private void createEmbedExp(WebDriver driver) {
        WebElement addRecord = driver.findElement(By.xpath("//tr[@id='add-row-23']/td/button"));
        ProjectUtils.click(driver, addRecord);
        driver.findElement(By.id("t-undefined-r-1-_line_number")).click();

        driver.findElement(By.id("t-23-r-1-string")).click();
        driver.findElement(By.id("t-23-r-1-string")).clear();
        driver.findElement(By.id("t-23-r-1-string")).sendKeys(tablString);

        driver.findElement(By.xpath("//tr[@id='row-23-1']/td[4]")).click();
        driver.findElement(By.id("t-23-r-1-text")).click();
        driver.findElement(By.id("t-23-r-1-text")).clear();
        driver.findElement(By.id("t-23-r-1-text")).sendKeys(tableTex);

        driver.findElement(By.id("t-23-r-1-int")).click();
        driver.findElement(By.id("t-23-r-1-int")).clear();
        driver.findElement(By.id("t-23-r-1-int")).sendKeys(tableInt);

        driver.findElement(By.id("t-23-r-1-decimal")).click();
        driver.findElement(By.id("t-23-r-1-decimal")).clear();
        driver.findElement(By.id("t-23-r-1-decimal")).sendKeys(tableDec);

        driver.findElement(By.id("t-23-r-1-date")).click();
        driver.findElement(By.id("t-23-r-1-date")).clear();
        driver.findElement(By.id("t-23-r-1-date")).sendKeys(Data);

        driver.findElement(By.id("t-23-r-1-datetime")).click();
        driver.findElement(By.id("t-23-r-1-datetime")).clear();
        driver.findElement(By.id("t-23-r-1-datetime")).sendKeys(DataTime);

        driver.findElement(By.id("t-23-r-1-user")).click();
        new Select(driver.findElement(By.id("t-23-r-1-user"))).selectByVisibleText(User);

        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, submit);
    }

    private void clickSandwichAction(WebElement row, String menuItem) throws InterruptedException {
        row.findElement(By.tagName("button")).click();
        Thread.sleep(500);
        row.findElement(By.xpath(String.format("//li/a[contains(@href, '%s')]", menuItem.toLowerCase()))).click();
    }

    @Test
    public void inputTest() {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 4);

        createFieldForm(driver);
        createEmbedExp(driver);

        int size = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr")).size();

        List<WebElement> list = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[7]"));

        int number = 0;
        for (int i = 0; i < size; i++) {
            if (list.get(i).getText().equals(DataTime)) {
                number = i;
            }
        }

        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number + 1) + "]/td[2]")).getText(),
                exportString);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number + 1) + "]/td[3]")).getText(),
                exportText);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number + 1) + "]/td[4]")).getText(),
                exportInt);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number + 1) + "]/td[5]")).getText(),
                exportDec);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number + 1) + "]/td[6]")).getText(),
                Data);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number + 1) + "]/td[7]")).getText(),
                DataTime);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number + 1) + "]/td[9]")).getText(),
                User);

        driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number + 1) + "]/td[2]/a/div")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[2]")).getText(), tablString);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[3]")).getText(), tableTex);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[4]")).getText(), tableInt);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[5]")).getText(), tableDec);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[6]")).getText(), Data);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[7]")).getText(), DataTime);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[9]")).getText(), User);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//p[contains(text(), ' Export ')]/..")));
    }

    @Test(dependsOnMethods = "inputTest")
    public void viewTest() throws InterruptedException {

        WebDriver driver = getDriver();

        WebElement exportButton = driver.findElement(By.xpath("//div[@id= 'menu-list-parent']/ul/li[8]/a"));
        ProjectUtils.click(driver, exportButton);
        WebElement record = driver.findElement(By.xpath("//tbody/tr"));
        clickSandwichAction(record, "view");

        WebElement exportPage = driver.findElement(By.xpath("//h4[contains(text(),'Export')]"));
        WebElement verifyString = driver.findElement(By.xpath("//label[text()='String']/../div[1]//span"));
        WebElement verifyText = driver.findElement(By.xpath("//label[text()='String']/../div[2]//span"));
        WebElement verifyInt = driver.findElement(By.xpath("//label[text()='String']/../div[3]//span"));
        WebElement verifyDecimal = driver.findElement(By.xpath("//label[text()='String']/../div[4]//span"));
        WebElement tableNumberColumn = driver.findElement(By.xpath("//tbody/tr[1]/td[1]"));
        WebElement tableStringField = driver.findElement(By.xpath("//tbody/tr[1]/td[2]"));
        WebElement tableTextField = driver.findElement(By.xpath("//tbody/tr[1]/td[3]"));
        WebElement tableIntField = driver.findElement(By.xpath("//tbody/tr[1]/td[4]"));
        WebElement tableDecimalField = driver.findElement(By.xpath("//tbody/tr[1]/td[5]"));

        Assert.assertTrue(exportPage.isDisplayed());
        Assert.assertEquals(verifyString.getText(), exportString);
        Assert.assertEquals(verifyText.getText(), exportText);
        Assert.assertEquals(verifyInt.getText(), exportInt);
        Assert.assertTrue(verifyDecimal.isDisplayed());
        Assert.assertTrue(tableNumberColumn.isDisplayed());
        Assert.assertEquals(tableStringField.getText(), tablString);
        Assert.assertEquals(tableTextField.getText(), tableTex);
        Assert.assertEquals(tableIntField.getText(), tableInt);
        Assert.assertEquals(tableDecimalField.getText(), tableDec);
    }

    @Test(dependsOnMethods = {"inputTest", "viewTest"})
    public void editTest() throws InterruptedException {
        WebDriver driver = getDriver();
        WebElement export = driver.findElement(By.xpath("//div[@id= 'menu-list-parent']/ul/li[8]/a"));
        ProjectUtils.click(driver, export);
        WebElement createdRecord = driver.findElement(By.xpath("//tbody/tr"));
        clickSandwichAction(createdRecord, "edit");

        sendKeys(driver, BY_STRING, EDITED_STRING);
        sendKeys(driver, BY_TEXT, EDITED_TEXT);
        sendKeys(driver, BY_INT, EDITED_INT);
        sendKeys(driver, BY_DECIMAL, EDITED_DECIMAL);
        sendKeys(driver, BY_DATE, EDITED_DATE);
        sendKeys(driver, BY_DATETIME, EDITED_DATETIME);

        WebElement saveButton = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, saveButton);
        List<WebElement> savedRecord = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(savedRecord.size(), 1);
        Assert.assertEquals(savedRecord.get(0).findElements(By.tagName("td")).get(1).getText(), EDITED_STRING);
        Assert.assertEquals(savedRecord.get(0).findElements(By.tagName("td")).get(2).getText(), EDITED_TEXT);
        Assert.assertEquals(savedRecord.get(0).findElements(By.tagName("td")).get(3).getText(), EDITED_INT);
        Assert.assertEquals(savedRecord.get(0).findElements(By.tagName("td")).get(4).getText(), EDITED_DECIMAL);
        Assert.assertEquals(savedRecord.get(0).findElements(By.tagName("td")).get(5).getText(), EDITED_DATE);
        Assert.assertEquals(savedRecord.get(0).findElements(By.tagName("td")).get(6).getText(), EDITED_DATETIME);
    }

    @Test(dependsOnMethods = "inputTest")
    public void someLabelTest() throws InterruptedException {
        WebDriver driver = getDriver();
        WebElement export = driver.findElement(By.xpath("//div[@id= 'menu-list-parent']/ul/li[8]/a"));
        ProjectUtils.click(driver, export);

        WebElement recordLabel = driver.findElement(By.xpath("//a[contains(text(),'Some label')]"));
        ProjectUtils.click(driver,recordLabel);
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        saveButton.click();

        WebElement exportDistination = driver.findElement(By.xpath("//div[@id= 'menu-list-parent']/ul/li[9]/a"));
        ProjectUtils.click(driver,exportDistination);
        WebElement verifyRecord = driver.findElement(By.xpath("//tbody/tr/td[2]/a"));
        verifyRecord.click();

        WebElement verifyString = driver.findElement(By.xpath("//label[text()='String']/../div[1]//span"));
        WebElement verifyText = driver.findElement(By.xpath("//label[text()='String']/../div[2]//span"));
        WebElement verifyInt = driver.findElement(By.xpath("//label[text()='String']/../div[3]//span"));
        WebElement verifyDecimal = driver.findElement(By.xpath("//label[text()='String']/../div[4]//span"));
        WebElement tableNumberColumn = driver.findElement(By.xpath("//tbody/tr[1]/td[1]"));
        WebElement tableStringField = driver.findElement(By.xpath("//tbody/tr[1]/td[2]"));
        WebElement tableTextField = driver.findElement(By.xpath("//tbody/tr[1]/td[3]"));
        WebElement tableIntField = driver.findElement(By.xpath("//tbody/tr[1]/td[4]"));
        WebElement tableDecimalField = driver.findElement(By.xpath("//tbody/tr[1]/td[5]"));

        Assert.assertEquals(verifyString.getText(), exportString);
        Assert.assertEquals(verifyText.getText(), exportText);
        Assert.assertEquals(verifyInt.getText(), exportInt);
        Assert.assertTrue(verifyDecimal.isDisplayed());
        Assert.assertTrue(tableNumberColumn.isDisplayed());
        Assert.assertEquals(tableStringField.getText(), tablString);
        Assert.assertEquals(tableTextField.getText(), tableTex);
        Assert.assertEquals(tableIntField.getText(), tableInt);
        Assert.assertEquals(tableDecimalField.getText(), tableDec);


    }
}