package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

@Ignore
public class GroupAutomationFL extends BaseTest {

    @Test
    public void apAkunaMatataTest() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://www.medicinalsupplies.com/");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//body/div[4]/aside[2]/div[2]/button[1]")).click();
        driver.findElement(By.xpath("//a[contains(text(),'My Account')]")).click();
        WebElement title = driver.findElement(By.xpath("//span[contains(text(),'Customer Login')]"));

        Assert.assertEquals(title.getText(), "Customer Login");
    }

}
