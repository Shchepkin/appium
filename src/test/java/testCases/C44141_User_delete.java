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

    private Base $;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        $ = new Base(deviceName_);
        $.initPageObjects($.getDriver());

        Base.log("login without Pin");
        $.loginPage.loginWithPinCancel();

        $.hub.goToTheUserlistPage();
    }

    @Test(priority = 1, enabled = false)
    public void all_pending_users() {
        $.user.deleteAllPending();
    }

    @Test(priority = 2, enabled = true)
    public void all_guest_users() {
        $.user.deleteAllGuests();
    }

    @Test(priority = 3, enabled = false)
    public void master_user() {
        $.user.deleteMasterUser();
        $.nav.cancelIt();

        Assert.assertTrue($.dashboard.getPlusButton().isDisplayed());

        Base.log("return hub for next tests");
        $.hub.addNew();
    }

    @AfterClass
    public void endSuit() {
        $.getDriver().quit();
    }
}
