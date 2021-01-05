import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    private static final By fieldsPageButtonIcon = By.xpath("//p[contains(text(), ' Fields ')]/..");
    private static final By createNewIcon = (By.xpath("//i[text()='create_new_folder']"));
    private static final By saveButton = By.cssSelector("button[id*='save']");
    private static final By saveDraftButton = By.cssSelector("button[id*='draft']");
    private static final By titleInputField = By.id("title");
    private static final By commentInputField = By.id("comments");
    private static final By intInputField = By.id("int");
    private static final By decimalInputField = By.id("decimal");
    private static final By dateInputField = By.id("date");
    private static final By dateTimeInputField = By.id("datetime");
    private static final By selectUserField = By.cssSelector("button[data-id=user]");
    private static final By rows = By.xpath("//tbody/tr");
    private static final By recycleBinIcon = By.cssSelector("a[href*=recycle] > i");
    private static final By errorMessage = By.cssSelector("div[id*=error]");

    private WebDriverWait getWait(int timeout) { return new WebDriverWait(getDriver(), timeout); }

    private WebDriverWait getWait() { return getWait(5); }

    private Actions getActions() { return new Actions(getDriver()); }

    public JavascriptExecutor getExecutor() { return (JavascriptExecutor)getDriver(); }

    private void click(By by) {
        getWait().until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    private String getText(By by) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(by)).getText();
    }

    private String getCurrentUser() {
        return getText(By.id("navbarDropdownProfile")).split(" ")[1].toLowerCase();
    }

    private String getRandomUser() {
        List<WebElement> userList = getDriver().findElements(By.cssSelector("select#user > option"));
        return userList.get(ThreadLocalRandom.current().nextInt(1, userList.size())).getText();
    }

    private void goFieldsPage() {
        WebDriver driver = getDriver();
        getExecutor().executeScript("arguments[0].click();", driver.findElement(fieldsPageButtonIcon));
    }

    private void sendKeys(By by, String text) {
        WebElement textInputField = getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
        if (by.toString().contains("date")) {
            textInputField.click();
        }
        textInputField.clear();
        textInputField.sendKeys(text);
        getWait(2).until(ExpectedConditions.attributeContains(textInputField, "value", text));
    }

    private void selectUser(String user) {
        WebElement userText = getWait().until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector("div[class$=inner-inner]")));
        getActions().moveToElement(userText).perform();
        new Select(getDriver().findElement(By.cssSelector("select#user"))).selectByVisibleText(user);
    }

    private boolean isVisible(By by) {
        List<WebElement> list = getDriver().findElements(by);
        if (list.isEmpty()) {
            return false;
        } else {
            return list.get(0).isDisplayed();
        }
    }

    private void softWaitInvisibilityOf(WebElement element, int timeout) {
        try {
            getWait(timeout).until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException ignored) {}
    }

    private void clickSandwichAction(WebElement row, String menuItem) throws InterruptedException {
        row.findElement(By.tagName("button")).click();
        Thread.sleep(500);
        row.findElement(By.xpath(String.format("//li/a[contains(@href, '%s')]", menuItem.toLowerCase()))).click();
    }

    private void clickButton(String buttonType) {
        WebDriver driver = getDriver();
        if (buttonType.equalsIgnoreCase("save")) {
            ProjectUtils.click(driver, driver.findElement(saveButton));
        } else if ((buttonType.equalsIgnoreCase("draft"))) {
            driver.findElement(saveDraftButton).click();
        } else {
            throw new RuntimeException("Unexpected button type");
        }
    }

    private String getRecordTypeIcon() {
        String script = "return window.getComputedStyle(document.querySelector('td i.fa'),'::before').getPropertyValue('content')";
        return getExecutor().executeScript(script).toString().replace("\"", "");
    }

    @Test
    public void createNewRecordTest() {

        WebDriver driver = getDriver();
        ProjectUtils.reset(driver);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        String currentUser = getCurrentUser();
        String[] expectedValues = {null, TITLE, COMMENT, INT, DECIMAL, DATE, DATE_TIME, null, currentUser, null, null};
        goFieldsPage();

        driver.findElement(createNewIcon).click();
        getWait(10).until(ExpectedConditions.visibilityOfElementLocated(titleInputField));
        sendKeys(titleInputField, TITLE);
        sendKeys(commentInputField, COMMENT);
        sendKeys(intInputField, INT);
        sendKeys(decimalInputField, DECIMAL);
        sendKeys(dateInputField, DATE);
        sendKeys(dateTimeInputField, DATE_TIME);
        selectUser(currentUser);
        clickButton("save");

        List<WebElement> records = getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rows));
        Assert.assertEquals(records.size(), 1);
        List<WebElement> cols = records.get(0).findElements(By.tagName("td"));
        Assert.assertEquals(cols.size(), expectedValues.length);
        for (int i = 1; i < cols.size(); i++) {
            if (expectedValues[i] != null){
                Assert.assertEquals(cols.get(i).getText(), expectedValues[i]);
            }
        }
        Assert.assertEquals(getRecordTypeIcon(), "", "Wrong record icon, expected '\\f046'");
    }

    @Test
    public void createNewDraftTest() {

        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        String currentUser = getCurrentUser();
        String[] expectedValues = {null, TITLE, COMMENT, "0", "0", null, null, null, currentUser, null, null};
        goFieldsPage();

        click(createNewIcon);
        getWait(10).until(ExpectedConditions.visibilityOfElementLocated(titleInputField));
        sendKeys(titleInputField, TITLE);
        sendKeys(commentInputField, COMMENT);
        selectUser(currentUser);
        clickButton("draft");

        List<WebElement> records = getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rows));
        Assert.assertEquals(records.size(), 1);
        List<WebElement> cols = records.get(0).findElements(By.tagName("td"));
        Assert.assertEquals(cols.size(), expectedValues.length);
        for (int i = 1; i < cols.size(); i++) {
            if (expectedValues[i] != null){
                Assert.assertEquals(cols.get(i).getText(), expectedValues[i]);
            }
        }
        Assert.assertEquals(getRecordTypeIcon(), "", "Wrong draft icon, expected '\\f040'");
    }

    @Test(dependsOnMethods = "createNewRecordTest")
    public void editRecordTest() throws InterruptedException {

        final String NEW_TITLE = String.format("%s_EditTextAllNew", UUID.randomUUID().toString());
        final String NEW_COMMENT = "New comment text for edit test";
        final String NEW_INT = Integer.toString(ThreadLocalRandom.current().nextInt(300, 400));
        final String NEW_DECIMAL = "128.01";
        final String NEW_DATE = "25/10/2018";
        final String NEW_DATE_TIME = "25/10/2018 08:22:05";
        String randomUser = "";
        String[] expectedValues = {null, NEW_TITLE, NEW_COMMENT, NEW_INT, NEW_DECIMAL, NEW_DATE, NEW_DATE_TIME, null,
                randomUser, null, null};

        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        goFieldsPage();

        WebElement record = getWait().until(ExpectedConditions.visibilityOfElementLocated(rows));
        clickSandwichAction(record, "edit");

        getWait(10).until(ExpectedConditions.visibilityOfElementLocated(titleInputField));
        expectedValues[8] = randomUser = getRandomUser();
        sendKeys(titleInputField, NEW_TITLE);
        sendKeys(commentInputField, NEW_COMMENT);
        sendKeys(intInputField, NEW_INT);
        sendKeys(decimalInputField, NEW_DECIMAL);
        sendKeys(dateInputField, NEW_DATE);
        sendKeys(dateTimeInputField, NEW_DATE_TIME);
        selectUser(randomUser);
        clickButton("save");

        List<WebElement> records = getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rows));
        Assert.assertEquals(records.size(), 1);
        List<WebElement> cols = records.get(0).findElements(By.tagName("td"));
        Assert.assertEquals(cols.size(), expectedValues.length);
        for (int i = 1; i < cols.size(); i++) {
            if (expectedValues[i] != null){
                Assert.assertEquals(cols.get(i).getText(), expectedValues[i]);
            }
        }
        Assert.assertEquals(getRecordTypeIcon(), "", "Wrong record icon, expected '\\f046'");
    }

    @Test(dependsOnMethods = {"createNewRecordTest", "editRecordTest"})
    public void deleteRecordTest() throws InterruptedException {

        final String recordTitle;
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        goFieldsPage();
        WebElement record = getWait().until(ExpectedConditions.visibilityOfElementLocated(rows));
        recordTitle = record.findElement(By.xpath("//td[2]")).getText();
        clickSandwichAction(record, "delete");

        softWaitInvisibilityOf(record, 2);
        Assert.assertFalse(isVisible(rows));

        driver.findElement(recycleBinIcon).click();
        List<WebElement> deletedItems = driver.findElements(rows);
        Assert.assertEquals(deletedItems.size(), 1);
        Assert.assertEquals(deletedItems.get(0).findElement(By.xpath("//span[contains(text(), 'Title:')]/b")).getText(),
                recordTitle);
    }

    @Test
    public void invalidIntEntryCreateTest() {

        final String invalidEntry = "a";
        WebDriver driver = getDriver();
        ProjectUtils.reset(driver);
        goFieldsPage();

        driver.findElement(createNewIcon).click();
        getWait(10).until(ExpectedConditions.visibilityOfElementLocated(intInputField)).sendKeys(invalidEntry);
        clickButton("save");

        WebElement error = getWait(2).until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        Assert.assertTrue(error.isDisplayed());
        Assert.assertEquals(error.getText(), "Error saving entity");
    }
}
