package testCases;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.Base;

public class C42098_Login_to_the_existing_account extends Base{

    private String login, pass, server, expectedText, actual;
    private Base $;

    @BeforeMethod
    public void driverInit(){
        $ = new Base(getDriver());
    }

    @Test(priority = 1, enabled = true)
    public void Positive_test_with_valid_data() {
        log("TEST IS STARTED");

        login = "ajax1@i.ua";
        pass = "qwe123";
        server = "Develop";

        log("start from IntroPage");
        $.introPage.goToAuthorization();
        $.loginPage.loginToTheServer(login, pass, server);

        $.wait.invisibilityOfLoaderLogo(true);
        Assert.assertFalse($.check.forSnackBarIsPresent(3), "SnackBar is shown");

        log("waiting for Pincode PopUp");
        if($.wait.element($.popUp.loadingWindow, 90, true)) {
            log("Check localized text");
            expectedText = getLocalizeTextForKey("do_you_want_to_enable_passcode");
            actual = $.popUp.getContentText();

            System.out.println("expected: \"" + expectedText + "\"");
            System.out.println("actual: \"" + actual + "\"");

            Assert.assertEquals(expectedText, actual, "localization of Text on Pincode PopUp is wrong!");
            log("localized text is OK");
            $.nav.cancelIt();
        }

        Assert.assertTrue($.wait.element($.dashboardHeader.getMenuDrawer(), 5, true), "Menu Icon is not shown");

        log("TEST IS FINISHED");
    }

    @AfterMethod
    public void endSuit() {
        driver.quit();
    }

}
