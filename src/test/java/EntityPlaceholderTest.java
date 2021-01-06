import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import java.util.List;
import java.util.UUID;

public class EntityPlaceholderTest extends BaseTest {

    private final String TITLE = UUID.randomUUID().toString();
    private final String COMMENTS = RandomStringUtils.randomAlphabetic(50);
    private final String INT_NUMBER = String.valueOf((int) (Math.random() * 100000000));
    private final String userSelected = "User 2";

    By placeholderButtonXpath = By.xpath("//p[contains(text(),'Placeholder')]/preceding-sibling::i");
    By createNewFolderXpath = By.xpath("//i[contains(text(),'create_new_folder')]");
    By inputTitleXpath = By.xpath("//input[@placeholder='String placeholder']");
    By inputCommentsXpath = By.xpath("//textarea[@placeholder='Text placeholder']");
    By inputIntNumberXpath = By.xpath("//input[@id='int']");
    By inputDecimalXpath = By.xpath("//input[@id='decimal']");
    By saveButtonXpath = By.id("pa-entity-form-save-btn");
    By newUserSelectedXpath = By.xpath("//span[text()='User 2']");

    @Test
    public void inputTest() throws InterruptedException {

        final String decimal = "95.46";
        String[] array = {null, TITLE, COMMENTS, INT_NUMBER, decimal, null, null, null, null, userSelected, null};
        WebDriver driver = getDriver();

        ProjectUtils.click(driver, driver.findElement(placeholderButtonXpath));
        ProjectUtils.click(driver, driver.findElement(createNewFolderXpath));
        WebElement titleElement = driver.findElement(inputTitleXpath);
        titleElement.sendKeys(TITLE);
        WebElement commentsElement = driver.findElement(inputCommentsXpath);
        commentsElement.sendKeys((COMMENTS));
        WebElement numberElement = driver.findElement(inputIntNumberXpath);
        numberElement.sendKeys(INT_NUMBER);
        WebElement valueElement = driver.findElement(inputDecimalXpath);
        valueElement.sendKeys(decimal);
        WebElement userSelection = driver.findElement(By.xpath("//div[contains(text(),'Demo')]"));
        ProjectUtils.click(driver, userSelection);
        ProjectUtils.click(driver, driver.findElement(newUserSelectedXpath));
        ProjectUtils.click(driver, driver.findElement(saveButtonXpath));

        List<WebElement> listOfRecords = driver.findElements(By.xpath("//tbody/tr"));
        List<WebElement> listOfValues = listOfRecords.get(0).findElements(By.xpath("//td"));
        Assert.assertEquals(listOfValues.size(), array.length);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                Assert.assertEquals(listOfValues.get(i).getText(), array[i]);
            }
        }
    }

    @Ignore
    @Test
    public void verifyDecimal() throws InterruptedException {

        final String newDecimal = "37.0";
        String[] array = {null, TITLE, COMMENTS, INT_NUMBER, newDecimal, null, null, null, null,userSelected,null};
        WebDriver driver = getDriver();

        ProjectUtils.click(driver, driver.findElement(placeholderButtonXpath));
        ProjectUtils.click(driver, driver.findElement(createNewFolderXpath));
        WebElement titleElement = driver.findElement(inputTitleXpath);
        titleElement.sendKeys(TITLE);
        WebElement commentsElement = driver.findElement(inputCommentsXpath);
        commentsElement.sendKeys(COMMENTS);
        WebElement numberElement = driver.findElement(inputIntNumberXpath);
        numberElement.sendKeys(INT_NUMBER);
        WebElement valueElement = driver.findElement(inputDecimalXpath);
        valueElement.sendKeys(newDecimal);
        WebElement userSelection = driver.findElement(By.xpath("//div[contains(text(),'Demo')]"));
        ProjectUtils.click(driver, userSelection);
        ProjectUtils.click(driver, driver.findElement(newUserSelectedXpath));
        ProjectUtils.click(driver, driver.findElement(saveButtonXpath));

        List<WebElement> listOfRecords = driver.findElements(By.xpath("//tbody/tr"));
        List<WebElement> listOfValues = listOfRecords.get(0).findElements(By.xpath("//td"));
        Assert.assertEquals(listOfValues.size(), array.length);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                Assert.assertEquals(listOfValues.get(i).getText(), array[i]);
            }
        }
    }

    @Test
    public void view02Test() {

        final String textLinkPlaceholder = "Placeholder";
        final String textFirstElementMenu = "delete_outline";
        final String textSecondElementMenu = "notifications_none";
        final String textThirdElementMenu = "person";
        final String textCreateNewBtn = "create_new_folder";
        final String textHeader = "Placeholder";

        WebDriver driver = getDriver();

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
    @Test
    public void deleteRecord() {

        final String id = UUID.randomUUID().toString();

        WebDriver driver = getDriver();

        WebElement tab = driver.findElement(By.xpath("//p[text()=' Placeholder ']"));
        ProjectUtils.click(driver,tab);

        WebElement createNewFolder = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createNewFolder.click();

        WebElement titleElement = driver.findElement(By.xpath("//input[@name='entity_form_data[string]']"));
        titleElement.sendKeys(id);

        WebElement saveBtn = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver,saveBtn);

        WebElement list = driver.findElement(By.xpath("(//div[1]/div/ul/li[1]/a/i)[3]"));
        list.click();

        WebElement firstColumn = driver.findElement(By.xpath("(//td/a/div)[1]"));
        Assert.assertEquals(firstColumn.getText(),id);

        WebElement menuAction = driver.findElement(By.xpath("//i[text() = 'menu']/.."));
        menuAction.click();

        WebElement deleteButton = driver.findElement(By.xpath("//a[contains(text(),'delete')]"));
        ProjectUtils.click(driver,deleteButton);

        new WebDriverWait(driver, 3).until(ExpectedConditions.
                invisibilityOfElementLocated(By.xpath("//div[contains(text(),'" + id +"')]")));
        List<WebElement> listOfElements = driver.findElements(By.xpath("//tbody/tr/.."));
        Assert.assertEquals(listOfElements.size(), 0);

        WebElement recycleButton = driver.findElement(By.xpath("//i[contains(text(),'delete_outline')]/.."));//
        recycleButton.click();

        WebElement deletedField = driver.findElement(By.xpath("//b[contains(text(),'" + id + "')]"));
        Assert.assertEquals(deletedField.getText(), id);
    }
}
