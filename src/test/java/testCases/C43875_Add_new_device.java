package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;
import utils.Imitator;

/**
 * PRECONDITION
 * There are:
 * - at least one Hub in account
 * - at least three rooms in the Hub
 */

    // TODO check whether we are on Devices Page before start new device
    //

public class C43875_Add_new_device {
    private Base base;
    private Imitator imitator;

    @Parameters({"deviceName_"})
    @BeforeClass
    public void init(String deviceName_) {
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        imitator = new Imitator(base);

        Base.log(1, "login without Pin");
        base.loginPage.loginWithPinCancel();
    }

    @Test(priority = 1, enabled = true)
    public void add_Door_Protect() {
        Base.log(1, "add devices DoorProtect to Hub");
        imitator.addDevice(203061, 1, 1, "Door_Protect", 1);

        Base.log(1, "check is device DoorProtect added to Hub");
        Assert.assertTrue(base.devicesPage.checkIsNewAdded("Door_Protect"));
    }

    @Test(priority = 2, enabled = true)
    public void add_Motion_Protect() {
        Base.log(1, "add devices MotionProtect to Hub");
        imitator.addDevice(203062, 2, 2, "Motion_Protect", 2);

        Base.log(1, "check is device MotionProtect added to Hub");
        Assert.assertTrue(base.devicesPage.checkIsNewAdded("Motion_Protect"));
    }

    @Test(priority = 3, enabled = true)
    public void add_Glass_Protect() {
        Base.log(1, "add devices GlassProtect to Hub");
        imitator.addDevice(203063, 3, 4, "Glass_Protect", 3);

        Base.log(1, "check is device GlassProtect added to Hub");
        Assert.assertTrue(base.devicesPage.checkIsNewAdded("Glass_Protect"));
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }

}
