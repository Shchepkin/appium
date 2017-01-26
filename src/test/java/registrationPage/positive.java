package registrationPage;

import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;
import utils.AppiumSetup;
import utils.Check;

import java.net.MalformedURLException;

public class positive {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private RegistrationPage registrationPage;
    private DashboardHeader dashboardHeader;
    private MenuMainPage menuPage;
    private MenuAccountPage accountPage;
    private Check assertion;
    private AddImagePage addImagePage;
    private ValidationCodePage validationCodePage;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        dashboardHeader = new DashboardHeader(driver);
        menuPage = new MenuMainPage(driver);
        accountPage = new MenuAccountPage(driver);
        registrationPage = new RegistrationPage(driver);
        addImagePage = new AddImagePage(driver);
        validationCodePage = new ValidationCodePage(driver);

        // Create assertion object
        assertion = new Check(driver);

    }

    @Test()
    public void Registration_with_correct_data_to_the_Develop_server() {

        System.out.print("Precondition:\n1. Go to the authorization page and set server type to Develop ... ");
        introPage.goToAuthorization();
        authorizationPage.longTapLoginButton();
        authorizationPage.serverDevelop.click();
        System.out.println("Done");

        System.out.print("2. Comeback to the Registration Page ... ");
        authorizationPage.backBtn.click();
        introPage.registrationBtn.click();
        System.out.println("Done");

        System.out.print("\nSteps:\n1. Set UserPic icon ... ");
        registrationPage.userPic.click();
        addImagePage.thumbnail.get(3).click();
        addImagePage.saveBtn.click();
        System.out.println("Done");


        System.out.print("2. Fill the Name field ... ");
        registrationPage.nameField.sendKeys("Test name");
        System.out.println("Done");

        System.out.print("3. Fill the e-mail field with correct data and confirm it ... ");
        registrationPage.emailField.sendKeys("develop.ajax.sys@i.ua");
        registrationPage.emailConfirmField.sendKeys("develop.ajax.sys@i.ua");
        System.out.println("Done");

        System.out.print("4. Fill the phone number field with correct data ... ");
        registrationPage.phoneField.sendKeys("979901726");
        System.out.println("Done");



        System.out.println("5. Fill the password field with correct data and confirm it ... ");
        registrationPage.swipeUp();
        registrationPage.passwordField.sendKeys("qwe123");
        registrationPage.swipeUp();
        registrationPage.passwordConfirmField.sendKeys("qwe123");
        System.out.println("Done");

        System.out.print("6. Tap the registration button ... ");
        registrationPage.registrationBtn.click();
        System.out.println("Done");

        // Assert is Validation Code page opened
        assertion.checkIsDisplayed(validationCodePage.smsCode);

        // check auto Loading Code From Sms
        validationCodePage.autoLoadCode(validationCodePage.smsCode, 30);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void logOut() {
        dashboardHeader.menuDrawer.click();
        menuPage.accountBtn.click();
        accountPage.logoutBtn.click();
        assertion.checkIsDisplayed(authorizationPage.loginBtn);
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
