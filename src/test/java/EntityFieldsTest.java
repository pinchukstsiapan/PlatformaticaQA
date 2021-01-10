import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.Assert;
import runner.BaseTest;
import runner.type.Run;
import runner.type.RunType;

@Run(run = RunType.Multiple)
public class EntityFieldsTest extends BaseTest {

    private static final String TITLE = UUID.randomUUID().toString();
    private static final String COMMENTS = RandomStringUtils.randomAlphanumeric(25);
    private static final String INT = Integer.toString(ThreadLocalRandom.current().nextInt(100, 200));
    private static final String DECIMAL = "12.34";
    private static final String DECIMAL_ENDS_ZERO = "1.10";
    private static final String ALL_ZERO_AFTER_DECIMAL = "1.00";
    private static final String DATE = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    private static final String DATE_TIME = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    private static final String NEW_TITLE = String.format("%s_EditTextAllNew", UUID.randomUUID().toString());
    private static final String NEW_COMMENTS = "New comment text for edit test";
    private static final String NEW_INT = Integer.toString(ThreadLocalRandom.current().nextInt(300, 400));
    private static final String NEW_DECIMAL = "128.01";
    private static final String NEW_DATE = "25/10/2018";
    private static final String NEW_DATE_TIME = "25/10/2018 08:22:05";
    private static final String INVALID_ENTRY = "a";
    private static final String ERROR_MESSAGE = "Error saving entity";
    private static String RANDOM_USER = null;
    private static String CURRENT_USER = null;

    private void verifyEntityData(List<String> actual, String[] expected) {
        Assert.assertEquals(actual.size(), expected.length);
        for (int i = 1; i < actual.size(); i++) {
            Assert.assertEquals(actual.get(i), expected[i]);
        }
    }

    private void verifyEntityTypeIcon(String iconUnicode, String entityType) {
        switch (entityType) {
            case "record":
                Assert.assertEquals(iconUnicode, "f046", "Wrong record icon, expected '\\f046'");
                break;
            case "draft":
                Assert.assertEquals(iconUnicode, "f040", "Wrong draft icon, expected '\\f040'");
                break;
            default:
                throw new RuntimeException("Unexpected entity type");
        }
    }

    @Test
    public void createNewRecordTest() {

            String[] expectedValues = {"", TITLE, COMMENTS, INT, DECIMAL, DATE, DATE_TIME, "", CURRENT_USER, ""};

            MainPage mainPage = new MainPage(getDriver());
            CURRENT_USER = expectedValues[8] = mainPage.getCurrentUser();

            FieldsEditPage fieldsEditPage = mainPage
                    .clickMenuFields()
                    .clickNewButton();

            FieldsPage fieldsPage = fieldsEditPage
                    .fillTitle(TITLE)
                    .fillComments(COMMENTS)
                    .fillInt(INT)
                    .fillDecimal(DECIMAL)
                    .fillDate(DATE)
                    .fillDateTime(DATE_TIME)
                    .selectUser(CURRENT_USER)
                    .clickSaveButton();

            Assert.assertEquals(fieldsPage.getRowCount(), 1);
            verifyEntityData(fieldsPage.getRecordData(0), expectedValues);
            verifyEntityTypeIcon(fieldsPage.getEntityIconUnicode(0), "record");
    }

    @Test(dependsOnMethods = "deleteRecordTest")
    public void createNewDraftTest() {

        String[] expectedValues = {"", TITLE, COMMENTS, "0", "0", "", "", "", CURRENT_USER, ""};

        MainPage mainPage = new MainPage(getDriver());
        CURRENT_USER = expectedValues[8] = mainPage.getCurrentUser();

        FieldsEditPage fieldsEditPage = mainPage
                .clickMenuFields()
                .clickNewButton();

        FieldsPage fieldsPage = fieldsEditPage
                .fillTitle(TITLE)
                .fillComments(COMMENTS)
                .selectUser(CURRENT_USER)
                .clickSaveDraftButton();

        Assert.assertEquals(fieldsPage.getRowCount(), 1);
        verifyEntityData(fieldsPage.getRecordData(0), expectedValues);
        verifyEntityTypeIcon(fieldsPage.getEntityIconUnicode(0), "draft");
    }

    @Test(dependsOnMethods = "createNewRecordTest")
    public void editRecordTest() {

        String[] expectedValues = {"", NEW_TITLE, NEW_COMMENTS, NEW_INT, NEW_DECIMAL, NEW_DATE, NEW_DATE_TIME, "", RANDOM_USER, ""};

        MainPage mainPage = new MainPage(getDriver());
        FieldsEditPage fieldsEditsPage = mainPage.clickMenuFields().clickEntityMenuEditButton(0);

        RANDOM_USER = expectedValues[8] = fieldsEditsPage.getRandomUser();
        FieldsPage fieldsPage = fieldsEditsPage
                .fillTitle(NEW_TITLE)
                .fillComments(NEW_COMMENTS)
                .fillInt(NEW_INT)
                .fillDecimal(NEW_DECIMAL)
                .fillDate(NEW_DATE)
                .fillDateTime(NEW_DATE_TIME)
                .selectUser(RANDOM_USER)
                .clickSaveButton();

        Assert.assertEquals(fieldsPage.getRowCount(), 1);
        verifyEntityData(fieldsPage.getRecordData(0), expectedValues);
        verifyEntityTypeIcon(fieldsPage.getEntityIconUnicode(0), "record");
    }

    @Test(dependsOnMethods = "editRecordTest")
    public void deleteRecordTest() {

        FieldsPage fieldsPage = new FieldsPage(getDriver());
        final String recordTitle = fieldsPage.clickMenuFields().getTitle(0);
        fieldsPage.clickEntityMenuDeleteButton(0);

        Assert.assertEquals(fieldsPage.getRowCount(), 0, "Record has not been deleted");

        RecycleBinPage recycleBinPage = fieldsPage.clickRecycleBin();

        Assert.assertEquals(recycleBinPage.getRowCount(), 1);
        Assert.assertEquals(recycleBinPage.getDeletedEntityTitle(0), recordTitle);
    }

    @Test(dependsOnMethods = "createNewDraftTest")
    public void deleteDraftTest() {

        FieldsPage fieldsPage = new FieldsPage(getDriver());
        final String entityTitle = fieldsPage.clickMenuFields().getTitle(0);
        fieldsPage.clickEntityMenuDeleteButton(0);

        Assert.assertEquals(fieldsPage.getRowCount(), 0, "Draft has not been deleted");

        RecycleBinPage recycleBinPage = fieldsPage.clickRecycleBin();

        Assert.assertEquals(recycleBinPage.getRowCount(), 1);
        Assert.assertEquals(recycleBinPage.getDeletedEntityTitle(0), entityTitle);
    }

    @Test
    public void invalidIntEntryCreateTest() {

        ErrorPage errorPage = new FieldsPage(getDriver())
                .clickMenuFields()
                .clickNewButton()
                .fillInt(INVALID_ENTRY)
                .clickSaveButtonErrorExpected();

        Assert.assertEquals(errorPage.getErrorMessage(), ERROR_MESSAGE);
    }

    @Test
    public void invalidDecimalEntryCreateTest() {

        ErrorPage errorPage = new FieldsPage(getDriver())
                .clickMenuFields()
                .clickNewButton()
                .fillDecimal(INVALID_ENTRY)
                .clickSaveButtonErrorExpected();

        Assert.assertEquals(errorPage.getErrorMessage(), ERROR_MESSAGE);
    }

    @Ignore
    @Test(dependsOnMethods = "deleteDraftTest")
    public void entityDecimalEndsZeroTest() {

        FieldsPage fieldsPage = new FieldsPage(getDriver())
                .open()
                .clickNewButton()
                .fillDecimal(DECIMAL_ENDS_ZERO)
                .clickSaveButton();

        Assert.assertEquals(fieldsPage.getDecimal(0), DECIMAL_ENDS_ZERO);

        fieldsPage.clickEntityMenuEditButton(0)
                .fillDecimal(ALL_ZERO_AFTER_DECIMAL)
                .clickSaveButton();

        Assert.assertEquals(fieldsPage.getDecimal(0), ALL_ZERO_AFTER_DECIMAL);
    }

}
