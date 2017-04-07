package testCases;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.Base;

public class C42098_Login_to_the_existing_account{

    private String login, pass, server, expectedText, actual;
    private Base $;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(){
        $ = new Base();
        $.initPageObjects($.getDriver());
    }

    @Test(priority = 1, enabled = true)
    public void Positive_test_with_valid_data() {
        Base.log("TEST IS STARTED");

        Base.log("get credentials for login");
        login = $.creds.get("login").toString();
        pass = $.creds.get("password").toString();
        server = $.creds.get("server").toString();

        Base.log("start from IntroPage");
        $.introPage.goToAuthorization();
        $.loginPage.loginToTheServer(login, pass, server);

        $.wait.invisibilityOfLoaderLogo(true);
        Assert.assertFalse($.check.forSnackBarIsPresent(3), "SnackBar is shown");

        Base.log("waiting for Pincode PopUp");
        if($.wait.element($.popUp.loadingWindow, 90, true)) {
            Base.log("Check localized text");
            expectedText = $.getLocalizeTextForKey("do_you_want_to_enable_passcode");
            actual = $.popUp.getContentText();

            System.out.println("expected: \"" + expectedText + "\"");
            System.out.println("actual: \"" + actual + "\"");

            Assert.assertEquals(expectedText, actual, "localization of Text on Pincode PopUp is wrong!");
            Base.log("localized text is OK");
            $.nav.cancelIt();
        }

        Assert.assertTrue($.wait.element($.dashboardHeader.getMenuDrawer(), 5, true), "Menu Icon is not shown");

        Base.log("TEST IS FINISHED");
    }

    @AfterMethod
    public void endSuit() {
        $.getDriver().quit();
    }

}
