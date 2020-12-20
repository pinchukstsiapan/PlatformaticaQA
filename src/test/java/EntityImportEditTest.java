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
    final String date = "14122020";
    final String dateTime = "14/19/2020 12:33:05";

    final String titleEdit = "String new Added Edit";
    final String textEdit = "Text new Added Edit";
    final int integerEdit = 1234;
    final double decimalEdit = 33.4;
    final String dateEdit = "14123020";
    final String dateTimeEdit = "14/19/2020 12:33:05";

    final String viewValue = "view";
    final String editValue = "edit";
    final String deleteValue = "delete";

    final String doImport = "Do import";
    final String customImport = "Custom Import";
    final String filteredImport2 = "Filtered Import2";
    final String filteredImport3 = "Filtered Import3";

    final String filteredImportResult = "This is a custom TEXT";

    private void saveButton(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

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

    public void userEdit(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 4);

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
        wait.until(ExpectedConditions.elementToBeClickable(decimalElement));
        decimalElement.clear();
        decimalElement.sendKeys(String.valueOf(decimalEdit));

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

    @Test
    public void importEditManually() {

        WebDriver driver = getDriver();
        ProjectUtils.reset(driver);
        clickImport(driver);
        WebDriverWait wait = new WebDriverWait(driver, 4);
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

}


