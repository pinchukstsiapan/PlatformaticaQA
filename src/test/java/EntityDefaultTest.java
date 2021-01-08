import java.util.List;
import java.util.UUID;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

@Run(run = RunType.Single)

public class EntityDefaultTest extends BaseTest {

    private class FieldValues {
        String lineNumber;
        String fieldString;
        String fieldText;
        String fieldInt;
        String fieldDecimal;
        String fieldDate;
        String fieldDateTime;
        String fieldUser;

        private FieldValues(String lineNumber, String fieldString, String fieldText, String fieldInt, String fieldDecimal, String fieldDate,
                            String fieldDateTime, String fieldUser) {
            this.lineNumber = lineNumber;
            this.fieldString = fieldString;
            this.fieldText = fieldText;
            this.fieldInt = fieldInt;
            this.fieldDecimal = fieldDecimal;
            this.fieldDate = fieldDate;
            this.fieldDateTime = fieldDateTime;
            this.fieldUser = fieldUser;
        }
    }

    private static final By BY_STRING = By.id("string");
    private static final By BY_TEXT = By.id("text");
    private static final By BY_INT = By.id("int");
    private static final By BY_DECIMAL = By.id("decimal");
    private static final By BY_DATE = By.id("date");
    private static final By BY_DATETIME = By.id("datetime");
    private static final By BY_USER = By.xpath("//div[@id='_field_container-user']/div/button");
    private static final By BY_EMBEDD_STRING = By.xpath("//td/textarea[@id='t-11-r-1-string']");
    private static final By BY_EMBEDD_TEXT = By.xpath("//td/textarea[@id='t-11-r-1-text']");
    private static final By BY_EMBEDD_INT = By.xpath("//td/textarea[@id='t-11-r-1-int']");
    private static final By BY_EMBEDD_DECIMAL = By.xpath("//td/textarea[@id='t-11-r-1-decimal']");
    private static final By BY_EMBEDD_DATE = By.id("t-11-r-1-date");
    private static final By BY_EMBEDD_DATETIME = By.id("t-11-r-1-datetime");
    private static final By BY_EMBEDD_USER = By.xpath("//select[@id='t-11-r-1-user']/option[@value='0']");
    private static final By BY_SAVE_BUTTON = By.xpath("//button[.='Save']");
    private static final By BY_RECORD_HAMBURGER_MENU = By.xpath("//button[contains(@data-toggle, 'dropdown')] ");
    private static final By BY_DROPDOWN = (By.xpath("//select[@id = 'user']"));
    private static final By BY_EMBEDD_DROPDOWN = By.xpath("//select[@id='t-11-r-1-user']");
    private static final By BY_VIEW = By.xpath("//a[text() = 'view']");
    private static final By BY_EDIT = By.xpath("//a[text() = 'edit']");
    private static final By BY_DELETE = By.xpath("//a[text() = 'delete']");

    private final FieldValues defaultValues = new FieldValues(
            null,
            "DEFAULT STRING VALUE",
            "DEFAULT TEXT VALUE",
            "55",
            "110.32",
            "01/01/1970",
            "01/01/1970 00:00:00",
            "USER 1 DEMO");

    private final FieldValues defaultEmbeDValues = new FieldValues(
            "1",
            "Default String",
            "Default text",
            "77",
            "153.17",
            "",
            "",
            "Not selected");

    private final FieldValues changedDefaultValues = new FieldValues(
            null,
            "Changed default String",
            "Changed default Text",
             String.valueOf((int) (Math.random() * 100)),
             "33.33", //String.valueOf((int) (Math.random()*20000) / 100.0),
            "01/01/2021",
            "01/01/2021 12:34:56",
            "user115@tester.com");

    private final FieldValues changedEmbedDValues = new FieldValues(
            "1",
            "Changed EmbedD String",
            "Changed EmbedD Text",
            String.valueOf((int) (Math.random() * 100)),
            "55.55",   //String.valueOf((int) (Math.random()*20000) / 100.0 after a bug will be fixed),
            "12/12/2020",
            "12/12/2020 00:22:22",
            "User 4");

    private final FieldValues newValues = new FieldValues(
            null,
            UUID.randomUUID().toString(),
            "Some random text as Edited Text Value",
             String.valueOf((int) (Math.random() * 100)),
            "77.77",    //String.valueOf((int) (Math.random()*20000) / 100.0 after a bug will be fixed),
            "30/12/2020",
            "30/12/2020 12:34:56",
            "user100@tester.com");

    private final String[] NEW_VALUES = {null, newValues.fieldString, newValues.fieldText,
                  newValues.fieldInt, newValues.fieldDecimal,
                  newValues.fieldDate, newValues.fieldDateTime, null, null, newValues.fieldUser, null};

    private final String[] CHANGED_DEFAULT_VALUES = {changedDefaultValues.fieldString,
                   changedDefaultValues.fieldText, changedDefaultValues.fieldInt, changedDefaultValues.fieldDecimal,
                   changedDefaultValues.fieldDate, changedDefaultValues.fieldDateTime};

    private final String[] CHANGED_EMBEDD_VALUES = {changedEmbedDValues.lineNumber, changedEmbedDValues.fieldString,
                   changedEmbedDValues.fieldText, changedEmbedDValues.fieldInt, changedEmbedDValues.fieldDecimal,
                   changedEmbedDValues.fieldDate, changedEmbedDValues.fieldDateTime, null, null, changedEmbedDValues.fieldUser};

    private void assertAndReplace(WebDriver driver, By by, String oldValue, String newValue, boolean isEmbedD ) {
        WebElement element = driver.findElement(by);
        if (isEmbedD) {
            Assert.assertEquals(element.getText(), oldValue);
            element.click();
        } else {
            Assert.assertEquals(element.getAttribute("value"), oldValue);
        }
        element.clear();
        element.sendKeys(newValue);
        element.sendKeys("\t");
    }

    private void assertAndReplaceFieldUser(WebDriver driver, String oldValue, String newValue, By byUser, By bySelect ){
        WebElement fieldUser = driver.findElement(byUser);
        Assert.assertEquals(fieldUser.getText(), oldValue);
        Select userSelect = new Select(driver.findElement(bySelect));
        userSelect.selectByVisibleText(newValue);
    }

    private void createDefaultRecord(WebDriver driver) {

        driver.findElement(By.xpath("//a[@href='#menu-list-parent']")).click();
        driver.findElement(By.xpath("//i/following-sibling::p[contains (text(), 'Default')]")).click();
        WebElement createFolder = driver.findElement(By.xpath("//i[.='create_new_folder']/ancestor::a"));
        ProjectUtils.click(driver,createFolder);

        WebElement saveBtn = driver.findElement(BY_SAVE_BUTTON);
        ProjectUtils.click(driver, saveBtn);
    }

    private void selectFromRecordMenu (WebDriver driver, By byFunction) {

        driver.findElement(BY_RECORD_HAMBURGER_MENU).click();

        WebElement viewFunction = driver.findElement(byFunction);
        ProjectUtils.click(driver, viewFunction);
    }

    private void assertRecordValues(WebDriver driver, String xpath, String[] changed_default_values) {
        List<WebElement> rows = driver.findElements(By.xpath(xpath));
        Assert.assertEquals(rows.size(), changed_default_values.length);
        for (int i = 0; i < changed_default_values.length; i++) {
            if (changed_default_values[i] != null) {
                Assert.assertEquals(rows.get(i).getText(), changed_default_values[i]);
            }
        }
    }

    @Test
    public void checkDefaultValuesAndUpdate() {

        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//p[contains (text(), 'Default')]")).click();

        WebElement createFolder = driver.findElement(By.xpath("//i[.='create_new_folder']/ancestor::a"));
        ProjectUtils.click(driver, createFolder);

        assertAndReplace(driver, BY_STRING, defaultValues.fieldString, changedDefaultValues.fieldString, false);
        assertAndReplace(driver, BY_TEXT, defaultValues.fieldText, changedDefaultValues.fieldText, false);
        assertAndReplace(driver, BY_INT, defaultValues.fieldInt, changedDefaultValues.fieldInt, false);
        assertAndReplace(driver, BY_DECIMAL, defaultValues.fieldDecimal, changedDefaultValues.fieldDecimal, false);
        assertAndReplace(driver, BY_DATE, defaultValues.fieldDate, changedDefaultValues.fieldDate, false);
        assertAndReplace(driver, BY_DATETIME, defaultValues.fieldDateTime, changedDefaultValues.fieldDateTime, false);
        assertAndReplaceFieldUser(driver, defaultValues.fieldUser, changedDefaultValues.fieldUser, BY_USER, BY_DROPDOWN);

        WebElement greenPlus = driver.findElement(By.xpath("//button[@data-table_id='11']"));
        ProjectUtils.click(driver, greenPlus);

        WebElement lineNumber = driver.findElement(By.xpath("//input[@id='t-undefined-r-1-_line_number']"));
        Assert.assertEquals(lineNumber.getAttribute("data-row"), changedEmbedDValues.lineNumber);

        assertAndReplace(driver, BY_EMBEDD_STRING, defaultEmbeDValues.fieldString, changedEmbedDValues.fieldString, false);
        assertAndReplace(driver, BY_EMBEDD_TEXT, defaultEmbeDValues.fieldText, changedEmbedDValues.fieldText, false);
        assertAndReplace(driver, BY_EMBEDD_INT, defaultEmbeDValues.fieldInt, changedEmbedDValues.fieldInt, false);
        assertAndReplace(driver, BY_EMBEDD_DECIMAL, defaultEmbeDValues.fieldDecimal, changedEmbedDValues.fieldDecimal, false);
        assertAndReplace(driver, BY_EMBEDD_DATE, defaultEmbeDValues.fieldDate, changedEmbedDValues.fieldDate, true);
        assertAndReplace(driver, BY_EMBEDD_DATETIME, defaultEmbeDValues.fieldDateTime, changedEmbedDValues.fieldDateTime, true);
        assertAndReplaceFieldUser(driver,  defaultEmbeDValues.fieldUser,changedEmbedDValues.fieldUser, BY_EMBEDD_USER, BY_EMBEDD_DROPDOWN);

        WebElement saveBtn = driver.findElement(BY_SAVE_BUTTON);
        ProjectUtils.click(driver, saveBtn);

        selectFromRecordMenu(driver, BY_VIEW);

        assertRecordValues(driver, "//span[@class='pa-view-field']", CHANGED_DEFAULT_VALUES);

        WebElement fieldUser = driver.findElement(By.xpath("//div[@class='form-group']/p"));
        Assert.assertEquals(fieldUser.getText(), changedDefaultValues.fieldUser);

        assertRecordValues(driver, "//table/tbody/tr/td", CHANGED_EMBEDD_VALUES);
    }

    @Test
    public void deleteRecord() {

        WebDriver driver = getDriver();

        WebElement defaultBtn = driver.findElement(By.xpath("//p[contains(text(),' Default ')]"));
        ProjectUtils.click(driver,defaultBtn);

        WebElement newFolderBtn = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        ProjectUtils.click(driver,newFolderBtn);

        WebElement newField = driver.findElement(BY_STRING);
        newField.clear();
        newField.sendKeys(changedDefaultValues.fieldString);

        WebElement saveBtn = driver.findElement(BY_SAVE_BUTTON);
        ProjectUtils.click(driver,saveBtn);

        WebElement firstColumn = driver.findElement(By.xpath("//table/tbody/tr/td[2]"));
        Assert.assertEquals(firstColumn.getText(),changedDefaultValues.fieldString);

        selectFromRecordMenu(driver, BY_DELETE);

        WebElement recycleBin = driver.findElement(By.xpath("//i[contains(text(),'delete_outline')]"));
        ProjectUtils.click(driver, recycleBin);

        WebElement deletedRecord = driver.findElement(By.xpath(String.format("//b[contains(text(),'%s')]",
                                                                             changedDefaultValues.fieldString)));
        Assert.assertEquals(deletedRecord.getText(), changedDefaultValues.fieldString);

        WebElement deletePermanently = driver.findElement(By.xpath("//a[contains (text(), 'delete permanently')]"));
        deletePermanently.click();
    }

    @ Test
    public void editExistingRecord() {

        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//p[contains(text(), 'Default')]")).click();

        createDefaultRecord(driver);

        selectFromRecordMenu(driver, BY_EDIT);

        assertAndReplace(driver, BY_STRING, defaultValues.fieldString, newValues.fieldString, false);
        assertAndReplace(driver, BY_TEXT, defaultValues.fieldText, newValues.fieldText, false);
        assertAndReplace(driver, BY_INT, defaultValues.fieldInt, newValues.fieldInt, false);
        assertAndReplace(driver, BY_DECIMAL, defaultValues.fieldDecimal, newValues.fieldDecimal,false );
        assertAndReplace(driver, BY_DATE, defaultValues.fieldDate, newValues.fieldDate,false );
        assertAndReplace(driver, BY_DATETIME, defaultValues.fieldDateTime, newValues.fieldDateTime, false);
        Select userSelect = new Select(driver.findElement(By.xpath("//select[@id = 'user']")));
        userSelect.selectByVisibleText(newValues.fieldUser);

        WebElement saveButton = driver.findElement(BY_SAVE_BUTTON);
        ProjectUtils.click(driver, saveButton);

        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr"));
        Assert.assertEquals(rows.size(), 1);

        assertRecordValues(driver, "//table/tbody/tr/td", NEW_VALUES);
    }
}