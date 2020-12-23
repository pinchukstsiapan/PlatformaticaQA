import java.util.List;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Run;
import runner.type.RunType;

@Run(run = RunType.Multiple)
public class EntityFieldsTest extends BaseTest {

    By mainMenuFieldsButton = By.xpath("//p[text()=' Fields ']");
    By titleInputField = By.xpath("//input[@id='title']");
    By commentInputField = By.xpath("//textarea[@id='comments']");
    By intInputField = By.xpath("//input[@id='int']");
    By decimalInputField = By.xpath("//input[@id='decimal']");
    By saveButton = By.cssSelector("button[id*='save']");
    By saveDraftButton = By.cssSelector("button[id*='draft']");
    By recordSandwichMenuButton = By.xpath("//tr//button");
    By sandwichMenuEditButton = By.xpath("//a[contains(@href, 'edit')]");
    By draftPencil = By.xpath("//i[@class='fa fa-pencil']");
    By recordTitle = By.xpath("//tr/td[2]//div");
    By recordComment = By.xpath("//tr/td[3]//div");
    By recordInt = By.xpath("//tr/td[4]//div");
    By recordDecimal = By.xpath("//tr/td[5]//div");

    @Test
    public void newRecord() {

        final String title = UUID.randomUUID().toString();
        final String comment = "simple text";
        final int number = 10;
        final double decimal = 10.01;
        WebDriver driver = getDriver();

        driver.findElement(mainMenuFieldsButton).click();

        WebElement newRecord = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        newRecord.click();

        WebElement titleElement = driver.findElement(titleInputField);
        titleElement.sendKeys(title);
        WebElement commentElement = driver.findElement(commentInputField);
        commentElement.sendKeys(comment);
        WebElement numberElement = driver.findElement(intInputField);
        numberElement.sendKeys(String.valueOf(number));
        WebElement decimalElement = driver.findElement(decimalInputField);
        decimalElement.sendKeys(String.valueOf(decimal));

        ProjectUtils.click(driver, driver.findElement(saveButton));

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
        By newRecordDecimal = By.xpath(String.format("%s/../../../td[5]/a/div", recordTitleXpath));
        WebElement createdRecordComment = driver.findElement(newRecordComment);
        WebElement createdRecordInt = driver.findElement(newRecordInt);
        WebElement createdRecordDecimal = driver.findElement(newRecordDecimal);

        Assert.assertEquals(createdRecordComment.getText(), comment, "Created record comment text issue");
        Assert.assertEquals(createdRecordInt.getText(), Integer.toString(number),
                "Created record int value issue");
        Assert.assertEquals(createdRecordDecimal.getText(), Double.toString(decimal),
                "Created record decimal value issue");
    }

    @Test(dependsOnMethods = "newRecord")
    public void fieldsEdit() {

        final String newTitle = String.format("%s -- edited", UUID.randomUUID().toString());
        final String newComment = "New comment for edit test";
        final int newNumber = 257;
        final double newDecimal = 128.77;
        WebDriver driver = getDriver();

        driver.findElement(mainMenuFieldsButton).click();
        clickSandwichEdit();

        WebElement titleField = getWait(6)
                .until(ExpectedConditions.presenceOfElementLocated(titleInputField));
        reSendKeys(titleField, newTitle);
        WebElement commentsField = driver.findElement(commentInputField);
        reSendKeys(commentsField, newComment);
        WebElement intField = driver.findElement(intInputField);
        reSendKeys(intField, String.valueOf(newNumber));
        WebElement decimalField = driver.findElement(decimalInputField);
        reSendKeys(decimalField, String.valueOf(newDecimal));

        ProjectUtils.click(driver, driver.findElement(saveButton));

        WebElement title = getWait(5).until(ExpectedConditions.visibilityOfElementLocated(recordTitle));
        Assert.assertEquals(title.getText(), newTitle);
        Assert.assertEquals(driver.findElement(recordComment).getText(),newComment);
        Assert.assertEquals(driver.findElement(recordInt).getText(),String.valueOf(newNumber));
        Assert.assertEquals(driver.findElement(recordDecimal).getText(),String.valueOf(newDecimal));
    }

    @Test(dependsOnMethods = {"newRecord","fieldsEdit"})
    public void saveDraft() {

        final String newTitle = String.format("%s -- draft", UUID.randomUUID().toString());
        final String newComment = "New comment for draft test";
        final int newNumber = 513;
        final double newDecimal = 1024.01;
        WebDriver driver = getDriver();

        driver.findElement(mainMenuFieldsButton).click();
        clickSandwichEdit();

        WebElement editTitle = driver.findElement(titleInputField);
        reSendKeys(editTitle, newTitle);
        WebElement editComments = driver.findElement(commentInputField);
        reSendKeys(editComments, newComment);
        WebElement editInt = driver.findElement(intInputField);
        reSendKeys(editInt, String.valueOf(newNumber));
        WebElement editDecimal = driver.findElement(decimalInputField);
        reSendKeys(editDecimal, String.valueOf(newDecimal));

        ProjectUtils.click(driver, driver.findElement(saveDraftButton));

        WebElement pencil = getWait(5).until(ExpectedConditions.visibilityOfElementLocated(draftPencil));
        Assert.assertTrue(pencil.isDisplayed());
        Assert.assertEquals(driver.findElement(recordTitle).getText(), newTitle);
        Assert.assertEquals(driver.findElement(recordComment).getText(),newComment);
        Assert.assertEquals(driver.findElement(recordInt).getText(),String.valueOf(newNumber));
        Assert.assertEquals(driver.findElement(recordDecimal).getText(),String.valueOf(newDecimal));
    }

    @Test(dependsOnMethods = {"newRecord","fieldsEdit","saveDraft"})
    public void invalidEditEntry() {
        /*
        This test will fail false positive after invalid data handling bug fixed
        https://trello.com/c/tR13iOX9/2-error-message-when-adding-new-field-with-valid-input-data-and-attachments
         */

        final String invalidEntry = "text";
        WebDriver driver = getDriver();

        driver.findElement(mainMenuFieldsButton).click();
        clickSandwichEdit();

        WebElement editInt = driver.findElement(intInputField);
        reSendKeys(editInt, invalidEntry);
        WebElement editDecimal = driver.findElement(decimalInputField);
        reSendKeys(editDecimal, invalidEntry);

        ProjectUtils.click(driver, driver.findElement(saveButton));

        WebElement error = driver.findElement(By.xpath("//div[text()='Error saving entity']"));
        Assert.assertTrue(error.isDisplayed());
    }

    private WebDriverWait getWait(int timeoutSecond) {
        return new WebDriverWait(getDriver(), timeoutSecond);
    }

    public void reSendKeys(WebElement element, String newText) {
        element.clear();
        element.sendKeys(newText);
    }

    private void clickSandwichEdit() {
        WebElement recordMenuButton = getWait(6)
                .until(ExpectedConditions.elementToBeClickable(recordSandwichMenuButton));
        recordMenuButton.click();
        WebElement menuEditButton = getWait(2)
                .until(ExpectedConditions.elementToBeClickable(sandwichMenuEditButton));
        ProjectUtils.click(getDriver(), menuEditButton);
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

}
