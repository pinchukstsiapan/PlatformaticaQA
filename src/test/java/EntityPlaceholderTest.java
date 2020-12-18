import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.List;
import java.util.UUID;

public class EntityPlaceholderTest extends BaseTest {

    @Test
    public void inputTest() throws InterruptedException {

        WebDriver driver = ProjectUtils.loginProcedure(getDriver());

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Placeholder')]/preceding-sibling::i"));
        ProjectUtils.click(driver, tab);
        WebElement icon = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        ProjectUtils.click(driver,icon);

        final String title = UUID.randomUUID().toString();
        final String comments = "my pretty simple text";
        final int number = 37;
        final double value = 18.73;

        WebElement titleElement = driver.findElement(By.xpath("//input[@placeholder='String placeholder']"));
        titleElement.sendKeys(title);
        WebElement commentsElement= driver.findElement(By.xpath("//textarea[@placeholder='Text placeholder']"));
        commentsElement.sendKeys((comments));
        WebElement numberElement = driver.findElement(By.xpath("//input[@id='int']"));
        numberElement.sendKeys(String.valueOf(number));
        WebElement valueElement = driver.findElement(By.xpath("//input[@id='decimal']"));
        valueElement.sendKeys(String.valueOf(value));
        WebElement userSelection = driver.findElement(By.xpath("//div[contains(text(),'Demo')]"));
        ProjectUtils.click(driver,userSelection);
        WebElement newUser= driver.findElement(By.xpath("//span[text()='User 2']"));
        ProjectUtils.click(driver,newUser);

        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver,submit);

        String recordTitleXpath = String.format("//div[contains(text(), '%s')]", title);
        By newRecordComments = By.xpath(String.format(" %s/../../../td[3]/a/div", recordTitleXpath));
        By newRecordInt = By.xpath(String.format(" %s/../../../td[4]/a/div", recordTitleXpath));
        By newRecordVal = By.xpath(String.format(" %s/../../../td[5]/a/div", recordTitleXpath));
        WebElement createdRecordComments = driver.findElement(newRecordComments);
        WebElement createdRecordInt = driver.findElement(newRecordInt);
        WebElement createdRecordVal = driver.findElement(newRecordVal);

        Assert.assertEquals(createdRecordComments.getText(),comments);
        Assert.assertEquals(createdRecordInt.getText(),Integer.toString(number));
        Assert.assertEquals(createdRecordVal.getText(),Double.toString(value));
    }
    @Ignore
    @Test
    public void verifyDecimal() throws InterruptedException {

        WebDriver driver = ProjectUtils.loginProcedure(getDriver());

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Placeholder')]/preceding-sibling::i"));
        ProjectUtils.click(driver, tab);
        WebElement icon = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        ProjectUtils.click(driver,icon);

        final String title = UUID.randomUUID().toString();
        final String comments = "This is my record";
        final int number = 1;
        final double decimalValue = 37.0;

        WebElement titleElement = driver.findElement(By.xpath("//input[@placeholder='String placeholder']"));
        titleElement.sendKeys(title);
        WebElement commentsElement= driver.findElement(By.xpath("//textarea[@placeholder='Text placeholder']"));
        commentsElement.sendKeys((comments));
        WebElement numberElement = driver.findElement(By.xpath("//input[@id='int']"));
        numberElement.sendKeys(String.valueOf(number));
        WebElement valueElement = driver.findElement(By.xpath("//input[@id='decimal']"));
        valueElement.sendKeys(String.valueOf(decimalValue));
        WebElement userSelection = driver.findElement(By.xpath("//div[contains(text(),'Demo')]"));
        ProjectUtils.click(driver,userSelection);
        WebElement newUser= driver.findElement(By.xpath("//span[text()='User 2']"));
        ProjectUtils.click(driver,newUser);

        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver,submit);

        String recordTitleXpath = String.format("//div[contains(text(), '%s')]", title);
        By newRecordComments = By.xpath(String.format(" %s/../../../td[3]/a/div", recordTitleXpath));
        By newRecordInt = By.xpath(String.format(" %s/../../../td[4]/a/div", recordTitleXpath));
        By newRecordVal = By.xpath(String.format(" %s/../../../td[5]/a/div", recordTitleXpath));
        WebElement createdRecordComments = driver.findElement(newRecordComments);
        WebElement createdRecordInt = driver.findElement(newRecordInt);
        WebElement createdRecordVal = driver.findElement(newRecordVal);

        Assert.assertEquals(createdRecordComments.getText(),comments);
        Assert.assertEquals(createdRecordInt.getText(),Integer.toString(number));
        Assert.assertEquals(createdRecordVal.getText(),Double.toString(decimalValue));
    }

    @Test
    public void view02Test() {

        final String textLinkPlaceholder = "Placeholder";
        final String textFirstElementMenu = "delete_outline";
        final String textSecondElementMenu = "notifications_none";
        final String textThirdElementMenu = "person";
        final String textCreateNewBtn = "create_new_folder";
        final String textHeader = "Placeholder";

        WebDriver driver = ProjectUtils.loginProcedure(getDriver());

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
    }

    @Test
    public void view01Test() {
        WebDriver driver = getDriver();
        ProjectUtils.loginProcedure(driver);

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Placeholder')]"));
        ProjectUtils.click(driver, tab);

        WebElement newRecord = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]/../.."));
        ProjectUtils.click(driver, newRecord);

        final String title = "TestPlaceholderView";
        final String text = "PlaceholderViewText";
        final int number = 11;
        final double decimal = 11.65;

        WebElement titleElement = driver.findElement(By.xpath("//input[@name='entity_form_data[string]']"));
        titleElement.sendKeys(title);
        WebElement textElement = driver.findElement(By.xpath("//textarea[@name='entity_form_data[text]']"));
        textElement.sendKeys(text);
        WebElement numberElement = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        numberElement.sendKeys(String.valueOf(number));
        WebElement decimalElement = driver.findElement(By.xpath("//input[@name='entity_form_data[decimal]']"));
        decimalElement.sendKeys(String.valueOf(decimal));
        WebElement submit = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, submit);

        WebElement openRecord = driver.findElement(By.xpath("//div[contains(text(),'" + title + "')]"));
        ProjectUtils.click(driver, openRecord);
        WebElement newWindow = driver.findElement(By.xpath("//h4[contains(text(),'Placeholder')]"));
        Assert.assertEquals(newWindow.getText(), "Placeholder");
        WebElement newTitle = driver.findElement(By.xpath("//span[contains(text(),'" + title + "')]"));
        Assert.assertEquals(newTitle.getText(), title);
        WebElement newText = driver.findElement(By.xpath("//span[contains(text(),'" + text + "')]"));
        Assert.assertEquals(newText.getText(), text);
        WebElement newInt = driver.findElement(By.xpath("//span[contains(text(),'" + number + "')]"));
        Assert.assertEquals(newInt.getText(), String.valueOf(number));
        WebElement newDecimal = driver.findElement(By.xpath("//span[contains(text(),'" + decimal + "')]"));
        Assert.assertEquals(newDecimal.getText(), String.valueOf(decimal));

        WebElement returnTab = driver.findElement(By.xpath("//p[contains(text(),'Placeholder')]"));
        ProjectUtils.click(driver, returnTab);

        WebElement menuButton = driver.findElement(By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']"));
        ProjectUtils.click(driver, menuButton);
        WebElement menuDelete = driver.findElement(By.xpath("//a[contains(text(),'delete')]"));
        ProjectUtils.click(driver, menuDelete);
    }
}
