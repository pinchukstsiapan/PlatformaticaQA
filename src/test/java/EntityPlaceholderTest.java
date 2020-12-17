import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;
import java.util.UUID;

public class EntityPlaceholderTest extends BaseTest {

    @Test
    public void inputTest() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Placeholder')]/preceding-sibling::i"));
        ProjectUtils.click(driver, tab);

        WebElement icon = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        icon.click();

        final String title = UUID.randomUUID().toString();
        final String comments = "my pretty simple text";
        final int number = 37;
        final double value = 18.73;

        WebElement titleElement = driver.findElement(By.xpath("//input[@placeholder='String placeholder']"));
        titleElement.sendKeys(title);
        WebElement commentsElement = driver.findElement(By.xpath("//textarea[@placeholder='Text placeholder']"));
        commentsElement.sendKeys((comments));
        WebElement numberElement = driver.findElement(By.xpath("//input[@id='int']"));
        numberElement.sendKeys(String.valueOf(number));
        WebElement valueElement = driver.findElement(By.xpath("//input[@id='decimal']"));
        valueElement.sendKeys(String.valueOf(value));
        WebElement userSelection = driver.findElement(By.xpath("//div[contains(text(),'Demo')]"));
        ProjectUtils.click(driver, userSelection);
        WebElement newUser = driver.findElement(By.xpath("//span[text()='User 2']"));
        ProjectUtils.click(driver, newUser);

        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, submit);

        WebElement paginator = driver.findElement(By.xpath("//li[@class='page-item page-last-separator disabled']/following-sibling::li"));
        Thread.sleep(1000);
        ProjectUtils.click(driver, paginator);

        String recordTitleXpath = String.format("//div[contains(text(), '%s')]", title);
        By newRecordComments = By.xpath(String.format(" %s/../../../td[3]/a/div", recordTitleXpath));
        By newRecordInt = By.xpath(String.format(" %s/../../../td[4]/a/div", recordTitleXpath));
        By newRecordVal = By.xpath(String.format(" %s/../../../td[5]/a/div", recordTitleXpath));
        WebElement createdRecordComments = driver.findElement(newRecordComments);
        WebElement createdRecordInt = driver.findElement(newRecordInt);
        WebElement createdRecordVal = driver.findElement(newRecordVal);

        Assert.assertEquals(createdRecordComments.getText(), comments);
        Assert.assertEquals(createdRecordInt.getText(), Integer.toString(number));
        Assert.assertEquals(createdRecordVal.getText(), Double.toString(value));
    }

    // TC: https://trello.com/c/SnEQAe5S/196-entity-placeholder-view-tc06-verify-that-viewer-can-see-common-and-specific-ui-elements-on-the-page-placeholder-list
    @Test
    public void view02Test() throws InterruptedException {
        final String textLinkPlaceholder = "Placeholder";
        final String textFirstElementMenu = "delete_outline";
        final String textSecondElementMenu = "notifications_none";
        final String textThirdElementMenu = "person";
        final String textCreateNewBtn = "create_new_folder";
        final String textHeader = "Placeholder";
        final String attributeInputSearch = "placeholder";
        final String textInputSearch = "Search";
        final String attributeToggleBtn = "name";
        final String textToggleBtn = "toggle";
        final String[] headersOfColumns = new String[]{"String", "String", "Text", "Int", "Decimal", "Date", "Datetime", "File", "File image", "User", "Actions"};

        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work/");
        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");

        WebElement placeholderBtn = driver.findElement(By.xpath("//p[contains(text(),'Placeholder')]"));
        ProjectUtils.click(driver, placeholderBtn);

        WebElement listActive = driver.findElement(By.xpath("//ul[@role='tablist']//a[@href='index.php?action=action_list&list_type=table&entity_id=8']"));
        WebElement linkPlaceholder = driver.findElement(By.xpath("//div[@class='navbar-wrapper']/a/b"));

        List<WebElement> listMenu = driver.findElements(By.xpath("//ul[@class='navbar-nav']/li"));
        WebElement firstElementMenu = driver.findElement(By.xpath("//li[@class='nav-item']/a/i[1]"));
        WebElement secondElementMenu = driver.findElement(By.xpath("//li[@class='nav-item dropdown']/a/i[1]"));
        WebElement thirdElementMenu = driver.findElement(By.xpath("//a[@id='navbarDropdownProfile']/i"));

        WebElement createNewBtn = driver.findElement(By.xpath("//div[@class='card-icon']/i[@class='material-icons']"));
        WebElement header = driver.findElement(By.xpath("//div[@class='d-flex justify-content-between']/h3"));
        List<WebElement> tabListOrder = driver.findElements(By.xpath("//ul[@role='tablist']/li"));

        WebElement inputSearch = driver.findElement(By.cssSelector("input"));
        WebElement toggleBtn = driver.findElement(By.xpath("//button[@class='btn btn-secondary']"));

        List<WebElement> columns = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']//th/div[contains(@class, 'th-inner')]"));

        for (int i = 1; i < columns.size(); i++) {
            String headers = columns.get(i).getText();
            Assert.assertEquals(headers, headersOfColumns[i]);
        }

        Assert.assertTrue(listActive.isDisplayed());
        Assert.assertEquals(linkPlaceholder.getText(), textLinkPlaceholder);

        Assert.assertEquals(listMenu.size(), 3);
        Assert.assertEquals(firstElementMenu.getText(), textFirstElementMenu);
        Assert.assertEquals(secondElementMenu.getText(), textSecondElementMenu);
        Assert.assertEquals(thirdElementMenu.getText(), textThirdElementMenu);

        Assert.assertEquals(createNewBtn.getText(), textCreateNewBtn);
        Assert.assertTrue(createNewBtn.isEnabled());
        Assert.assertEquals(header.getText(), textHeader);
        Assert.assertEquals(tabListOrder.size(), 2);

        Assert.assertTrue(inputSearch.isDisplayed());
        Assert.assertEquals(inputSearch.getAttribute(attributeInputSearch), textInputSearch);
        Assert.assertEquals(toggleBtn.getAttribute(attributeToggleBtn), textToggleBtn);

        Assert.assertEquals(columns.size(), 11);
    }
}
