import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

@Run(run = RunType.Single)
public class EntityArithmeticFunctionTest extends BaseTest {

    private static final By ENTITY_ARITHMETIC_FUNCTION =
            By.xpath("//div[@id = 'menu-list-parent']//p[text() = ' Arithmetic Function ']");
    private static final By NEW_RECORD = By.xpath("//div[@class='card-icon']");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By VIEW_MODE = By.xpath("//ul[@x-placement='bottom-end']//a[text()='view']");
    private static final By EDIT_MODE = By.xpath("//ul[@x-placement='bottom-end']//a[text()='edit']");
    private static final By DELETE_BUTTON = By.xpath("//ul[@x-placement='bottom-end']//a[text()='delete']");
    private static final By ALL_RECORDS = By.xpath("//tr[@data-index]");

    private static final By INPUT_F1 = By.id("f1");
    private static final By INPUT_F2 = By.id("f2");
    private static final By SUM_VIEW_MODE = By.xpath("//div[3]//span");
    private static final By SUB_VIEW_MODE = By.xpath("//div[4]//span");
    private static final By MUL_VIEW_MODE = By.xpath("//div[5]//span");
    private static final By DIV_VIEW_MODE = By.xpath("//div[6]//span");
    private static final By SUM_EDIT_MODE = By.id("sum");
    private static final By SUB_EDIT_MODE = By.id("sub");
    private static final By MUL_EDIT_MODE = By.id("mul");
    private static final By DIV_EDIT_MODE = By.id("div");
    private static final By ERROR = By.id("pa-error");

    private static final int F1 = 12;
    private static final int F2 = 6;
    private static final int F3 = 24;
    private static final int F4 = 8;
    private static final String STRING = "Text";
    private static final String ERROR_MESSAGE = "Error saving entity";

    private void sendKeys(WebDriver driver, By locator, int number) {

        WebElement inputField = driver.findElement(locator);
        inputField.clear();
        inputField.sendKeys(String.valueOf(number));
    }

    private void sendKeys(WebDriver driver, By locator, String text) {

        WebElement inputField = driver.findElement(locator);
        inputField.clear();
        inputField.sendKeys(text);
    }

    private void setValuesF1F2(WebDriver driver, int number1, int number2) {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        sendKeys(driver, INPUT_F1, number1);
        wait.until(driver1 -> String.valueOf(number1).equals(driver.findElement(INPUT_F1).getAttribute("value")));

        sendKeys(driver, INPUT_F2, number2);
        wait.until(driver1 -> String.valueOf(number2).equals(driver.findElement(INPUT_F2).getAttribute("value")));

        wait.until(driver1 -> String.valueOf(number1 / number2).equals(driver.findElement(DIV_EDIT_MODE).getAttribute("value")));
    }

    private void setValuesF1F2String(WebDriver driver, String text1, String text2) {

        sendKeys(driver, INPUT_F1, text1);
        sendKeys(driver, INPUT_F2, text2);
    }

    private void clickMenuOfLastRecord(WebDriver driver) {

        int numberOfRecordsOnPage = driver.findElements(ALL_RECORDS).size();
        driver.findElements(ALL_RECORDS).get(numberOfRecordsOnPage-1).findElement(By.xpath("//td[8]")).click();
    }

    private void valuesAssertViewMode(WebDriver driver, int number1, int number2) {

        Assert.assertEquals(driver.findElement(SUM_VIEW_MODE).getText(), String.valueOf(number1 + number2));
        Assert.assertEquals(driver.findElement(SUB_VIEW_MODE).getText(), String.valueOf(number1 - number2));
        Assert.assertEquals(driver.findElement(MUL_VIEW_MODE).getText(), String.valueOf(number1 * number2));
        Assert.assertEquals(driver.findElement(DIV_VIEW_MODE).getText(), String.valueOf(number1 / number2));
    }

    private void valuesAssertEditMode(WebDriver driver, int number1, int number2) {

        Assert.assertEquals(driver.findElement(SUM_EDIT_MODE).getAttribute("value"), String.valueOf(number1 + number2));
        Assert.assertEquals(driver.findElement(SUB_EDIT_MODE).getAttribute("value"), String.valueOf(number1 - number2));
        Assert.assertEquals(driver.findElement(MUL_EDIT_MODE).getAttribute("value"), String.valueOf(number1 * number2));
        Assert.assertEquals(driver.findElement(DIV_EDIT_MODE).getAttribute("value"), String.valueOf(number1 / number2));
    }

    private void recordCreate(WebDriver driver, int f1, int f2) {

        driver.findElement(ENTITY_ARITHMETIC_FUNCTION).click();
        driver.findElement(NEW_RECORD).click();
        setValuesF1F2(driver, f1, f2);
        driver.findElement(SAVE_BUTTON).click();
    }

    @Test
    public void recordCreateTest() {

        WebDriver driver = getDriver();

        recordCreate(driver, F1, F2);

        Assert.assertEquals(driver.findElements(ALL_RECORDS).size(), 1);
        Assert.assertEquals(driver.findElements(ALL_RECORDS).get(0)
                .findElement(By.xpath("//td[2]")).getText(), String.valueOf(F1));
    }

    @Test
    public void recordViewTest() {

        WebDriver driver = getDriver();

        recordCreate(driver, F1, F2);

        clickMenuOfLastRecord(driver);
        ProjectUtils.click(driver, driver.findElement(VIEW_MODE));

        valuesAssertViewMode(driver, F1, F2);
    }


    @Test
    public void recordEditTest() {

        WebDriver driver = getDriver();

        recordCreate(driver, F1, F2);

        clickMenuOfLastRecord(driver);
        ProjectUtils.click(driver, driver.findElement(EDIT_MODE));

        setValuesF1F2(driver, F3, F4);
        valuesAssertEditMode(driver, F3, F4);

        driver.findElement(SAVE_BUTTON).click();
    }

    @Test
    public void recordCreateNegativeStringTest() {

        WebDriver driver = getDriver();

        recordCreate(driver, F1, F2);

        clickMenuOfLastRecord(driver);
        ProjectUtils.click(driver, driver.findElement(EDIT_MODE));

        setValuesF1F2String(driver, STRING, STRING);
        driver.findElement(SAVE_BUTTON).click();

        Assert.assertEquals(driver.findElement(ERROR).getText(), ERROR_MESSAGE);
    }

    @Test
    public void recordDeleteTest() {

        WebDriver driver = getDriver();

        driver.findElement(ENTITY_ARITHMETIC_FUNCTION).click();
        recordCreate(driver, F1, F2);

        clickMenuOfLastRecord(driver);
        ProjectUtils.click(driver, driver.findElement(DELETE_BUTTON));

        Assert.assertEquals(driver.findElements(ALL_RECORDS).size(), 0);
    }
}