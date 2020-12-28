import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;



public class EntityReferenceValuesTest extends BaseTest {
    final String LABEL="Automation label - do not delete";
    final String FILTER_1="Filter 1";
    final String FILTER_2="Filter 2";

    void clickReferenceValue(WebDriver driver){
        WebElement referenceValuesMenuBtn= driver.findElement(By.xpath("//i/following-sibling::p[contains (text(), 'Reference values')]"));
        referenceValuesMenuBtn.click();
    }
    void createNewReferenceValueBtn(WebDriver driver){
        WebElement createNewReferenceValueBtn = driver.findElement(By.xpath("//div[@class='card-icon']"));
        createNewReferenceValueBtn.click();
    }
    void fillUpValues(WebDriver driver,String label,String filter_1,String filter_2){
        WebElement labelField = driver.findElement(By.xpath("//input[@data-field_name='label']"));
        WebElement filter1 = driver.findElement(By.xpath("//input[@data-field_name='filter_1']"));
        WebElement filter2 = driver.findElement(By.xpath("//input[@data-field_name='filter_2']"));
        labelField.sendKeys(label);
        filter1.sendKeys(filter_1);
        filter2.sendKeys(filter_2);
    }
    void clickSaveButton(WebDriver driver){
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver,saveButton);
    }
    void clickSaveDraftButton(WebDriver driver){
        WebElement saveDraftButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-draft-btn']"));
        ProjectUtils.click(driver,saveDraftButton);
    }
    int paginationFindRicent(WebDriver driver){
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
        return id;
    }
    @Test
    public void newRecordTest(){

        WebDriver driver = getDriver();

        clickReferenceValue(driver);
        createNewReferenceValueBtn(driver);
        fillUpValues(driver,LABEL,FILTER_1,FILTER_2);
        clickSaveButton(driver);
        paginationFindRicent(driver);

        //validation
        WebElement labelInList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", paginationFindRicent(driver), LABEL)));
        WebElement filter1_InList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", paginationFindRicent(driver), FILTER_1)));
        WebElement filter2_InList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", paginationFindRicent(driver), FILTER_2)));
    }

    @Test
    public void newRecordEmptyFieldsTest(){
        WebDriver driver = getDriver();

        clickReferenceValue(driver);
        createNewReferenceValueBtn(driver);
        clickSaveButton(driver);
        paginationFindRicent(driver);

        //validation
        WebElement labelInList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", paginationFindRicent(driver), "")));
        WebElement filter1_InList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", paginationFindRicent(driver), "")));
        WebElement filter2_InList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", paginationFindRicent(driver), "")));
    }
    @Test
    public void saveAsDraftTest(){

        WebDriver driver = getDriver();

        clickReferenceValue(driver);
        createNewReferenceValueBtn(driver);
        fillUpValues(driver,LABEL,FILTER_1,FILTER_2);
        clickSaveDraftButton(driver);
        paginationFindRicent(driver);

        //validation
        Assert.assertTrue(driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//i[contains(@class, 'fa fa-pencil')]",paginationFindRicent(driver)))).isDisplayed());
        WebElement labelInList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", paginationFindRicent(driver), LABEL)));
        WebElement filter1_InList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", paginationFindRicent(driver), FILTER_1)));
        WebElement filter2_InList = driver.findElement(By.xpath(String.format("//tr[@data-index='%s']//div[contains(text(), '%s')]", paginationFindRicent(driver), FILTER_2)));
    }

}
