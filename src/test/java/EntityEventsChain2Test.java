import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.List;
import java.util.UUID;

public class EntityEventsChain2Test extends BaseTest {

    By entityEventsChain2Tab = By.cssSelector("#menu-list-parent>ul>li>a[href*='id=62");
    By createNewRecord = By.cssSelector("div.card-icon>i");
    By view = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='view']");
    By edit = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='edit']");
    By delete = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='delete']");
    By saveButton = By.cssSelector("#pa-entity-form-save-btn");
    By f1 = By.cssSelector("#f1");
    By f2 = By.cssSelector("#f2");
    By f3 = By.cssSelector("#f3");
    By f4 = By.cssSelector("#f4");
    By f5 = By.cssSelector("#f5");
    By f6 = By.cssSelector("#f6");
    By f7 = By.cssSelector("#f7");
    By f8 = By.cssSelector("#f8");
    By f9 = By.cssSelector("#f9");
    By f10 = By.cssSelector("#f10");

    @Ignore
    @Test
    public void createSaveAssertNewRecord() throws InterruptedException {
        final int f1Value = 1;
        final String[] expectedValuesF1ToF10 = {"1", "1", "2", "3", "5", "8", "13", "21", "34", "55"};

        createNewEntityEventsChain2Record(f1Value);

        clickOn(view);

        assertValuesPopulatedAsExpected(expectedValuesF1ToF10);
    }

    @Test
    public void replaceF1WithZeroAssertAllZeros() throws InterruptedException {
        final int f1OldValue = 1;
        final int f1NewValue = 0;
        final String[] expectedValuesF1ToF10 = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};

        createNewEntityEventsChain2Record(f1OldValue);

        clickOn(edit);

        replaceElementWithNewIntValue(f1, f1NewValue);

        clickOnSave();

        clickOn(view);

        assertValuesPopulatedAsExpected(expectedValuesF1ToF10);
    }

    @Test
    public void assertValuesOnViewMode() throws InterruptedException {
        final int f1Value = 5;
        final String[] expectedValuesF1ToF10 = {"5", "5", "10", "15", "25", "40", "65", "105", "170", "275"};

        createNewEntityEventsChain2Record(f1Value);

        clickOn(view);

        assertValuesPopulatedAsExpected(expectedValuesF1ToF10);
    }

    @Test
    public void assertValuesOnEditMode() throws InterruptedException {
        final int f1Value = 3;
        final String[] expectedValuesF1ToF10 = {"3", "3", "6", "9", "15", "24", "39", "63", "102", "165"};

        createNewEntityEventsChain2Record(f1Value);

        clickOn(edit);

        assertValuesPopulatedAsExpected(expectedValuesF1ToF10);
    }

    @Ignore
    @Test
    public void replaceWithInvalidValuesAssertError() throws InterruptedException {
        final int f1Value = 415;
        final int elementInLastRecord = 3-1;
        final By elementToReplace = f3;

        createNewEntityEventsChain2Record(f1Value);

        clickOn(edit);

        String replacedText = UUID.randomUUID().toString();

        replaceElementWithNewStringValue(elementToReplace, replacedText);

        clickOnSave();

        assertErrorForInvalidValues();

        assertRecordNotCreated(elementInLastRecord, replacedText);
    }

    private void sleep(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    private void createNewEntityEventsChain2Record(int f1Value) throws InterruptedException {
        final String url = "https://ref.eteam.work";

        WebDriver driver = getDriver();
        driver.get(url);
        driver.manage().window().maximize();
        ProjectUtils.loginProcedure(driver);

        driver.findElement(entityEventsChain2Tab).click();
        driver.findElement(createNewRecord).click();
        sleep(1000);
        driver.findElement(f1).sendKeys(String.valueOf(f1Value));
        sleep(2000);
        clickOnSave();
        sleep(1000);
    }

    public void replaceElementWithNewIntValue(By elementToReplace, int newValue) throws InterruptedException {

        WebDriver driver = getDriver();

        driver.findElement(elementToReplace).click();
        driver.findElement(elementToReplace).clear();
        sleep(1000);
        driver.findElement(elementToReplace).sendKeys(String.valueOf(newValue));
        sleep(2000);
    }

    public void replaceElementWithNewStringValue(By elementToReplace, String newValue) throws InterruptedException {

        WebDriver driver = getDriver();

        driver.findElement(elementToReplace).click();
        driver.findElement(elementToReplace).clear();
        sleep(1000);
        driver.findElement(elementToReplace).sendKeys(String.valueOf(newValue));
        sleep(2000);
    }

    private void clickOn(By dropDownMenuChoice) throws InterruptedException {
        final String url = "https://ref.eteam.work/index.php?action=action_list&entity_id=62";

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver,5);

        driver.get(url);
        driver.manage().window().maximize();

        List<WebElement> dropDownMenus = driver.findElements(By.cssSelector("div.dropdown.pull-left>button>i"));
        sleep(1000);
        dropDownMenus.get(dropDownMenus.size() - 1).click();
        sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(dropDownMenuChoice)).click();
    }

    private void clickOnSave() throws InterruptedException {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver,5);

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        sleep(500);
    }

    private void assertValuesPopulatedAsExpected(String[] expectedValuesF1ToF10) {

        WebDriver driver = getDriver();

        List<WebElement> actualValues = driver.findElements(By.cssSelector(".pa-view-field"));

        for(int i = 0; i < actualValues.size(); i ++) {
            Assert.assertEquals(actualValues.get(i).getText(), expectedValuesF1ToF10[i]);
        }
    }

    private void assertErrorForInvalidValues() {

        WebDriver driver = getDriver();

        By errorMessage = By.cssSelector("#pa-error");

        Assert.assertEquals(driver.findElement(errorMessage).getText(), "Error saving entity");
    }

    private void assertRecordNotCreated(int element, String text) {
        final String url = "https://ref.eteam.work/index.php?action=action_list&entity_id=62";

        By lastRecord = By.cssSelector("tbody>tr:last-child>td>a>div");

        WebDriver driver = getDriver();
        driver.get(url);
        driver.manage().window().maximize();

        List<WebElement> valuesForLastRecord = driver.findElements(lastRecord);
        Assert.assertNotEquals(valuesForLastRecord.get(element).getText(), text);
    }
}
