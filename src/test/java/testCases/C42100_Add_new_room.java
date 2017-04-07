package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;

/**
 * PRECONDITION:
 * Account has no room
 */
public class C42100_Add_new_room{

    private String login, pass, server, name, actual;
    private Base $;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(){
        $ = new Base();
        $.initPageObjects($.getDriver());

        Base.log("get credentials for login");
        login = $.creds.get("login").toString();
        pass = $.creds.get("password").toString();
        server = $.creds.get("server").toString();

        $.loginPage.loginWithPinCancel(login, pass, server);

        Base.log("tap the Room Page button in the footer");
        $.dashboard.goToTheRoomPage();
    }

    @Test(priority = 1, enabled = true)
    public void First_room_without_image() {
        Base.log("add Room without image");
        $.roomsPage.addRoom("Without image", 0);

        Assert.assertTrue($.roomsPage.isRoomPresens("Without image"));
    }

    @Test(priority = 2, enabled = true)
    public void Gallery_image() {
        Base.log("close pop up if present");
        $.nav.cancelIt();

        Base.log("add Room with image from popup gallery");
        $.roomsPage.addRoom("Gallery image", 2, 2);

        Assert.assertTrue($.roomsPage.isRoomPresens("Gallery image"));
    }

    @Test(priority = 3, enabled = true)
    public void Camera_image() {
        Base.log("close pop up if present");
        $.nav.cancelIt();

        Base.log("add Room with image from camera");
        $.roomsPage.addRoom("Camera image", 1);

        Assert.assertTrue($.roomsPage.isRoomPresens("Camera image"));
    }

    @AfterClass
    public void endSuit() {
        $.getDriver().quit();
    }

}
