import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import runner.ProjectUtils;

public class EntityDefaultTest extends BaseTest {

    private WebDriver driver;
    private final String title = UUID.randomUUID().toString();

    /**
     * initialize driver field and login
     */
    private void initTest() {
        driver = getDriver();
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");
    }

    @Ignore
    @Test
    public void createRecord() {
        initTest();

        //Code to create and test new default using value in this.title
    }

    @Ignore
    @Test(dependsOnMethods = "editRecord")
    public void deleteRecord() {
        initTest();

        //Code to delete default using title value in this.title
    }

    @Ignore
    @Test
    public void editRecord() throws InterruptedException {
        initTest();

        final String editedTitle = UUID.randomUUID().toString();
        final String editedText = "Edited Text Value";
        final int editedInt = 10;

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(), 'Default')]"));
        tab.click();

        WebElement recordMenu = driver.findElement(By.xpath("//button[contains(@data-toggle, 'dropdown')] "));
        recordMenu.click();

        WebElement editFunction = driver.findElement(By.xpath("//a[text() = 'edit']"));
        System.out.println(editFunction);
        ProjectUtils.click(driver, editFunction);

        WebElement fieldString = driver.findElement(By.xpath("//input[@id = 'string']"));
        fieldString.clear();
        fieldString.sendKeys(editedTitle);

        WebElement fieldText = driver.findElement(By.xpath("//span//textarea[@id = 'text']"));
        fieldText.clear();
        fieldText.sendKeys(editedText);

        WebElement fieldInt = driver.findElement(By.xpath("//input[@id = 'int']"));
        fieldInt.clear();
        fieldInt.sendKeys(String.valueOf(editedInt));

        ClickSaveButton(driver);

        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr"));

        boolean isFailed = true;
        for (WebElement row : rows) {
            String value = row.findElements(By.cssSelector("td")).get(1).getText();

            if (editedTitle.equals(value)) {
                Assert.assertEquals(row.findElements(By.cssSelector("td")).get(2).getText(), editedText);
                Assert.assertEquals(row.findElements(By.cssSelector("td")).get(3).getText(), String.valueOf(editedInt));
                isFailed = false;
                break;
            }
        }

        if (isFailed) {
            Assert.fail("Didn't find updated Default Entity");
        }

        // Validating changed fields of the record

    }

    /**
     * scroll down to the Save button and click on it
     */
    private void ClickSaveButton(WebDriver driver) throws InterruptedException {
        WebElement saveButton = driver.findElement(By.xpath("//button[text() = 'Save']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", saveButton);
        ProjectUtils.click(driver, saveButton);
    }

    @Ignore
    @Test
    public void checkDefaultValueAndUpdateThem() throws InterruptedException {

        final String stringLineDefaultText = "DEFAULT STRING VALUE";
        final String textLineDefaultText = "DEFAULT TEXT VALUE";
        final int intLineDefault = 55;
        final double decimalLineDefault = 110.32;
        final String dateLineDefault = "01/01/1970";
        final String dateTimeLineDefault = "01/01/1970 00:00:00";
        final int defaultLinesQty = 9;
        final String userDefault = "User 1 Demo";

        final String stringLineNewText = "Updated String Value For Checking Purposes";
        final String textLineNewText = "Updated Text Value";
        final int intLineNew = 123;
        final double decimalLineNew = 22.22;
        final String dateLineNew = "11/11/2011";
        final String dateTimeLineNew = "11/11/2011 11:11:11";

        final String stringEmbedLineDefaultString = "Default String";
        final String textEmbedLineDefaultText = "Default text";
        final int intEmbedLineDefault = 77;
        final double decimalEmbedLineDefault = 153.17;
        final String userEmbedNotSelected = "Not selected";

        final String stringEmbedLineNewText = "EmbedD New String";
        final String textEmbedLineNewText = "EmbedD New text";
        final int intEmbedLineNew = 123;
        final double decimalEmbedLineNew = 22.22;
        final String dateEmbedLineNew = "12/12/2020";
        final String dateTimeEmbedLineNew = "12/12/2020 00:22:22";
        final String userEmbedDSelected = "User 4";

        WebDriver driver = ProjectUtils.loginProcedure(getDriver());

        driver.findElement(By.xpath("//a[@href='#menu-list-parent']")).click();
        driver.findElement(By.xpath("//i/following-sibling::p[contains (text(), 'Default')]")).click();
        WebElement createFolder = driver.findElement(By.xpath("//i[.='create_new_folder']/ancestor::a"));
        ProjectUtils.click(driver, createFolder);
        WebElement saveBtn = driver.findElement(By.xpath("//button[.='Save']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", saveBtn);
        Assert.assertTrue(saveBtn.isDisplayed());

        int count = driver.findElements(By.xpath("//div/label")).size();
        Assert.assertTrue(count == defaultLinesQty);

        WebElement stringLineDefaultData = driver.findElement(By.xpath("//input[@id='string']"));
        Assert.assertTrue(stringLineDefaultData.getAttribute("value").equals(stringLineDefaultText));


        WebElement textLineDefaultData = driver.findElement(By.xpath("//textarea[@id='text']"));
        Assert.assertTrue(textLineDefaultData.getText().equals(textLineDefaultText));

        WebElement intLineDefaultData = driver.findElement(By.xpath("//input[@id='int']"));
        Assert.assertTrue(intLineDefaultData.getAttribute("value").equals(intLineDefault + ""));

        WebElement decimalLineDefaultData = driver.findElement(By.xpath("//input[@id='decimal']"));
        Assert.assertTrue(decimalLineDefaultData.getAttribute("value").equals(decimalLineDefault + ""));

        WebElement dateLineDefaultData = driver.findElement(By.xpath("//input[@id='date']"));
        Assert.assertTrue(dateLineDefaultData.getAttribute("value").equals(dateLineDefault));

        WebElement dateTimeLineDefaultData = driver.findElement(By.xpath("//input[@id='datetime']"));
        Assert.assertTrue(dateTimeLineDefaultData.getAttribute("value").equals(dateTimeLineDefault));

        WebElement user = driver.findElement(By.xpath("//div[@class='filter-option-inner']/div[.='User 1 Demo']"));
        Assert.assertTrue(user.getText().equals(userDefault.toUpperCase()));

        stringLineDefaultData.clear();
        stringLineDefaultData.sendKeys(stringLineNewText);
        textLineDefaultData.clear();
        textLineDefaultData.sendKeys(textLineNewText);
        intLineDefaultData.clear();
        intLineDefaultData.sendKeys(String.valueOf(intLineNew));
        decimalLineDefaultData.clear();
        decimalLineDefaultData.sendKeys(String.valueOf(decimalLineNew));
        dateLineDefaultData.clear();
        dateLineDefaultData.sendKeys(dateLineNew);
        dateTimeLineDefaultData.clear();
        dateTimeLineDefaultData.sendKeys(dateTimeLineNew);

        WebElement dropdownUsers = driver.findElement(By.xpath("//button[@data-id='user']"));
        dropdownUsers.click();

        Thread.sleep(700);
        WebElement listOfUsers = driver.findElement(By.xpath("//span[.='User 4']/ancestor::a"));
        listOfUsers.click();

        WebElement greenPlus = driver.findElement(By.xpath("//button[@data-table_id='11']"));
        ProjectUtils.click(driver, greenPlus);

        WebElement lineNumber = driver.findElement(By.xpath("//input[@id='t-undefined-r-1-_line_number']"));
        Assert.assertEquals(lineNumber.getAttribute("data-row"), "1");

        WebElement embedDString = driver.findElement(By.xpath("//td/textarea[@id='t-11-r-1-string']"));
        Assert.assertEquals(embedDString.getText(), stringEmbedLineDefaultString);

        WebElement embedDText = driver.findElement(By.xpath("//td/textarea[@id='t-11-r-1-text']"));
        Assert.assertEquals(embedDText.getText(), textEmbedLineDefaultText);

        WebElement embedDInt = driver.findElement(By.xpath("//td/textarea[@id='t-11-r-1-int']"));
        Assert.assertEquals(embedDInt.getText(), intEmbedLineDefault + "");

        WebElement embedDDecimal = driver.findElement(By.xpath("//td/textarea[@id='t-11-r-1-decimal']"));
        Assert.assertEquals(embedDDecimal.getText(), decimalEmbedLineDefault + "");

        WebElement embedDDate = driver.findElement(By.id("t-11-r-1-date"));
        Assert.assertEquals(embedDDate.getText(), "");

        WebElement embedDDateTime = driver.findElement(By.id("t-11-r-1-datetime"));
        Assert.assertEquals(embedDDateTime.getText(), "");

        WebElement embedDUser = driver.findElement(By.xpath("//select[@id='t-11-r-1-user']/option[@value='0']"));
        Assert.assertEquals(embedDUser.getText(), userEmbedNotSelected);

        embedDString.clear();
        embedDString.sendKeys(stringEmbedLineNewText);

        embedDText.clear();
        embedDText.sendKeys(textEmbedLineNewText);

        embedDInt.clear();
        embedDInt.sendKeys(intEmbedLineNew + "");

        embedDDecimal.clear();
        embedDDecimal.sendKeys(decimalEmbedLineNew + "");

        embedDDate.click();
        embedDDate.clear();
        embedDDate.sendKeys(dateEmbedLineNew);

        embedDDateTime.click();
        embedDDateTime.clear();
        embedDDateTime.sendKeys(dateTimeEmbedLineNew);

        Select embedDUserSelect = new Select(driver.findElement(By.xpath("//select[@id='t-11-r-1-user']")));
        embedDUserSelect.selectByVisibleText(userEmbedDSelected);

        ProjectUtils.click(driver, saveBtn);

        WebElement orderBtn = driver.findElement(By.xpath("//a[contains(string(), 'Order')]"));
        orderBtn.click();

        WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Search']"));
        searchInput.sendKeys(stringLineNewText);

        WebElement ourRecord = driver.findElement(By.xpath("//div[contains (text(), 'Updated String Value For Checking Purposes')]/ancestor::a"));
        ourRecord.click();

        List<WebElement> listOfNewValues = driver.findElements(By.xpath("//span[@class='pa-view-field']"));
        Assert.assertEquals(listOfNewValues.get(0).getText(), stringLineNewText);
        Assert.assertEquals(listOfNewValues.get(1).getText(), textLineNewText);
        Assert.assertEquals(listOfNewValues.get(2).getText(), intLineNew + "");
        Assert.assertEquals(listOfNewValues.get(3).getText(), decimalLineNew + "");
        Assert.assertEquals(listOfNewValues.get(4).getText(), dateLineNew);
        Assert.assertEquals(listOfNewValues.get(5).getText(), dateTimeLineNew);

        List<WebElement> embedDArrayOfNewValues = driver.findElements(By.xpath("//table/tbody/tr/td"));
        Assert.assertEquals(embedDArrayOfNewValues.get(1).getText(), stringEmbedLineNewText);
        Assert.assertEquals(embedDArrayOfNewValues.get(2).getText(), textEmbedLineNewText);
        Assert.assertEquals(embedDArrayOfNewValues.get(3).getText(), intEmbedLineNew + "");
        Assert.assertEquals(embedDArrayOfNewValues.get(4).getText(), decimalEmbedLineNew + "");
        Assert.assertEquals(embedDArrayOfNewValues.get(5).getText(), dateEmbedLineNew);
        Assert.assertEquals(embedDArrayOfNewValues.get(6).getText(), dateTimeEmbedLineNew);
        Assert.assertEquals(embedDArrayOfNewValues.get(9).getText(), userEmbedDSelected);


        driver.navigate().back();
        driver.navigate().back();

        WebElement lastRecordDeleteBtn = null;

        List<WebElement> deleteBtns = driver.findElements(By.xpath("//a[.='delete']"));

        if (deleteBtns.size() == 1){
         lastRecordDeleteBtn = deleteBtns.get(0);
        } else {
         lastRecordDeleteBtn = deleteBtns.get(deleteBtns.size() - 1);
        }
        ProjectUtils.click(driver, lastRecordDeleteBtn);
    }

    private void accessToEntityDefaultScreen(WebDriver driver) {

        WebElement tab = driver.findElement(By.xpath("//a[@href='#menu-list-parent']/i"));
        tab.click();

        WebElement entityOpenList= driver.findElement(By.xpath("//div[@id='menu-list-parent']"));
        entityOpenList.click();

        WebElement entityTab = driver.findElement(By.xpath("//a[@href='index.php?action=action_list&entity_id=7']"));

        entityTab.click();
    }
    private void createDefaultEntity(WebDriver driver){

        WebElement newRecord = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        newRecord.click();
    }

    public void entityCreation() {

        WebDriver driver = getDriver();
        ProjectUtils.loginProcedure(driver);
        accessToEntityDefaultScreen(driver);
        createDefaultEntity(driver);
    }



    public boolean isInt(String str){

        try
        {
            Integer.parseInt(str);
            return  true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    private boolean isDouble(String str) {

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public boolean isValidDateFormat(String dateStr) {

        DateFormat sdf = new SimpleDateFormat(dateStr);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private void clickOnLastElementInTable() {

        List<WebElement> titlesWe = getDriver().findElements(By.xpath("//tr/td[2]"));
        titlesWe.get(titlesWe.size()-1).click();
    }

    private void goToLastPage(WebDriver driver){

        WebDriverWait wait = new WebDriverWait(getDriver(), 1);
        // boolean titleFound = false;
        int rowsPerPage;
        int numOfAllRecords = Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[@class='pagination-info']"))).getText().split(" ")[5]);
        WebElement rowsPerPageWe = driver.findElement(By.cssSelector("span.page-size"));
        if (rowsPerPageWe.isDisplayed()) {
            rowsPerPage = Integer.parseInt(rowsPerPageWe.getText());
        } else {
            rowsPerPage = 26;
        }
        if (numOfAllRecords > rowsPerPage) {
            boolean firstRound = true;
            while (firstRound) {
                if (getDriver().findElements(By.xpath("//a[@href='javascript:void(0)']")).size()<=3) {
                    break;
                }
                List<WebElement> pagination = driver.findElements(By.cssSelector("a.page-link"));
                pagination.get(pagination.size() -2).click();
                WebElement paginationLastIndexWe = driver.findElements(By.cssSelector("a.page-link")).get(pagination.size() -2);
                boolean paginationLastIndexActive =
                        paginationLastIndexWe.getCssValue("color").equals("rgba(255, 255, 255, 1)");
                if ( paginationLastIndexActive) {
                    firstRound = false;
                }
            }
        }

        clickOnLastElementInTable();
    }

    @Test(enabled = true)
    public void validateEntityDefaultValuesCreation() {

        WebDriver driver = getDriver();
        entityCreation();

        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='_field_container-string']//span//input")).getAttribute("value")
                ,"DEFAULT STRING VALUE");

        String attributeTextValueToCompare= driver.findElement(By.xpath("//div[@id='_field_container-text']//p//span")).getText();
        Assert.assertEquals(attributeTextValueToCompare,"DEFAULT TEXT VALUE");

        boolean isInt = isInt(driver.findElement(By.xpath("//div[@id='_field_container-int']//span//input")).getAttribute("value"));
        Assert.assertTrue(isInt);

        boolean isDouble = isDouble(driver.findElement(By.xpath("//div[@id='_field_container-int']//span//input")).getAttribute("value"));
        Assert.assertTrue(isDouble);

        boolean isValidDate = isValidDateFormat(driver.findElement(By.xpath("//div[@id='_field_container-date']//input")).getAttribute("value"));
        Assert.assertTrue(isValidDate);

        boolean isValidDateTime = isValidDateFormat(driver.findElement(By.xpath("//div[@id='_field_container-datetime']//input")).getAttribute("value"));
        Assert.assertTrue(isValidDateTime);

        String userName= driver.findElement(By.xpath("//button[@data-id='user']")).getAttribute("title").toUpperCase();
        String userNameExpected= driver.findElement(By.xpath("//button[@data-id='user']//div//div//div[@class='filter-option-inner-inner']")).getText().toUpperCase();
        Assert.assertEquals(userName,userNameExpected);

        WebElement saveBtn = driver.findElement(By.xpath("//button[.='Save']"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", saveBtn);
        Assert.assertTrue(saveBtn.isDisplayed());

        driver.findElement(By.xpath("//table[@id='table-11']//button[@data-table_id='11']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='table-11']//tbody//tr[2]//td[3]")).getText()
                ,"Default String");

        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='table-11']//tbody//tr[2]//td[4]")).getText()
                ,"Default text");

        Assert.assertTrue(isInt(driver.findElement(By.xpath("//table[@id='table-11']//tbody//tr[2]//td[5]")).getText()));

        Assert.assertTrue(isDouble(driver.findElement(By.xpath("//table[@id='table-11']//tbody//tr[2]//td[6]")).getText()));

        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);
    }

    @Test(enabled = true)
    public void validateEntityDefaultValuesRecord() {

        WebDriver driver = getDriver();
        entityCreation();
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", saveButton);
        Assert.assertTrue(saveButton.isDisplayed());

        driver.findElement(By.xpath("//table[@id='table-11']//button[@data-table_id='11']")).click();
        ProjectUtils.click(driver, saveButton);
        clickOnLastElementInTable();

        List<WebElement> objectValidation = driver.findElements(By.xpath("//span[@class='pa-view-field']"));

        Assert.assertEquals((objectValidation.get(0).getText()),"DEFAULT STRING VALUE");

        Assert.assertEquals((objectValidation.get(1).getText()),"DEFAULT TEXT VALUE");

        boolean isInt = isInt(objectValidation.get(2).getText());
        Assert.assertTrue(isInt);

        boolean isDouble = isDouble(objectValidation.get(3).getText());
        Assert.assertTrue(isDouble);

        boolean isValidDate = isValidDateFormat(objectValidation.get(3).getText());
        Assert.assertTrue(isValidDate);

        boolean isValidDateTime = isValidDateFormat(objectValidation.get(5).getText());
        Assert.assertTrue(isValidDateTime);

        String userName= "USER 1 DEMO".toUpperCase();
        String userNameExpected= driver.findElement(By.xpath("//label[text()='User']/following-sibling::p")).getText().toUpperCase();
        Assert.assertEquals(userName,userNameExpected);

        List<WebElement> allCells = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']//tr//td"));

        Assert.assertEquals(allCells.get(1).getText()
                ,"Default String");

        Assert.assertEquals(allCells.get(2).getText()
                ,"Default text");

        Assert.assertTrue(isInt(allCells.get(3).getText()));

        Assert.assertTrue(isDouble(allCells.get(4).getText()));
    }
}
