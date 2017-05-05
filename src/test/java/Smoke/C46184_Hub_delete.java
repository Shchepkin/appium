package Smoke;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Base;

/**
 * PRECONDITION
 * - one hub
 */
public class C46184_Hub_delete {
    private Base base;
    private String expectedSnackBarText;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        Base.log(1, "login without Pin");
        base.loginPage.loginWithPinCancel();
        expectedSnackBarText = base.getLocalizeTextForKey("cannot_perform_action_while_hub_is_armed");
    }

    @Test(priority = 1, enabled = true)
    public void While_Hub_Armed() {
        Base.log(1, "Arm hub from Remote Page", true);
        base.hub.security.arm();

        Base.log(1, "goto Hub Settings", true);
        base.nav.gotoPage.hubSettings();

        Base.log(1, "wait Snack Bar with error message", true);
        base.wait.element(base.popUp.getSnackBarElement(), 5, true);

        String actualSnackBarText = base.popUp.getSnackBarText();

        Base.log(1, "expected: " + expectedSnackBarText, true);
        Base.log(1, "actual: " + actualSnackBarText, true);

        Assert.assertEquals(actualSnackBarText, expectedSnackBarText, "SnackBar with error text is not shown");
        Base.log(1, "SnackBar with error text is successfully shown", true);

        Base.log(1, "disarm hub");
        base.hub.security.disarm();
    }

    @Test(priority = 2, enabled = true)
    public void While_Hub_Partial_Armed() {
        Base.log(1, "Partially Arm hub from Remote Page", true);
        base.hub.security.partialArm();

        Base.log(1, "goto Hub Settings", true);
        base.nav.gotoPage.hubSettings();

        Base.log(1, "wait Snack Bar with error message", true);
        base.wait.element(base.popUp.getSnackBarElement(), 5, true);

        String actualSnackBarText = base.popUp.getSnackBarText();

        Base.log(1, "expected: " + expectedSnackBarText, true);
        Base.log(1, "actual: " + actualSnackBarText, true);

        Assert.assertEquals(actualSnackBarText, expectedSnackBarText, "SnackBar with error text is not shown");
        Base.log(1, "SnackBar with error text is successfully shown", true);

        Base.log(1, "disarm hub");
        base.hub.security.disarm();
    }

    @Test(priority = 3, enabled = true)
    public void From_MasterUser_Settings() {
        base.hub.deleteFrom.masterUserSettings(true);
        Assert.assertTrue(base.wait.element(base.dashboard.getPlusButton(),10, true), "dashboard with Plus Button does not shown\n");
    }

    @Test(priority = 4, enabled = true)
    public void From_Hub_Settings() {
        Base.log(1, "return hub for next tests");
        base.hub.addNewManual();
        base.hub.deleteFrom.hubSettings(true);
        Assert.assertTrue(base.wait.element(base.dashboard.getPlusButton(),10, true), "dashboard with Plus Button does not shown\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
