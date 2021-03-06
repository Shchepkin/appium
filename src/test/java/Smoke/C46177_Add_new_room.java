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
 * - at least one Hub
 * - no rooms in the Hub
 */
public class C46177_Add_new_room {

    private String login, pass, server, name, actual;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        Base.log(3, "\nSTART TEST\n");
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        base.loginPage.loginWithPinCancel();

        Base.log(1, "tap the Room Page button in the footer");
        base.nav.gotoPage.rooms();
    }

    @Test(priority = 1, enabled = true)
    public void Name_Field_Is_Empty() {
        Base.log(1, "add Room with empty Name field", true);
    }

    @Test(priority = 1, enabled = true)
    public void Name_Field_Has_() {
        Base.log(1, "add Room with ...", true);
    }

    @Test(priority = 1, enabled = true)
    public void Without_image() {
        Base.log(1, "add Room without image", true);
        base.roomsPage.addRoom("Without image", 0);

        Assert.assertTrue(base.roomsPage.isRoomPresens("Without image"));
        Base.log(1, "successfully", true);
    }

    @Test(priority = 2, enabled = true)
    public void Gallery_image() {
        Base.log(1, "add Room with image from popup gallery", true);
        base.roomsPage.addRoom("Gallery image", 2, 2);

        Assert.assertTrue(base.roomsPage.isRoomPresens("Gallery image"));
        Base.log(1, "successfully", true);
    }

    @Test(priority = 3, enabled = true)
    public void Camera_image() {
        Base.log(1, "add Room with image from camera", true);
        base.roomsPage.addRoom("Camera image", 1);

        Assert.assertTrue(base.roomsPage.isRoomPresens("Camera image"));
        Base.log(1, "successfully", true);
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }

}
