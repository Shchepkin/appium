package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.*;

/**
 * PRECONDITION
 * - gallery on device must have at least 3 pics
 */

public class C42097_New_user_registration{

    private String userName, login, pass, phone, server;

    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        Base.log(3, "\nSTART TEST\n");
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        login = base.getCredsWithKey("login");
        pass = base.getCredsWithKey("password");
        server = base.getCredsWithKey("server");
        phone = base.getCredsWithKey("phone");
        userName = base.getCredsWithKey("userName");

        Base.log(1, "delete user if phone already exist at the server");
        base.sql.getDelete("Phone", "%" + phone + "%");
    }

    @Test(priority = 1, enabled = true)
    public void With_Validation() {
        Base.log(1, "start from Intro Page and click Registration button", true);
        base.introPage.setServer(server);
        base.introPage.goToRegistration();

        Base.log(1, "registration process", true);
        base.regPage.setUserPic(1);
        base.regPage.fillFields(userName, login, pass, phone);
        base.regPage.confirmAgreementCheckBox();

        base.regPage.registrationButtonClick();
        base.wait.invisibilityOfWaiter();

        Base.log(1, "check is SnackBar with error message present on page", true);
        Assert.assertFalse(base.wait.visibilityOfSnackBar(5, true), "SnackBar is shown with error text");
        Assert.assertFalse(base.check.isPresent.snackBar(5), "SnackBar is shown with error text");

        Base.log(1, "waiting for Validation Code Page");
        base.wait.element(base.validationCodePage.getSmsCodeField(), 60, true);

        Base.log(1, "get and fill Validation Codes", true);
        base.validationCodePage.getAndFillValidationCodes("Phone", "%" + phone + "%");
        base.nav.confirmIt();

        Base.log(1, "waiting for Welcome Page with dashboard link");
        base.wait.element(base.regPage.getDashboardLink(), 30, true);

        Base.log(1, "Welcome Page is shown, so go to the dashboard", true);
        base.regPage.dashboardLinkClick();
        Assert.assertTrue(base.wait.menuIconOrPinPopUp(100, true));
        Base.log(1, "Registration successfully", true);
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
