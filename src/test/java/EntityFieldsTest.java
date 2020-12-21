import java.util.List;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

@Run(run = RunType.Multiple)
public class EntityFieldsTest extends BaseTest {

    @Test
    public void newRecord() {

        WebDriver driver = getDriver();

        WebElement tab = driver.findElement(By.xpath("//li[@id='pa-menu-item-45']/a"));
        tab.click();
        WebElement newRecord = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        newRecord.click();

        final String title = UUID.randomUUID().toString();
        final String comment = "simple text";
        final int number = 10;

        WebElement titleElement = driver.findElement(By.xpath("//input[@name='entity_form_data[title]']"));
        titleElement.sendKeys(title);
        WebElement commentElement = driver.findElement(By.xpath("//textarea[@name='entity_form_data[comments]']"));
        commentElement.sendKeys(comment);
        WebElement numberElement = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        numberElement.sendKeys(String.valueOf(number));

        WebElement submit = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, submit);

        // validation of record
        List<WebElement> recordRowsWe = driver.findElements(By.cssSelector("tbody > tr"));
        if (recordRowsWe.size() == 0) {
            Assert.fail("No Fields records found after creating one record");
        }

        boolean titleFound = false;
        int rowsPerPage;
        int numOfAllRecords = Integer.parseInt(getWait(1).until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//span[@class='pagination-info']")))
                .getText().split(" ")[5]);
        WebElement rowsPerPageWe = driver.findElement(By.cssSelector("span.page-size"));
        if (rowsPerPageWe.isDisplayed()) {
            rowsPerPage = Integer.parseInt(rowsPerPageWe.getText());
        } else {
            rowsPerPage = 26;
        }
        if (numOfAllRecords > rowsPerPage) {
            boolean firstRound = true;
            while (firstRound) {
                if (isTitleFound(title)) {
                    titleFound = true;
                    break;
                    }
                List<WebElement> paginationButtons = driver.findElements(By.cssSelector("a.page-link"));
                WebElement paginationNext = paginationButtons.get(paginationButtons.size() -1);
                paginationNext.click();
                WebElement paginationFirstIndexWe = driver.findElements(By.cssSelector("a.page-link")).get(1);
                String paginationFirstIndex = paginationFirstIndexWe.getText();
                boolean paginationFirstIndexActive =
                        paginationFirstIndexWe.getCssValue("color").equals("rgba(255, 255, 255, 1)");
                if (paginationFirstIndex.equals("1") && paginationFirstIndexActive) {
                    firstRound = false;
                }
            }
        } else {
            titleFound = isTitleFound(title);
        }

        Assert.assertTrue(titleFound, "Created record not found");

        String recordTitleXpath = String.format("//div[contains(text(), '%s')]", title);
        By newRecordComment = By.xpath(String.format("%s/../../../td[3]/a/div", recordTitleXpath));
        By newRecordInt = By.xpath(String.format("%s/../../../td[4]/a/div", recordTitleXpath));
        WebElement createdRecordComment = driver.findElement(newRecordComment);
        WebElement createdRecordInt = driver.findElement(newRecordInt);

        Assert.assertEquals(createdRecordComment.getText(), comment, "Created record comment text issue");
        Assert.assertEquals(createdRecordInt.getText(), Integer.toString(number),
                "Created record int value issue");

        // cleanup, delete created record
        deleteRecordByTitle(title);
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

    private void deleteRecordByTitle(String title) {

        WebDriver driver = getDriver();
        String recordTitleXpath = String.format("//div[contains(text(), '%s')]", title);

        By recordMenuButton = By.xpath(String.format("%s/../../..//button", recordTitleXpath));
        By deleteButton = By.xpath(String.format("%s/../../..//a[contains(@href, 'delete')]", recordTitleXpath));

        driver.findElement(recordMenuButton).click();
        ProjectUtils.click(getDriver(),
                getWait(2).until(ExpectedConditions.elementToBeClickable(deleteButton)));
    }
    private WebDriverWait getWait(int timeoutSecond) {
        return new WebDriverWait(getDriver(), timeoutSecond);
    }

    @Ignore
    @Test
    public void editForm() throws InterruptedException {

        final String newTitle = "A-12/12/2020 has been edited";
        final String newComment = "Entry is created to test Edit functionality. Please do not delete.(Edited)";
        final int newNumber = 102;
        final double newDecimal = 101.25;

        WebDriver driver = getDriver();

        WebElement fieldsMenu = driver.findElement(By.xpath("//li[@id='pa-menu-item-45']/a"));
        fieldsMenu.click();
        WebElement hamburgerMenu = driver.findElement(By.xpath("//tr[@data-row_id='473']/td[11]//button"));
        hamburgerMenu.click();
        Thread.sleep(500);
        WebElement editButton =
                driver.findElement(By.xpath("//a[@href='index.php?action=action_edit&entity_id=5&row_id=473']"));
        editButton.click();

        Assert.assertEquals(driver.getCurrentUrl(),
                "https://ref.eteam.work/index.php?action=action_edit&entity_id=5&row_id=473");

        WebElement editTitle = driver.findElement(By.xpath("//input[@name='entity_form_data[title]']"));
        editTitle.clear();
        editTitle.sendKeys(newTitle);
        WebElement editComments = driver.findElement(By.xpath("//textarea[@name='entity_form_data[comments]']"));
        editComments.clear();
        editComments.sendKeys(newComment);
        WebElement editInt = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        editInt.clear();
        editInt.sendKeys(String.valueOf(newNumber));
        WebElement editDecimal = driver.findElement(By.xpath("//input[@name='entity_form_data[decimal]']"));
        editDecimal.clear();
        editDecimal.sendKeys(String.valueOf(newDecimal));

        WebElement tableString = driver.findElement(By.xpath("//textarea[@name='t-9-r-1-data[string]']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", tableString);
        tableString.clear();
        tableString.sendKeys(newTitle);
        WebElement tableText = driver.findElement(By.xpath("//textarea[@name='t-9-r-1-data[text]']"));
        tableText.clear();
        tableText.sendKeys(newComment);
        WebElement tableInt = driver.findElement(By.xpath("//textarea[@name='t-9-r-1-data[int]']"));
        tableInt.clear();
        tableInt.sendKeys(String.valueOf(newNumber));
        WebElement tableDecimal = driver.findElement(By.xpath("//textarea[@name='t-9-r-1-data[decimal]']"));
        tableDecimal.clear();
        tableDecimal.sendKeys(String.valueOf(newDecimal));

        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);

        Assert.assertEquals(driver.getCurrentUrl(),
                "https://ref.eteam.work/index.php?action=action_list&entity_id=5&filter");
        Assert.assertEquals(driver.findElement(By.xpath("//tr[@data-row_id='473']/td[2]/a/div")).getText(),newTitle);
        Assert.assertEquals(driver.findElement(By.xpath("//tr[@data-row_id='473']/td[3]/a/div")).getText(),newComment);
        Assert.assertEquals(driver.findElement(By.xpath("//tr[@data-row_id='473']/td[4]/a/div"))
                .getText(),String.valueOf(newNumber));
        Assert.assertEquals(driver.findElement(By.xpath("//tr[@data-row_id='473']/td[5]/a/div")).
                getText(),String.valueOf(newDecimal));
    }

    @Ignore
    @Test
    public void saveDraft() throws InterruptedException {

        final String newTitle = "A-12/12/2020 has been edited";
        final String newComment = "Entry is created to test Edit functionality. Please do not delete.(Edited)";
        final int newNumber = 102;
        final double newDecimal = 101.25;

        WebDriver driver = getDriver();

        WebElement fieldsMenu = driver.findElement(By.xpath("//li[@id='pa-menu-item-45']/a"));
        fieldsMenu.click();
        WebElement hamburgerMenu = driver.findElement(By.xpath("//tr[@data-row_id='473']/td[11]//button"));
        hamburgerMenu.click();
        Thread.sleep(500);
        WebElement editButton =
                driver.findElement(By.xpath("//a[@href='index.php?action=action_edit&entity_id=5&row_id=473']"));
        editButton.click();

        Assert.assertEquals(driver.getCurrentUrl(),
                "https://ref.eteam.work/index.php?action=action_edit&entity_id=5&row_id=473");

        WebElement editTitle = driver.findElement(By.xpath("//input[@name='entity_form_data[title]']"));
        editTitle.clear();
        editTitle.sendKeys(newTitle);
        WebElement editComments = driver.findElement(By.xpath("//textarea[@name='entity_form_data[comments]']"));
        editComments.clear();
        editComments.sendKeys(newComment);
        WebElement editInt = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        editInt.clear();
        editInt.sendKeys(String.valueOf(newNumber));
        WebElement editDecimal = driver.findElement(By.xpath("//input[@name='entity_form_data[decimal]']"));
        editDecimal.clear();
        editDecimal.sendKeys(String.valueOf(newDecimal));

        WebElement saveDraft = driver.findElement(By.xpath("//button[@id='pa-entity-form-draft-btn']"));
        ProjectUtils.click(driver, saveDraft);
        WebElement pencil = driver.findElement(By.xpath("//tr[@data-row_id='473']/td/i[@class='fa fa-pencil']"));

        Assert.assertTrue(pencil.isDisplayed());
    }

    @Ignore
    @Test
    public void invalidEditEntry() throws InterruptedException {

        final String invalidEntry = "test";

        WebDriver driver = getDriver();

        WebElement fieldsMenu = driver.findElement(By.xpath("//li[@id='pa-menu-item-45']/a"));
        fieldsMenu.click();
        WebElement hamburgerMenu = driver.findElement(By.xpath("//tr[@data-row_id='473']/td[11]//button"));
        hamburgerMenu.click();
        Thread.sleep(500);
        WebElement editButton =
                driver.findElement(By.xpath("//a[@href='index.php?action=action_edit&entity_id=5&row_id=473']"));
        editButton.click();

        Assert.assertEquals(driver.getCurrentUrl(),
                "https://ref.eteam.work/index.php?action=action_edit&entity_id=5&row_id=473");

        WebElement editInt = driver.findElement(By.xpath("//input[@name='entity_form_data[int]']"));
        editInt.clear();
        editInt.sendKeys(invalidEntry);
        WebElement editDecimal = driver.findElement(By.xpath("//input[@name='entity_form_data[decimal]']"));
        editDecimal.clear();
        editDecimal.sendKeys(invalidEntry);

        WebElement tableInt = driver.findElement(By.xpath("//textarea[@name='t-9-r-1-data[int]']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", tableInt);
        tableInt.clear();
        tableInt.sendKeys(invalidEntry);
        WebElement tableDecimal = driver.findElement(By.xpath("//textarea[@name='t-9-r-1-data[decimal]']"));
        tableDecimal.clear();
        tableDecimal.sendKeys(invalidEntry);

        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);

        WebElement error = driver.findElement(By.xpath("//div[text()='Error saving entity']"));

        Assert.assertTrue(error.isDisplayed());
    }

    @Test
    public void fieldInputNewRecordTest() {

        final String title = "KyKy";
        final String comments = "Good";
        final int number = 555;
        final double decimal = 555.33;

        WebDriver driver = getDriver();

        WebElement sideBarField = driver.findElement(By.xpath("//body/div[1]/div[1]/div[2]/ul[1]/li[4]/a[1]/p[1]"));
        sideBarField.click();

        WebElement buttonField = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        buttonField.click();

        WebElement titleInput = driver.findElement(By.xpath("//input[@id='title']"));
        titleInput.clear();
        titleInput.sendKeys(title);

        WebElement commentsInput = driver.findElement(By.xpath("//textarea[@id='comments']"));
        commentsInput.clear();
        commentsInput.sendKeys(comments);

        WebElement numberInput = driver.findElement(By.xpath("//input[@id='int']"));
        numberInput.clear();
        numberInput.sendKeys(String.valueOf(number));

        WebElement decimalInput = driver.findElement(By.xpath("//input[@id='decimal']"));
        decimalInput.clear();
        decimalInput.sendKeys(String.valueOf(decimal));

        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);

        Assert.assertEquals(driver.getCurrentUrl(),"https://ref.eteam.work/index.php?action=action_list&entity_id=5&filter");
    }

    @Test(dependsOnMethods = "fieldInputNewRecordTest")
    public void fieldsEditTest() throws InterruptedException {

        final String newTitle = "Ky";
        final String newComments = "Goooood";
        final int newInt = 222;
        final double newDecimal = 222.33;

        WebDriver driver = getDriver();

        WebElement sideBarField = driver.findElement(By.xpath("//body/div[1]/div[1]/div[2]/ul[1]/li[4]/a[1]/p[1]"));
        sideBarField.click();

        WebElement createdRecordSandwich = getWait(5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//table//button[@aria-expanded='false']")));
        ProjectUtils.click(driver, createdRecordSandwich);
        Thread.sleep(500);

        WebElement editField = getWait(5)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@aria-expanded='true']/..//a[text() = 'edit']")));
        editField.click();

        WebElement titleField = getWait(5)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='title']")));
        titleField.clear();
        titleField.sendKeys(newTitle);

        WebElement commentsField = driver.findElement(By.xpath("//textarea[@id='comments']"));
        commentsField.clear();
        commentsField.sendKeys(newComments);

        WebElement intField = driver.findElement(By.xpath("//input[@id='int']"));
        intField .clear();
        intField .sendKeys(String.valueOf(newInt));

        WebElement decimalField = driver.findElement(By.xpath("//input[@id='decimal']"));
        decimalField.clear();
        decimalField.sendKeys(String.valueOf(newDecimal));

        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);

        Assert.assertEquals(driver.getCurrentUrl(),
                "https://ref.eteam.work/index.php?action=action_list&entity_id=5&filter");
    }
}
