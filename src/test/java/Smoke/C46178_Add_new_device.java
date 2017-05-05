package Smoke;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Base;
import utils.Imitator;

/**
 * PRECONDITION
 * There are:
 * - at least one Hub in account
 * - at least three rooms in the Hub
 */

public class C46178_Add_new_device {
    private Base base;
    private Imitator imitator;
    private String deviceName;

    @Parameters({"deviceName_"})
    @BeforeClass
    public void init(String deviceName_) {
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        imitator = new Imitator(base);

        base.loginPage.loginWithPinCancel();
    }

    @Test(priority = 1, enabled = true)
    public void add_Device_While_Hub_Armed() {
        Base.log(3, "START TEST");
        base.hub.security.arm();
        base.nav.gotoPage.Devices();

        Base.log(1, "check is Add New Device button is shown", true);
        Assert.assertFalse(base.check.isPresent.button.addNewDevice(), "Add New Device button is shown\n");
        Base.log(1, "Add New Device button is not shown", true);
        base.hub.security.disarm();
    }

    @Test(priority = 2, enabled = true, invocationCount = 2)
    public void add_Device_From_Rooms_Page() {
        Base.log(3, "START TEST");
        deviceName = "Motion_Protect";
        base.nav.gotoPage.Rooms();

        imitator.addDevice(203062, 2, 2, deviceName);

        Base.log(1, "check is device added to Hub", true);

        deviceName = imitator.getDeviceNameNew();
        Assert.assertTrue(base.check.isPresent.elementWith.name(deviceName), "device with name \"" + deviceName + "\" is not added\n");
        Base.log(1, "new device with name \"" + deviceName + "\" is added successfully", true);
    }

    @Test(priority = 3, enabled = true)
    public void add_Device_From_Devices_Page() {
        Base.log(3, "START TEST");
        deviceName = "Door_Protect";
        base.nav.gotoPage.Devices();

        Base.log(1, "add device \"" + deviceName + "\" from Devices Page", true);
        imitator.addDevice(203061, 1, 1, deviceName, 2);

        Base.log(1, "check is device added to Hub", true);
        Assert.assertTrue(base.check.isPresent.elementWith.name(deviceName));
    }

    @Test(priority = 4, enabled = true)
    public void add_Existing_Device() {
        Base.log(3, "START TEST");
        deviceName = "Existing_Device";
        base.nav.gotoPage.Devices();

        Base.log(1, "add existing device from Devices Page", true);
        Assert.assertFalse(imitator.addDevice(203061, 1, 1, deviceName, 2), "");
        Base.log(1, "cannot add existing device", true);
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
