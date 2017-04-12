package testCases;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.Base;

/**
 * PRECONDITION
 * - account exist and validated
 * -
 */

public class C42098_Login_to_the_existing_account{

    private String login, pass, server, expectedText, actual;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
    }

    @Test(priority = 1, enabled = true)
    public void Positive_test_with_valid_data() {
        Base.log("TEST IS STARTED");

        Base.log("get credentials for login");
        login = base.getCredsWithKey("login");
        pass = base.getCredsWithKey("password");
        server = base.getCredsWithKey("server");

        Base.log("start from IntroPage");
        base.introPage.goToAuthorization();
        base.loginPage.loginToTheServer(login, pass, server);

        base.wait.invisibilityOfLoaderLogo(true);
        Assert.assertFalse(base.check.forSnackBarIsPresent(3), "SnackBar is shown");

        Base.log("waiting for Pincode PopUp");
        if(base.wait.element(base.popUp.loadingWindow, 90, true)) {
            Base.log("Check localized text");
            expectedText = base.getLocalizeTextForKey("do_you_want_to_enable_passcode");
            actual = base.popUp.getContentText();

            System.out.println("expected: \"" + expectedText + "\"");
            System.out.println("actual: \"" + actual + "\"");

            Assert.assertEquals(expectedText, actual, "localization of Text on Pincode PopUp is wrong!");
            Base.log("localized text is OK");
            base.nav.cancelIt();
        }

        Assert.assertTrue(base.wait.element(base.dashboardHeader.getMenuDrawer(), 5, true), "Menu Icon is not shown");

        Base.log("TEST IS FINISHED");
    }

    @AfterMethod
    public void endSuit() {
        base.getDriver().quit();
    }

}
