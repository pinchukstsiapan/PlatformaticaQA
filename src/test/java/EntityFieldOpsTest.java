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

public class EntityFieldOpsTest extends BaseTest {

    private WebDriverWait getWait(int timeoutSecond) {
        return new WebDriverWait(getDriver(), timeoutSecond);
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

        final String entityName = "Fields Ops";
        final String expDropdown = "Pending";
        final String expSwitch = "0";
        final String expReferenceConstant = "contact@company.com";

        By cardTitle = By.className("card-title");
        By cardHeader = By.cssSelector("div.card-header");
        By createNew = By.xpath("//div[@class='card-icon']/i");
        By saveButton = By.cssSelector("button[id*='save']");
        String lastRecordXpath = "//tbody/tr[last()]";
        By recordCheckbox = By.xpath(String.format("%s/td/i[@class='fa fa-check-square-o']", lastRecordXpath));
        By switchValue = By.xpath(String.format("%s/td[2]", lastRecordXpath));
        By dropdownValue = By.xpath(String.format("%s/td[3]", lastRecordXpath));
        By referenceValue = By.xpath(String.format("%s/td[4]", lastRecordXpath));
        By multiReferenceValue = By.xpath(String.format("%s/td[5]", lastRecordXpath));
        By referenceWithFilterValue = By.xpath(String.format("%s/td[6]", lastRecordXpath));
        By referenceConstantValue = By.xpath(String.format("%s/td[7]", lastRecordXpath));
        By viewSwitchValue = By.xpath("//label[text()='Switch']/../div[1]//span");
        By viewDropdownValue = By.xpath("//label[text()='Switch']/../div[2]//span");
        By fieldsOpsRecordCard = By.cssSelector("div.card");

        WebDriver driver = getDriver();
        goPageByName(entityName);
        driver.findElement(createNew).click();
        driver.findElement(saveButton).click();
        try {
            Assert.assertEquals(driver.findElement(cardTitle).getText(), entityName,
                    String.format("Redirection to wrong page after saving new %s record", entityName));
        } catch (TimeoutException e) {
            Assert.fail(String.format("Redirection to wrong page after saving new %s record", entityName));
        }
        try {
            driver.findElement(recordCheckbox);
        } catch (TimeoutException e) {
            Assert.fail(String.format("Checkbox not found for new %s record", entityName));
        }
        Assert.assertEquals(driver.findElement(switchValue).getText(), expSwitch);
        Assert.assertEquals(driver.findElement(dropdownValue).getText(), expDropdown);
        Assert.assertEquals(driver.findElement(referenceValue).getText(), "");
        Assert.assertEquals(driver.findElement(multiReferenceValue).getText(), "");
        Assert.assertEquals(driver.findElement(referenceWithFilterValue).getText(), "");
        Assert.assertEquals(driver.findElement(referenceConstantValue).getText(), expReferenceConstant);

        driver.findElement(dropdownValue).click();
        Assert.assertEquals(driver.findElement(cardHeader).getText(), entityName);
        Assert.assertEquals(driver.findElement(viewSwitchValue).getText(), expSwitch);
        Assert.assertEquals(driver.findElement(viewDropdownValue).getText(), expDropdown);
        String[] cardText = driver.findElement(fieldsOpsRecordCard).getText()
                .split("EmbedFO")[0].split("\n");
        String viewReferenceConstant = cardText[cardText.length - 1];
        Assert.assertEquals(viewReferenceConstant, expReferenceConstant);
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