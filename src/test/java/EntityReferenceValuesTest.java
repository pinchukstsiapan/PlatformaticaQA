import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

public class EntityReferenceValuesTest extends BaseTest {

    @Ignore
    @Test
    public void newRecord(){

        WebDriver driver = getDriver();
        ProjectUtils.loginProcedure(driver);

        final String Label="Automation label - do not delete";
        final String Filter_1="Filter 1";
        final String Filter_2="Filter 2";

        WebElement referenceValuesMenuBtn= driver.findElement(By.xpath("//i/following-sibling::p[contains (text(), 'Reference values')]"));
        referenceValuesMenuBtn.click();
        WebElement createNewReferenceValueBtn = driver.findElement(By.xpath("//div[@class='card-icon']"));
        createNewReferenceValueBtn.click();

        WebElement labelField = driver.findElement(By.xpath("//input[@data-field_name='label']"));
        WebElement filter1 = driver.findElement(By.xpath("//input[@data-field_name='filter_1']"));
        WebElement filter2 = driver.findElement(By.xpath("//input[@data-field_name='filter_2']"));
        labelField.sendKeys(Label);
        filter1.sendKeys(Filter_1);
        filter2.sendKeys(Filter_2);

        WebElement saveBtn = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn'] "));
        ProjectUtils.click(driver,saveBtn);

        //validation
        WebElement numOfPagesS = driver.findElement(By.xpath("//span[@class='pagination-info']"));
        String str = numOfPagesS.getText();
        String[] arrOfStr = str.split(" ");

        int numOfRows =  Integer.parseInt(arrOfStr[5]);
        int rowsPerPage =  Integer.parseInt(arrOfStr[3]);
        int pageNumber;

        if (numOfRows%rowsPerPage == 0) {
            pageNumber = numOfRows/rowsPerPage;
        } else {
            pageNumber = numOfRows/rowsPerPage + 1;
        }

        // id of the newly created record (the last record)
        int id = numOfRows-1;

        WebElement page = driver.findElement(By.xpath("//a[@class='page-link'][@aria-label='to page " + pageNumber + "']"));
        ProjectUtils.click(driver, page);

        WebElement labelInList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", id, Label)));
        WebElement filter1_InList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", id, Filter_1)));
        WebElement filter2_InList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", id, Filter_2)));
    }

}
