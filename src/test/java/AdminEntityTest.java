import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.type.Profile;
import runner.type.ProfileType;
import runner.type.Run;
import runner.type.RunType;

import java.util.*;

@Profile(profile = ProfileType.MARKETPLACE)
@Run(run = RunType.Multiple)
public class AdminEntityTest extends BaseTest {

    private static final String[] FIELD_TYPE = {"string", "text", "int", "decimal", "date", "datetime", "file", "user"};

    private Boolean isUnableCreateApp() {
        return getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//body"))).getText().equals("Unable to create instance");
    }

    private String[] getEntityValues() {
        String name = RandomStringUtils.randomAlphanumeric(6, 10).toLowerCase();
        return new String[] {name, name, String.format("https://%s.eteam.work", name), "admin", "2", "1", "English"};
    }

    private int getRandomInteger() {
        Random r = new Random();
        return r.nextInt(Integer.MAX_VALUE);
    }

    private Map<Integer, String> createRecordValues(){
        double random_double = getRandomInteger();
        while (random_double % 10 == 0) {
            random_double = getRandomInteger();
        }
        double finalRandom_double = random_double * 0.01;
        return new HashMap<Integer, String>() {{
//        Map<Integer, String> entity_record = new HashMap<Integer, String>() {{
            put(1, ProjectUtils.createRandomString());
            put(2, ProjectUtils.createRandomString());
            put(3, String.valueOf(getRandomInteger()));
            put(4, String.format("%.2f", finalRandom_double));
        }};
//        return entity_record;
    }

    private final String entity_name = ProjectUtils.createRandomString();
    private final By entity_in_menu = By.xpath(String.format
            ("//p[contains(text(),'%s')]/preceding-sibling::i/parent::a", entity_name));
    private final Map<Integer, String> entity_record = createRecordValues();

    private void selectRecordAction (WebDriver driver, String action) {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//tr[@data-index='0']/td/div/button"))).click();
        ProjectUtils.click(driver, driver.findElement(By.linkText(action)));
    }

    private void goToConfiguration() {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//a[@id='navbarDropdownProfile']"))).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//a[contains(text(),'Configuration')]"))).click();
    }

    private void goToEntities() {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[contains(@class,'card-body')]")));
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//i[contains(text(),'dynamic_feed')]/parent::a"))).click();
    }

    private void commandInCMD(WebDriver driver, String command) {
        WebElement cmd = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//textarea[@id='pa-cli-cmd']")));
        cmd.click();
        cmd.sendKeys(command);
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable
                (By.xpath("//button[@id='pa-cli-cmd-enter']"))).click();
        getWebDriverWait().until(ExpectedConditions.stalenessOf(driver.findElement
                (By.xpath("//textarea[@id='pa-cli-cmd']"))));
    }

    private void createFieldForEntity(WebDriver driver, int label, String type) throws InterruptedException {
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//h3[contains(text(),'Fields')]")));
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//i[contains(text(),'create_new_folder')]"))).click();
        ProjectUtils.inputKeys(driver, getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//input[@id='pa-adm-new-fields-label']"))), label);
        driver.findElement(By.xpath("//div[@class='filter-option-inner-inner']")).click();
        ProjectUtils.click(driver, getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath(String.format("//span[text()='%s']/preceding-sibling::span/..", type)))));
        driver.findElement(By.xpath("//button[@id='pa-adm-create-fields-btn']")).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//h3[contains(text(),'Fields')]")));
    }

    private void assertEntityRecords (Map<Integer, String> expected_element){
        List<WebElement> actual_entity_record = getWebDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy
                (By.xpath("//table[@id='pa-all-entities-table']//a/div")));
        for (int i = 0; i < entity_record.size(); i++){
            String actual_value = String.valueOf(actual_entity_record.get(i).getText());
            Assert.assertEquals(actual_value, String.valueOf(expected_element.get(i+1)));
        }
    }

    @Test
    public void createApplicationTest(ITestContext context) throws InterruptedException {
        WebDriver driver = getDriver();

        WebElement instance_table = getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//div[contains(@class,'card-body')]")));
        Assert.assertTrue(instance_table.getText().isEmpty());

        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//i[contains(text(),'create_new_folder')]"))).click();
        String[] entity_values;
        do {
            entity_values = getEntityValues();
            if (isUnableCreateApp()) {
                driver.navigate().back();
            }
            WebElement app_name = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("//input[@id='name']")));
            ProjectUtils.inputKeys(driver, app_name, entity_values[0]);
            getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("//button[@id='pa-entity-form-save-btn']"))).click();
        } while (isUnableCreateApp());

        String congrats = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[contains(@class,'card-body')]//h3[1]"))).getText();
        Assert.assertEquals(congrats, "Congratulations! Your instance was successfully created");

        context.setAttribute("app_name", entity_values[0]);

        final String admin_password = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[contains(@class,'card-body')]//h4[2]/b"))).getText();

        driver.get(String.format("https://%s.eteam.work", entity_values[0]));
        WebElement login_element = driver.findElement(By.xpath("//input[@name='login_name']"));
        login_element.sendKeys("admin");
        WebElement pasw_element = driver.findElement(By.xpath("//input[@name='password']"));
        pasw_element.sendKeys(admin_password);
        driver.findElement(By.xpath("//button[contains(text(),'Sign in')]")).click();
    }

    @Test (dependsOnMethods = "createApplicationTest")
    public void createEntityTest(ITestContext context) throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get(String.format("https://%s.eteam.work", context.getAttribute("app_name")));

        goToConfiguration();
        goToEntities();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//span[contains(text(),'New...')]"))).click();

        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//input[@id='pa-adm-new-entity-label']"))).sendKeys(entity_name);
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//button[@id='pa-adm-create-entity-btn']"))).click();
        Assert.assertEquals(getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//ul[@class='navbar-nav']/div"))).getText(), entity_name);

        for (int i = 0; i < FIELD_TYPE.length; i++) {
            createFieldForEntity(driver, i + 1, FIELD_TYPE[i]);
        }

        List<WebElement> field_label_element = driver.findElements
                (By.xpath("//tbody//td[1]"));
        List<WebElement> field_type_element = driver.findElements
                (By.xpath("//tbody//td[3]"));
        for (int i = 0; i < field_label_element.size(); i++) {
            Assert.assertEquals(field_label_element.get(i).getText(), String.valueOf(i + 1));
            Assert.assertEquals(field_type_element.get(i).getText(), FIELD_TYPE[i]);
        }
    }

    @Test (dependsOnMethods = {"createApplicationTest", "createEntityTest"})
    public void createRecordsTest(ITestContext context) {
        WebDriver driver = getDriver();
        driver.get(String.format("https://%s.eteam.work", context.getAttribute("app_name")));

        Assert.assertTrue(driver.findElement(entity_in_menu).isDisplayed());
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(entity_in_menu)).click();
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//i[contains(text(),'create_new_folder')]"))).click();
        for (int i = 1; i < entity_record.size() + 1; i++){
            if (i == 2) {
                getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(String.format("//textarea[@id='%d']", i)))).sendKeys(entity_record.get(i));
            } else {
                getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(String.format("//input[@id='%d']", i)))).sendKeys(entity_record.get(i));
            }
        }
        ProjectUtils.click(driver, getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//button[@id='pa-entity-form-save-btn']"))));
        assertEntityRecords (entity_record);
    }

    @Test (dependsOnMethods = {"createApplicationTest", "createEntityTest", "createRecordsTest"})
    public void recordActionsTest(ITestContext context) {
        WebDriver driver = getDriver();
        driver.get(String.format("https://%s.eteam.work", context.getAttribute("app_name")));

        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(entity_in_menu)).click();
        selectRecordAction(driver, "view");
        List<WebElement> actual_entity_record = driver.findElements(By.xpath("//label/following-sibling::div//span"));
        for (int i = 0; i < entity_record.size(); i++){
            Assert.assertEquals(actual_entity_record.get(i).getText(), entity_record.get(i + 1));
        }
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(entity_in_menu)).click();

        Map<Integer, String> edited_entity_record = createRecordValues();
        selectRecordAction(driver, "edit");
        for (int i = 1; i < edited_entity_record.size() + 1; i++){
            if (i == 2) {
                getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(String.format("//textarea[@id='%d']", i)))).clear();
                getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(String.format("//textarea[@id='%d']", i)))).sendKeys(edited_entity_record.get(i));
            } else {
                getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(String.format("//input[@id='%d']", i)))).clear();
                getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath(String.format("//input[@id='%d']", i)))).sendKeys(edited_entity_record.get(i));
            }
        }
        ProjectUtils.click(driver, getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//button[@id='pa-entity-form-save-btn']"))));
        assertEntityRecords (edited_entity_record);

        selectRecordAction(driver, "delete");
        WebElement records_table = getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//div[contains(@class,'card-body')]")));
        Assert.assertTrue(records_table.getText().isEmpty());

        goToConfiguration();
        commandInCMD(driver, String.format("delete entity \"%s\"", entity_name));

        getWebDriverWait().until(ExpectedConditions.elementToBeClickable
                (By.xpath("//a[@class='simple-text logo-normal']/preceding::div[@class='navbar-minimize']"))).click();
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable
                (By.xpath("//a[@class='simple-text logo-normal']"))).click();
        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(@class,'sidebar-wrapper')]")).getText(), "dashboard");
    }
}