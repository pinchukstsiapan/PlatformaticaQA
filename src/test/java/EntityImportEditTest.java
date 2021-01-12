import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

public class EntityImportEditTest extends BaseTest {

    private static final String STRING = "String new Added";
    private static final String TEXT = "Text new Added";
    private static final int INTEGER = 123;
    private static final double DECIMAL = 23.4;

    private static final String STRING_EDIT = "Edited String";
    private static final String TEXT_EDIT = "Edited Text";
    private static final int INTEGER_EDIT = 432;
    private static final double DECIMAL_EDIT = 33.5;

    private static final String EDIT_VALUE = "edit";
    private static final String DELETE_VALUE = "delete";

    private static final String DO_IMPORT = "Do import";
    private static final String CUSTOM_IMPORT = "Custom Import";
    private static final String FILTERED_IMPORT_2 = "Filtered Import2";
    private static final String FILTERED_IMPORT_3 = "Filtered Import3";
    private static final String SELECT_FOR_EMBEDED = "Select For Embeded";
    private static final String SELECT_FOR_EMBEDED_CUSTOM = "Select For Embeded Custom";

    private static final String THIS_IS_A_CUSTOM_TEXT = "This is a custom TEXT";

    private void clickImportTag(WebDriver driver) {
        ProjectUtils.click(driver, driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]")));
    }

    private void clickImport(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement importExecutor = driver.findElement(By.xpath("//p[(text()=' Import ')]"));
        js.executeScript("arguments[0].scrollIntoView();", importExecutor);
        importExecutor.click();
    }

    private void clickImportValues(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement importExecutor = driver.findElement(By.xpath("//p[(text()=' Import values ')]"));
        js.executeScript("arguments[0].scrollIntoView();", importExecutor);
        importExecutor.click();
    }

    private void greenImportClick(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement greenImport = driver.findElement(By.xpath("//i[contains(text(),'done_all')]"));
        wait.until(ExpectedConditions.elementToBeClickable(greenImport));
        ProjectUtils.click(driver, greenImport);
    }

    private void okButton(WebDriver driver) {
        ProjectUtils.click(driver, driver
                .findElement(By.xpath("//div[@id='pa-ajax-data-27']//button[@type='button'][normalize-space()='OK']")));
    }

    private void saveButton(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement save = driver.findElement(By.id("pa-entity-form-save-btn"));
        wait.until(ExpectedConditions.elementToBeClickable(save));
        ProjectUtils.click(driver, save);
    }

    private void checkBoxClick(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement checkBox = driver.findElement(By.xpath("//span[@class='check']"));
        wait.until(ExpectedConditions.elementToBeClickable(checkBox));
        ProjectUtils.click(driver, checkBox);
    }

    private void userCreate(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement importButton = driver.findElement(By.xpath("//i[contains(text(), 'create_new_folder')]"));
        wait.until(ExpectedConditions.elementToBeClickable(importButton));
        importButton.click();

        WebElement stringElement = driver.findElement(By.id("string"));
        stringElement.sendKeys(STRING);

        WebElement textElement = driver.findElement(By.id("text"));
        textElement.sendKeys(TEXT);

        WebElement integerElement = driver.findElement(By.id("int"));
        integerElement.sendKeys(String.valueOf(INTEGER));

        WebElement decimalElement = driver.findElement(By.id("decimal"));
        decimalElement.sendKeys((String.valueOf(DECIMAL)));

        driver.findElement(By.id("date")).click();

        driver.findElement(By.id("datetime")).click();

        saveButton(driver);
    }

    private void userEdit(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 3);

        WebElement stringElement = driver.findElement(By.id("string"));
        wait.until(ExpectedConditions.elementToBeClickable(stringElement));
        ProjectUtils.click(driver, stringElement);
        stringElement.clear();
        stringElement.sendKeys(STRING_EDIT);

        WebElement textElement = driver.findElement(By.id("text"));
        wait.until(ExpectedConditions.elementToBeClickable(textElement));
        textElement.clear();
        textElement.sendKeys(TEXT_EDIT);

        WebElement integerElement = driver.findElement(By.id("int"));
        wait.until(ExpectedConditions.elementToBeClickable(integerElement));
        integerElement.clear();
        integerElement.sendKeys(String.valueOf(INTEGER_EDIT));

        WebElement decimalElement = driver.findElement(By.id("decimal"));
        wait.until(ExpectedConditions.elementToBeClickable(decimalElement));
        decimalElement.clear();
        decimalElement.sendKeys(String.valueOf(DECIMAL_EDIT));

        saveButton(driver);
    }

    private void userDelete(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement buttonEdit = driver.findElement(By.xpath("//i[normalize-space()='menu']"));
        ProjectUtils.click(driver, buttonEdit);
        WebElement delete = buttonEdit.findElement(By.xpath("//a[contains(text(),'" + DELETE_VALUE + "')]"));
        wait.until(ExpectedConditions.elementToBeClickable(delete));
        ProjectUtils.click(driver, delete);
    }

    @Test
    public void importEditManually() {

        WebDriver driver = getDriver();

        WebDriverWait wait = new WebDriverWait(driver, 3);

        clickImport(driver);

        userCreate(driver);

        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'" + STRING + "')]")).getText(), STRING);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//i[normalize-space()='menu']")));

        WebElement menuButtonValue = driver.findElement(By.xpath("//a[contains(text(),'" + EDIT_VALUE + "')]"));
        wait.until(ExpectedConditions.elementToBeClickable(menuButtonValue));
        ProjectUtils.click(driver, menuButtonValue);

        userEdit(driver);

        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text() , '" + STRING_EDIT + "')]"))
                .getText(), STRING_EDIT);

        userDelete(driver);
    }

    @Test
    public void importEditDoImport() {

        WebDriver driver = getDriver();

        clickImportValues(driver);

        userCreate(driver);

        clickImport(driver);

        clickImportTag(driver);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//input[@value='" + DO_IMPORT + "']")));

        greenImportClick(driver);

        userEdit(driver);

        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text() , '" + TEXT_EDIT + "')]"))
                .getText(), TEXT_EDIT);

        userDelete(driver);
    }

    @Ignore
    @Test
    public void importEditCustomImport() {

        WebDriver driver = getDriver();

        WebDriverWait wait = new WebDriverWait(driver, 3);

        clickImportValues(driver);

        userCreate(driver);

        clickImport(driver);

        clickImportTag(driver);

        WebElement customImportElement = driver.findElement(By.xpath("//input[@value='" + CUSTOM_IMPORT + "']"));
        wait.until(ExpectedConditions.elementToBeClickable(customImportElement));
        ProjectUtils.click(driver, customImportElement);

        greenImportClick(driver);

        userEdit(driver);

        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text() , '" + TEXT_EDIT + "')]"))
                .getText(), TEXT_EDIT);

        userDelete(driver);
    }

    @Ignore
    @Test
    public void importEditFilteredImport3() {

        WebDriver driver = getDriver();

        clickImportValues(driver);

        userCreate(driver);

        clickImport(driver);

        clickImportTag(driver);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//input[@value='" + DO_IMPORT + "']")));

        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[normalize-space()='New record']")));

        WebElement stringElement = driver.findElement(By.id("string"));
        stringElement.sendKeys(STRING);

        WebElement textElement = driver.findElement(By.id("text"));
        textElement.sendKeys(TEXT);

        saveButton(driver);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//input[@value='" + FILTERED_IMPORT_3 + "']")));

        greenImportClick(driver);

        userEdit(driver);

        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text() , '" + THIS_IS_A_CUSTOM_TEXT + "')]"))
                .getText(), THIS_IS_A_CUSTOM_TEXT);

        userDelete(driver);
    }

    @Ignore
    @Test
    public void importEditSelectForEmbeded() {

        WebDriver driver = getDriver();

        clickImportValues(driver);

        userCreate(driver);

        clickImport(driver);

        clickImportTag(driver);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//input[@value='" + SELECT_FOR_EMBEDED + "']")));

        checkBoxClick(driver);

        okButton(driver);

        saveButton(driver);

        WebElement resultEdit = driver.findElement(By.xpath("//div[contains(text() , '" + TEXT_EDIT + "')]"));
        Assert.assertEquals(resultEdit.getText(), TEXT_EDIT);
    }

    @Ignore
    @Test
    public void importEditSelectForEmbededCustom() {

        WebDriver driver = getDriver();

        clickImportValues(driver);

        userCreate(driver);

        clickImport(driver);

        clickImportTag(driver);

        ProjectUtils.click(driver, driver.findElement(By.xpath("//input[@value='" + SELECT_FOR_EMBEDED_CUSTOM + "']")));

        checkBoxClick(driver);

        okButton(driver);

        saveButton(driver);

        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text() , '" + TEXT_EDIT + "')]"))
                .getText(), TEXT_EDIT);
    }
}


