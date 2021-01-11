package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class Chain2ViewPage extends BasePage {

    @FindBy(css = ".pa-view-field")
    private List<WebElement> fValuesView;

    public Chain2ViewPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getActualValues() {
        List<WebElement> fValues = fValuesView;

        final List<String> actualValues = new ArrayList<>();
        for (WebElement fValue : fValues) {
            actualValues.add(fValue.getText());
        }
        return actualValues;
    }
}
