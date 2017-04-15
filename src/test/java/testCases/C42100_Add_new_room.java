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
 * - at least one Hub
 * - no rooms in the Hub
 */
public class C42100_Add_new_room{

    private String login, pass, server, name, actual;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        Base.log(1, "get credentials for login");
        login = base.getCredsWithKey("login");
        pass = base.getCredsWithKey("password");
        server = base.getCredsWithKey("server");

        base.loginPage.loginWithPinCancel(login, pass, server);

        Base.log(1, "tap the Room Page button in the footer");
        base.dashboard.goToTheRoomPage();
    }

    @Test(priority = 1, enabled = true)
    public void First_room_without_image() {
        Base.log(1, "add Room without image");
        base.roomsPage.addRoom("Without image", 0);

        Assert.assertTrue(base.roomsPage.isRoomPresens("Without image"));
    }

    @Test(priority = 2, enabled = true)
    public void Gallery_image() {
        Base.log(1, "close pop up if present");
        base.nav.cancelIt();

        Base.log(1, "add Room with image from popup gallery");
        base.roomsPage.addRoom("Gallery image", 2, 2);

        Assert.assertTrue(base.roomsPage.isRoomPresens("Gallery image"));
    }

    @Test(priority = 3, enabled = true)
    public void Camera_image() {
        Base.log(1, "close pop up if present");
        base.nav.cancelIt();

        Base.log(1, "add Room with image from camera");
        base.roomsPage.addRoom("Camera image", 1);

        Assert.assertTrue(base.roomsPage.isRoomPresens("Camera image"));
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }

}
