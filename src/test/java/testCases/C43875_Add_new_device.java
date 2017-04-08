package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;

/**
 *PRECONDITION
 * There are:
 * - at least one Hub in account
 * - at least three rooms in the Hub
 */
public class C43875_Add_new_device{
    private Base $;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        $ = new Base(deviceName_);
        $.initPageObjects($.getDriver());

        Base.log("login without Pin");
        $.loginPage.loginWithPinCancel();
    }

    @Test(priority = 1, enabled = true)
    public void add_Door_Protect() {
        Base.log("add devices DoorProtect to Hub");
        $.devicesPage.addNew(203061, 1, 1, "Door_Protect", 1);
        Assert.assertTrue($.devicesPage.checkIsNewDeviceAdded("Door_Protect"));
    }

    @Test(priority = 2, enabled = true)
    public void add_Motion_Protect() {
        Base.log("add devices MotionProtect to Hub");
        $.devicesPage.addNew(203062, 2, 2, "Motion_Protect", 2);
        Assert.assertTrue($.devicesPage.checkIsNewDeviceAdded("Motion_Protect"));
    }

    @Test(priority = 3, enabled = true)
    public void add_Glass_Protect() {
        Base.log("add devices GlassProtect to Hub");
        $.devicesPage.addNew(203063, 3, 4, "Glass_Protect", 3);
        Assert.assertTrue($.devicesPage.checkIsNewDeviceAdded("Glass_Protect"));
    }

    @AfterClass
    public void endSuit() {
        $.getDriver().quit();
    }

}
