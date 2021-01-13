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

        Chain2Page chain2Page = new MainPage(getDriver())
                .clickMenuEventsChain2()
                .clickNewFolder()
                .inputF1Value(f1Value)
                .clickSaveButton();

        Assert.assertEquals(chain2Page.getRowCount(), 1);
        Assert.assertEquals(chain2Page.getRow(0), EXPECTED_VALUES_F1_1);
    }

    @Test(dependsOnMethods = "createNewRecord")
    public void verifyRecordView() {
        Assert.assertEquals(
                new Chain2Page(getDriver())
                .viewRow()
                .getValues(), EXPECTED_VALUES_F1_1);
    }

    @Test(dependsOnMethods = {"verifyRecordView"})
    public void verifyRecordEdit() {
        Assert.assertEquals(new Chain2Page(getDriver())
                .editRow()
                .getActualValues(), EXPECTED_VALUES_F1_1);
    }

    @Test(dependsOnMethods = {"verifyRecordEdit"})
    public void editRecord() {
        final String newF1 = "0";

        Chain2Page chain2Page = new Chain2Page(getDriver())
                .editRow()
                .editF1Value(newF1, EXPECTED_VALUES_F1_0)
                .clickSaveButton();

        Assert.assertEquals(chain2Page.getRowCount(), 1);
        Assert.assertEquals(chain2Page.getRow(0), EXPECTED_VALUES_F1_0);
    }

    @Test(dependsOnMethods = {"editRecord"})
    public void editRecordInvalidValues() {

        Chain2ErrorPage chain2ErrorPage = new Chain2Page(getDriver())
                .editRow()
                .editValues(EXPECTED_VALUES_UUID)
                .clickSaveButtonReturnError();

        Assert.assertEquals(chain2ErrorPage.getActualError(), "Error saving entity");

        Chain2Page chain2Page = Chain2Page.getPage(getDriver());

        Assert.assertEquals(chain2Page.getRowCount(), 1);
        Assert.assertEquals(chain2Page.getRow(0), EXPECTED_VALUES_F1_0);
    }

    @Test(dependsOnMethods = {"editRecordInvalidValues"})
    public void deleteRecord() {
        Chain2Page chain2Page = new Chain2Page(getDriver())
                .deleteRow();

        Assert.assertEquals(chain2Page.getRowCount(), 0);
    }
}