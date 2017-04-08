package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;

/**
 * PRECONDITION
 * There are:
 * - one hub, so  at least master-user present
 * - at least one active user
 * - at least one pending user
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
    public void one_device_deleting() {
        $.user.deleteAllPending();
    }

    @Test(priority = 2, enabled = true)
    public void all_device_deleting() {
        $.user.deleteAllGuests();
    }

    @AfterClass
    public void endSuit() {
        $.getDriver().quit();
    }
}
