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

public class EntityPlatformFunctionsTest extends BaseTest {

    private WebDriverWait getWait(int timeoutSecond) {
        return new WebDriverWait(getDriver(), timeoutSecond);
    }

   @Test
    public void verifyViewButtonFunction() {

        WebDriver driver = getDriver();
        WebElement clickOnLogo = driver.findElement(By.xpath("//i[text()='person']"));
        clickOnLogo.click();
        WebElement sideBarplatformFunctionsLink = driver.findElement(By.xpath("//p[text()=' Platform functions ']"));
        ProjectUtils.click(driver, sideBarplatformFunctionsLink);
        WebElement createNewFolderButton = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        createNewFolderButton.click();
        WebElement saveButton = driver.findElement(By.id("pa-entity-form-save-btn"));
        saveButton.click();
        String lastIntElement = driver.findElement(By.xpath("//td[@style='width: 10px; ']//following-sibling::td[1]//a//div")).getText();
        WebElement actionButton = driver.findElement(By.xpath("//i[text()='menu']"));
        actionButton.click();
        WebElement viewButton = getWait(2).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='view']")));
        viewButton.click();
        String lastIntInViewPage = driver.findElement(By.xpath("(//span[@class='pa-view-field'])[1]")).getText();
        Assert.assertEquals(lastIntInViewPage, lastIntElement);
    }

    @Ignore
    @Test
    public void verifyEditButtonFunction() {
        WebDriver driver = getDriver();
        WebElement clickOnLogo = driver.findElement(By.xpath("//i[text()='person']"));
        clickOnLogo.click();
        WebElement sideBarplatformFunctionsLink = driver.findElement(By.xpath("//p[text()=' Platform functions ']"));
        ProjectUtils.click(driver, sideBarplatformFunctionsLink);
        WebElement createNewFolderButton = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
        createNewFolderButton.click();
        WebElement saveButton = driver.findElement(By.id("pa-entity-form-save-btn"));
        saveButton.click();
        String lastIntValue = driver.findElement(By.xpath("(//div[@style='height:100%;width:100%'])[1]")).getText();
        String lastStringValue = driver.findElement(By.xpath("(//div[@style='height:100%;width:100%'])[2]")).getText();
        WebElement actionButton = driver.findElement(By.xpath("//i[text()='menu']"));
        actionButton.click();
        WebElement editButton = getWait(2).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='edit']")));
        editButton.click();
        WebElement saveButtonOnEditPage = driver.findElement(By.id("pa-entity-form-save-btn"));
        saveButtonOnEditPage.click();
        String lastIntEditPage = driver.findElement(By.xpath("(//div[@style='height:100%;width:100%'])[1]")).getText();
        String lastStringEditPage = driver.findElement(By.xpath("(//div[@style='height:100%;width:100%'])[2]")).getText();
        Assert.assertEquals(lastIntEditPage, lastIntValue);
        Assert.assertEquals(lastStringEditPage, lastStringValue);
    }
}
