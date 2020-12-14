import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import runner.BaseTest;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;

public class EntityFieldOpsTest extends BaseTest {

    /*
    fieldOpsView test: https://trello.com/c/MrpbKi4t/90-entity-fields-ops-view-tc
    */
    @Test
    public void fieldOpsView() throws InterruptedException {

        WebDriver driver = getDriver();
        setup(driver);

        int numOfPreexistingRecords = getNumberOfRecords();

        By createNew = By.xpath("//div[@class='card-icon']/i");
        driver.findElement(createNew).click();

        By upperToggle = By.xpath("//div[@class='d-flex']//span");
        driver.findElement(upperToggle).click();

        final String mainDropdown = "Done";
        By dropDown = By.cssSelector("select#dropdown");
        new Select(driver.findElement(dropDown)).selectByValue(mainDropdown);

        String mainReference = driver.findElement(By.cssSelector("select#reference option[value='1']")).getText();
        By refSelect = By.cssSelector("select#reference");
        new Select(driver.findElement(refSelect)).selectByVisibleText(mainReference);

        By firstRefLabel = By.xpath("//input[@id='multireference-1']/..");
        WebElement firstReferenceLabel = driver.findElement(firstRefLabel);
        String multiRefOne = firstReferenceLabel.getText();
        firstReferenceLabel.click();
        By secondRefLabel = By.xpath("//input[@id='multireference-2']/..");
        WebElement secondReferenceLabel = driver.findElement(secondRefLabel);
        String multiRefTwo = secondReferenceLabel.getText();
        secondReferenceLabel.click();

        String referenceWithFilter = driver.findElement(By.cssSelector(
                "select#reference_with_filter option:nth-of-type(2)")).getText();
        By refFilterSelect = By.cssSelector("select#reference_with_filter");
        new Select(driver.findElement(refFilterSelect)).selectByVisibleText(referenceWithFilter);

        By refConstant = By.cssSelector("span[class$='filled'] > input[id$='reference_constant']");
        String referenceConstant = driver.findElement(refConstant).getAttribute("value");

        By embedFoAddButton = By.cssSelector("td > button");
        driver.findElement(embedFoAddButton).click();

        By saveButton = By.cssSelector("button[id*='save']");
        driver.findElement(saveButton).click();

        try {
            By pageTitle = By.cssSelector("h3");
            Assert.assertEquals(driver.findElement(pageTitle).getText(), "Fields Ops",
                    "Redirection to wrong page after saving new Fields Ops record");
        } catch (TimeoutException e) {
            Assert.fail("Redirection to wrong page after saving new Fields Ops record");
        }

        //TODO: 10. Observe one record added at the bottom of list with corresponding values from previous steps:
        int numOfRecords = getNumberOfRecords();
        if (numOfRecords == numOfPreexistingRecords) {
            Assert.fail("Number of records didn't change after creating new Fields Ops record");
        } else if (numOfRecords != numOfPreexistingRecords + 1) {
            String errorMessage = String.format(
                    "Number of records changed by %s after creating one new Fields Ops record",
                    String.valueOf(numOfRecords - numOfPreexistingRecords));
            Assert.fail(errorMessage);
        }

        getLastPage();

        String lastRecordXpath = "//tbody/tr[last()]";
        try {
            By recordCheckbox = By.xpath(String.format("%s/td/i[@class='fa fa-check-square-o']", lastRecordXpath));
            driver.findElement(recordCheckbox);
        } catch (TimeoutException e) {
            Assert.fail("Checkbox not found for new Fields Ops record");
        }

        By switchValue = By.xpath(String.format("%s/td[2]", lastRecordXpath));
        Assert.assertEquals(driver.findElement(switchValue).getText(), "1",
                "Wrong Switch value for new Fields Ops record");

        By dropdownValue = By.xpath(String.format("%s/td[3]", lastRecordXpath));
        Assert.assertEquals(driver.findElement(dropdownValue).getText(), mainDropdown,
                "Wrong Switch value for new Fields Ops record");

        By referenceValue = By.xpath(String.format("%s/td[4]", lastRecordXpath));
        Assert.assertEquals(driver.findElement(referenceValue).getText(), mainReference,
                "Wrong Reference value for new Fields Ops record");

        //TODO: - Multireference: First reference, Second reference
        //TODO: - Reference with filter: Third reference
        //TODO: - Reference constant: contact@company.com
        //TODO: 11. Click on Dropdown value for newly created record (the bottom record)
        //TODO: 12. Observe user redirected to individual record page
        //TODO: 13. Verify values from previous steps:
        //TODO: - Switch value: 1
        //TODO: - Dropdown: Done
        //TODO: - Reference: First reference
        //TODO: - Multireference: First reference, Second reference
        //TODO: - Reference with filter: Third reference
        //TODO: - Reference constant: contact@company.com
        //TODO: - EmbedFO : #: 1
        //TODO: - EmbedFO : Switch value: 0
        //TODO: - EmbedFO : Dropdown: Plan
        //TODO: - EmbedFO : Reference: First reference
        //TODO: - EmbedFO : Reference with filter: Third reference
        //TODO: - EmbedFO : Reference constant: contact@company.com
        //TODO: - EmbedFO : Multireference: None
        //TODO: CleanUp - delete last record
    }

    public void goPageByName(String name) {
        By entityIcon = By.xpath(String.format("//p[contains(text(), ' %s ')]/..", name));
        getDriver().findElement(entityIcon).click();
    }

    private void setup(WebDriver driver) {
        driver.get("https://ref.eteam.work");
        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");
        goPageByName("Fields Ops");
    }

    private WebDriverWait getWait(int timeoutSecond) {
        return new WebDriverWait(getDriver(), timeoutSecond);
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

    private void getLastPage() {
        if (getNumberOfRecords() > 10) {
            List<WebElement> paginationButtons = getDriver().findElements(By.cssSelector("a.page-link"));
            System.out.println(paginationButtons.size() - 2);
            paginationButtons.get(paginationButtons.size() - 2).click();
        }
    }

}