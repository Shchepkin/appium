package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;

/**
 * PRECONDITION
 * - one hub
 * - at least three devices with different names
 */
public class C44142_Device_delete {
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        Base.log("login without Pin");
        base.loginPage.loginWithPinCancel();
    }

    @Test(priority = 1, enabled = false)
    public void While_Hub_Disarmed() {
        String successText = base.getLocalizeTextForKey("Deleting_success1");

        Base.log(2, "get name of first device from device list");
        String devName = base.devicesPage.getFirstDeviceName();

        base.devicesPage.goToDeviceSettingsPage();

        Base.log("scroll bottom and tap Delete button");
        base.nav.scrollBottom();
        base.devicesPage.unpairButtonClick();

        Base.log("confirm proposition from popUp");
        base.nav.confirmIt();

        Assert.assertTrue(base.wait.elementWithText(successText, 10, true));

        base.wait.element(base.dashboardHeader.getMenuDrawer(), 5, true);

        Assert.assertTrue(base.devicesPage.checkIsDeleted(devName));
        Base.log("device with name \"" + devName + "\" is deleted successfully and SUCCESS text is shown");
    }

    @Test(priority = 2, enabled = false)
    public void While_Hub_Armed() {
        String expectedSnackBarText = base.getLocalizeTextForKey("cannot_perform_action_while_hub_is_armed");
        base.hub.arm();
        base.dashboard.goToTheDevicesPage();
        base.devicesPage.goToDeviceSettingsPage();
        base.wait.element(base.popUp.getSnackBarElement(), 5, true);
        String actualSnackBarText = base.popUp.getSnackBarText();
        Assert.assertEquals(actualSnackBarText, expectedSnackBarText, "SnackBar with error text is not shown");
        Base.log("SnackBar with error text is successfully shown");

        Base.log("disarm hub");
        base.nav.goBack();
        base.hub.disarm();
    }

    @Test(priority = 3, enabled = false)
    public void While_Hub_Partial_Armed() {
        String expectedSnackBarText = base.getLocalizeTextForKey("cannot_perform_action_while_hub_is_armed");
        base.hub.partialArm();
        base.dashboard.goToTheDevicesPage();
        base.devicesPage.goToDeviceSettingsPage();
        base.wait.element(base.popUp.getSnackBarElement(), 5, true);
        String actualSnackBarText = base.popUp.getSnackBarText();
        Assert.assertEquals(actualSnackBarText, expectedSnackBarText, "SnackBar with error text is not shown");
        Base.log("SnackBar with error text is successfully shown");

        Base.log("disarm hub");
        base.nav.goBack();
        base.hub.disarm();
    }

    @Test(priority = 4, enabled = false)
    public void All_Device_Deleting() {
        base.devicesPage.deleteAllDevices();
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
