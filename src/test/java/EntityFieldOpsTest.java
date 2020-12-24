import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EntityFieldOpsTest extends BaseTest {

    private final By rows = By.xpath("//tbody/tr");
    private final By createNew = By.xpath("//div[@class='card-icon']/i");
    private final By saveButton = By.cssSelector("button[id*='save']");
    private final By fieldsOpsRecordCard = By.cssSelector("div.card");
    private final By viewSwitchValue = By.xpath("//label[text()='Switch']/../div[1]//span");
    private final By viewDropdownValue = By.xpath("//label[text()='Switch']/../div[2]//span");
    private final By viewReferenceValue = By.xpath("//label[text()='Reference']/following-sibling::p");
    private final By viewMultiReferenceValue = By.xpath("//label[text()='Multireference']/following-sibling::p");
    private final By viewReferenceWithFilterValue = By.xpath("//label[text()='Reference with filter']/following-sibling::p");

    private WebDriverWait getWait(int timeoutSecond) {
        return new WebDriverWait(getDriver(), timeoutSecond);
    }

    private boolean isVisible(By by) {
        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> list = getDriver().findElements(by);
        if (list.isEmpty()) {
            return false;
        } else {
            return list.get(0).isDisplayed();
        }
    }

    private void goPageByName(String name) {
        WebDriver driver = getDriver();
        ProjectUtils.click(driver, driver.findElement(By.xpath(String.format("//p[contains(text(), ' %s ')]/..", name))));
    }

    private int getNumberOfRecords() {
        int numOfRecords;
        try {
            By rowsInfo = By.xpath("//span[@class='pagination-info']");
            numOfRecords = Integer.parseInt(getWait(1).until(ExpectedConditions
                    .visibilityOfElementLocated(rowsInfo)).getText().split("of ")[1].split(" ")[0]);
        } catch (TimeoutException e) {
            numOfRecords = 0;
        }

        return numOfRecords;
    }

    private void deleteLastRecord() {
        WebDriver driver = getDriver();
        String lastRecordRowXpath = "//tbody/tr[last()]";
        By recordMenuButton = By.xpath(String.format("%s//button", lastRecordRowXpath));
        By deleteButton = By.xpath(String.format("%s//a[contains(@href, 'delete')]", lastRecordRowXpath));
        driver.findElement(recordMenuButton).click();
        ProjectUtils.click(driver, getWait(2).until(ExpectedConditions.elementToBeClickable(deleteButton)));
    }

    private void scrollToElement(WebElement element) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element);
        actions.perform();
    }

    private void createReferenceValue(String referenceValue) {
        goPageByName("Reference values");
        getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        WebElement labelInput = getDriver().findElement(By.xpath("//input[@id='label']"));
        labelInput.sendKeys(referenceValue);
        WebElement saveButton = getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(getDriver(), saveButton);
    }

    private void createFieldOpsToDelete(String referenceValue) throws InterruptedException {
        goPageByName("Fields Ops");
        getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        Thread.sleep(5000);
        WebElement deleteReferenceCheckBox = getDriver().findElement(By.xpath(String.format("//label[contains(text(), '%s')]", referenceValue)));
        scrollToElement(deleteReferenceCheckBox);
        deleteReferenceCheckBox.click();
        WebElement saveButton = getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        scrollToElement(saveButton);
        ProjectUtils.click(getDriver(), saveButton);
    }

    @Test
    public void newRecord() throws InterruptedException {
        WebDriver driver = getDriver();
        goPageByName("Fields Ops");

        WebElement createNewFolder = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createNewFolder.click();

        WebElement checkbox = driver.findElement(By.xpath("//div[@class='d-flex']//span"));
        checkbox.click();

        Select dropdownMenu = new Select(driver.findElement(By.xpath("//select[@name='entity_form_data[dropdown]']")));
        dropdownMenu.selectByValue("Done");

        WebElement saveBtn = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver,saveBtn);

        try {
            WebElement pageTitle = driver.findElement(By.className("card-title"));
            Assert.assertEquals(pageTitle.getText(), "Fields Ops",
                    "Redirection works incorrectly");
        } catch (TimeoutException e) {
            Assert.fail("Redirection works incorrectly");
        }
    }

    @Test
    public void fieldOpsViewBaseRecord() {

        final String expSwitch = "0";
        final String expDropdown = "Pending";
        final String reference = "";
        final String multiReference = "";
        final String referenceWithFilter = "";
        final String referenceConstant = "contact@company.com";
        String[] values = {null, expSwitch, expDropdown, reference, multiReference, referenceWithFilter,
                            referenceConstant, null};

        WebDriver driver = getDriver();
        JavascriptExecutor executor = (JavascriptExecutor)driver;

        goPageByName("Fields Ops");
        driver.findElement(createNew).click();
        driver.findElement(saveButton).click();

        Assert.assertEquals(driver.findElements(rows).size(), 1);
        WebElement row = driver.findElement(rows);
        List<WebElement> cols = row.findElements(By.tagName("td"));
        Assert.assertEquals(cols.size(), values.length);
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                Assert.assertEquals(cols.get(i).getText(), values[i]);
            }
        }
        String script = "return window.getComputedStyle(document.querySelector('i.fa'),'::before').getPropertyValue('content')";
        String faCheckSquareO = executor.executeScript(script).toString();
        Assert.assertEquals(faCheckSquareO, "\"\"");

        cols.get(2).findElement(By.tagName("a")).click();
        Assert.assertEquals(driver.findElement(viewSwitchValue).getText(), expSwitch);
        Assert.assertEquals(driver.findElement(viewDropdownValue).getText(), expDropdown);
        String[] cardText = driver.findElement(fieldsOpsRecordCard).getText()
                .split("EmbedFO")[0].split("\n");
        String viewReferenceConstant = cardText[cardText.length - 1];
        Assert.assertEquals(viewReferenceConstant, referenceConstant);
        Assert.assertFalse(isVisible(viewReferenceValue));
        Assert.assertFalse(isVisible(viewMultiReferenceValue));
        Assert.assertFalse(isVisible(viewReferenceWithFilterValue));
        Assert.assertFalse(isVisible(rows));
    }

    @Test
    @Ignore("https://trello.com/c/dG6yE2lf/35-fields-ops-deleted-record-isnt-present-in-the-recycle-bin")
    public void fieldOpsDeleteTest() throws InterruptedException {
        String referenceValue = "Delete Reference";

        goPageByName("Fields Ops");

        createReferenceValue(referenceValue);

        createFieldOpsToDelete(referenceValue);
        createFieldOpsToDelete(referenceValue);

        goPageByName("Fields Ops");

        WebElement searchInput = getDriver().findElement(By.xpath("//input[@placeholder='Search']"));
        searchInput.sendKeys("Delete Reference");
        int beforeCountOfRecords = getNumberOfRecords();
        Thread.sleep(3000);
        WebElement dropDown = getDriver().findElement(By.xpath("//td[contains(text(), 'Delete Reference')]/../td/div/button"));
        ProjectUtils.click(getDriver(), dropDown);
        WebElement deleteMenuItem = getDriver().findElement(By.cssSelector("ul.dropdown-menu.show li:nth-child(3) > a"));
        ProjectUtils.click(getDriver(), deleteMenuItem);
        Thread.sleep(5000);
        goPageByName("Fields Ops");
        getDriver().findElement(By.xpath("//input[@placeholder='Search']")).sendKeys("Delete Reference");
        int afterCountOfRecords = getNumberOfRecords();
        Assert.assertNotEquals(beforeCountOfRecords, afterCountOfRecords);

        WebElement notificationIcon = getDriver().findElement(By.xpath("//i[contains(text(),'delete_outline')]"));
        ProjectUtils.click(getDriver(), notificationIcon);

        Thread.sleep(13000);
        WebElement firstRow = getDriver().findElement(By.xpath("//tbody/tr[1]/td[1]"));
        Assert.assertTrue(firstRow.getText().contains("Delete Reference"));
    }

}
