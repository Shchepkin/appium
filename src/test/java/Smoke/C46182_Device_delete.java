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
 * - at least three devices with different names
 */
public class C46182_Device_delete {
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        Base.log(1, "login without Pin");
        base.loginPage.loginWithPinCancel();
    }

    // TODO make classes for device deleting like rooms deleting class

    @Test(priority = 1, enabled = true)
    public void While_Hub_Disarmed() {
        Base.log(3, "START TEST");
        String successText = base.getLocalizeTextForKey("Deleting_success1");

        Base.log(4, "get name of first device from device list");
        String devName = base.devicesPage.getFirstDeviceName();

        base.devicesPage.goToFirstDeviceSettingsPage();

        Base.log(1, "scroll bottom and tap Delete button", true);
//        base.nav.scrollBottom();
        base.devicesPage.unpairButtonClick();

        Base.log(1, "confirm proposition from popUp", true);
        base.nav.confirmIt();

        Assert.assertTrue(base.wait.elementWithText(successText, 10, true));

        base.wait.menuIconOrPinPopUp(1);

        Assert.assertFalse(base.check.isPresent.elementWith.name(devName));
        Base.log(1, "device with name \"" + devName + "\" is deleted successfully");
    }

    @Test(priority = 2, enabled = true)
    public void While_Hub_Armed() {
        Base.log(3, "START TEST");
        String expectedSnackBarText = base.getLocalizeTextForKey("cannot_perform_action_while_hub_is_armed");
        base.hub.security.arm();
        base.dashboard.goToTheDevicesPage();

        Base.log(1, "goto first device settings", true);
        base.devicesPage.goToFirstDeviceSettingsPage();

        Base.log(1, "wait for SnackBar with error message", true);
        Assert.assertTrue(base.wait.element(base.popUp.getSnackBarElement(), 3, true));

        String actualSnackBarText = base.popUp.getSnackBarText();
        Assert.assertEquals(actualSnackBarText, expectedSnackBarText, "SnackBar with error text is not shown");
        Base.log(1, "SnackBar with error text is successfully shown", true);

        Base.log(1, "disarm hub");
        base.nav.goBack();
        base.hub.security.disarm();
    }

    @Test(priority = 3, enabled = true)
    public void While_Hub_Partial_Armed() {
        Base.log(3, "START TEST");
        String expectedSnackBarText = base.getLocalizeTextForKey("cannot_perform_action_while_hub_is_armed");
        base.hub.security.partialArm();
        base.dashboard.goToTheDevicesPage();

        Base.log(1, "goto first device settings", true);
        base.devicesPage.goToFirstDeviceSettingsPage();

        Base.log(1, "wait for SnackBar with error message", true);
        base.wait.element(base.popUp.getSnackBarElement(), 3, true);
        String actualSnackBarText = base.popUp.getSnackBarText();

        Assert.assertEquals(actualSnackBarText, expectedSnackBarText, "SnackBar with error text is not shown");
        Base.log(1, "SnackBar with error text is successfully shown", true);

        Base.log(1, "disarm hub");
        base.nav.goBack();
        base.hub.security.disarm();
    }

    @Test(priority = 4, enabled = true)
    public void All_Device_Deleting() {
        Base.log(3, "START TEST");
        Base.log(1, "delete all devices", true);
        base.devicesPage.deleteAll();
        Assert.assertTrue(base.check.isEmpty.devicesList(), "Device List is not Empty\n");
        Base.log(1, "All devices is deleted successfully", true);
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
