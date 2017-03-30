package testCases;

import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import pages.*;

public class C42098_Login_to_the_existing_account extends Base{

    private String login, pass, server, expected, actual;

    public C42098_Login_to_the_existing_account(AppiumDriver driver, String locale_) {
        super(driver, locale_);

        log("TEST IS STARTED");
        login = "ajax1@i.ua";
        pass = "qwe";
        server = "Develop";

        log("start from IntroPage");
        introPage.goToAuthorization();
        authorizationPage.loginToTheServer(login, pass, server);

        log("waiting for Pincode PopUp");
        if(check.waitElement(popUp.cancelButton, 15, true)) {
            log("Pincode PopUp is shown with text: \"" + popUp.contentText.getText() + "\", so click CANCEL button");

            expected = getLocalizeTextForKey("do_you_want_to_enable_passcode");
            actual = popUp.contentText.getText();
            Assert.assertEquals(expected, actual, "Text on Pincode PopUp is wrong!");
            popUp.cancelButton.click();
        }

        log("check whether login was successfully");
        Assert.assertTrue(check.waitElement(dashboardHeader.menuDrawer, 60, true));
        log("TEST IS FINISHED");
    }




}
