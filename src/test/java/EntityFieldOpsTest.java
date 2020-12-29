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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EntityFieldOpsTest extends BaseTest {

    private final String DEFAULT_DROPDOWN = "Pending";
    private final String OPTIONAL_DROPDOWN = "Done";
    private final String REFERENCE_1 = UUID.randomUUID().toString();
    private final String REFERENCE_2 = UUID.randomUUID().toString();
    private final String REFERENCE_3 = UUID.randomUUID().toString();
    private final String REFERENCE_CONSTANT = "contact@company.com";

    private final By rows = By.xpath("//tbody/tr");
    private final By createNew = By.xpath("//div[@class='card-icon']/i");
    private final By saveButton = By.cssSelector("button[id*='save']");
    private final By createReferenceLabelInput = By.cssSelector("input[name*=label]");
    private final By createReferenceFilter1Input = By.cssSelector("input[name*=filter_1]");
    private final By createReferenceFilter2Input = By.cssSelector("input[name*=filter_2]");
    private final By createEditMainToggle = By.cssSelector("div#_field_container-switch span.toggle");
    private final By createEditDropdownSelect = By.id("dropdown");
    private final By createEditReferenceSelect = By.id("reference");
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
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (list.isEmpty()) {
            return false;
        } else {
            return list.get(0).isDisplayed();
        }
    }

    private boolean isInViewport(WebElement element) {
        Dimension portViewSize = getDriver().findElement(By.tagName("body")).getSize();
        Dimension elementSize = element.getSize();
        Point elementLocation = element.getLocation();
        boolean inViewportByHeight = portViewSize.getHeight() > elementLocation.getY() + elementSize.getHeight();
        boolean inViewportByWidth = portViewSize.getWidth() > elementLocation.getX() + elementSize.getWidth();

        return inViewportByHeight && inViewportByWidth;
    }

    private void goPageByName(String name) {
        WebDriver driver = getDriver();
        ProjectUtils.click(driver, driver.findElement(By.xpath(String.format("//p[contains(text(), ' %s ')]/..", name))));
    }

    private void clickSaveButton() {
        WebElement button = getDriver().findElement(saveButton);
        if (!isInViewport(button)) {
            scrollToElement(button);
        }
        button.click();
    }

    private void createReference(String label) {
        WebDriver driver = getDriver();
        goPageByName("Reference values");
        driver.findElement(createNew).click();
        driver.findElement(createReferenceLabelInput).sendKeys(label);
        clickSaveButton();
    }

    private void createReference(String label, String filterOne) {
        WebDriver driver = getDriver();
        goPageByName("Reference values");
        driver.findElement(createNew).click();
        driver.findElement(createReferenceLabelInput).sendKeys(label);
        driver.findElement(createReferenceFilter1Input).sendKeys(filterOne);
        clickSaveButton();
    }

    private void createReference(String label, String filterOne, String filterTwo) {
        WebDriver driver = getDriver();
        goPageByName("Reference values");
        driver.findElement(createNew).click();
        driver.findElement(createReferenceLabelInput).sendKeys(label);
        driver.findElement(createReferenceFilter1Input).sendKeys(filterOne);
        driver.findElement(createReferenceFilter2Input).sendKeys(filterTwo);
        clickSaveButton();
    }

    private void clickSandwichAction(WebElement row, String menuItem) throws InterruptedException {
        row.findElement(By.tagName("button")).click();
        Thread.sleep(500);
        row.findElement(By.xpath(String.format("//li/a[contains(@href, '%s')]", menuItem.toLowerCase()))).click();
    }

    private String getRecordTypeIcon() {
        JavascriptExecutor executor = (JavascriptExecutor)getDriver();
        String script = "return window.getComputedStyle(document.querySelector('i.fa'),'::before').getPropertyValue('content')";
        String recordTypeIcon = executor.executeScript(script).toString().replace("\"", "");

        return recordTypeIcon;
    }

    private String getViewMultiReference() {
        StringBuilder viewlMultiReference = new StringBuilder();
        for (WebElement element : getDriver().findElements(viewMultiReferenceValue)) {
            viewlMultiReference.append(String.format(" %s", element.getText()));
        }
        String result = viewlMultiReference.toString().trim().replace(" ", ", ");

        return result;
    }

    private String getViewReferenceConstant() {
        String viewReferenceConstant;
        String[] cardText = getDriver().findElement(fieldsOpsRecordCard).getText()
                .split("EmbedFO")[0].split("\n");
        viewReferenceConstant = cardText[cardText.length - 1];

        return viewReferenceConstant;
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

    private String getCreatedReferenceValue() {
        String lastRecordRowXpath = "//tbody/tr[last()]/td[2]/a";

        WebElement refValueId = getDriver().findElement(By.xpath(lastRecordRowXpath));
        String href = refValueId.getAttribute("href");
        return href.substring(href.lastIndexOf('=') + 1);
    }

    private void scrollToElement(WebElement element) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element);
        actions.perform();
    }

    private String createReferenceValue(String referenceValue) {
        goPageByName("Reference values");
        getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        WebElement labelInput = getDriver().findElement(By.xpath("//input[@id='label']"));
        labelInput.sendKeys(referenceValue);
        WebElement saveButton = getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(getDriver(), saveButton);
        return getCreatedReferenceValue();
    }

    private void createFieldOpsToDelete(String referenceValue) {
        goPageByName("Fields Ops");
        getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        WebElement referenceCheckBox = getWait(3).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//label[contains(text(), '%s')]", referenceValue))));
        scrollToElement(referenceCheckBox);
        referenceCheckBox.click();
        WebElement saveButton = getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(getDriver(), saveButton);
    }

    @Test
    public void createNewRecordTest() {
        WebDriver driver = getDriver();
        goPageByName("Fields Ops");

        WebElement createNewFolder = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createNewFolder.click();

        WebElement checkbox = driver.findElement(By.xpath("//div[@class='d-flex']//span"));
        checkbox.click();

        Select dropdownMenu = new Select(driver.findElement(By.xpath("//select[@name='entity_form_data[dropdown]']")));
        dropdownMenu.selectByValue("Done");

        WebElement saveBtn = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, saveBtn);

        try {
            WebElement pageTitle = driver.findElement(By.className("card-title"));
            Assert.assertEquals(pageTitle.getText(), "Fields Ops",
                    "Redirection works incorrectly");
        } catch (TimeoutException e) {
            Assert.fail("Redirection works incorrectly");
        }
    }

    @Test
    public void viewBaseRecordNoRefExistTest() throws InterruptedException {

        final String SWITCH_VALUE = "0";
        final String REFERENCE = "";
        final String MULTI_REFERENCE = "";
        final String REFERENCE_WITH_FILTER = "";
        String[] values = {null, SWITCH_VALUE, DEFAULT_DROPDOWN, REFERENCE, MULTI_REFERENCE, REFERENCE_WITH_FILTER,
                REFERENCE_CONSTANT, null};

        WebDriver driver = getDriver();

        goPageByName("Fields Ops");
        driver.findElement(createNew).click();
        clickSaveButton();

        Assert.assertEquals(driver.findElements(rows).size(), 1);
        WebElement row = driver.findElement(rows);
        List<WebElement> cols = row.findElements(By.tagName("td"));
        Assert.assertEquals(cols.size(), values.length);
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                Assert.assertEquals(cols.get(i).getText(), values[i]);
            }
        }
        Assert.assertEquals(getRecordTypeIcon(), "");

        clickSandwichAction(driver.findElement(rows), "view");
        Assert.assertEquals(driver.findElement(viewSwitchValue).getText(), SWITCH_VALUE);
        Assert.assertEquals(driver.findElement(viewDropdownValue).getText(), DEFAULT_DROPDOWN);
        Assert.assertEquals(getViewReferenceConstant(), REFERENCE_CONSTANT);
        Assert.assertFalse(isVisible(viewReferenceValue));
        Assert.assertFalse(isVisible(viewMultiReferenceValue));
        Assert.assertFalse(isVisible(viewReferenceWithFilterValue));
        Assert.assertFalse(isVisible(rows));
    }

    @Ignore
    @Test
    public void viewBaseRecordRefExistTest() throws InterruptedException {

        final String SWITCH_VALUE = "0";
        final String reference = "";
        final String multiReference = "";
        final String referenceWithFilter = "";
        String[] values = {null, SWITCH_VALUE, DEFAULT_DROPDOWN, reference, multiReference, referenceWithFilter,
                REFERENCE_CONSTANT, null};

        WebDriver driver = getDriver();

        createReference(REFERENCE_1);
        createReference(REFERENCE_2, REFERENCE_1);
        createReference(REFERENCE_3, REFERENCE_2, REFERENCE_1);

        goPageByName("Fields Ops");
        driver.findElement(createNew).click();
        clickSaveButton();

        Assert.assertEquals(driver.findElements(rows).size(), 1);
        WebElement row = driver.findElement(rows);
        List<WebElement> cols = row.findElements(By.tagName("td"));
        Assert.assertEquals(cols.size(), values.length);
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                Assert.assertEquals(cols.get(i).getText(), values[i]);
            }
        }
        Assert.assertEquals(getRecordTypeIcon(), "");

        clickSandwichAction(driver.findElement(rows), "view");
        Assert.assertEquals(driver.findElement(viewSwitchValue).getText(), SWITCH_VALUE);
        Assert.assertEquals(driver.findElement(viewDropdownValue).getText(), DEFAULT_DROPDOWN);
        Assert.assertEquals(getViewReferenceConstant(), REFERENCE_CONSTANT);
        Assert.assertFalse(isVisible(viewReferenceValue));
        Assert.assertFalse(isVisible(viewMultiReferenceValue));
        Assert.assertFalse(isVisible(viewReferenceWithFilterValue));
        Assert.assertFalse(isVisible(rows));
    }

    @Ignore
    @Test
    public void viewRecordWithRefTest() throws InterruptedException {

        final String SWITCH_VALUE = "1";
        final String REFERENCE = REFERENCE_1;
        final String MULTI_REFERENCE = "";
        final String REFERENCE_WITH_FILTER = "";
        String[] values = {null, SWITCH_VALUE, OPTIONAL_DROPDOWN, REFERENCE, MULTI_REFERENCE, REFERENCE_WITH_FILTER,
                REFERENCE_CONSTANT, null};

        WebDriver driver = getDriver();

        createReference(REFERENCE_1);
        createReference(REFERENCE_2, REFERENCE_1);
        createReference(REFERENCE_3, REFERENCE_2, REFERENCE_1);

        goPageByName("Fields Ops");
        driver.findElement(createNew).click();
        driver.findElement(createEditMainToggle).click();
        new Select(driver.findElement(createEditDropdownSelect)).selectByVisibleText(OPTIONAL_DROPDOWN);
        new Select(driver.findElement(createEditReferenceSelect)).selectByVisibleText(REFERENCE);
        clickSaveButton();

        Assert.assertEquals(driver.findElements(rows).size(), 1);
        WebElement row = driver.findElement(rows);
        List<WebElement> cols = row.findElements(By.tagName("td"));
        Assert.assertEquals(cols.size(), values.length);
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                Assert.assertEquals(cols.get(i).getText(), values[i]);
            }
        }
        Assert.assertEquals(getRecordTypeIcon(), "");

        clickSandwichAction(driver.findElement(rows), "view");
        Assert.assertEquals(driver.findElement(viewSwitchValue).getText(), SWITCH_VALUE);
        Assert.assertEquals(driver.findElement(viewDropdownValue).getText(), OPTIONAL_DROPDOWN);
        Assert.assertEquals(driver.findElement(viewReferenceValue).getText(), REFERENCE);
        Assert.assertEquals(getViewReferenceConstant(), REFERENCE_CONSTANT);
        Assert.assertFalse(isVisible(viewMultiReferenceValue));
        Assert.assertFalse(isVisible(viewReferenceWithFilterValue));
        Assert.assertFalse(isVisible(rows));
    }

    @Ignore
    @Test
    public void viewRecordWithRefMultiRefTest() throws InterruptedException {

        final String SWITCH_VALUE = "1";
        final String REFERENCE = REFERENCE_1;
        final String MULTI_REFERENCE = String.format("%s, %s", REFERENCE_1, REFERENCE_2);
        final String REFERENCE_WITH_FILTER = "";
        String[] values = {null, SWITCH_VALUE, OPTIONAL_DROPDOWN, REFERENCE, MULTI_REFERENCE, REFERENCE_WITH_FILTER,
                REFERENCE_CONSTANT, null};

        WebDriver driver = getDriver();

        createReference(REFERENCE_1);
        createReference(REFERENCE_2, REFERENCE_1);
        createReference(REFERENCE_3, REFERENCE_2, REFERENCE_1);

        goPageByName("Fields Ops");
        driver.findElement(createNew).click();
        driver.findElement(createEditMainToggle).click();
        new Select(driver.findElement(createEditDropdownSelect)).selectByVisibleText(OPTIONAL_DROPDOWN);
        new Select(driver.findElement(createEditReferenceSelect)).selectByVisibleText(REFERENCE);
        for (String ref : MULTI_REFERENCE.split(", ")) {
            driver.findElement(By.xpath(String.format("//label[contains(text(), '%s')]/span", ref))).click();
        }
        clickSaveButton();

        Assert.assertEquals(driver.findElements(rows).size(), 1);
        WebElement row = driver.findElement(rows);
        List<WebElement> cols = row.findElements(By.tagName("td"));
        Assert.assertEquals(cols.size(), values.length);
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                Assert.assertEquals(cols.get(i).getText(), values[i]);
            }
        }
        Assert.assertEquals(getRecordTypeIcon(), "");

        clickSandwichAction(driver.findElement(rows), "view");
        Assert.assertEquals(driver.findElement(viewSwitchValue).getText(), SWITCH_VALUE);
        Assert.assertEquals(driver.findElement(viewDropdownValue).getText(), OPTIONAL_DROPDOWN);
        Assert.assertEquals(driver.findElement(viewReferenceValue).getText(), REFERENCE);
        Assert.assertEquals(getViewMultiReference(), MULTI_REFERENCE);
        Assert.assertFalse(isVisible(viewReferenceWithFilterValue));
        Assert.assertEquals(getViewReferenceConstant(), REFERENCE_CONSTANT);
        Assert.assertFalse(isVisible(rows));
    }

    @Test
    public void fieldOpsDeleteTest() throws InterruptedException {
        String referenceValue = UUID.randomUUID().toString();
        String refValueId = createReferenceValue(referenceValue);
        createFieldOpsToDelete(referenceValue);

        goPageByName("Fields Ops");

        int beforeCountOfRecords = getNumberOfRecords();
        By dropdownMenu = By.xpath("//td[contains(text(), '" + referenceValue + "' )]/../td/div/button");
        WebElement dropDown = getWait(3).until(ExpectedConditions.elementToBeClickable(dropdownMenu));
        ProjectUtils.click(getDriver(), dropDown);
        WebElement deleteMenuItem = getDriver().findElement(By.cssSelector("ul.dropdown-menu.show li:nth-child(3) > a"));
        ProjectUtils.click(getDriver(), deleteMenuItem);

        int afterCountOfRecords = getNumberOfRecords();
        Assert.assertNotEquals(beforeCountOfRecords, afterCountOfRecords);

        WebElement notificationIcon = getDriver().findElement(By.xpath("//i[contains(text(),'delete_outline')]"));
        ProjectUtils.click(getDriver(), notificationIcon);

        WebElement firstRow = getDriver().findElement(By.xpath("//tbody/tr[1]/td[1]"));
        Assert.assertTrue(firstRow.getText().contains(refValueId));
    }
}
