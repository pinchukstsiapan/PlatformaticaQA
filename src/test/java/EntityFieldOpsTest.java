import runner.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.Assert;

public class EntityFieldOpsTest extends BaseTest {

    @Test
    public void fieldOpsView() throws InterruptedException {

        WebDriver driver = getDriver();

        ProjectUtils.goAndLogin(driver);
        goPageByName("Fields Ops");

        By createNew = By.xpath("//div[@class='card-icon']/i");
        driver.findElement(createNew).click();

        Thread.sleep(3000);

        //TODO: 2. Set 'Switch' to 'On'
        //TODO: 3. In 'Dropdown' select 'Done'
        //TODO: 4. In 'Reference' select 'First Reference'
        //TODO: 5. Check checkboxes for 'First reference' and 'Second reference' in 'Multireference' section
        //TODO: 6. For 'Reference with filter' select 'THIRD REFERENCE'
        //TODO: 7. Click '+' button in 'EmbedFO' section and leave all fields default
        //TODO: 8. Click 'SAVE' button
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
    }

    public void goPageByName(String name) {
        By entityIconXpath = By.xpath(String.format("//p[contains(text(), ' %s ')]/..", name));
        getDriver().findElement(entityIconXpath).click();
    }
}