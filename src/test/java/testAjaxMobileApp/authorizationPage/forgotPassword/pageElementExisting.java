package testAjaxMobileApp.authorizationPage.forgotPassword;

import ajaxMobileApp.*;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class pageElementExisting {
    private AndroidDriver driver;
    private IntroPage introScreen;
    private AuthorizationPage authorizationPage;
    private ForgotPasswordPage forgotPasswordPage;
    private Check assertion;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })

    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introScreen = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);

        // Create assertion object
        assertion = new Check(driver);

        // Go to the authorization page
        introScreen.goToAuthorization();
        authorizationPage.forgotPasswordBtn.click();
    }

    @Test()
    public void The_Title_exists_on_the_ForgotPassword_page() {
        assertion.checkIsDisplayed(forgotPasswordPage.emailField);
    }

    @Test()
    public void The_Email_field_exists_on_the_ForgotPassword_page() {
        assertion.checkIsDisplayed(forgotPasswordPage.emailField);
    }

    @Test()
    public void The_Country_Code_field_exists_on_the_ForgotPassword_page() {
        assertion.checkIsDisplayed(forgotPasswordPage.countryCode);
    }

    @Test()
    public void The_Phone_field_exists_on_the_ForgotPassword_page() {
        assertion.checkIsDisplayed(forgotPasswordPage.phoneField);
    }

    @Test()
    public void The_Cancel_Button_exists_on_the_ForgotPassword_page() {
        assertion.checkIsDisplayed(forgotPasswordPage.canselBtn);
    }

    @Test()
    public void The_OK_Button_exists_on_the_ForgotPassword_page() {
        assertion.checkIsDisplayed(forgotPasswordPage.okBtn);
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }

}
