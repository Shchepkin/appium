package testCases;

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
 * - at least one room
 * - hub disarmed
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

    }

    @Test(priority = 1, enabled = true)
    public void Click_Arm_Button() {
        Base.log(1, "click Arm Button and confirm if there is shown popUp");
        base.hub.security.arm();
        Assert.assertTrue(base.wait.elementWithText(armedText, 10, true), "Text \"" + armedText + "\" is not found\n");
        System.out.println("text \"" + armedText + "\" shown successfully");
    }

    @Test(priority = 2, enabled = true)
    public void Click_Partial_Arm_Button() {
        Base.log(1, "click Partial Arm Button and confirm if there is shown popUp");
        base.hub.security.partialArm();
        Assert.assertTrue(base.wait.elementWithText(patrialArmedText, 10, true), "Text \"" + patrialArmedText + "\" is not found\n");
        System.out.println("text \"" + patrialArmedText + "\" shown successfully");
    }

    @Test(priority = 3, enabled = true)
    public void Click_Disarm_Button() {
        Base.log(1, "Disarm hub");
        base.hub.security.disarm();
        Assert.assertTrue(base.wait.elementWithText(disarmedText, 10, true), "Text \"" + disarmedText + "\" is not found\n");
        System.out.println("text \"" + disarmedText + "\" shown successfully");
    }

    @Test(priority = 4, enabled = true)
    public void Click_Alarm_Button() {

        Base.log(1, "open Notification page for erasing badge counter");
        base.nav.gotoPage.Notifications();
        base.hub.security.alarm();

        Base.log(1, "wait for badge appear");
        base.wait.element(base.notificationsPage.getBadgeOnNotificationTab(), 10, true);

        Base.log(1, "check number on badge");
        expected = "1";
        actual = base.notificationsPage.getBadgeOnNotificationTab().getText();

        System.out.println("Check badge after first alarm");
        System.out.println("expected: " + expected);
        System.out.println("actual: " + actual);

        Base.log(1, "\nexpected: " + expected + "\nactual: " + actual);
        Assert.assertTrue(actual.equals(expected));
        Base.log(1, "successfully");

        base.hub.security.alarm();

        Base.log(1, "wait for badge appear");
        base.wait.element(base.notificationsPage.getBadgeOnNotificationTab(), 10, true);

        Base.log(1, "check number on badge");
        expected = "2";
        actual = base.notificationsPage.getBadgeOnNotificationTab().getText();

        System.out.println("Check badge second first alarm");
        System.out.println("expected: " + expected);
        System.out.println("actual: " + actual);

        Base.log(1, "\nexpected: " + expected + "\nactual: " + actual);
        Assert.assertTrue(actual.equals(expected));
        Base.log(1, "successfully");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
