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
import runner.type.Run;
import runner.type.RunType;

@Run(run = RunType.Single)

public class EntityDefaultTest extends BaseTest {

    private class FieldValues {
        String fieldString;
        String fieldText;
        String fieldInt;
        String fieldDecimal;
        String fieldDate;
        String fieldDateTime;
        String fieldUser;
        int linesQty;

        private FieldValues(String fieldString, String fieldText, String fieldInt, String fieldDecimal, String fieldDate, String fieldDateTime, String fieldUser, int linesQty) {
            this.fieldString = fieldString;
            this.fieldText = fieldText;
            this.fieldInt = fieldInt;
            this.fieldDecimal = fieldDecimal;
            this.fieldDate = fieldDate;
            this.fieldDateTime = fieldDateTime;
            this.fieldUser = fieldUser;
            this.linesQty = linesQty;
        }
    }

    private final FieldValues defaultValues = new FieldValues(
            "DEFAULT STRING VALUE",
            "DEFAULT TEXT VALUE",
            "55",
            "110.32",
            "01/01/1970",
            "01/01/1970 00:00:00",
            "User 1 Demo",
            9);

    private final FieldValues changedDefaultValues = new FieldValues(
            UUID.randomUUID().toString(),
            "Some random text as Edited Text Value",
             String.valueOf((int) (Math.random() * 100)),
            "12.3",
            "01/01/2021",
            "01/01/2021 12:34:56",
            "user115@tester.com",
            9);

    private final FieldValues newValues = new FieldValues(
            UUID.randomUUID().toString(),
            "Some random text as Edited Text Value",
             String.valueOf((int) (Math.random() * 100)),
            "50.7",
            "30/12/2020",
            "30/12/2020 12:34:56",
            "user100@tester.com",
            9);

    private final String[] VALUES = {null, newValues.fieldString, newValues.fieldText,
                  newValues.fieldInt, newValues.fieldDecimal,
                  newValues.fieldDate, newValues.fieldDateTime, null, null, newValues.fieldUser, null};

    private static final By BY_STRING = By.id("string");
    private static final By BY_TEXT = By.id("text");
    private static final By BY_INT = By.id("int");
    private static final By BY_DECIMAL = By.id("decimal");
    private static final By BY_DATE = By.id("date");
    private static final By BY_DATETIME = By.id("datetime");

    private void assertAndReplace(WebDriver driver, By by, String oldText, String newText ) {
        WebElement element = driver.findElement(by);
        Assert.assertEquals(element.getAttribute("value"), oldText);
        element.clear();
        element.sendKeys(newText);
    }

    @ Test
    public void editRecord() {

        WebDriver driver = getDriver();

        createRecord(driver);

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(), 'Default')]"));
        tab.click();

        WebElement recordMenu = driver.findElement(By.xpath("//button[contains(@data-toggle, 'dropdown')] "));
        recordMenu.click();

        WebElement editFunction = driver.findElement(By.xpath("//a[text() = 'edit']"));
        ProjectUtils.click(driver, editFunction);

        assertAndReplace(driver, BY_STRING, defaultValues.fieldString, newValues.fieldString);
        assertAndReplace(driver, BY_TEXT, defaultValues.fieldText, newValues.fieldText);
        assertAndReplace(driver, BY_INT, defaultValues.fieldInt, newValues.fieldInt);
        assertAndReplace(driver, BY_DECIMAL, defaultValues.fieldDecimal, newValues.fieldDecimal);
        assertAndReplace(driver, BY_DATE, defaultValues.fieldDate, newValues.fieldDate);
        assertAndReplace(driver, BY_DATETIME, defaultValues.fieldDateTime, newValues.fieldDateTime);

        WebElement user = driver.findElement(By.xpath("//div[@class='filter-option-inner']/div[.='User 1 Demo']"));
        Assert.assertEquals(user.getText(), defaultValues.fieldUser.toUpperCase());

        WebElement fieldUser = driver.findElement(By.xpath("//div[@id='_field_container-user']/div/button"));
        ProjectUtils.click(driver, fieldUser);

        WebElement scrollDownMenuField = driver.findElement(By.xpath("//span[text() = '" + newValues.fieldUser + "']"));
        ProjectUtils.click(driver, scrollDownMenuField);

        WebElement saveButton = driver.findElement(By.xpath("//button[text() = 'Save']"));
        ProjectUtils.click(driver, saveButton);

        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr"));
        Assert.assertEquals(rows.size(), 1);

        List<WebElement> columns = rows.get(0).findElements(By.tagName("td"));
        //Assert.assertEquals(columns.size(), VALUES.length);

        Assert.assertEquals(columns.get(1).getText(), newValues.fieldString);
        Assert.assertEquals(columns.get(2).getText(), newValues.fieldText);
        Assert.assertEquals(columns.get(3).getText(), newValues.fieldInt);
        Assert.assertEquals(columns.get(4).getText(), newValues.fieldDecimal);
        Assert.assertEquals(columns.get(5).getText(), newValues.fieldDate);
        Assert.assertEquals(columns.get(6).getText(), newValues.fieldDateTime);
        Assert.assertEquals(columns.get(9).getText().toLowerCase(), newValues.fieldUser);

    }

    private void navigateToEntityDefaultPage(WebDriver driver) {
        driver.findElement(By.xpath("//i/following-sibling::p[contains (text(), 'Default')]")).click();
    }


    @Ignore
    @Test
    public void checkDefaultValueAndUpdateThem() throws InterruptedException {

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

        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//i/following-sibling::p[contains (text(), 'Default')]")).click();

        WebElement createFolder = driver.findElement(By.xpath("//i[.='create_new_folder']/ancestor::a"));
        ProjectUtils.click(driver, createFolder);

        WebElement saveBtn = driver.findElement(By.xpath("//button[.='Save']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", saveBtn);
        Assert.assertTrue(saveBtn.isDisplayed());

        assertAndReplace(driver, BY_STRING, defaultValues.fieldString, newValues.fieldString);
        assertAndReplace(driver, BY_TEXT, defaultValues.fieldText, newValues.fieldText);
        assertAndReplace(driver, BY_INT, defaultValues.fieldInt, newValues.fieldInt);
        assertAndReplace(driver, BY_DECIMAL, defaultValues.fieldDecimal, newValues.fieldDecimal);
        assertAndReplace(driver, BY_DATE, defaultValues.fieldDate, newValues.fieldDate);
        assertAndReplace(driver, BY_DATETIME, defaultValues.fieldDateTime, newValues.fieldDateTime);

        WebElement user = driver.findElement(By.xpath("//div[@class='filter-option-inner']/div[.='User 1 Demo']"));
        Assert.assertEquals(user.getText(), defaultValues.fieldUser.toUpperCase());

        WebElement fieldUser = driver.findElement(By.xpath("//div[@id='_field_container-user']/div/button"));
        ProjectUtils.click(driver, fieldUser);

        WebElement scrollDownMenuField = driver.findElement(By.xpath("//span[text() = 'user100@tester.com']"));
        ProjectUtils.click(driver, scrollDownMenuField);



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
        searchInput.sendKeys(newValues.fieldString);

        WebElement ourRecord = driver.findElement(By.xpath("//div[contains (text(), '" + newValues.fieldString + "')]/ancestor::a"));
        ourRecord.click();

        List<WebElement> listOfNewValues = driver.findElements(By.xpath("//span[@class='pa-view-field']"));
        Assert.assertEquals(listOfNewValues.get(0).getText(), newValues.fieldString);
        Assert.assertEquals(listOfNewValues.get(1).getText(), newValues.fieldText);
        Assert.assertEquals(listOfNewValues.get(2).getText(), newValues.fieldInt);
        Assert.assertEquals(listOfNewValues.get(3).getText(), newValues.fieldDecimal);
        Assert.assertEquals(listOfNewValues.get(4).getText(), newValues.fieldDate);
        Assert.assertEquals(listOfNewValues.get(5).getText(), newValues.fieldDateTime);
        fieldUser = driver.findElement(By.xpath("//div[@class='form-group']/p"));
        Assert.assertEquals(fieldUser.getText(), newValues.fieldUser);

        List<WebElement> embedDArrayOfNewValues = driver.findElements(By.xpath("//table/tbody/tr/td"));
        Assert.assertEquals(embedDArrayOfNewValues.get(1).getText(), stringEmbedLineNewText);
        Assert.assertEquals(embedDArrayOfNewValues.get(2).getText(), textEmbedLineNewText);
        Assert.assertEquals(embedDArrayOfNewValues.get(3).getText(), String.valueOf(intEmbedLineNew));
        Assert.assertEquals(embedDArrayOfNewValues.get(4).getText(), String.format("%.2f", decimalEmbedLineNew));
        Assert.assertEquals(embedDArrayOfNewValues.get(5).getText(), dateEmbedLineNew);
        Assert.assertEquals(embedDArrayOfNewValues.get(6).getText(), dateTimeEmbedLineNew);
        Assert.assertEquals(embedDArrayOfNewValues.get(9).getText(), userEmbedDSelected);

        driver.navigate().back();
        driver.navigate().back();

        WebElement lastRecordDeleteBtn;

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

    private void entityCreation() {

        WebDriver driver = getDriver();

        accessToEntityDefaultScreen(driver);
        createDefaultEntity(driver);
    }



    private boolean isInt(String str){

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


    private boolean isValidDateFormat(String dateStr) {

        DateFormat sdf = new SimpleDateFormat(dateStr);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private boolean isTitleFound(String title) {

        List<WebElement> titlesWe = getDriver().findElements(By.xpath("//tr/td[2]"));
        for (WebElement we : titlesWe) {
            if (we.getText().equals(title)) {
                return true;
            }
        }
        return false;
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

    @Ignore
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

    @Ignore
    @Test(dependsOnMethods ="validateEntityDefaultValuesCreation")
    public void validateEntityDefaultValuesRecord() {

        WebDriver driver = getDriver();

        accessToEntityDefaultScreen(driver);
        goToLastPage(driver);

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


    /** create and test new default record and save Title value in this.title */
    private void createRecord(WebDriver driver) {

        driver.findElement(By.xpath("//a[@href='#menu-list-parent']")).click();
        driver.findElement(By.xpath("//i/following-sibling::p[contains (text(), 'Default')]")).click();
        WebElement createFolder = driver.findElement(By.xpath("//i[.='create_new_folder']/ancestor::a"));
        ProjectUtils.click(driver,createFolder);

        WebElement stringLineDefaultData = driver.findElement(By.xpath("//input[@id='string']"));
        Assert.assertEquals(stringLineDefaultData.getAttribute("value"), defaultValues.fieldString);

        WebElement textLineDefaultData = driver.findElement(By.xpath("//textarea[@id='text']"));
        Assert.assertEquals(textLineDefaultData.getText(),(defaultValues.fieldText));

        WebElement intLineDefaultData = driver.findElement(By.xpath("//input[@id='int']"));
        Assert.assertEquals(intLineDefaultData.getAttribute("value"),defaultValues.fieldInt);

        WebElement decimalLineDefaultData = driver.findElement(By.xpath("//input[@id='decimal']"));
        Assert.assertEquals(decimalLineDefaultData.getAttribute("value"),defaultValues.fieldDecimal);

        WebElement dateLineDefaultData = driver.findElement(By.xpath("//input[@id='date']"));
        Assert.assertEquals(dateLineDefaultData.getAttribute("value"),(defaultValues.fieldDate));

        WebElement dateTimeLineDefaultData = driver.findElement(By.xpath("//input[@id='datetime']"));
        Assert.assertEquals(dateTimeLineDefaultData.getAttribute("value"),(defaultValues.fieldDateTime));

        WebElement user = driver.findElement(By.xpath("//div[@class='filter-option-inner']/div[.='User 1 Demo']"));
        Assert.assertEquals(user.getText(),(defaultValues.fieldUser.toUpperCase()));

        //save new record
        WebElement saveBtn = driver.findElement(By.xpath("//button[.='Save']"));
        ProjectUtils.click(driver, saveBtn);
    }
}