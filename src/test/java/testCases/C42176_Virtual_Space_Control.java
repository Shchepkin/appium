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
    private Base $;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        $ = new Base(deviceName_);
        $.initPageObjects($.getDriver());

        Base.log("get localized keys");
        armedText = $.getLocalizeTextForKey("armed");
        disarmedText = $.getLocalizeTextForKey("disarmed");
        patrialArmedText = $.getLocalizeTextForKey("partially_armed");

        Base.log("login without Pin");
        $.loginPage.loginWithPinCancel();

        Base.log("go to the Remote Page");
        $.dashboard.goToTheRemotePage();
    }

    @Test(priority = 1, enabled = true)
    public void Click_Arm_Button() {

        Base.log("make precondition disarmed state");
        $.remotePage.clickDisarmButton();
        Assert.assertTrue($.wait.elementWithText(armedText, 10, true), "Text \"" + armedText + "\" is not found");

        Base.log("click Arm Button and confirm if there is shown popUp");
        $.check.clickElementAndWaitingPopup($.remotePage.getArmButton(), true);
        Assert.assertTrue($.wait.elementWithText(armedText, 10, true), "Text \"" + armedText + "\" is not found");
    }

    @Test(priority = 2, enabled = true)
    public void Click_Partial_Arm_Button() {

        Base.log("click Partial Arm Button and confirm if there is shown popUp");
        $.check.clickElementAndWaitingPopup($.remotePage.getPartialArmButton(), true);
        Assert.assertTrue($.wait.elementWithText(patrialArmedText, 10, true), "Text \"" + patrialArmedText + "\" is not found");
    }

    @Test(priority = 3, enabled = true)
    public void Click_Disarm_Button() {

        Base.log("click Disarm Button");
        $.remotePage.clickDisarmButton();
        Assert.assertTrue($.wait.elementWithText(disarmedText, 10, true), "Text \"" + disarmedText + "\" is not found");
    }

    @AfterClass
    public void endSuit() {
        $.getDriver().quit();
    }
}
