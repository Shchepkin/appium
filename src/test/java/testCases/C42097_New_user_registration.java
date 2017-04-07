package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;
import utils.Sql;

public class C42097_New_user_registration extends Base {

    private String userName, login, pass, phone, server;

    private Base $;
    private Sql sql;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(){
        $ = new Base(getDriver());
        sql = new Sql();

        login = creds.get("login").toString();
        pass = creds.get("password").toString();
        server = creds.get("server").toString();
        phone = creds.get("phone").toString();
        userName = creds.get("userName").toString();

        sql.getDelete("Phone", "%" + phone + "%");


//        login = "ajaxsys33@bigmir.net";
//        phone = "971112234";
//        pass = "qwe";
//        userName = "name";
//        server = "Develop";


    }

    @Test(priority = 1, enabled = true)
    public void With_Validation() {
        log("start from Intro Page and click Registration button");
        $.introPage.setServer(server);
        $.introPage.goToRegistration();

        log("registration process");
        $.regPage.setUserPic(1);
        $.regPage.fillFields(userName, login, pass, phone);
        $.regPage.confirmAgrimentCheckBox();

        $.check.clickElementAndWaitingPopup($.regPage.getRegistrationButtonLink(), 5, 2, false);

        log("waiting for Validation Code Page");
        Assert.assertTrue($.wait.element($.validationCodePage.getSmsCodeField(), 60, true));

        log("get and fill Validation Codes");
        $.validationCodePage.getAndFillValidationCodes("Phone", "%" + phone + "%");
        $.nav.confirmIt();

        log("waiting for Welcome Page with dashboard link");
        Assert.assertTrue($.wait.element($.regPage.getDashboardLink(), 30, true));

        log("Welcome Page is shown, so go to the dashboard");
        $.check.clickElementAndWaitingPopup($.regPage.getDashboardLink(), 5, 3, false);

//        log("waiting for Pincode PopUp and cancel it");
//        $.check.waitElementWithoutPin($.dashboardHeader.getMenuDrawer(), 30);
//
//        Assert.assertTrue($.wait.element($.dashboardHeader.getMenuDrawer(), 15, true), "Login failed!\n");
        Assert.assertTrue( $.check.waitElementWithoutPin($.dashboardHeader.getMenuDrawer(), 30), "Login failed!\n");
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
