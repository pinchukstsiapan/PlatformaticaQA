
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class EntityBoardTest extends BaseTest {

    @Ignore
    @Test
    public void inputTest() throws IOException {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDataEuropean = formatter.format(calendar.getTime());
        final String text = UUID.randomUUID().toString();
        final int number = 12;
        final double decimal = 10.25;
        final String pending = "Pending";
        final String user1Demo = "User 1 Demo";

        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");

        WebElement tabBoard = driver.findElement(By.xpath("//p[contains(text(),'Board')]"));
        ProjectUtils.click(driver, tabBoard);

        WebElement viewList = driver.findElement(By.xpath("//a[contains(@href, '31')]/i[text()='list']"));
        viewList.click();

        WebElement createNew = driver.findElement(By.xpath("//div[@class = 'card-icon']"));
        createNew.click();

        WebElement stringDropdown = driver.findElement(By.xpath("//div[text()= 'Pending']/.."));
        stringDropdown.click();

        WebElement stringPending = driver.findElement(By.xpath("//div[text()= 'Pending']"));
        stringPending.click();

        WebElement textPlaceholder = driver.findElement(By.id("text"));
        textPlaceholder.sendKeys(text);

        WebElement intPlaceholder = driver.findElement(By.id("int"));
        intPlaceholder.sendKeys(String.valueOf(number));

        WebElement decimalPlaceholder = driver.findElement(By.id("decimal"));
        decimalPlaceholder.sendKeys(String.valueOf(decimal));

        WebElement date = driver.findElement(By.id("date"));
        date.click();

        WebElement dateTime = driver.findElement(By.id("datetime"));
        dateTime.click();

        WebElement userDropdown = driver.findElement(By.xpath("//div[text() = 'User 1 Demo']/.."));
        userDropdown.click();

        WebElement userUser1Demo = driver.findElement(By.xpath("//div[text() = 'User 1 Demo']"));
        userUser1Demo.click();

        WebElement saveBtn = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver, saveBtn);

        List<WebElement> recordRowsWe = driver.findElements(By.cssSelector("tbody > tr"));
        if (recordRowsWe.size() == 0) {
            Assert.fail("No Fields records found after creating one record");
            TakesScreenshot ts = (TakesScreenshot)driver;
            FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), new File("TCB-001creationFail.png"));
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
                if (isTitleFound(text)) {
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
            titleFound = isTitleFound(text);
        }

        if (titleFound = false) {
            System.out.println("Created record not found");
        }

        String recordTitleXpath = String.format("//div[contains(text(), '%s')]", text);
        By stringText = By.xpath(String.format("%s", recordTitleXpath));
        By newRecordStringPending = By.xpath(String.format("%s/../../../td[2]/a/div", recordTitleXpath));
        By newRecordInt = By.xpath(String.format("%s/../../../td[4]/a/div", recordTitleXpath));
        By newRecordDecimal = By.xpath(String.format("%s/../../../td[5]/a/div", recordTitleXpath));
        By newRecordDate = By.xpath(String.format("%s/../../../td[6]/a/div", recordTitleXpath));
        By newRecordDateTime = By.xpath(String.format("%s/../../../td[7]/a/div", recordTitleXpath));
        By newRecordUser1Demo = By.xpath(String.format("%s/../../../td[9]", recordTitleXpath));

        WebElement createdRecordText = driver.findElement(stringText);
        WebElement createdRecordStringPending = driver.findElement(newRecordStringPending);
        WebElement createdRecordInt = driver.findElement(newRecordInt);
        WebElement createdRecordDecimal = driver.findElement(newRecordDecimal);
        WebElement createdRecordDate = driver.findElement(newRecordDate);
        WebElement createdRecordDateTime = driver.findElement(newRecordDateTime);
        WebElement createdRecordUser1Demo = driver.findElement(newRecordUser1Demo);

        Assert.assertEquals(createdRecordText.getText(), text, "Created record text issue");
        Assert.assertEquals(createdRecordStringPending.getText(), pending, "Created record Pending issue");
        Assert.assertEquals(createdRecordInt.getText(), Integer.toString(number), "Created record number issue");
        Assert.assertEquals(createdRecordDecimal.getText(), Double.toString(decimal), "Created record decimal issue");
        Assert.assertEquals(createdRecordDate.getText(), currentDataEuropean, "Created date issue");
        Assert.assertEquals(createdRecordUser1Demo.getText(), user1Demo, "Created user issue");
        deleteRecordByTitle(text);
    }

    private boolean isTitleFound(String text) {

        List<WebElement> titlesWe = getDriver().findElements(By.xpath("//tr/td[3]"));
        for (WebElement we : titlesWe) {
            if (we.getText().equals(text)) {
                return true;
            }
        }
        return false;
    }

    private void deleteRecordByTitle(String text) {

        WebDriver driver = getDriver();
        String recordTitleXpath = String.format("//div[contains(text(), '%s')]", text);
        By recordMenuButton = By.xpath(String.format("%s/../../..//button", recordTitleXpath));
        WebElement deleteButton = driver.findElement(By.xpath(String.format("%s/../../..//a[contains(@href, 'delete')]", recordTitleXpath)));
        driver.findElement(recordMenuButton).click();
        ProjectUtils.click(driver, deleteButton);
    }

    private WebDriverWait getWait(int timeoutSecond) {
        return new WebDriverWait(getDriver(), timeoutSecond);
    }
}