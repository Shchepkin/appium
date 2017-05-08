package Smoke;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Base;

/**
 * PRECONDITION
 * Account has no hub
 */
public class C46176_Add_new_Hub_manually {

    private String hubName, hubMasterKey;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        hubName = base.getCredsWithKey("hubName");
        hubMasterKey = base.getCredsWithKey("hubMasterKey");

        Base.log(1, "login to the server", true);
        base.loginPage.loginWithPinCancel();
    }

    @Test(priority = 1, enabled = true)
    public void Add_first_Hub() {
        Base.log(3, "START TEST");
        Assert.assertTrue(base.hub.add.manual.fromPlusButton());

    }

    @Test(priority = 2, enabled = false)
    public void Add_Hub_from_menu() {
        Base.log(3, "START TEST");
        Base.log(1, "delete hub");
        base.hub.deleteFrom.hubSettings(false);
        Assert.assertTrue(base.hub.add.manual.fromMenu());
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
