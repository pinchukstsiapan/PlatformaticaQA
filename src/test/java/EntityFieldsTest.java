import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Run(run = RunType.Multiple)
public class EntityFieldsTest extends BaseTest {

    private static final String TITLE = UUID.randomUUID().toString();
    private static final String COMMENT = RandomStringUtils.randomAlphanumeric(25);
    private static final String INT = Integer.toString(ThreadLocalRandom.current().nextInt(100, 200));
    private static final String DECIMAL = String.format("%.2f", (Math.random() * 20000) / 100.0);
    private static final String DATE = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    private static final String DATE_TIME = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    private static final String NEW_TITLE = String.format("%s_EditTextAllNew", UUID.randomUUID().toString());
    private static final String NEW_COMMENT = "New comment text for edit test";
    private static final String NEW_INT = Integer.toString(ThreadLocalRandom.current().nextInt(300, 400));
    private static final String NEW_DECIMAL = "128.01";
    private static final String NEW_DATE = "25/10/2018";
    private static final String NEW_DATE_TIME = "25/10/2018 08:22:05";
    private static final String INVALID_ENTRY = "a";
    private static String RANDOM_USER = null;
    private static String CURRENT_USER = null;

    private static final By fieldsPageButtonIcon = By.xpath("//p[contains(text(), ' Fields ')]/..");
    private static final By createNewIcon = (By.xpath("//i[text()='create_new_folder']"));
    private static final By saveButton = By.cssSelector("button[id*='save']");
    private static final By saveDraftButton = By.cssSelector("button[id*='draft']");
    private static final By cancelButton = By.xpath("//button[text()='Cancel']");
    private static final By titleInputField = By.id("title");
    private static final By commentInputField = By.id("comments");
    private static final By intInputField = By.id("int");
    private static final By decimalInputField = By.id("decimal");
    private static final By dateInputField = By.id("date");
    private static final By dateTimeInputField = By.id("datetime");
    private static final By rows = By.xpath("//tbody/tr");
    private static final By recycleBinIcon = By.cssSelector("a[href*=recycle] > i");
    private static final By errorMessage = By.cssSelector("div[id*=error]");

    private void goFieldsPage() {
        WebDriver driver = getDriver();
        ProjectUtils.click(driver, driver.findElement(fieldsPageButtonIcon));
    }

    private void click(By by) {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    private String getCurrentUser() {
        String profileButtonText = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By
                .id("navbarDropdownProfile"))).getText();
        String currentUser = profileButtonText.split(" ")[1].toLowerCase();

        return currentUser;
    }

    private String getRandomUser() {
        List<WebElement> userList = getDriver().findElements(By.cssSelector("select#user > option"));
        String randomUser = userList.get(ThreadLocalRandom.current().nextInt(1, userList.size())).getText();

        return randomUser;
    }

    private void sendKeys(By by, String text) {
        WebElement textInputField = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(by));
        if (by.toString().contains("date")) {
            textInputField.click();
        }
        if (!textInputField.getAttribute("value").isEmpty()) {
            textInputField.clear();
        }
        textInputField.sendKeys(text);
        getWebDriverWait().until(ExpectedConditions.attributeContains(textInputField, "value", text));
    }

    private void selectUser(String user) {
        WebElement userText = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector("div[class$=inner-inner]")));
        new Actions(getDriver()).moveToElement(userText).perform();
        new Select(getDriver().findElement(By.cssSelector("select#user"))).selectByVisibleText(user);
    }

    private void clickSandwichAction(WebElement row, String menuItem) throws InterruptedException {
        row.findElement(By.tagName("button")).click();
        Thread.sleep(500);
        row.findElement(By.xpath(String.format("//li/a[contains(@href, '%s')]", menuItem.toLowerCase()))).click();
    }

    private void clickButton(String buttonType) {
        switch (buttonType) {
            case "save":
                click(saveButton);
                break;
            case "draft":
                click(saveDraftButton);
                break;
            case "cancel":
                click(cancelButton);
                break;
            default:
                throw new RuntimeException("Unexpected button type");
        }
    }

    private void verifyEntityTypeIcon(String entityType) {
        String script = "return window.getComputedStyle(document.querySelector('td i.fa'),'::before')" +
                ".getPropertyValue('content').codePointAt(1).toString(16)";
        String entityTypeIconUnicode = ((JavascriptExecutor) getDriver()).executeScript(script).toString();
        switch (entityType) {
            case "record":
                Assert.assertEquals(entityTypeIconUnicode, "f046", "Wrong record icon, expected '\\f046'");
                break;
            case "draft":
                Assert.assertEquals(entityTypeIconUnicode, "f040", "Wrong draft icon, expected '\\f040'");
                break;
             default:
                 throw new RuntimeException("Unexpected entity type");
        }
    }

    private void verifyDataTypeError() {
        WebElement error = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        Assert.assertTrue(error.isDisplayed());
        Assert.assertEquals(error.getText(), "Error saving entity");
    }

    private String formatDecimal(String decimalString) {
        DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(false);
        return format.format(Double.valueOf(decimalString));
    }

    private void verifyEntityData(WebElement row, String[] expected) {
        List<WebElement> cols = row.findElements(By.tagName("td"));
        Assert.assertEquals(cols.size(), expected.length);
        expected[4] = formatDecimal(expected[4]);
        for (int i = 1; i < cols.size(); i++) {
            if (expected[i] != null){
                Assert.assertEquals(cols.get(i).getText(), expected[i]);
            }
        }
    }

    private void resetUserData(WebDriver driver) {
        ProjectUtils.click(driver, driver.findElement(By.id("navbarDropdownProfile")));
        ProjectUtils.click(driver, driver.findElement(By.xpath("//a[contains(text(), 'Reset')]")));
    }

    @Test
    public void createNewRecordTest() {

        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        resetUserData(driver);

        CURRENT_USER = getCurrentUser();
        final String[] expectedValues = {null, TITLE, COMMENT, INT, DECIMAL, DATE, DATE_TIME, null, CURRENT_USER, null, null};

        goFieldsPage();
        driver.findElement(createNewIcon).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(titleInputField));
        sendKeys(titleInputField, TITLE);
        sendKeys(commentInputField, COMMENT);
        sendKeys(intInputField, INT);
        sendKeys(decimalInputField, DECIMAL);
        sendKeys(dateInputField, DATE);
        sendKeys(dateTimeInputField, DATE_TIME);
        selectUser(CURRENT_USER);
        clickButton("save");

        List<WebElement> records = getWebDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rows));
        Assert.assertEquals(records.size(), 1);
        verifyEntityData(records.get(0), expectedValues);
        verifyEntityTypeIcon("record");
    }

    @Test
    public void createNewDraftTest() {

        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        CURRENT_USER = getCurrentUser();
        final String[] expectedValues = {null, TITLE, COMMENT, "0", "0", null, null, null, CURRENT_USER, null, null};

        goFieldsPage();
        click(createNewIcon);
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(titleInputField));
        sendKeys(titleInputField, TITLE);
        sendKeys(commentInputField, COMMENT);
        selectUser(CURRENT_USER);
        clickButton("draft");

        List<WebElement> records = getWebDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rows));
        Assert.assertEquals(records.size(), 1);
        verifyEntityData(records.get(0), expectedValues);
        verifyEntityTypeIcon("draft");
    }

    @Test(dependsOnMethods = "createNewRecordTest")
    public void editRecordTest() throws InterruptedException {

        String[] expectedValues = {null, NEW_TITLE, NEW_COMMENT, NEW_INT, NEW_DECIMAL, NEW_DATE, NEW_DATE_TIME, null,
                RANDOM_USER, null, null};

        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        goFieldsPage();
        WebElement record = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(rows));
        clickSandwichAction(record, "edit");
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(titleInputField));
        expectedValues[8] = RANDOM_USER = getRandomUser();
        sendKeys(titleInputField, NEW_TITLE);
        sendKeys(commentInputField, NEW_COMMENT);
        sendKeys(intInputField, NEW_INT);
        sendKeys(decimalInputField, NEW_DECIMAL);
        sendKeys(dateInputField, NEW_DATE);
        sendKeys(dateTimeInputField, NEW_DATE_TIME);
        selectUser(RANDOM_USER);
        clickButton("save");

        List<WebElement> records = getWebDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rows));
        Assert.assertEquals(records.size(), 1);
        verifyEntityData(records.get(0), expectedValues);
        verifyEntityTypeIcon("record");
    }

    @Test(dependsOnMethods = {"createNewRecordTest", "editRecordTest"})
    public void deleteRecordTest() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        goFieldsPage();
        WebElement record = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(rows));
        final String recordTitle = record.findElement(By.xpath("//td[2]")).getText();
        clickSandwichAction(record, "delete");

        WebElement card = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.card-body")));
        Assert.assertEquals(card.getText(), "", "Record has not been deleted");

        click(recycleBinIcon);
        List<WebElement> deletedItems = getWebDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rows));
        Assert.assertEquals(deletedItems.size(), 1);
        Assert.assertEquals(deletedItems.get(0).findElement(By.xpath("//span[contains(text(), 'Title:')]/b")).getText(),
                recordTitle);
    }

    @Ignore
    @Test
    public void invalidIntEntryCreateTest() {

        WebDriver driver = getDriver();
        resetUserData(driver);

        goFieldsPage();
        click(createNewIcon);
        sendKeys(intInputField, INVALID_ENTRY);
        clickButton("save");

        verifyDataTypeError();
    }

    @Ignore
    @Test
    public void invalidDecimalEntryCreateTest() {

        WebDriver driver = getDriver();
        resetUserData(driver);

        goFieldsPage();
        click(createNewIcon);
        sendKeys(decimalInputField, INVALID_ENTRY);
        clickButton("save");

        verifyDataTypeError();
    }

}
