package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class BaseViewPage extends BasePage {

    @FindBy(css = ".pa-view-field")
    private List<WebElement> values;

    public BaseViewPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getValues() {
        return values.stream().map(WebElement::getText).collect(Collectors.toList());
    }

}
