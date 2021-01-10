package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.ArrayList;
import java.util.List;

public final class FieldsPage extends MainPage {

    @FindBy(xpath = "//i[text() = 'create_new_folder']")
    private WebElement buttonNew;

    @FindBy(className = "card-body")
    private WebElement body;

    @FindBy(xpath = "//table[@id='pa-all-entities-table']/tbody/tr")
    private List<WebElement> rows;

    public FieldsPage(WebDriver driver) {
        super(driver);
    }

    public FieldsEditPage clickNewButton() {
        click(buttonNew);
        return new FieldsEditPage(getDriver());
    }

    public int getRowCount() {
        if (Strings.isStringEmpty(body.getText())) {
            return 0;
        } else {
            return rows.size();
        }
    }

    public List<String> getRecordData(int rowNumber) {
        List<String> recordData = new ArrayList<>();
        List<WebElement> cols = rows.get(rowNumber).findElements(By.tagName("td"));
        for (int i = 0; i < cols.size() - 1; i++) {
            recordData.add(cols.get(i).getText());
        }

        return recordData;
    }

    public String getEntityIconUnicode(int rowNumber) {
        String script = String.format("return window.getComputedStyle(document.querySelector" +
                "('tr:nth-of-type(%d) td i.fa'),'::before').getPropertyValue('content')" +
                ".codePointAt(1).toString(16)", rowNumber + 1);
        String entityTypeIconUnicode = ((JavascriptExecutor) getDriver()).executeScript(script).toString();

        return entityTypeIconUnicode;
    }

    public String getTitle(int rowNumber) {
        return rows.get(rowNumber).findElement(By.xpath("//td[2]/a/div")).getText();
    }

    public FieldsEditPage clickEntityMenuEditButton(int rowNumber) {
        WebElement row = rows.get(rowNumber);
        click(row.findElement(By.tagName("button")));
        click(row.findElement(By.xpath("//li/a[contains(@href, 'edit')]")));

        return new FieldsEditPage(getDriver());
    }

    public void clickEntityMenuDeleteButton(int rowNumber) {
        WebElement row = rows.get(rowNumber);
        click(row.findElement(By.tagName("button")));
        click(row.findElement(By.xpath("//li/a[contains(@href, 'delete')]")));
    }

}
