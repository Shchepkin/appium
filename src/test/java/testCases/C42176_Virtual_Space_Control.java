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
public class C42176_Virtual_Space_Control{

    private String login, pass, server, armedText, disarmedText, patrialArmedText;
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

        $.log("get localized keys");
        armedText = $.getLocalizeTextForKey("armed");
        disarmedText = $.getLocalizeTextForKey("disarmed");
        patrialArmedText = $.getLocalizeTextForKey("partially_armed");

        $.log("login without Pin");
        $.loginPage.loginWithPinCancel(login, pass, server);

        $.log("go to the Remote Page");
        $.dashboard.goToTheRemotePage();
    }

    @Test(priority = 1, enabled = true)
    public void Click_Arm_Button() {

        $.log("make precondition disarmed state");
        $.remotePage.clickDisarmButton();
        Assert.assertTrue($.wait.elementWithText(armedText, 10, true), "Text \"" + armedText + "\" is not found");

        $.log("click Arm Button and confirm if there is shown popUp");
        $.check.clickElementAndWaitingPopup($.remotePage.getArmButton(), true);
        Assert.assertTrue($.wait.elementWithText(armedText, 10, true), "Text \"" + armedText + "\" is not found");
    }

    @Test(priority = 2, enabled = true)
    public void Click_Partial_Arm_Button() {

        $.log("click Partial Arm Button and confirm if there is shown popUp");
        $.check.clickElementAndWaitingPopup($.remotePage.getPartialArmButton(), true);
        Assert.assertTrue($.wait.elementWithText(patrialArmedText, 10, true), "Text \"" + patrialArmedText + "\" is not found");
    }

    @Test(priority = 3, enabled = true)
    public void Click_Disarm_Button() {

        $.log("click Disarm Button");
        $.remotePage.clickDisarmButton();
        Assert.assertTrue($.wait.elementWithText(disarmedText, 10, true), "Text \"" + disarmedText + "\" is not found");
    }

    @AfterClass
    public void endSuit() {
        $.getDriver().quit();
    }
}
