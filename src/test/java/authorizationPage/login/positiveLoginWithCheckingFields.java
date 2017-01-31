package authorizationPage.login;

import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.AppiumSetup;
import utils.Check;

import java.net.MalformedURLException;

public class positiveLoginWithCheckingFields {
    private AndroidDriver driver;
    private IntroPage introPage;
    private RegistrationPage registrationPage;
    private ValidationCodePage validationCodePage;
    private AuthorizationPage authorizationPage;
    private DashboardHeader dashboardHeader;
    private MenuMainPage menuPage;
    private MenuAccountPage accountPage;
    private Check assertion;

    @Parameters({"deviceName_", "UDID_", "platformVersion_", "URL_"})
    @BeforeMethod
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        dashboardHeader = new DashboardHeader(driver);

        // Create assertion object
        assertion = new Check(driver);

        // Go to the authorization page
        introPage.goToAuthorization();
    }

    @Test()
    public void Email_and_Pass_with_spaces() {
        authorizationPage.loginToTheServer("  ajax1@i.ua  ", "  qwe123  ", "Production");
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
    }

    @Test()
    public void Capital_letters_in_Email() {
        authorizationPage.loginToTheServer("AjaX1@i.Ua", "qwe123", "Production");
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
    }

    @Test()
    public void Login_to_Account_without_accepting_validation_codes() {
        registrationPage = new RegistrationPage(driver);
        validationCodePage = new ValidationCodePage(driver);
        String login = "qweqweqweqweqwe@i.ua";
        String pass = "qwe123";
        String phone = "683669947";
        registrationPage.fakeRegistration(login, pass, phone);
        assertion.waitElement(validationCodePage.smsCode);
        validationCodePage.cancelBtn.click();
        assertion.waitElement(authorizationPage.forgotPasswordBtn);
        authorizationPage.passwordField.sendKeys(pass);

//        String actual = authorizationPage.loginField.getText();
//        String expected = login;
//        Assert.assertEquals(expected, actual);
    }


    @AfterMethod
    public void endSuit() {
        driver.quit();
    }
}
