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

    private String armedText, disarmedText, patrialArmedText, actual, expected;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        Base.log(1, "get localized keys");
        armedText = base.getLocalizeTextForKey("armed");
        disarmedText = base.getLocalizeTextForKey("disarmed");
        patrialArmedText = base.getLocalizeTextForKey("partially_armed");

        Base.log(1, "login without Pin");
        base.loginPage.loginWithPinCancel();

        Base.log(1, "go to the Remote Page");
        base.nav.gotoPage.Remote();
    }

    @Test(priority = 1, enabled = false)
    public void Click_Arm_Button() {

        Base.log(1, "make precondition disarmed state");
        base.remotePage.clickDisarmButton();
        Assert.assertTrue(base.wait.elementWithText(armedText, 10, true), "Text \"" + armedText + "\" is not found");

        Base.log(1, "click Arm Button and confirm if there is shown popUp");
        base.check.clickElementAndWaitingPopup(base.remotePage.getArmButton(), true);
        Assert.assertTrue(base.wait.elementWithText(armedText, 10, true), "Text \"" + armedText + "\" is not found");
    }

    @Test(priority = 2, enabled = false)
    public void Click_Partial_Arm_Button() {

        Base.log(1, "click Partial Arm Button and confirm if there is shown popUp");
        base.check.clickElementAndWaitingPopup(base.remotePage.getPartialArmButton(), true);
        Assert.assertTrue(base.wait.elementWithText(patrialArmedText, 10, true), "Text \"" + patrialArmedText + "\" is not found");
    }

    @Test(priority = 3, enabled = false)
    public void Click_Disarm_Button() {

        Base.log(1, "click Disarm Button");
        base.remotePage.clickDisarmButton();
        Assert.assertTrue(base.wait.elementWithText(disarmedText, 10, true), "Text \"" + disarmedText + "\" is not found");
    }

    @Test(priority = 3, enabled = true)
    public void Click_Alarm_Button() {

        Base.log(1, "open Notification page for erasing badge counter");
        base.nav.gotoPage.Notifications();

        Base.log(1, "open Remote page");
        base.nav.gotoPage.Remote();

        Base.log(1, "click Alarm Button");
        base.remotePage.clickAlarmButton();

        Base.log(1, "wait for badge appear");
        base.wait.element(base.notificationsPage.getBadgeOnNotificationTab(), 10, true);

        Base.log(1, "check number oin badge");
        expected = "1";
        actual = base.notificationsPage.getBadgeOnNotificationTab().getText();
        System.out.println("expected: " + expected);
        System.out.println("actual: " + actual);
        Assert.assertTrue(actual.equals(expected));

        Base.log(1, "click Alarm Button");
        base.remotePage.clickAlarmButton();

        Base.log(1, "wait for badge appear");
        base.wait.element(base.notificationsPage.getBadgeOnNotificationTab(), 10, true);

        Base.log(1, "check number oin badge");
        expected = "2";
        actual = base.notificationsPage.getBadgeOnNotificationTab().getText();
        System.out.println("expected: " + expected);
        System.out.println("actual: " + actual);
        Assert.assertTrue(actual.equals(expected));
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
