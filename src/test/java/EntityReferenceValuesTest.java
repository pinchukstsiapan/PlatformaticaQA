import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import java.util.List;
import java.util.UUID;


public class EntityReferenceValuesTest extends BaseTest {
    final String LABEL = UUID.randomUUID().toString();
    final String FILTER_1 = "Filter 1";
    final String FILTER_2 = "Filter 2";

    void clickReferenceValue(WebDriver driver) {
        WebElement referenceValuesMenuBtn = driver.findElement(By.xpath("//i/following-sibling::p[contains (text(), 'Reference values')]"));
        referenceValuesMenuBtn.click();
    }

    void createNewReferenceValueBtn(WebDriver driver) {
        WebElement createNewReferenceValueBtn = driver.findElement(By.xpath("//div[@class='card-icon']"));
        createNewReferenceValueBtn.click();
    }

    void fillUpValues(WebDriver driver, String label, String filter_1, String filter_2) {
        WebElement labelField = driver.findElement(By.xpath("//input[@data-field_name='label']"));
        WebElement filter1 = driver.findElement(By.xpath("//input[@data-field_name='filter_1']"));
        WebElement filter2 = driver.findElement(By.xpath("//input[@data-field_name='filter_2']"));
        labelField.sendKeys(label);
        filter1.sendKeys(filter_1);
        filter2.sendKeys(filter_2);
    }

    void clickSaveButton(WebDriver driver) {
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);
    }

    void clickSaveDraftButton(WebDriver driver) {
        WebElement saveDraftButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-draft-btn']"));
        ProjectUtils.click(driver, saveDraftButton);
    }

    @Test
    public void newRecordTest() {

        WebDriver driver = getDriver();
        String[] expectedData = {null, LABEL, FILTER_1, FILTER_2, null};
        clickReferenceValue(driver);
        createNewReferenceValueBtn(driver);
        fillUpValues(driver, LABEL, FILTER_1, FILTER_2);
        clickSaveButton(driver);

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(rows.size(), 1);
        List<WebElement> columns = rows.get(0).findElements(By.tagName("td"));
        Assert.assertEquals(columns.size(), expectedData.length);
        for (int i = 0; i < columns.size(); i++) {
            if (expectedData[i] != null) {
                Assert.assertEquals(columns.get(i).getText(), expectedData[i]);
            }
        }
    }

    @Test
    public void newRecordEmptyFieldsTest() {

        WebDriver driver = getDriver();
        String[] expectedData = {null, "", "", "", null};
        clickReferenceValue(driver);
        createNewReferenceValueBtn(driver);
        clickSaveButton(driver);


        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(rows.size(), 1);
        List<WebElement> columns = rows.get(0).findElements(By.tagName("td"));
        Assert.assertEquals(columns.size(), expectedData.length);
        for (int i = 0; i < columns.size(); i++) {
            if (expectedData[i] != null) {
                Assert.assertEquals(columns.get(i).getText(), expectedData[i]);
            }
        }
    }

    @Test
    public void saveAsDraftTest() {

        WebDriver driver = getDriver();
        clickReferenceValue(driver);
        createNewReferenceValueBtn(driver);
        fillUpValues(driver, LABEL, FILTER_1, FILTER_2);
        clickSaveDraftButton(driver);

        //validation
        Assert.assertTrue(driver.findElement(By.xpath("//tr[@data-index='0']//i[contains(@class, 'fa fa-pencil')]")).isDisplayed());
    }

}
