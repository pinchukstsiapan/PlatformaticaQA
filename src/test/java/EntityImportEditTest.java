import org.testng.annotations.Ignore;
import runner.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.ProjectUtils;

public class EntityImportEditTest extends BaseTest {

    final String title = "String new Added";
    final String text = "Text new Added";
    final int integer = 123;
    final double decimal = 23.4;

    final String titleEdit = "String new Added Edit";
    final String textEdit = "Text new Added Edit";
    final int integerEdit = 1234;
    final double decimalEdit = 33.4;

    final String viewValue = "view";
    final String editValue = "edit";
    final String deleteValue = "delete";

    final String doImport = "Do import";
    final String customImport = "Custom Import";
    final String filteredImport2 = "Filtered Import2";
    final String filteredImport3 = "Filtered Import3";

    final String filteredImportResult = "This is a custom TEXT";

    private void clickImportValues(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement importExecutor = driver.findElement(By.xpath("//p[(text()=' Import values ')]"));
        js.executeScript("arguments[0].scrollIntoView();", importExecutor);
        importExecutor.click();
    }

    private void clickImportTag(WebDriver driver) {
        WebElement importTag = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        importTag.click();
    }

    private void clickImport(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement importExecutor = driver.findElement(By.xpath("//p[(text()=' Import ')]"));
        js.executeScript("arguments[0].scrollIntoView();", importExecutor);
        importExecutor.click();
    }

    private void userCreate(WebDriver driver) {

        WebElement importButton = driver.findElement(By.xpath("//i[contains(text(), 'create_new_folder')]"));
        importButton.click();

        WebElement stringElement = driver.findElement(By.id("string"));
        stringElement.sendKeys(title);

        WebElement textElement = driver.findElement(By.id("text"));
        textElement.sendKeys(text);

        WebElement integerElement = driver.findElement(By.id("int"));
        integerElement.sendKeys(String.valueOf(integer));

        WebElement decimalElement = driver.findElement(By.id("decimal"));
        decimalElement.sendKeys((String.valueOf(decimal)));

        WebElement dateElement = driver.findElement(By.id("date"));
        dateElement.click();

        WebElement dateTimeElement = driver.findElement(By.id("datetime"));
        dateTimeElement.click();

        WebElement save = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, save);
    }

    public void userEdit(WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 3);

        WebElement stringElement = driver.findElement(By.id("string"));
        wait.until(ExpectedConditions.elementToBeClickable(stringElement));
        stringElement.clear();
        stringElement.sendKeys(titleEdit);

        WebElement textElement = driver.findElement(By.id("text"));
        textElement.clear();
        textElement.sendKeys(textEdit);

        WebElement integerElement = driver.findElement(By.id("int"));
        integerElement.clear();
        integerElement.sendKeys(String.valueOf(integerEdit));

        WebElement decimalElement = driver.findElement(By.id("decimal"));
        decimalElement.clear();
        decimalElement.sendKeys(String.valueOf(decimalEdit));

        Thread.sleep(3000);
        WebElement save = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, save);
    }

    public void userDelete(WebDriver driver) {

        WebElement buttonEdit = driver.findElement(By.xpath("//i[normalize-space()='menu']"));
        buttonEdit.click();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement delete = buttonEdit.findElement(By.xpath("//a[contains(text(),'" + deleteValue + "')]"));
        wait.until(ExpectedConditions.elementToBeClickable(delete));
        delete.click();
    }

    @Ignore
    @Test
    public void importEditManually() throws InterruptedException {

        WebDriver driver = getDriver();
        ProjectUtils.reset(driver);
        clickImport(driver);
        WebDriverWait wait = new WebDriverWait(driver, 3);
        userCreate(driver);

        WebElement result = driver.findElement(By.xpath("//div[contains(text(),'" + title + "')]"));
        Assert.assertEquals(result.getText(), title);

        WebElement menuButton = driver.findElement(By.xpath("//i[normalize-space()='menu']"));
        menuButton.click();

        WebElement menuButtonValue = driver.findElement(By.xpath("//a[contains(text(),'" + editValue + "')]"));
        wait.until(ExpectedConditions.elementToBeClickable(menuButtonValue));
        menuButtonValue.click();

        userEdit(driver);
        WebElement resultEdit = driver.findElement(By.xpath("//div[contains(text(),'" + titleEdit + "')]"));
        Assert.assertEquals(resultEdit.getText(), titleEdit);

        userDelete(driver);
    }

    @Ignore
    @Test
    public void importEditDoImport() throws InterruptedException {
        WebDriver driver = getDriver();
        clickImportValues(driver);
        userCreate(driver);
        clickImport(driver);
        clickImportTag(driver);
        WebElement doImportBtn = driver.findElement(By.xpath("//input[@value='" + doImport + "']"));
        doImportBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement greenImport = driver.findElement(By.xpath("//i[contains(text(),'done_all')]"));
        wait.until(ExpectedConditions.elementToBeClickable(greenImport));
        greenImport.click();

        Thread.sleep(2000);
        userEdit(driver);
        WebElement resultEdit = driver.findElement(By.xpath("//div[contains(text() , '" + titleEdit + "')]"));
        Assert.assertEquals(resultEdit.getText(), titleEdit);

        userDelete(driver);
    }

    @Test
    public void importEditCustomImport() throws InterruptedException {
        WebDriver driver = getDriver();
        clickImportValues(driver);
        userCreate(driver);
        clickImport(driver);
        clickImportTag(driver);
        WebElement doImportBtn = driver.findElement(By.xpath("//input[@value='" + customImport + "']"));
        doImportBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement greenImport = driver.findElement(By.xpath("//i[contains(text(),'done_all')]"));
        wait.until(ExpectedConditions.elementToBeClickable(greenImport));
        greenImport.click();

        Thread.sleep(2000);
        userEdit(driver);
        WebElement resultEdit = driver.findElement(By.xpath("//div[contains(text() , '" + titleEdit + "')]"));
        Assert.assertEquals(resultEdit.getText(), titleEdit);

        userDelete(driver);
    }

    @Ignore
    @Test
    public void importEditFilteredImport3() throws InterruptedException {
        WebDriver driver = getDriver();
        clickImportValues(driver);
        userCreate(driver);
        clickImport(driver);
        clickImportTag(driver);
        WebElement doImportBtn = driver.findElement(By.xpath("//input[@value='" + filteredImport3 + "']"));
        doImportBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement greenImport = driver.findElement(By.xpath("//i[contains(text(),'done_all')]"));
        wait.until(ExpectedConditions.elementToBeClickable(greenImport));
        greenImport.click();

        userEdit(driver);
        WebElement resultEdit = driver.findElement(By.xpath("//div[contains(text() , '" + filteredImportResult + "')]"));
        Assert.assertEquals(resultEdit.getText(), filteredImportResult);

        Thread.sleep(2000);
        userDelete(driver);
    }

}


