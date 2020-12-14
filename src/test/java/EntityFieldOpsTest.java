import runner.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import org.testng.Assert;

public class EntityFieldOpsTest extends BaseTest {

    @Test
    public void fieldOpsView() throws InterruptedException {

        WebDriver driver = getDriver();
        setup(driver);

        By createNew = By.xpath("//div[@class='card-icon']/i");
        driver.findElement(createNew).click();

        By upperToggle = By.xpath("//div[@class='d-flex']//span");
        driver.findElement(upperToggle).click();

        By dropDown = By.cssSelector("select#dropdown");
        new Select(driver.findElement(dropDown)).selectByValue("Done");

        String upperReference = driver.findElement(By.cssSelector("select#reference option[value='1']")).getText();
        By refSelect = By.cssSelector("select#reference");
        new Select(driver.findElement(refSelect)).selectByVisibleText(upperReference);

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

        By embedFoAddButton = By.cssSelector("td > button");
        driver.findElement(embedFoAddButton).click();

        By saveButton = By.cssSelector("button[id*='save']");
        driver.findElement(saveButton).click();

        //TODO: 9. Observe user redirected to 'Fields Ops' page
        //TODO: 10. Observe one record added at the bottom of list with corresponding values from previous steps:
        //TODO: - Checked checkbox on the left
        //TODO: - Switch value: 1
        //TODO: - Dropdown: Done
        //TODO: - Reference: First reference
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
        By entityIconXpath = By.xpath(String.format("//p[contains(text(), ' %s ')]/..", name));
        getDriver().findElement(entityIconXpath).click();
    }

    private void setup(WebDriver driver) {
        ProjectUtils.goAndLogin(driver);
        goPageByName("Fields Ops");
    }

}