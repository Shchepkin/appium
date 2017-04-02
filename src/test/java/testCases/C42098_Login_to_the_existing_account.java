package testCases;

import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.Base;

public class C42098_Login_to_the_existing_account extends Base{

    private String login, pass, server, expectedText, actual;
    private Base base;

    @BeforeMethod
    public void driverInit(){
        base = new Base(getDriver());
    }

    @Test(priority = 1, enabled = true)
    public void Positive_test_with_valid_data() {
        log("TEST IS STARTED");

        login = "ajax1@i.ua";
        pass = "qwe123";
        server = "Develop";

        log("start from IntroPage");
        base.introPage.goToAuthorization();
        base.loginPage.loginToTheServer(login, pass, server);

        log("waiting for Pincode PopUp");
        if(base.wait.element(base.popUp.loadingWindow, 90, true)) {
            log("Check localized text");
            expectedText = getLocalizeTextForKey("do_you_want_to_enable_passcode");
            actual = base.popUp.getContentText();

            System.out.println("expected: \"" + expectedText + "\"");
            System.out.println("actual: \"" + actual + "\"");

            Assert.assertEquals(expectedText, actual, "localization of Text on Pincode PopUp is wrong!");
            log("localized text is OK");
            base.nav.cancelIt();
        }
        log("TEST IS FINISHED");
    }

    @AfterMethod
    public void endSuit() {
        driver.quit();
    }

}
