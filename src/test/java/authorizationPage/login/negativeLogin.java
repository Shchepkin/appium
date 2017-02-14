package authorizationPage.login;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.AuthorizationPage;
import pages.IntroPage;
import utils.AppiumSetup;
import utils.Check;
import utils.Setup;

import java.net.MalformedURLException;
import java.util.Map;

/**
 * This covers test C29048
 *
 */

public class negativeLogin {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private Check assertion;
    private String expected;
    private String actual;
    private Map localizeKeys;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        Reporter.log("Create setup", true);
        Setup setup = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        driver = setup.getDriver();

        // Create objects of pages
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);

        // Create assertion object
        assertion = new Check(driver);


//        localizeKeys = setup.getLocalizeKeys(locale_);

        // Go to the authorization page
        introPage.goToAuthorization();
//        authorizationPage.longTapLoginButton();
//        authorizationPage.serverDevelop.click();
    }

    @Test(enabled = true)
    public void Email_and_Password_is_empty() {
        authorizationPage.loginToTheServer("", "", "Develop");
        assertion.waitElement(authorizationPage.snackBar, 60);
        expected = localizeKeys.get("please_fill_in_all_of_the_required_fields").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @Test(enabled = true)
    public void Email_is_empty() {
        authorizationPage.loginToTheServer("", "qwe123", "Develop");
        assertion.waitElement(authorizationPage.snackBar, 60);
        expected = localizeKeys.get("please_fill_in_all_of_the_required_fields").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @Test(enabled = true)
    public void Password_is_empty() {
        authorizationPage.loginToTheServer("ajax1@i.ua", "", "Develop");
        assertion.waitElement(authorizationPage.snackBar, 60);
        expected = localizeKeys.get("please_fill_in_all_of_the_required_fields").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @Test(enabled = true)
    public void Email_includes_spaces() {
        authorizationPage.loginToTheServer("aj ax1@i.u a", "qwe123", "Develop");
        assertion.waitElement(authorizationPage.snackBar, 60);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @Test(enabled = true)
    public void Password_includes_spaces() {
        authorizationPage.loginToTheServer("ajax1@i.ua", "q we1 23", "Develop");
        assertion.waitElement(authorizationPage.snackBar, 60);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @Test(enabled = true)
    public void Capital_letters_in_Password() {
        authorizationPage.loginToTheServer("ajax1@i.ua", "QwE123", "Develop");
        assertion.waitElement(authorizationPage.snackBar, 60);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @Test(enabled = true)
    public void Dots_in_Email() {
        authorizationPage.loginToTheServer("aj..ax1@i.u..a", "qwe123", "Develop");
        assertion.waitElement(authorizationPage.snackBar, 60);
        expected = localizeKeys.get("Login_bad_credentials0").toString();
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
