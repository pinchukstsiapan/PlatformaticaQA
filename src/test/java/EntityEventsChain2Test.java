import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Run(run = RunType.Single)
public class EntityEventsChain2Test extends BaseTest {

    private static final String URL_CHAIN2_RECORDS = "https://ref.eteam.work/index.php?action=action_list&entity_id=62";
    private static final String ERROR_MESSAGE = "Error saving entity";

    // positive scenario EXPECTED_VALUES:
    private static final String[] EXPECTED_VALUES_FOR_F1_1 = {"1", "1", "2", "3", "5", "8", "13", "21", "34", "55"};
    private static final String[] EXPECTED_VALUES_FOR_F1_0 = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
    private static final String[] EXPECTED_VALUES_FOR_F1_10 =
            {"10", "10", "20", "30", "50", "80", "130", "210", "340", "550"};
    private static final String[] EXPECTED_VALUES_FOR_F1_NEGATIVE_1 =
            {"-1", "-1", "-2", "-3", "-5", "-8", "-13", "-21", "-34", "-55"};
    private static final String[] EXPECTED_VALUES_FOR_F1_999 =
            {"999", "999", "1998", "2997", "4995", "7992", "12987", "20979", "33966", "54945"};

    // negative scenario EXPECTED_VALUES:
    private static final String[] EXPECTED_VALUES_FOR_F1_TO_F10_UUID =
            {UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                    UUID.randomUUID().toString()};

    // failing EXPECTED_VALUES (should be re-tested after fixed:)
    private static final String[] EXPECTED_VALUES_FOR_F1_3000000 =
            {"3000000", "3000000", "6000000", "9000000", "15000000", "24000000", "39000000",
                    "63000000", "102000000", "165000000"};
    private static final String[] EXPECTED_VALUES_FOR_F1_0_Point_99 =
            {"0.99", "0.99", "1.98", "2.97", "4.95", "7.92", "12.87", "20.79", "33.66", "54.45"};
    private static final String[] EXPECTED_VALUES_FOR_F1_0_Point_999 =
            {"0.999", "1.00", "2.00", "3", "5", "8", "13", "21", "34", "55"};
    private static final String[] EXPECTED_VALUES_FOR_F1_POINT_1 =
            {"0.1", "0.1", "0.2", "0.3", "0.5", "0.8", "1.3", "2.1", "3.4", "5.5"};
    private static final String[] EXPECTED_VALUES_FOR_F1_3000000000 =
            {"3000000000", "3000000000", "6000000000", "9000000000", "15000000000", "24000000000", "39000000000",
                    "63000000000", "102000000000", "165000000000"};

    private static final By ENTITY_EVENT_CHAIN2_TAB = By.cssSelector("#menu-list-parent>ul>li>a[href*='id=62");
    private static final By CREATE_NEW_RECORD = By.cssSelector("div.card-icon>i");
    private static final By CHAIN2_RECORDS = By.cssSelector("#pa-all-entities-table>tbody>tr");
    private static final By CELLS_IN_RECORD = By.cssSelector("td");
    private static final By MODES_MENU = By.cssSelector("div.dropdown.pull-left>button>i");
    private static final By VIEW_MODE = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='view']");
    private static final By EDIT_MODE = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='edit']");
    private static final By DELETE = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='delete']");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By ACTUAL_VALUES_F1_TO_F10_VIEW_MODE = By.cssSelector(".pa-view-field");
    private static final By ACTUAL_VALUES_F1_TO_F10_EDIT_MODE = By.cssSelector("input[id*='f']");
    private static final By ERROR = By.id("pa-error");
    private static final By F1 = By.id("f1");
    private static final By F2 = By.id("f2");
    private static final By F3 = By.id("f3");
    private static final By F4 = By.id("f4");
    private static final By F5 = By.id("f5");
    private static final By F6 = By.id("f6");
    private static final By F7 = By.id("f7");
    private static final By F8 = By.id("f8");
    private static final By F9 = By.id("f9");
    private static final By F10 = By.id("f10");

    private void sleep(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    private void click(By element) {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    private By getFLocator(int f) {
        final List<By> f_locators = new ArrayList<>();
        f_locators.add(F1);
        f_locators.add(F2);
        f_locators.add(F3);
        f_locators.add(F4);
        f_locators.add(F5);
        f_locators.add(F6);
        f_locators.add(F7);
        f_locators.add(F8);
        f_locators.add(F9);
        f_locators.add(F10);

        By f_locator = null;
        if (f > 0 && f <= 10) {
            f_locator = f_locators.get(f - 1);
        } else {
            Assert.fail("Incorrect f");
        }
        return f_locator;
    }

    private String getF_Value(int f, String[] expectedValues) {
        String f_Value = "";
        if (f > 0 && f <= 10) {
            f_Value = expectedValues[f-1];
        } else {
            Assert.fail("Incorrect F_");
        }
        return f_Value;
    }

    private void inputF1Value(String f1Value, WebDriver driver) {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(F1)).sendKeys(f1Value);
        getWebDriverWait().until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(F10),"value"));
    }

    public void editValue(String fValue, By f, WebDriver driver) {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(f)).clear();
        driver.findElement(f).sendKeys(fValue);
    }

    public void openRecordOn(Mode mode, WebDriver driver) {
        driver.get(URL_CHAIN2_RECORDS);
        click(MODES_MENU);

        switch (mode)
        {
            case VIEW:
                ProjectUtils.click(driver, driver.findElement(VIEW_MODE));
                break;
            case EDIT:
                ProjectUtils.click(driver, driver.findElement(EDIT_MODE));
                break;
            case DELETE:
                ProjectUtils.click(driver, driver.findElement(DELETE));
                break;
            default:
                Assert.fail("Incorrect Mode Choice");
        }
    }

    public void assertOnlyOneRecordSaved(WebDriver driver) {
        final List<WebElement> records = driver.findElements(CHAIN2_RECORDS);
        final List<WebElement> columns = records.get(0).findElements(CELLS_IN_RECORD);

        Assert.assertEquals(records.size(), 1);
        Assert.assertEquals(columns.size(), 12);
    }

    public void assertRecordDeleted(WebDriver driver) {
        final List<WebElement> records = driver.findElements(CHAIN2_RECORDS);

        Assert.assertEquals(records.size(), 0);
    }

    public void assertValuesAsExpected(String[] expectedValues, Mode mode, WebDriver driver) {
        switch (mode)
        {
            case VIEW:
                final List<WebElement> actualValuesView = driver.findElements(ACTUAL_VALUES_F1_TO_F10_VIEW_MODE);

                for (int i = 0; i < actualValuesView.size(); i++) {
                    Assert.assertEquals(actualValuesView.get(i).getText(), expectedValues[i]);
                }
                break;

            case EDIT:
                final List<WebElement> actualValuesElements = driver.findElements(ACTUAL_VALUES_F1_TO_F10_EDIT_MODE);

                for (int i = 0; i < actualValuesElements.size(); i++) {
                    Assert.assertEquals(actualValuesElements.get(i).getAttribute("value"), expectedValues[i]);
                }
                break;

            default:
                Assert.fail("Incorrect Mode Choice");
        }
    }

    public void assertError(By error, String expectedErrorMessage, WebDriver driver) {
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(error));
        String actualErrorMessage = driver.findElement(error).getText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
@Ignore
    @Test
    public void createNewRecordAndVerifyOnViewMode() {

        WebDriver driver = getDriver();

        final List<String> f1Values = new ArrayList<>();
        f1Values.add(getF_Value(1, EXPECTED_VALUES_FOR_F1_1));
        f1Values.add(getF_Value(1, EXPECTED_VALUES_FOR_F1_10));

        final List<String[]> expectedValues = new ArrayList<>();
        expectedValues.add(EXPECTED_VALUES_FOR_F1_1);
        expectedValues.add(EXPECTED_VALUES_FOR_F1_10);

        for (int index = 0; index < expectedValues.size();) {
            for (String f1Value : f1Values) {

                click(ENTITY_EVENT_CHAIN2_TAB);
                click(CREATE_NEW_RECORD);
                inputF1Value(f1Value, driver);
                click(SAVE_BUTTON);
                assertOnlyOneRecordSaved(driver);

                openRecordOn(Mode.VIEW, driver);
                assertValuesAsExpected(expectedValues.get(index), Mode.VIEW, driver);

                openRecordOn(Mode.DELETE, driver);
                assertRecordDeleted(driver);

                index++;
            }
        }
    }

    @Test
    public void editRecordAndVerifyOnEditMode() throws InterruptedException {

        WebDriver driver = getDriver();

        final List<String> f1Values = new ArrayList<>();
        f1Values.add(getF_Value(1, EXPECTED_VALUES_FOR_F1_1));
        f1Values.add(getF_Value(1, EXPECTED_VALUES_FOR_F1_0));
        f1Values.add(getF_Value(1, EXPECTED_VALUES_FOR_F1_NEGATIVE_1));
        f1Values.add(getF_Value(1, EXPECTED_VALUES_FOR_F1_999));

        final List<String[]> expectedValues = new ArrayList<>();
        expectedValues.add(EXPECTED_VALUES_FOR_F1_1);
        expectedValues.add(EXPECTED_VALUES_FOR_F1_0);
        expectedValues.add(EXPECTED_VALUES_FOR_F1_NEGATIVE_1);
        expectedValues.add(EXPECTED_VALUES_FOR_F1_999);

        for (int index = 0; index < expectedValues.size();) {
            for (String f1Value : f1Values) {
                switch (index % 2) {
                    case 0:
                        click(ENTITY_EVENT_CHAIN2_TAB);
                        click(CREATE_NEW_RECORD);
                        inputF1Value(f1Value, driver);
                        click(SAVE_BUTTON);
                        assertOnlyOneRecordSaved(driver);

                        openRecordOn(Mode.EDIT, driver);
                        assertValuesAsExpected(expectedValues.get(index), Mode.EDIT, driver);

                        index++;
                        break;
                    case 1:
                        editValue(f1Value, F1, driver);
                        sleep(2000);
                        click(SAVE_BUTTON);
                        assertOnlyOneRecordSaved(driver);

                        openRecordOn(Mode.EDIT, driver);
                        assertValuesAsExpected(expectedValues.get(index), Mode.EDIT, driver);

                        openRecordOn(Mode.DELETE, driver);
                        assertRecordDeleted(driver);

                        index++;
                        break;
                    default:
                        Assert.fail("No values found");
                        break;
                }
            }
        }
    }

    @Test
    private void editRecordWithInvalidValues() throws InterruptedException {
        final String f1Value = "1";

        WebDriver driver = getDriver();

        click(ENTITY_EVENT_CHAIN2_TAB);
        click(CREATE_NEW_RECORD);
        inputF1Value(f1Value, driver);
        click(SAVE_BUTTON);
        assertOnlyOneRecordSaved(driver);

        openRecordOn(Mode.EDIT, driver);
        for (int i = 1; i <= EXPECTED_VALUES_FOR_F1_TO_F10_UUID.length; i ++) {
            editValue(EXPECTED_VALUES_FOR_F1_TO_F10_UUID[i-1], getFLocator(i), driver);
            sleep(1500);
        }
        click(SAVE_BUTTON);
        sleep(500);

        assertError(ERROR, ERROR_MESSAGE, driver);
    }

    enum Mode
    {
        VIEW, EDIT, DELETE
    }
}