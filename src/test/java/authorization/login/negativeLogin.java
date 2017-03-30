package authorization.login;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AuthorizationPage;
import pages.Base;
import pages.IntroPage;
import utils.Check;
import utils.PopUp;
import utils.Setup;

import java.util.Map;

/**
 * This covers test C29048
 *
 */

public class negativeLogin extends Base{
    private AppiumDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private Check check;
    private String expected;
    private String actual;
    private PopUp popUp;
    private Map localizeKeys;
    private Setup s = new Setup();

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        log("Method is started");
        Setup setup = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        log("get Driver");
        driver = setup.getDriver();

        // Create objects of pages
        log("Create objects of pages");
        popUp = new PopUp(driver);
        check = new Check(driver);
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);


//        localizeKeys = setup.getLocalizeKeys(locale_);

        log("Go to the authorization page");
        introPage.goToAuthorization();
//        authorizationPage.longTapLoginButton();
//        authorizationPage.serverDevelop.click();
    }

    @Test(enabled = true)
    public void Email_and_Password_is_empty() {
        log("Method is started");
        authorizationPage.loginToTheServer("", "", "Release");
        log(2, "wait snackBar");
        check.waitElement(popUp.snackBar, 10, false);
        expected = localizeKeys.get("please_fill_in_all_of_the_required_fields").toString();
        actual = popUp.snackBar.getText();
        Assert.assertEquals(expected, actual);
        log("Method is finished");
    }

    @Test(enabled = true)
    public void Email_is_empty() {
        log("Method is started");
        authorizationPage.loginToTheServer("", "qwe123", "Release");
        check.waitElement(popUp.snackBar, 10, false);
        expected = localizeKeys.get("please_fill_in_all_of_the_required_fields").toString();
        actual = popUp.snackBar.getText();
        Assert.assertEquals(expected, actual);
        log("Method is finished");
    }

    @Test(enabled = true)
    public void Password_is_empty() {
        log("Method is started");

        authorizationPage.loginToTheServer("ajax1@i.ua", "", "Release");
        check.waitElement(popUp.snackBar, 10, false);

        expected = localizeKeys.get("please_fill_in_all_of_the_required_fields").toString();
        log("expected: " + expected);

        actual = popUp.snackBar.getText();
        log("actual: " + actual);

        Assert.assertEquals(expected, actual);
        log("Method is finished");
    }

    @Test(enabled = true)
    public void Email_includes_spaces() {
        log("Method is started");
        authorizationPage.loginToTheServer("aj ax1@i.u a", "qwe123", "Release");
        check.waitElement(popUp.snackBar, 60, false);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = popUp.snackBar.getText();
        Assert.assertEquals(expected, actual);
        log("Method is finished");
    }

    @Test(enabled = true)
    public void Password_includes_spaces() {
        log("Method is started");
        authorizationPage.loginToTheServer("ajax1@i.ua", "q we1 23", "Release");
        check.waitElement(popUp.snackBar, 60, false);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = popUp.snackBar.getText();
        Assert.assertEquals(expected, actual);
        log("Method is finished");
    }

    @Test(enabled = true)
    public void Capital_letters_in_Password() {
        log("Method is started");
        authorizationPage.loginToTheServer("ajax1@i.ua", "QwE123", "Release");
        check.waitElement(popUp.snackBar, 60, false);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = popUp.snackBar.getText();
        Assert.assertEquals(expected, actual);
        log("Method is finished");
    }

    @Test(enabled = true)
    public void Dots_in_Email() {
        log("Method is started");
        authorizationPage.loginToTheServer("aj..ax1@i.u..a", "qwe123", "Release");
        check.waitElement(popUp.snackBar, 60, false);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = popUp.snackBar.getText();
        Assert.assertEquals(expected, actual);
        log("Method is finished");
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
