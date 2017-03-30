package testCases;

import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import pages.Base;

/**
 * Created by installer on 3/28/17.
 */
public class C42176_Virtual_Space_Control extends Base {

    private String login, pass, server, expected, actual;

    public C42176_Virtual_Space_Control(AppiumDriver driver, String locale_) {
        super(driver, locale_);

        log("TEST IS STARTED");
        pass = "qwe";
        login = "ajax1@i.ua";
        server = "Develop";
        String armedText = getLocalizeTextForKey("armed");
        String disarmedText = getLocalizeTextForKey("disarmed");
        String patrialArmedText = getLocalizeTextForKey("partially_armed");

        log("start from IntroPage");
        introPage.goToAuthorization();
        authorizationPage.loginToTheServer(login, pass, server);

        log("waiting for Pincode PopUp");
        check.waitElementWithoutPin(dashboardHeader.menuDrawer, 3);

        log("go to the Remote Page");
        remotePage.goToTheRemotePage();

        log("click Disarm Button");
        remotePage.disarmBtn.click();
        Assert.assertTrue(wait.elementWithText(disarmedText, 10, true), "Text \"" + disarmedText + "\" is not found");

        log("click Arm Button and confirm if there is shown popUp");
        check.clickElementAndWaitingPopup(remotePage.armBtn, true);
        Assert.assertTrue(wait.elementWithText(armedText, 10, true), "Text \"" + armedText + "\" is not found");

        log("click Partial Arm Button and confirm if there is shown popUp");
        check.clickElementAndWaitingPopup(remotePage.partialArmBtn, true);
        Assert.assertTrue(wait.elementWithText(patrialArmedText, 10, true), "Text \"" + patrialArmedText + "\" is not found");

        log("click Disarm Button");
        remotePage.disarmBtn.click();
        Assert.assertTrue(wait.elementWithText(disarmedText, 10, true), "Text \"" + disarmedText + "\" is not found");

        log("TEST IS FINISHED");
    }

}
