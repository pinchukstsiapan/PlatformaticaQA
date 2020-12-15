import java.util.List;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.BaseTest;

public class EntityDefaultTest extends BaseTest {

    private WebDriver driver;

    /** initialize driver field and login */
    private void initTest() {
        driver = getDriver();
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");
    }

    @Test
    public void editRecord() throws InterruptedException {
        initTest();

        final String title = UUID.randomUUID().toString();

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(), 'Default')]"));
        tab.click();

        WebElement recordMenu = driver.findElement(By.xpath("//button[contains(@data-toggle, 'dropdown')] "));
        recordMenu.click();
        Thread.sleep(150);

        WebElement editFunction = driver.findElement(By.xpath("//a[text() = 'edit']"));
        System.out.println(editFunction);
        editFunction.click();
        //Assert.assertEquals(driver.getCurrentUrl(), "https://ref.eteam.work/index.php?action=action_edit&entity_id=7&row_id=2");
        Thread.sleep(1000);

        WebElement fieldString = driver.findElement(By.xpath("//input[@id = 'string']"));
        fieldString.getAttribute("value");
        System.out.println();

        //System.out.println(fieldString.getText());

        WebElement fieldText = driver.findElement(By.xpath("//span//textarea[@id = 'text']"));
        //System.out.println(fieldText.getText());
    }

    @Ignore
    @Test
    public void checkDefaultValueAndUpdateThem() throws InterruptedException {

        //lines data, default and updated
        final String stringLineDefaultText = "DEFAULT STRING VALUE";
        final String textLineDefaultText = "DEFAULT TEXT VALUE";
        final int intLineDefault = 55;
        final double decimalLineDefault = 110.32;
        final String dateLineDefault = "01/01/1970";
        final String dateTimeLineDefault = "01/01/1970 00:00:00";
        final int defaultLinesQty = 9;
        final String userDefault = "User 1 Demo";

        final String stringLineNewText = "Updated String Value For Checking Purposes";
        final String textLineNewText = "Updated Text Value";
        final int intLineNew = 123;
        final double decimalLineNew = 22.22;
        final String dateLineNew = "11/11/2011";
        final String dateTimeLineNew = "11/11/2011 11:11:11";

        //embedD lines data, default and updated
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

        final String login = "user1@tester.com";
        final String password = "ah1QNmgkEO";
        final String URL = "https://ref.eteam.work";

        WebDriver driver = getDriver();
        driver.get(URL);

        ProjectUtils.login(driver, login, password);

        driver.findElement(By.xpath("//a[@href='#menu-list-parent']")).click();
        driver.findElement(By.xpath("//i/following-sibling::p[contains (text(), 'Default')]")).click();
        driver.findElement(By.xpath("//div/i[.='create_new_folder']")).click();
        WebElement saveBtn = driver.findElement(By.xpath("//button[.='Save']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", saveBtn);
        Assert.assertTrue(saveBtn.isDisplayed());

        int count = driver.findElements(By.xpath("//div/label")).size();
        System.out.println("The default lines count is:" + count);
        Assert.assertTrue(count == defaultLinesQty);

        WebElement stringLineDefaultData = driver.findElement(By.xpath("//input[@id='string']"));
        Assert.assertTrue(stringLineDefaultData.getAttribute("value").equals(stringLineDefaultText));


        WebElement textLineDefaultData = driver.findElement(By.xpath("//textarea[@id='text']"));
        Assert.assertTrue(textLineDefaultData.getText().equals(textLineDefaultText));

        WebElement intLineDefaultData = driver.findElement(By.xpath("//input[@id='int']"));
        Assert.assertTrue(intLineDefaultData.getAttribute("value").equals(intLineDefault + ""));

        WebElement decimalLineDefaultData = driver.findElement(By.xpath("//input[@id='decimal']"));
        Assert.assertTrue(decimalLineDefaultData.getAttribute("value").equals(decimalLineDefault + ""));

        WebElement dateLineDefaultData = driver.findElement(By.xpath("//input[@id='date']"));
        Assert.assertTrue(dateLineDefaultData.getAttribute("value").equals(dateLineDefault));

        WebElement dateTimeLineDefaultData = driver.findElement(By.xpath("//input[@id='datetime']"));
        Assert.assertTrue(dateTimeLineDefaultData.getAttribute("value").equals(dateTimeLineDefault));

        WebElement user = driver.findElement(By.xpath("//div[@class='filter-option-inner']/div[.='User 1 Demo']"));
        System.out.println("User text:" + user.getText());
        Assert.assertTrue(user.getText().equals(userDefault.toUpperCase()));

        //replacing default data with new data
        stringLineDefaultData.clear();
        stringLineDefaultData.sendKeys(stringLineNewText);
        textLineDefaultData.clear();
        textLineDefaultData.sendKeys(textLineNewText);
        intLineDefaultData.clear();
        intLineDefaultData.sendKeys(String.valueOf(intLineNew));
        decimalLineDefaultData.clear();
        decimalLineDefaultData.sendKeys(String.valueOf(decimalLineNew));
        dateLineDefaultData.clear();
        dateLineDefaultData.sendKeys(dateLineNew);
        dateTimeLineDefaultData.clear();
        dateTimeLineDefaultData.sendKeys(dateTimeLineNew);

        //upload file and pics test passes on my local PC, I commented it cause I don't think it will pass on other PCs
        //driver.findElement(By.id("file")).sendKeys("C:\\Users\\Galina\\Desktop\\IMG_8992.JPG");
        //WebElement uploadPicture = driver.findElement(By.xpath("//input[@id='file_image']"));
        //uploadPicture.sendKeys("C:\\Users\\Galina\\Desktop\\IMG_8992.JPG");

        WebElement dropdownUsers = driver.findElement(By.xpath("//button[@data-id='user']"));
        dropdownUsers.click();

        Thread.sleep(1000);
        WebElement listOfUsers = driver.findElement(By.xpath("//span[.='User 4']/ancestor::a"));
        listOfUsers.click();

        //EmbedD default values check
        WebElement greenPlus = driver.findElement(By.xpath("//button[@data-table_id='11']"));
        greenPlus.click();

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

        //embedD values change
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

        saveBtn.click();

        WebElement orderBtn = driver.findElement(By.xpath("//a[contains(string(), 'Order')]"));
        orderBtn.click();

        WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Search']"));
        searchInput.sendKeys(stringLineNewText);
        Thread.sleep(500);

        WebElement ourRecord = driver.findElement(By.xpath("//div[contains (text(), 'Updated String Value For Checking Purposes')]/ancestor::a"));
        ourRecord.click();
        Thread.sleep(500);

//        Ask proficient users why its not working
//        String[] textArray = {stringLineNewText, textLineNewText, intLineNew +"", decimalLineNew +"", dateLineNew, dateTimeLineNew};
//        List<WebElement> myList = driver.findElements(By.className("pa-view-field"));
//        List<String> allElementsText = new ArrayList<>();
//        for (int i = 0; i < myList.size(); i++) {
//            allElementsText.add(myList.get(i).getText());
//            System.out.println(myList.get(i).getText());
//            Assert.assertEquals(allElementsText, textArray);

        //check all lines new values
        List<WebElement> listOfNewValues = driver.findElements(By.xpath("//span[@class='pa-view-field']"));
        Assert.assertEquals(listOfNewValues.get(0).getText(), stringLineNewText);
        Assert.assertEquals(listOfNewValues.get(1).getText(), textLineNewText);
        Assert.assertEquals(listOfNewValues.get(2).getText(), intLineNew + "");
        Assert.assertEquals(listOfNewValues.get(3).getText(), decimalLineNew + "");
        Assert.assertEquals(listOfNewValues.get(4).getText(), dateLineNew);
        Assert.assertEquals(listOfNewValues.get(5).getText(), dateTimeLineNew);

        //check EmbedD lines new values
        List<WebElement> embedDArrayOfNewValues = driver.findElements(By.xpath("//table/tbody/tr/td"));
        Assert.assertEquals(embedDArrayOfNewValues.get(1).getText(), stringEmbedLineNewText);
        Assert.assertEquals(embedDArrayOfNewValues.get(2).getText(), textEmbedLineNewText);
        Assert.assertEquals(embedDArrayOfNewValues.get(3).getText(), intEmbedLineNew + "");
        Assert.assertEquals(embedDArrayOfNewValues.get(4).getText(), decimalEmbedLineNew + "");
        Assert.assertEquals(embedDArrayOfNewValues.get(5).getText(), dateEmbedLineNew);
        Assert.assertEquals(embedDArrayOfNewValues.get(6).getText(), dateTimeEmbedLineNew);
        Assert.assertEquals(embedDArrayOfNewValues.get(9).getText(), userEmbedDSelected);
    }
}
