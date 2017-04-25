package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;

/**
 * PRECONDITION
 * Account has no hub
 */
public class C42099_Add_new_Hub_manually{

    private String hubName, hubMasterKey;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        hubName = base.getCredsWithKey("hubName");
        hubMasterKey = base.getCredsWithKey("hubMasterKey");

        base.loginPage.loginWithPinCancel();
    }

    @Test(priority = 1, enabled = true)
    public void Add_first_Hub() {

        Base.log(1, "tap to the Plus Button", true);
        base.dashboard.plusButtonClick();

        Base.log(1, "choose manual Hub adding", true);
        base.nav.nextButtonClick();

        base.dashboard.fillFieldsWith(hubName, hubMasterKey);

        Base.log(1, "confirm it", true);
        base.nav.confirmIt();

        base.wait.invisibilityOfWaiter(true);

        Assert.assertTrue(base.wait.element(base.dashboardHeader.getGprsImage(), 15, true));
        Base.log(1, "hub successfully added!", true);
    }

    @Test(priority = 2, enabled = false)
    public void Add_Hub_from_menu() {
        Base.log(1, "delete hub");
        base.hub.deleteFrom.hubSettings(false);
        base.hub.addNewManual();
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
