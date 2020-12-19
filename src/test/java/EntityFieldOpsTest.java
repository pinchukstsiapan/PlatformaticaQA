import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EntityFieldOpsTest extends BaseTest {

    @Test
    public void newRecord() throws InterruptedException {
        WebDriver driver = getDriver();
        ProjectUtils.loginProcedure(driver);
        driver.get("https://ref.eteam.work");

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Fields Ops')]"));
        tab.click();

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

    @Ignore
    @Test
    public void fieldOpsView() {

        WebDriver driver = getDriver();
        setup();

        int numOfPreexistingRecords = getNumberOfRecords();

        By createNew = By.xpath("//div[@class='card-icon']/i");
        By upperToggle = By.xpath("//div[@class='d-flex']//span");
        By dropDown = By.cssSelector("select#dropdown");
        By refSelect = By.cssSelector("select#reference");
        By firstRefLabel = By.xpath("//input[@id='multireference-1']/..");
        By secondRefLabel = By.xpath("//input[@id='multireference-2']/..");
        By refFilterSelect = By.cssSelector("select#reference_with_filter");
        By refConstant = By.cssSelector("span[class$='filled'] > input[id$='reference_constant']");
        By embedFoAddButton = By.cssSelector("td > button");
        By saveButton = By.cssSelector("button[id*='save']");

        String lastRecordXpath = "//tbody/tr[last()]";
        By recordCheckbox = By.xpath(String.format("%s/td/i[@class='fa fa-check-square-o']", lastRecordXpath));
        By switchValue = By.xpath(String.format("%s/td[2]", lastRecordXpath));
        By dropdownValue = By.xpath(String.format("%s/td[3]", lastRecordXpath));
        By referenceValue = By.xpath(String.format("%s/td[4]", lastRecordXpath));
        By multiReferenceValue = By.xpath(String.format("%s/td[5]", lastRecordXpath));
        By referenceWithFilterValue = By.xpath(String.format("%s/td[6]", lastRecordXpath));
        By referenceConstantValue = By.xpath(String.format("%s/td[7]", lastRecordXpath));

        By recordViewReference = By.xpath("//label[text()='Reference']/following-sibling::p");
        By recordViewMultireferences = By.xpath("//label[text()='Multireference']/following-sibling::p");
        By recordViewRefWithFilterValue = By.xpath("//label[text()='Reference with filter']/following-sibling::p");

        By embedFoTableData = By.cssSelector("tbody td");

        driver.findElement(createNew).click();
        driver.findElement(upperToggle).click();
        final String mainDropdown = "Done";
        new Select(driver.findElement(dropDown)).selectByValue(mainDropdown);
        String mainReference = driver.findElement(By.cssSelector("select#reference option[value='1']")).getText();
        new Select(driver.findElement(refSelect)).selectByVisibleText(mainReference);
        WebElement firstReferenceLabel = driver.findElement(firstRefLabel);
        String multiRefOne = firstReferenceLabel.getText();
        firstReferenceLabel.click();
        WebElement secondReferenceLabel = driver.findElement(secondRefLabel);
        String multiRefTwo = secondReferenceLabel.getText();
        secondReferenceLabel.click();
        String referenceWithFilter = driver.findElement(By.cssSelector(
                "select#reference_with_filter option:nth-of-type(2)")).getText();
        new Select(driver.findElement(refFilterSelect)).selectByVisibleText(referenceWithFilter);
        String referenceConstant = driver.findElement(refConstant).getAttribute("value");
        driver.findElement(embedFoAddButton).click();
        driver.findElement(saveButton).click();

        try {
            By pageTitle = By.cssSelector("h3");
            Assert.assertEquals(driver.findElement(pageTitle).getText(), "Fields Ops",
                    "Redirection to wrong page after saving new Fields Ops record");
        } catch (TimeoutException e) {
            Assert.fail("Redirection to wrong page after saving new Fields Ops record");
        }

        int numOfRecords = getNumberOfRecords();
        if (numOfRecords == numOfPreexistingRecords) {
            Assert.fail("Number of records didn't change after creating new Fields Ops record");
        } else if (numOfRecords != numOfPreexistingRecords + 1) {
            String errorMessage = String.format(
                    "Number of records changed by %s after creating one new Fields Ops record",
                    (numOfRecords - numOfPreexistingRecords));
            Assert.fail(errorMessage);
        }

        getPaginationLastPage();

        try {
            driver.findElement(recordCheckbox);
        } catch (TimeoutException e) {
            Assert.fail("Checkbox not found for new Fields Ops record");
        }
        Assert.assertEquals(driver.findElement(switchValue).getText(), "1",
                "Wrong Switch value for new Fields Ops record");
        Assert.assertEquals(driver.findElement(dropdownValue).getText(), mainDropdown,
                "Wrong Switch value for new Fields Ops record");
        Assert.assertEquals(driver.findElement(referenceValue).getText(), mainReference,
                "Wrong Reference value for new Fields Ops record");
        Assert.assertEquals(driver.findElement(multiReferenceValue).getText(),
                String.format("%s, %s", multiRefOne, multiRefTwo),
                "Wrong Multireference value for new Fields Ops record");
        Assert.assertEquals(driver.findElement(referenceWithFilterValue).getText(), referenceWithFilter,
                "Wrong Reference with filter value for new Fields Ops record");
        Assert.assertEquals(driver.findElement(referenceConstantValue).getText(), referenceConstant,
                "Wrong Multireference value for new Fields Ops record");

        driver.findElement(dropdownValue).click();

        try {
            By pageTitle = By.cssSelector("h4");
            Assert.assertEquals(driver.findElement(pageTitle).getText(), "Fields Ops",
                    "Redirection to wrong page, expected individual Fields Ops page");
        } catch (TimeoutException e) {
            Assert.fail("Redirection to wrong page, expected individual Fields Ops page");
        }

        String recordViewSwitchValue = driver.findElements(By.cssSelector("span.pa-view-field")).get(0).getText();
        String recordViewDropdownValue = driver.findElements(By.cssSelector("span.pa-view-field")).get(1).getText();
        List<String> recordViewMultireference = new ArrayList<>();
        for (WebElement reference : driver.findElements(recordViewMultireferences)) {
            recordViewMultireference.add(reference.getText());
        }
        String[] cardText = driver.findElement(By.cssSelector("div.card")).getText()
                .split("EmbedFO")[0].split("\n");
        String recordViewReferenceConstant = cardText[cardText.length - 1];

        List<WebElement> embedFoTableDataLstWe = driver.findElements(embedFoTableData);
        String embedFoNumberValue = embedFoTableDataLstWe.get(0).getText();
        String embedFoSwitchValue = embedFoTableDataLstWe.get(1).getText();
        String embedFoDropdownValue = embedFoTableDataLstWe.get(2).getText();
        String embedFoReferenceValue = embedFoTableDataLstWe.get(3).getText();
        String embedFoReferenceWithfilterValue = embedFoTableDataLstWe.get(4).getText();
        String embedFoReferenceConstantValue = embedFoTableDataLstWe.get(5).getText();
        String embedFoMultireferenceValue = embedFoTableDataLstWe.get(6).getText();

        Assert.assertEquals(recordViewSwitchValue, "1");
        Assert.assertEquals(recordViewDropdownValue, mainDropdown);
        Assert.assertEquals(driver.findElement(recordViewReference).getText(), mainReference);
        Assert.assertEquals(recordViewMultireference, new ArrayList<>(Arrays.asList(multiRefOne, multiRefTwo)));
        Assert.assertEquals(driver.findElement(recordViewRefWithFilterValue).getText(), referenceWithFilter);
        Assert.assertEquals(recordViewReferenceConstant, referenceConstant);
        Assert.assertEquals(embedFoNumberValue, "1");
        Assert.assertEquals(embedFoSwitchValue, "");
        Assert.assertEquals(embedFoDropdownValue, "Plan");
        Assert.assertEquals(embedFoReferenceValue, "First reference");
        Assert.assertEquals(embedFoReferenceWithfilterValue, "Third reference");
        Assert.assertEquals(embedFoReferenceConstantValue, referenceConstant);
        Assert.assertEquals(embedFoMultireferenceValue, "");

        driver.navigate().back();
        getPaginationLastPage();
        deleteLastRecord();
    }

    private WebDriverWait getWait(int timeoutSecond) {
        return new WebDriverWait(getDriver(), timeoutSecond);
    }

    private void setup() {
        ProjectUtils.loginProcedure(getDriver());
        goPageByName("Fields Ops");
    }

    public void goPageByName(String name) {
        WebElement iconElement = getDriver().findElement(By.xpath(String.format("//p[contains(text(), ' %s ')]/..", name)));

        if (!iconElement.isDisplayed()) {
            scrollToElement(iconElement);
        }
        iconElement.click();
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

    private void getPaginationLastPage() {
        if (getNumberOfRecords() > 10) {
            List<WebElement> paginationButtons = getDriver().findElements(By.cssSelector("a.page-link"));
            paginationButtons.get(paginationButtons.size() - 2).click();
        }
    }

    private void deleteLastRecord() {
        WebDriver driver = getDriver();
        String lastRecordRowXpath = "//tbody/tr[last()]";
        By recordMenuButton = By.xpath(String.format("%s//button", lastRecordRowXpath));
        By deleteButton = By.xpath(String.format("%s//a[contains(@href, 'delete')]", lastRecordRowXpath));
        driver.findElement(recordMenuButton).click();
        ProjectUtils.click(driver, getWait(2).until(ExpectedConditions.elementToBeClickable(deleteButton)));
    }


    @Test
    @Ignore("https://trello.com/c/dG6yE2lf/35-fields-ops-deleted-record-isnt-present-in-the-recycle-bin")
    public void fieldOpsDeleteTest() throws InterruptedException {
        String referenceValue = "Delete Reference";

        setup();

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
}