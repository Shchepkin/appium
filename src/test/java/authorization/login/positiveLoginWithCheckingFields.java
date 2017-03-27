package authorization.login;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.*;
import utils.Check;
import utils.Navigation;
import utils.Setup;


public class positiveLoginWithCheckingFields {
    private AppiumDriver driver;
    private IntroPage introPage;
    private RegistrationPage registrationPage;
    private ValidationCodePage validationCodePage;
    private AuthorizationPage authorizationPage;
    private DashboardHeader dashboardHeader;
    private Check assertion;
    private Navigation nav;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        Reporter.log("Create setup", true);
        Setup setup = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        driver = setup.getDriver();

        // Create objects of pages
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        dashboardHeader = new DashboardHeader(driver);
        nav = new Navigation(driver);

        // Create assertion object
        assertion = new Check(driver);
    }

    @Test(priority = 1, enabled = false)
    public void Email_and_Pass_with_spaces() {
        Reporter.log("===== Start Email_and_Pass_with_spaces test", true);
        Reporter.log("> email:\"  ajax1@i.ua  \"", true);
        Reporter.log("> login:\"  qwe123  \"", true);

        Reporter.log("> Go to the authorization page", true);
        introPage.goToAuthorization();

        Reporter.log("> Login", true);
        authorizationPage.loginToTheServer("  ajax1@i.ua  ", "  qwe123  ", "Production");

        Reporter.log("> Check login", true);
        assertion.isElementDisplayed(dashboardHeader.menuDrawer, 15);
        Reporter.log("> Done", true);
    }

    @Test(priority = 2)
    public void Capital_letters_in_Email() {
        Reporter.log("===== Start Capital_letters_in_Email test", true);
        Reporter.log("> email:\"AjaX1@i.Ua\"", true);
        Reporter.log("> login:\"qwe123\"", true);

        Reporter.log("> Go to the authorization page", true);
        introPage.goToAuthorization();

        Reporter.log("> Login", true);
        authorizationPage.loginToTheServer("AjaX1@i.Ua", "qwe123", "Production");

        Reporter.log("> Check login", true);
        assertion.isElementDisplayed(dashboardHeader.menuDrawer, 15);
        Reporter.log("> Done", true);
    }

    @Test(priority = 3)
    public void Login_to_Account_without_accepting_validation_codes() {
        Reporter.log("\n===== Start Capital_letters_in_Email test", true);
        Reporter.log("> email:\"qweqweqweqweqwe@i.ua\"", true);
        Reporter.log("> login:\"qwe123\"", true);
        Reporter.log("> phone:\"683669948\"", true);

        Reporter.log("\n=== Start fake registration", true);
        registrationPage = new RegistrationPage(driver);
        validationCodePage = new ValidationCodePage(driver);
        String login = "qweqweqweqweqwe@i.ua";
        String pass = "qwe123";
        String phone = "683669947";
        String server = "Develop";

        Reporter.log("> call method for fake registration", true);
        registrationPage.fakeRegistration(login, pass, phone, server);

        Reporter.log("> wait for appearing Validation Code page", true);
        assertion.waitElement(validationCodePage.smsCode, 60, false);

        Reporter.log("> tap on Cancel button", true);
        validationCodePage.cancelBtn.click();

        Reporter.log("> wait for appearing Authorization page", true);
        assertion.waitElement(authorizationPage.forgotPasswordBtn, 60, false);
        String actual = authorizationPage.loginField.getText();
        String expected = login;

        Reporter.log("> check existing login in the Email field", true);
        Assert.assertTrue(actual.equals(expected));

        Reporter.log("> fill password to the Password field and tap authorization button", true);
        authorizationPage.passwordField.sendKeys(pass);
        nav.nextButtonClick();

        Reporter.log("> wait for appearing Dialog message", true);
        assertion.waitElement(authorizationPage.dialogMessage, 15, false);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        String actual = authorizationPage.loginField.getText();
//        String expected = login;
//        Assert.assertEquals(expected, actual);
    }


    @AfterMethod
    public void endSuit() {
        driver.quit();
    }
}
