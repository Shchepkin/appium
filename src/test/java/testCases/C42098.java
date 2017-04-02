package testCases;

import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;
import utils.Setup;

public class C42098 extends Base{

    private String login, pass, server, expected, actual;
    private AppiumDriver driver;
    private Setup s;
    public String locale;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeMethod
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        log("setup");
        s = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_);
        driver = s.getDriver();
        this.locale = locale_;
        System.out.println(" setup locale: \"" + locale + "\"");
    }

    @Test(priority = 1, enabled = false)
    public C42098() {

        log("TEST IS STARTED");
        login = "ajax1@i.ua";
        pass = "qwe123";
        server = "Develop";

        log("start from IntroPage");
        introPage.goToAuthorization();
        loginPage.loginToTheServer(login, pass, server);

        log("waiting for Pincode PopUp");
        if(check.waitElement(popUp.cancelButton, 15, true)) {
            log("Pincode PopUp is shown with text: \"" + popUp.contentText.getText() + "\", so click CANCEL button");

            log("Check localized text");
            expected = getLocalizeTextForKey("do_you_want_to_enable_passcode");
            actual = popUp.contentText.getText();

            System.out.println("expected: \"" + expected + "\"");
            System.out.println("actual: \"" + actual + "\"");

            Assert.assertEquals(expected, actual, "Text on Pincode PopUp is wrong!");
            popUp.cancelButton.click();
        }

        log("check whether login was successfully");
        Assert.assertTrue(check.waitElement(dashboardHeader.menuDrawer, 60, true));
        log("TEST IS FINISHED");
    }

    @Test(priority = 1, enabled = false)
    public void C2() {

        log("TEST IS FINISHED");
    }

    @Test(priority = 1, enabled = false)
    public void C3() {

        log("TEST IS FINISHED");
    }

    @Test(priority = 1, enabled = false)
    public void C4() {

        log("TEST IS FINISHED");
    }

    @AfterMethod
    public void endSuit() {
        driver.quit();
    }

}
