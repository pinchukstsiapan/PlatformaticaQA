import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;

import java.util.List;

public class EntityImportTest extends BaseTest {

    String url = "https://ref.eteam.work";
    boolean flag = false;

    @Test
    public void deleteRecordFromEntityImport() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get(url);
        ProjectUtils.loginProcedure(driver);

        WebElement importValuesTab = driver.findElement(By.xpath("//p[contains(text(),'Import values')]"));
        importValuesTab.click();

        String randomString = createRecordInEntityImport(driver);

        List<WebElement> paginationNums = driver.findElements(By.xpath("//ul[@class='pagination'] //li //a[contains(@aria-label, 'to page')]"));
        int countPages = 0;

        do {
            List<WebElement> stringsOfImportValues = driver.findElements(By.xpath("//table[@id='pa-all-entities-table'] // tbody //tr //td[2] //a //div"));
            List<WebElement> actionsDropDownImportValues = driver.findElements(By.xpath("//table[@id='pa-all-entities-table'] // tbody //tr //td[10] //div //button"));
            for(int i = 0; i < stringsOfImportValues.size(); i++){
                    if(stringsOfImportValues.get(i).getText().equalsIgnoreCase(randomString)){
                        actionsDropDownImportValues.get(i).click();
                        driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right show'] //li[3]")).click();
                        flag = true;
                        break;
                    }else if(i == stringsOfImportValues.size() - 1){
                        if(countPages != paginationNums.size()){
                            driver.findElement(By.xpath("//li[@class='page-item page-next']")).click();
                            countPages++;
                        }else{
                            Assert.fail("No more pages to search for deleted Import values.");
                            break;
                        }
                    }
                }
        }while(flag == false);

        WebElement recycleBin = driver.findElement(By.xpath("//i[contains(text(),'delete_outline')]"));
        ProjectUtils.click(driver, recycleBin);

        List<WebElement> deletedImportValues = driver.findElements(By.xpath("//div[@class='card-body'] //table //tbody //tr //td //a //span"));
        Assert.assertTrue(deletedImportValues.get(0).getText().contains(randomString));

        cleanRecycleBin(driver, randomString);

        WebElement userButton = driver.findElement((By.xpath("//a[@id='navbarDropdownProfile']")));
        userButton.click();

        Thread.sleep(1000);

        WebElement logoutButton = driver.findElement((By.xpath("//a[contains(text(),'Log out')]")));
        logoutButton.click();
    }

    public String createRecordInEntityImport(WebDriver driver) throws InterruptedException {

        WebElement createImportValuesIcon = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createImportValuesIcon.click();

        Thread.sleep(2000);

        String randomString = RandomStringUtils.randomAlphanumeric(10);
        WebElement stringInImportValueField = driver.findElement(By.xpath("//input[@id='string']"));
        stringInImportValueField.sendKeys(randomString);

        Thread.sleep(1000);

        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);

        return randomString;
    }

    public void cleanRecycleBin(WebDriver driver, String randomString){

        boolean flag = false;
        do{
            try{
                WebElement firstDanRecord = driver.findElement(By.xpath("//div[@class='card-body'] //table //tbody //tr //td[1] //a //*[contains(text(), '" + randomString + "')][1] //following::td[4] //a"));
                    if(firstDanRecord.isDisplayed()){
                        firstDanRecord.click();
                    }
            }catch(Exception e){
                flag = true;
            }
        }while(flag == false);
    }
}