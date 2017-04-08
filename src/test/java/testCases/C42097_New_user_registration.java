package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;
import utils.Sql;

/**
 * PRECONDITION
 */

public class C42097_New_user_registration{

    private String userName, login, pass, phone, server;

    private Base $;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        $ = new Base(deviceName_);
        $.initPageObjects($.getDriver());

        login = $.getCredsWithKey("login");
        pass = $.getCredsWithKey("password");
        server = $.getCredsWithKey("server");
        phone = $.getCredsWithKey("phone");
        userName = $.getCredsWithKey("userName");

        $.sql.getDelete("Phone", "%" + phone + "%");
    }

    @Test(priority = 1, enabled = true)
    public void With_Validation() {
        Base.log("start from Intro Page and click Registration button");
        $.introPage.setServer(server);
        $.introPage.goToRegistration();

        Base.log("registration process");
        $.regPage.setUserPic(1);
        $.regPage.fillFields(userName, login, pass, phone);
        $.regPage.confirmAgrimentCheckBox();

        $.check.clickElementAndWaitingPopup($.regPage.getRegistrationButtonLink(), 5, 2, false);

        Base.log("waiting for Validation Code Page");
        Assert.assertTrue($.wait.element($.validationCodePage.getSmsCodeField(), 60, true));

        Base.log("get and fill Validation Codes");
        $.validationCodePage.getAndFillValidationCodes("Phone", "%" + phone + "%");
        $.nav.confirmIt();

        Base.log("waiting for Welcome Page with dashboard link");
        Assert.assertTrue($.wait.element($.regPage.getDashboardLink(), 30, true));

        Base.log("Welcome Page is shown, so go to the dashboard");
        $.check.clickElementAndWaitingPopup($.regPage.getDashboardLink(), 5, 3, false);

//        Base.log("waiting for Pincode PopUp and cancel it");
//        $.check.waitElementWithoutPin($.dashboardHeader.getMenuDrawer(), 30);
//
//        Assert.assertTrue($.wait.element($.dashboardHeader.getMenuDrawer(), 15, true), "Login failed!\n");
        Assert.assertTrue( $.check.waitElementWithoutPin($.dashboardHeader.getMenuDrawer(), 100), "Login failed!\n");
    }

    @AfterClass
    public void endSuit() {
        $.getDriver().quit();
    }
}
