package authorization.login;

import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AuthorizationPage;
import pages.IntroPage;
import utils.Check;
import utils.Setup;

import java.util.Map;

/**
 * This covers test C29048
 *
 */

public class negativeLogin {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private Check check;
    private String expected;
    private String actual;
    private Map localizeKeys;
    private Setup s = new Setup();

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        s.log("Method is started");
        Setup setup = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        s.log("get Driver");
        driver = setup.getDriver();

        // Create objects of pages
        s.log("Create objects of pages");
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        check = new Check(driver);


//        localizeKeys = setup.getLocalizeKeys(locale_);

        s.log("Go to the authorization page");
        introPage.goToAuthorization();
//        authorizationPage.longTapLoginButton();
//        authorizationPage.serverDevelop.click();
    }

    @Test(enabled = true)
    public void Email_and_Password_is_empty() {
        s.log("Method is started");
        authorizationPage.loginToTheServer("", "", "Release");
        s.log(2, "wait snackBar");
        check.waitElement(authorizationPage.snackBar, 10, false);
        expected = localizeKeys.get("please_fill_in_all_of_the_required_fields").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
        s.log("Method is finished");
    }

    @Test(enabled = true)
    public void Email_is_empty() {
        s.log("Method is started");
        authorizationPage.loginToTheServer("", "qwe123", "Release");
        check.waitElement(authorizationPage.snackBar, 10, false);
        expected = localizeKeys.get("please_fill_in_all_of_the_required_fields").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
        s.log("Method is finished");
    }

    @Test(enabled = true)
    public void Password_is_empty() {
        s.log("Method is started");

        authorizationPage.loginToTheServer("ajax1@i.ua", "", "Release");
        check.waitElement(authorizationPage.snackBar, 10, false);

        expected = localizeKeys.get("please_fill_in_all_of_the_required_fields").toString();
        s.log("expected: " + expected);

        actual = authorizationPage.snackBar.getText();
        s.log("actual: " + actual);

        Assert.assertEquals(expected, actual);
        s.log("Method is finished");
    }

    @Test(enabled = true)
    public void Email_includes_spaces() {
        s.log("Method is started");
        authorizationPage.loginToTheServer("aj ax1@i.u a", "qwe123", "Release");
        check.waitElement(authorizationPage.snackBar, 60, false);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
        s.log("Method is finished");
    }

    @Test(enabled = true)
    public void Password_includes_spaces() {
        s.log("Method is started");
        authorizationPage.loginToTheServer("ajax1@i.ua", "q we1 23", "Release");
        check.waitElement(authorizationPage.snackBar, 60, false);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
        s.log("Method is finished");
    }

    @Test(enabled = true)
    public void Capital_letters_in_Password() {
        s.log("Method is started");
        authorizationPage.loginToTheServer("ajax1@i.ua", "QwE123", "Release");
        check.waitElement(authorizationPage.snackBar, 60, false);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
        s.log("Method is finished");
    }

    @Test(enabled = true)
    public void Dots_in_Email() {
        s.log("Method is started");
        authorizationPage.loginToTheServer("aj..ax1@i.u..a", "qwe123", "Release");
        check.waitElement(authorizationPage.snackBar, 60, false);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
        s.log("Method is finished");
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
