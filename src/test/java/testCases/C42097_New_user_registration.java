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

    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        login = base.getCredsWithKey("login");
        pass = base.getCredsWithKey("password");
        server = base.getCredsWithKey("server");
        phone = base.getCredsWithKey("phone");
        userName = base.getCredsWithKey("userName");

        base.sql.getDelete("Phone", "%" + phone + "%");
    }

    @Test(priority = 1, enabled = true)
    public void With_Validation() {
        Base.log("start from Intro Page and click Registration button");
        base.introPage.setServer(server);
        base.introPage.goToRegistration();

        Base.log("registration process");
        base.regPage.setUserPic(1);
        base.regPage.fillFields(userName, login, pass, phone);
        base.regPage.confirmAgrimentCheckBox();

        base.check.clickElementAndWaitingPopup(base.regPage.getRegistrationButtonLink(), 5, 2, false);

        Base.log("waiting for Validation Code Page");
        Assert.assertTrue(base.wait.element(base.validationCodePage.getSmsCodeField(), 60, true));

        Base.log("get and fill Validation Codes");
        base.validationCodePage.getAndFillValidationCodes("Phone", "%" + phone + "%");
        base.nav.confirmIt();

        Base.log("waiting for Welcome Page with dashboard link");
        Assert.assertTrue(base.wait.element(base.regPage.getDashboardLink(), 30, true));

        Base.log("Welcome Page is shown, so go to the dashboard");
        base.check.clickElementAndWaitingPopup(base.regPage.getDashboardLink(), 5, 3, false);

//        Base.log("waiting for Pincode PopUp and cancel it");
//        base.check.waitElementWithoutPin(base.dashboardHeader.getMenuDrawer(), 30);
//
//        Assert.assertTrue(base.wait.element(base.dashboardHeader.getMenuDrawer(), 15, true), "Login failed!\n");
        Assert.assertTrue( base.check.waitElementWithoutPin(base.dashboardHeader.getMenuDrawer(), 100), "Login failed!\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
