package tmp;

import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.Base;

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
        Base.log(1, "TEST IS STARTED");

        Base.log(1, "get credentials for login");
        login = base.getCredsWithKey("login");
        pass = base.getCredsWithKey("password");
        server = base.getCredsWithKey("server");

        Base.log(1, "start from IntroPage");
        base.nav.gotoPage.authorization();
        base.loginPage.loginToTheServer(login, pass, server);

        base.wait.invisibilityOfLoaderLogo(true);
        Assert.assertFalse(base.check.isPresent.snackBar(3), "SnackBar is shown");

        Base.log(1, "waiting for Pincode PopUp");
        if(base.wait.element(base.popUp.getLoadingWindow(), 90, true)) {
            Base.log(1, "Check localized text");
            expectedText = base.getLocalizeTextForKey("do_you_want_to_enable_passcode");
            actual = base.popUp.getContentText();

            System.out.println("expected: \"" + expectedText + "\"");
            System.out.println("actual: \"" + actual + "\"");

            Assert.assertEquals(expectedText, actual, "localization of Text on Pincode PopUp is wrong!");
            Base.log(1, "localized text is OK");
            base.nav.cancelIt();
        }

        Assert.assertTrue(base.wait.element(base.header.getMenuDrawer(), 5, true), "Menu Icon is not shown");

        Base.log(1, "TEST IS FINISHED");
    }

    @AfterMethod
    public void endSuit() {
        base.getDriver().quit();
    }

}
