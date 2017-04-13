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
 * - at least three rooms with different names
 */
public class C44143_Room_delete {
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        Base.log("login without Pin");
        base.loginPage.loginWithPinCancel();

        base.dashboard.goToTheRoomPage();
    }



    @Test(priority = 1, enabled = true)
    public void While_Hub_Disarmed() {
        String successText = base.getLocalizeTextForKey("Deleting_success1");

        Base.log(2, "get name of first room from rooms list");
        String roomName = base.roomsPage.getFirstRoomName();

        base.roomsPage.goToRoomSettingsPage();

        Base.log("scroll bottom and tap Delete button");
        base.nav.scrollBottom();
        base.roomsPage.deleteButtonClick();

        Base.log("confirm proposition from popUp");
        base.nav.confirmIt();

        Assert.assertTrue(base.wait.elementWithText(successText, 10, true));

        base.nav.cancelIt();
        base.wait.element(base.dashboardHeader.getMenuDrawer(), 5, true);

        Assert.assertTrue(base.check.isDeletedBy("name", roomName));
        Base.log("room with name \"" + roomName + "\" is deleted successfully and SUCCESS text is shown");
    }

    @Test(priority = 2, enabled = true)
    public void While_Hub_Armed() {
        base.hub.arm();
        base.dashboard.goToTheRoomPage();
        Assert.assertFalse(base.check.isElementDisplayed(base.nav.getSettingsButton(), 5), "settings button present while hub is armed");

        Base.log("disarm hub");
        base.hub.disarm();
    }

    @Test(priority = 3, enabled = true)
    public void While_Hub_Partial_Armed() {
        base.hub.partialArm();
        base.dashboard.goToTheRoomPage();
        Assert.assertFalse(base.check.isElementDisplayed(base.nav.getSettingsButton(), 5), "settings button present while hub is armed");

        Base.log("disarm hub");
        base.hub.disarm();
    }

    @Test(priority = 4, enabled = true)
    public void All_Rooms_Deleting() {
        base.roomsPage.deleteAllRooms();
    }


    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
