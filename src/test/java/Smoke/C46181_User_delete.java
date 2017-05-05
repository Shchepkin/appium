package Smoke;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Base;

/**
 * PRECONDITION
 * There are:
 * - one hub, so  at least one master-user present
 * - at least one active user
 * - at least one pending user
 */
public class C46181_User_delete {

    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        Base.log(1, "login without Pin");
        base.loginPage.loginWithPinCancel();

        base.nav.gotoPage.userList();
    }

    @Test(priority = 1, enabled = true)
    public void all_pending_users() {
        Base.log(3, "START TEST");
        base.user.deleteAllPending();
    }

    @Test(priority = 2, enabled = true)
    public void all_guest_users() {
        Base.log(3, "START TEST");
        base.user.deleteAllGuests();
    }

    @Test(priority = 3, enabled = true)
    public void master_user() {
        Base.log(3, "START TEST");
        base.user.deleteMasterUser();
        base.nav.cancelIt();

        Assert.assertTrue(base.dashboard.getPlusButton().isDisplayed());

        Base.log(1, "return hub for next tests", true);
        base.hub.addNewManual();
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
