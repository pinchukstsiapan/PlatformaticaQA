import model.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.type.Run;
import runner.type.RunType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Run(run = RunType.Multiple)
public class EntityEventsChain2Test extends BaseTest {

    private static final List<String> EXPECTED_VALUES_F1_1 = new ArrayList<>(Arrays. asList(
            "1", "1", "2", "3", "5", "8", "13", "21", "34", "55"));
    private static final List<String> EXPECTED_VALUES_F1_0 = new ArrayList<>(Arrays. asList(
            "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"));
    private static final List<String> EXPECTED_VALUES_UUID = new ArrayList<>(Arrays. asList(
            UUID.randomUUID().toString(), "text", "text", "text", "text", "text", "text", "text", "text", "text"));

    @Test
    public void createNewRecord() {
        final String f1Value = "1";

        MainPage mainPage = new MainPage(getDriver());
        Chain2Page chain2Page = mainPage
                .clickMenuEventsChain2()
                .clickNewRecordButton()
                .inputF1Value(f1Value)
                .clickSaveButton();

        Assert.assertEquals(chain2Page.getRowsCount(), 1);
        Assert.assertEquals(chain2Page.getActualValues(), EXPECTED_VALUES_F1_1);
    }

    @Test(dependsOnMethods = "createNewRecord")
    public void verifyRecordView() {

        Chain2Page chain2Page = new Chain2Page(getDriver());
        Chain2ViewPage chain2ViewPage = chain2Page.viewRecord();

        Assert.assertEquals(chain2ViewPage.getActualValues(), EXPECTED_VALUES_F1_1);
    }

    @Test(dependsOnMethods = {"createNewRecord", "verifyRecordView"})
    public void verifyRecordEdit() {

        Chain2Page chain2Page = new Chain2Page(getDriver());
        Chain2EditPage chain2EditPage = chain2Page.editRecord();

        Assert.assertEquals(chain2EditPage.getActualValues(), EXPECTED_VALUES_F1_1);
    }

    @Test(dependsOnMethods = {"createNewRecord", "verifyRecordView", "verifyRecordEdit"})
    public void editRecord() {
        final String newF1 = "0";

        Chain2Page chain2Page = new Chain2Page(getDriver());
        chain2Page = chain2Page
                .editRecord()
                .editF1Value(newF1, EXPECTED_VALUES_F1_0)
                .clickSaveButton();

        Assert.assertEquals(chain2Page.getRowsCount(), 1);
        Assert.assertEquals(chain2Page.getActualValues(), EXPECTED_VALUES_F1_0);
    }

    @Test(dependsOnMethods = {"createNewRecord", "verifyRecordView", "verifyRecordEdit", "editRecord"})
    public void editRecordInvalidValues() {

        Chain2Page chain2Page = new Chain2Page(getDriver());
        Chain2ErrorPage chain2ErrorPage = chain2Page
                .editRecord()
                .editValues(EXPECTED_VALUES_UUID)
                .clickSaveButtonReturnError();

        Assert.assertEquals(chain2ErrorPage.getActualError(), "Error saving entity");

        chain2ErrorPage.openChain2Page();

        Assert.assertEquals(chain2Page.getRowsCount(), 1);
        Assert.assertNotEquals(chain2Page.getActualValues().get(0), EXPECTED_VALUES_UUID.get(0));
        Assert.assertEquals(chain2Page.getActualValues(), EXPECTED_VALUES_F1_0);
    }

    @Test(dependsOnMethods =
            {"createNewRecord", "verifyRecordView", "verifyRecordEdit", "editRecord", "editRecordInvalidValues"})
    public void deleteRecord() {

        Chain2Page chain2Page = new Chain2Page(getDriver());
        chain2Page.deleteRecord();

        WebElement parentElement = chain2Page.getTableParentElement();

        Assert.assertNotNull(parentElement);
        Assert.assertTrue(parentElement.getText().isEmpty());
    }
}