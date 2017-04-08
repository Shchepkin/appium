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
 * - at least three devices
 */
public class C44142_Device_delete {
    private Base $;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        $ = new Base(deviceName_);
        $.initPageObjects($.getDriver());

        Base.log("login without Pin");
        $.loginPage.loginWithPinCancel();
    }

    @Test(priority = 1, enabled = false)
    public void While_Hub_Disarmed() {
        String successText = $.getLocalizeTextForKey("Deleting_success1");

        Base.log(2, "get name of first device from device list");
        String devName = $.devicesPage.getFirstDeviceName();

        $.devicesPage.goToDeviceSettingsPage();

        Base.log("scroll bottom and tap Delete button");
        $.nav.scrollBottom();
        $.devicesPage.unpairButtonClick();

        Base.log("confirm proposition from popUp");
        $.nav.confirmIt();

        Assert.assertTrue($.wait.elementWithText(successText, 10, true));
        Base.log("device with name \"" + devName + "\" is deleted successfully and SUCCESS text is shown");

        Assert.assertTrue($.dashboardHeader.getMenuDrawer().isDisplayed());
        Base.log("device with name \"" + devName + "\" is deleted successfully and SUCCESS text is shown");
    }

    @Test(priority = 2, enabled = true)
    public void While_Hub_Armed() {
        String expectedSnackBarText = $.getLocalizeTextForKey("cannot_perform_action_while_hub_is_armed");
        $.hub.arm();
        $.dashboard.goToTheDevicesPage();
        $.devicesPage.goToDeviceSettingsPage();
        $.wait.element($.popUp.getSnackBarElement(), 5, true);
        String actualSnackBarText = $.popUp.getSnackBarText();
        Assert.assertEquals(actualSnackBarText, expectedSnackBarText, "Fail");
    }

    @Test(priority = 2, enabled = false)
    public void While_Hub_Partial_Armed() {
        String snackBarText = $.getLocalizeTextForKey("cannot_perform_action_while_hub_is_armed");
        $.hub.partialArm();

        $.devicesPage.deleteAllDevices();
    }

    @Test(priority = 2, enabled = false)
    public void all_device_deleting() {
        $.devicesPage.deleteAllDevices();
    }

    @AfterClass
    public void endSuit() {
        $.getDriver().quit();
    }
}
