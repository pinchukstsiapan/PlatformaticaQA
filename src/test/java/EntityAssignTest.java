import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EntityAssignTest extends BaseTest {

    final static String TASK_TITLE = "New task for the Assign entity";
    final static String DESCRIPTION = "Verifying the assignment";
    final static int NUMBER = 10;
    final static double DECIMAL = 5.5;


    public void createTask() {
        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");

        WebElement assignEntity = driver.findElement(By.xpath("//li/a[contains(@href, 'index.php?action=action_list&entity_id=37')]"));
        assignEntity.click();

        WebElement createButton = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        createButton.click();

        WebElement titleField = driver.findElement(By.id("string"));
        titleField.sendKeys(TASK_TITLE);

        WebElement descriptionField = driver.findElement(By.id("text"));
        descriptionField.sendKeys(DESCRIPTION);

        WebElement integerField = driver.findElement(By.id("int"));
        integerField.sendKeys(String.valueOf(NUMBER));

        WebElement decimalField = driver.findElement(By.id("decimal"));
        decimalField.sendKeys(String.valueOf(DECIMAL));

        WebElement dateField = driver.findElement(By.id("date"));
        dateField.click();

        WebElement dateTimeField = driver.findElement(By.id("datetime"));
        dateTimeField.click();

        WebElement userField = driver.findElement(By.xpath("//button[@data-id='user']"));
        userField.click();

        Select dropdown = new Select(driver.findElement(By.xpath("//select[@name='entity_form_data[user]']")));
        dropdown.selectByVisibleText("User 4");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
    }

    public int getColumnIndexByTitle(WebElement table, String title) {
        List<WebElement> tableHeaders = table.findElements(By.xpath(".//thead/tr/th"));
        for (int i = 0; i < tableHeaders.size(); i++) {
            if (tableHeaders.get(i).getText().equals(title)) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    public List<WebElement> findRowCellsByColumnValue(WebElement table, int columnIndex, String value) {
        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.get(columnIndex).getText().equals(value)) {
                return cells;
            }
        }
        return null;
    }

    public boolean checkIfIsAssigned(String title) {
        getDriver().findElement(By.id("pa-menu-item-41")).click();

        try {
            WebElement table2 = new WebDriverWait(getDriver(), 5)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='pa-all-entities-table']")));
            int stringColumnIndex2 = getColumnIndexByTitle(table2, "String");
            List<WebElement> rowCells2 = findRowCellsByColumnValue(table2, stringColumnIndex2, title);
            return rowCells2 != null;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void deleteTask(List<WebElement> cells) {
        cells.get(cells.size() - 1).findElement(By.xpath(".//div/button[@data-toggle='dropdown']")).click();
        getDriver().manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
        cells.get(cells.size() - 1).findElements(By.xpath(".//div/ul[@role='menu']/li/a")).get(2).click();
    }

    @BeforeMethod
    public void setUp() {
        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");
        //ProjectUtils.login(driver, "user4@tester.com", "CoBX8ym0T7");
    }

    @Ignore
    @Test
    public void assignTest() {

        createTask();

        WebElement table = new WebDriverWait(getDriver(), 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='pa-all-entities-table']")));

        int stringColumnIndex = getColumnIndexByTitle(table, "String");
        int assigneeCellIndex = getColumnIndexByTitle(table, "Assignee");

        List<WebElement> rowCells = findRowCellsByColumnValue(table, stringColumnIndex, TASK_TITLE);

        Assert.assertNotNull(rowCells);

        new Select(rowCells.get(assigneeCellIndex).findElement(By.tagName("select"))).selectByVisibleText("User 4");

        Assert.assertTrue(checkIfIsAssigned(TASK_TITLE));

        WebElement assignEntity = getDriver().findElement(By.xpath("//li/a[contains(@href, 'index.php?action=action_list&entity_id=37')]"));
        assignEntity.click();

        WebElement table1 = new WebDriverWait(getDriver(), 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='pa-all-entities-table']")));

        List<WebElement> rowCells1 = findRowCellsByColumnValue(table1, stringColumnIndex, TASK_TITLE);
        deleteTask(rowCells1);
    }

    @Ignore
    @Test (dependsOnMethods = "assignTest")
    public void editTest() {

        createTask();

        WebElement table = new WebDriverWait(getDriver(), 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='pa-all-entities-table']")));

        int stringColumnIndex = getColumnIndexByTitle(table, "String");
        int assigneeCellIndex = getColumnIndexByTitle(table, "Assignee");

        List<WebElement> rowCells = findRowCellsByColumnValue(table, stringColumnIndex, TASK_TITLE);
        new Select(rowCells.get(assigneeCellIndex).findElement(By.tagName("select"))).selectByVisibleText("User 3");

        Assert.assertFalse(checkIfIsAssigned(TASK_TITLE));

        getDriver().get("https://ref.eteam.work/index.php?action=login");
        //ProjectUtils.login(getDriver(), "user3@tester.com", "SXGJhNd1aM");

        Assert.assertTrue(checkIfIsAssigned(TASK_TITLE), "Task is not on the list");

        WebElement assignEntity = getDriver().findElement(By.xpath("//li/a[contains(@href, 'index.php?action=action_list&entity_id=37')]"));
        assignEntity.click();
        deleteTask(rowCells);
    }

    @Ignore
    @Test
    public void deleteTest() {

        createTask();
        WebElement table = new WebDriverWait(getDriver(), 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='pa-all-entities-table']")));

        int stringColumnIndex = getColumnIndexByTitle(table, "String");

        List<WebElement> rowCells = findRowCellsByColumnValue(table, stringColumnIndex, TASK_TITLE);
        deleteTask(rowCells);

        getDriver().findElement(By.partialLinkText("delete")).click();

        WebElement table2 = new WebDriverWait(getDriver(), 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));

        Assert.assertTrue(table2.getText().contains(TASK_TITLE));
    }

    private String currentUser = "";
    private final String myAssignmentsTabLink = "https://ref.eteam.work/index.php?action=screen&screen_id=40&filter";

    @Ignore
    @Test
    public void assignTest1() {

        createRecord();

        WebDriver driver = getDriver();

        driver.navigate().refresh();

        WebElement selectedUser = driver.findElement(By.xpath("//div[contains(text(),'mb--simple text')]" +
                "/../../..//option[@selected and normalize-space(text())='" + currentUser + "']"));

        Assert.assertTrue(selectedUser.isDisplayed());

        WebElement myAssignmentsTab = driver.findElement(By.xpath("//li[@id='pa-menu-item-41']"));
        myAssignmentsTab.click();

        Assert.assertEquals(driver.getCurrentUrl(), myAssignmentsTabLink);

        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'mb--simple text')]")).isDisplayed());
    }

    @Test
    public void editTest1() {

        final String anotherUser = "user198@tester.com";

        createRecord();

        WebDriver driver = getDriver();

        Select changeUser = new Select(driver.findElement(By.className("pa-list-assignee")));

        changeUser.selectByVisibleText(anotherUser);

        driver.navigate().refresh();

        WebElement selectedUser = driver.findElement(By.xpath("//div[contains(text(),'mb--simple text')]" +
                "/../../..//option[@selected and normalize-space(text())='" + anotherUser + "']"));

        Assert.assertTrue(selectedUser.isDisplayed());

        WebElement myAssignmentsTab = driver.findElement(By.xpath("//li[@id='pa-menu-item-41']"));
        myAssignmentsTab.click();

        Assert.assertEquals(driver.getCurrentUrl(), myAssignmentsTabLink);

        Assert.assertTrue(driver.findElements(By.xpath("//div[contains(text(),'mb--simple text')]")).isEmpty());
    }

    @Test
    public void deleteTest1() {

        createRecord();

        WebDriver driver = getDriver();

        WebElement deleteRecord = driver.findElement(By.xpath("//a[text()='delete']"));
        ProjectUtils.click(driver, deleteRecord);

        Assert.assertTrue(driver.findElements(By.xpath("//div[contains(text(),'mb--simple text')]")).isEmpty());

        WebElement recycleBin = driver.findElement(By.xpath("//a[contains(@href,'recycle_bin')]"));
        ProjectUtils.click(driver, recycleBin);

        WebElement deletedRecord = driver.findElement(By.xpath
                ("//td[text()='Assign']/..//b[contains(text(),'mb--simple text')]"));

        Assert.assertTrue(deletedRecord.isDisplayed());

        WebElement myAssignmentsTab = driver.findElement(By.xpath("//li[@id='pa-menu-item-41']"));
        myAssignmentsTab.click();

        Assert.assertEquals(driver.getCurrentUrl(), myAssignmentsTabLink);

        Assert.assertTrue(driver.findElements(By.xpath("//div[contains(text(),'mb--simple text')]")).isEmpty());
    }

    public void createRecord() {

        final String stringInp = UUID.randomUUID().toString();
        final String textInp = "mb--simple text";
        final int intInp = 10;
        final double decimalInp = 13.5;

        WebDriver driver = getDriver();

        WebElement assignTab = driver.findElement(By.xpath("//a[contains(@href, 'id=37')]"));
        ProjectUtils.click(driver, assignTab);

        WebElement newRecord = driver.findElement(By.xpath("//a[contains(@href, 'create&entity_id=37')]"));
        ProjectUtils.click(driver, newRecord);

        WebElement stringInput = driver.findElement(By.xpath("//input[@id='string']"));
        stringInput.sendKeys(stringInp);

        WebElement textInput = driver.findElement(By.xpath("//textarea[@id='text']"));
        textInput.sendKeys(textInp);

        WebElement intInput = driver.findElement(By.xpath("//input[@id='int']"));
        intInput.sendKeys(String.valueOf(intInp));

        WebElement decimalInput = driver.findElement(By.xpath("//input[@id='decimal']"));
        decimalInput.sendKeys(String.valueOf(decimalInp));

        WebElement dateInput = driver.findElement(By.xpath("//input[@id='date']"));
        dateInput.click();

        WebElement datetimeInput = driver.findElement(By.xpath("//input[@id='datetime']"));
        datetimeInput.click();

        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);

        String[] listOfUsers = driver.findElement(By.xpath("//select")).getText().split("\n|\\                ");

        String loggedInUser = driver.findElement(By.xpath("//a[@id='navbarDropdownProfile']")).getText().substring(7);

        for (String user : listOfUsers) {
            if (loggedInUser.equalsIgnoreCase(user)) {
                currentUser = user;
            }
        }

        WebElement userSelect = driver.findElement(By.xpath("//div[contains(text(),'mb--simple text')]" +
                "/../../..//option[normalize-space(text())='" + currentUser + "']"));
        userSelect.click();
    }
}