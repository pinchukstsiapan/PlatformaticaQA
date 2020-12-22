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
import runner.TestUtils;
import runner.type.Profile;
import runner.type.ProfileType;
import java.util.List;
import java.util.UUID;

public class EntityImportTest extends BaseTest {

    @Test
    @Profile(profile = ProfileType.DEFAULT)
    public void deleteRecordFromEntityImport() throws InterruptedException {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver,3);

        WebElement importValuesTab = driver.findElement(By.xpath("//p[contains(text(),'Import values')]"));
        importValuesTab.click();

        String randomString = createRecordInEntityImport(driver);

        List<WebElement> paginationNums = driver.findElements(By.xpath("//ul[@class='pagination'] //li //a[contains(@aria-label, 'to page')]"));
        int countPages = 0;

        boolean flag = false;
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

        WebElement userButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='navbarDropdownProfile']")));
        ProjectUtils.click(driver, userButton);

        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Log out')]")));
        ProjectUtils.click(driver, logoutButton);
    }

    public String createRecordInEntityImport(WebDriver driver) throws InterruptedException {

        WebElement createImportValuesIcon = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createImportValuesIcon.click();

        String randomString= UUID.randomUUID().toString();
        WebElement stringInImportValueField = driver.findElement(By.xpath("//input[@id='string']"));
        stringInImportValueField.sendKeys(randomString);

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

    @Ignore
    @Test
    public void doImportButton() throws InterruptedException{

        WebDriver driver = getDriver();
        ProjectUtils.loginProcedure(driver);

        final String str = "Denys_Test_1";
        final String text = "Do_Import_Button";
        final int integer = 1;
        final double decimal = 1.55;
        final String user = "User 1 Demo";

        createRecordInImportValuesEntity(driver, str, text, integer, decimal);

        WebElement ImportEntity =
                driver.findElement(By.xpath("//a[@href='index.php?action=action_list&entity_id=17']"));
        ProjectUtils.click(driver, ImportEntity);
        WebElement createImportFolder = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        createImportFolder.click();
        WebElement doImportButton = driver.findElement(By.xpath("//input[@value='Do import']"));
        doImportButton.click();
        WebElement chooseRecord = driver.findElement(By.xpath("//i[text()='done_all']"));
        chooseRecord.click();
        Thread.sleep(1000);
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);
        WebElement selectImportedRecord = driver.findElement(By.xpath("//button/i[@class='material-icons']"));
        selectImportedRecord.click();
        WebElement viewRecord = driver.findElement(By.xpath("//a[text()='view']"));
        viewRecord.click();

        final String FIELD_XPATH = "(//label[text()='String']/following::div/div/span)";
        WebElement fieldString = driver.findElement(By.xpath(FIELD_XPATH + "[1]"));
        WebElement fieldText = driver.findElement(By.xpath(FIELD_XPATH + "[2]"));
        WebElement fieldInt = driver.findElement(By.xpath(FIELD_XPATH + "[3]"));
        WebElement fieldDecimal = driver.findElement(By.xpath(FIELD_XPATH + "[4]"));
        WebElement fieldUser = driver.findElement(By.xpath("//div[@class='form-group']//p"));

        Assert.assertEquals(fieldString.getText(), str);
        Assert.assertEquals(fieldText.getText(), text);
        Assert.assertEquals(fieldInt.getText(), String.valueOf(integer));
        Assert.assertEquals(fieldDecimal.getText(),String.valueOf(decimal));
        Assert.assertEquals(fieldUser.getText(), user);
    }

    @Ignore
    @Test
    public void customImportButton() throws InterruptedException{

        WebDriver driver = getDriver();
        ProjectUtils.loginProcedure(driver);

        final String str = "Denys_Test_2";
        final String text = "Custom_Import_Button";
        final int integer = 2;
        final double decimal = 2.55;
        final String user = "User 1 Demo";

        createRecordInImportValuesEntity(driver, str, text, integer, decimal);

        WebElement ImportEntity =
                driver.findElement(By.xpath("//a[@href='index.php?action=action_list&entity_id=17']"));
        ProjectUtils.click(driver, ImportEntity);
        WebElement createImportFolder = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        createImportFolder.click();
        WebElement doImportButton = driver.findElement(By.xpath("//input[@value='Custom Import']"));
        doImportButton.click();
        WebElement chooseRecord = driver.findElement(By.xpath("//i[text()='done_all']"));
        chooseRecord.click();
        Thread.sleep(1000);
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);
        WebElement selectImportedRecord = driver.findElement(By.xpath("//button/i[@class='material-icons']"));
        selectImportedRecord.click();
        WebElement viewRecord = driver.findElement(By.xpath("//a[text()='view']"));
        viewRecord.click();

        final String FIELD_XPATH = "(//label[text()='String']/following::div/div/span)";
        WebElement fieldString = driver.findElement(By.xpath(FIELD_XPATH + "[1]"));
        WebElement fieldText = driver.findElement(By.xpath(FIELD_XPATH + "[2]"));
        WebElement fieldInt = driver.findElement(By.xpath(FIELD_XPATH + "[3]"));
        WebElement fieldDecimal = driver.findElement(By.xpath(FIELD_XPATH + "[4]"));
        WebElement fieldUser = driver.findElement(By.xpath("//div[@class='form-group']//p"));

        Assert.assertEquals(fieldString.getText(), "This is a custom TEXT");
        Assert.assertEquals(fieldText.getText(), text);
        Assert.assertEquals(fieldInt.getText(), String.valueOf(integer));
        Assert.assertEquals(fieldDecimal.getText(),String.valueOf(decimal));
        Assert.assertEquals(fieldUser.getText(), user);
    }

    public void createRecordInImportValuesEntity(WebDriver driver, String str, String text, int integ, double decimal){

        WebElement importValuesTab = driver.findElement(By.xpath("//p[contains(text(),'Import values')]"));
        ProjectUtils.click(driver, importValuesTab);
        WebElement createImportValuesIcon = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createImportValuesIcon.click();

        WebElement stringInImportValueField = driver.findElement(By.xpath("//input[@id='string']"));
        stringInImportValueField.sendKeys(str);
        WebElement textInImportValueField = driver.findElement(By.xpath("//textarea[@id='text']"));
        textInImportValueField.sendKeys(text);
        WebElement intInImportValueField = driver.findElement(By.xpath("//input[@id='int']"));
        intInImportValueField.sendKeys(String.valueOf(integ));
        WebElement decimalInImportValueField = driver.findElement(By.xpath("//input[@id='decimal']"));
        decimalInImportValueField.sendKeys(String.valueOf(decimal));
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        ProjectUtils.click(driver, saveButton);
    }
}