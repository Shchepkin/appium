package Smoke;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Base;

/**
 * PRECONDITION
 * - one hub
 * - at least three rooms with different names
 */
public class C46183_Room_delete {
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        Base.log(1, "login without Pin");
        base.loginPage.loginWithPinCancel();

        base.nav.gotoPage.Rooms();
    }



    @Test(priority = 1, enabled = true)
    public void One_With_Check_Localise_Text() {
        Base.log(1, "delete one room", true);
        base.nav.gotoPage.Rooms();
        Assert.assertTrue(base.roomsPage.delete.one(false));
    }

    @Test(priority = 2, enabled = true)
    public void While_Hub_Armed() {
        Base.log(1, "try to delete existing room while Hub armed", true);
        base.hub.security.arm();
        base.nav.gotoPage.Rooms();
        Assert.assertFalse(base.check.isPresent.element(base.nav.getSettingsButton(), 5), "settings button present while hub is armed");
        Base.log(1, "Settings button is not shown while Hub armed", true);
    }

    @Test(priority = 3, enabled = true)
    public void While_Hub_Partial_Armed() {
        Base.log(1, "try to delete existing room while Hub partial armed", true);
        base.hub.security.partialArm();
        base.nav.gotoPage.Rooms();
        Assert.assertFalse(base.check.isPresent.element(base.nav.getSettingsButton(), 5), "settings button present while hub is armed");
        Base.log(1, "Settings button is not shown while Hub armed", true);
    }

    @Test(priority = 4, enabled = true)
    public void All_Rooms_Deleting() {
        Base.log(1, "disarm hub");
        base.hub.security.disarm();

        Base.log(1, "delete all existing rooms", true);
        base.nav.gotoPage.Rooms();
        Assert.assertTrue(base.roomsPage.delete.all(), "deleting process failed\n");
        Assert.assertTrue(base.check.isEmpty.roomsList(), "description is not shown after deleting all rooms\n");
        Base.log(1, "description with text \"" + base.roomsPage.getDescriptionText() + "\" is successfully shown after deleting all rooms", true);
    }


    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
