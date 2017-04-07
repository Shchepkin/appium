package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;

/**
 * PRECONDITION:
 * There are at least one hub with at least one room in account
 */
public class C44141_User_delete{

    private String login, pass, server, adminUserText, guestUserText;
    private Base $;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(){
        $ = new Base();
        $.initPageObjects($.getDriver());

        adminUserText = $.getLocalizeTextForKey("admin");
        guestUserText = $.getLocalizeTextForKey("user");

        Base.log("get credentials for login");
        login = $.creds.get("login").toString();
        pass = $.creds.get("password").toString();
        server = $.creds.get("server").toString();

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
        $.user.deleteAllActive(guestUserText);
    }

    @Test(priority = 3, enabled = false)
    public void master_user() {
        $.user.deleteAllActive(adminUserText);
    }

    private void addHub() {
        String hubName = "1495";
        String hubKey = "00001495DDFB55691000";
//        String waiterText = getLocalizeTextForKey("request_send");

        Base.log("tap to the Plus Button");
        $.dashboard.plusButtonClick();

        Base.log("choose manual Hub adding ");
        $.nav.nextButtonClick();

        $.dashboard.fillFieldsWith(hubName, hubKey);
        $.nav.confirmIt();

//        $.wait.invisibilityElementWithText(waiterText, true);
        $.wait.invisibilityOfWaiter(true);
        Assert.assertFalse($.check.isErrorPresent(3), "Hub adding failed!");

        Assert.assertTrue($.wait.element($.dashboardHeader.getGprsImage(), 15, true));
        Base.log("hub successfully added!");
    }

    @AfterClass
    public void endSuit() {
        $.getDriver().quit();
    }
}
