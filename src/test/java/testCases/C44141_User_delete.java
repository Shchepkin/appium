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
 * - one hub, so  at least one master-user present
 * - at least one active user
 * - at least one pending user
 */
public class C44141_User_delete{

    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        Base.log("login without Pin");
        base.loginPage.loginWithPinCancel();

        base.hub.goToTheUserlistPage();
    }

    @Test(priority = 1, enabled = false)
    public void all_pending_users() {
        base.user.deleteAllPending();
    }

    @Test(priority = 2, enabled = true)
    public void all_guest_users() {
        base.user.deleteAllGuests();
    }

    @Test(priority = 3, enabled = false)
    public void master_user() {
        base.user.deleteMasterUser();
        base.nav.cancelIt();

        Assert.assertTrue(base.dashboard.getPlusButton().isDisplayed());

        Base.log("return hub for next tests");
        base.hub.addNew();
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
