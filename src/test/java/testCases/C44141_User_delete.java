package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;

/**
 * PRECONDITION:
 * There are:
 * - at least one hub, so master user present
 * - at least one active user
 * - at least one pending user
 */
public class C44141_User_delete{

    private String login, pass, server, adminStatus, userStatus;
    private Base $;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        $ = new Base(deviceName_);
        $.initPageObjects($.getDriver());

        Base.log("get credentials for login");
        login = $.getCredsWithKey("login");
        pass = $.getCredsWithKey("password");
        server = $.getCredsWithKey("server");

        Base.log("login without Pin");
        $.loginPage.loginWithPinCancel(login, pass, server);

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

    @Test(priority = 3, enabled = true)
    public void master_user() {
        $.user.deleteMasterUser();
        $.nav.cancelIt();
        Assert.assertTrue($.dashboard.getPlusButton().isDisplayed());
    }

    private void addHub() {
        String hubName = "1495";
        String hubKey = "00001495DDFB55691000";

        Base.log("tap to the Plus Button");
        $.dashboard.plusButtonClick();

        Base.log("choose manual Hub adding ");
        $.nav.nextButtonClick();

        $.dashboard.fillFieldsWith(hubName, hubKey);
        $.nav.confirmIt();

        $.wait.invisibilityOfWaiter(true);
        Assert.assertFalse($.check.isErrorPresent(3), "Hub adding failed!");

        Assert.assertTrue($.wait.element($.dashboardHeader.getGprsImage(), 15, true));
        Base.log("hub successfully added!");
    }

    @AfterClass
    public void endSuit() {
        Base.log("return hub for next tests");
        addHub();
        $.getDriver().quit();
    }
}
