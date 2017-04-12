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
 * - at least one room
 */
public class C42176_Virtual_Space_Control{

    private String armedText, disarmedText, patrialArmedText;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        Base.log("get localized keys");
        armedText = base.getLocalizeTextForKey("armed");
        disarmedText = base.getLocalizeTextForKey("disarmed");
        patrialArmedText = base.getLocalizeTextForKey("partially_armed");

        Base.log("login without Pin");
        base.loginPage.loginWithPinCancel();

        Base.log("go to the Remote Page");
        base.dashboard.goToTheRemotePage();
    }

    @Test(priority = 1, enabled = true)
    public void Click_Arm_Button() {

        Base.log("make precondition disarmed state");
        base.remotePage.clickDisarmButton();
        Assert.assertTrue(base.wait.elementWithText(armedText, 10, true), "Text \"" + armedText + "\" is not found");

        Base.log("click Arm Button and confirm if there is shown popUp");
        base.check.clickElementAndWaitingPopup(base.remotePage.getArmButton(), true);
        Assert.assertTrue(base.wait.elementWithText(armedText, 10, true), "Text \"" + armedText + "\" is not found");
    }

    @Test(priority = 2, enabled = true)
    public void Click_Partial_Arm_Button() {

        Base.log("click Partial Arm Button and confirm if there is shown popUp");
        base.check.clickElementAndWaitingPopup(base.remotePage.getPartialArmButton(), true);
        Assert.assertTrue(base.wait.elementWithText(patrialArmedText, 10, true), "Text \"" + patrialArmedText + "\" is not found");
    }

    @Test(priority = 3, enabled = true)
    public void Click_Disarm_Button() {

        Base.log("click Disarm Button");
        base.remotePage.clickDisarmButton();
        Assert.assertTrue(base.wait.elementWithText(disarmedText, 10, true), "Text \"" + disarmedText + "\" is not found");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
