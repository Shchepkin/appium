package testAjaxMobileApp.introPage;

import ajaxMobileApp.AppiumSetup;
import ajaxMobileApp.AuthorizationScreen;
import ajaxMobileApp.IntroScreen;
import ajaxMobileApp.RegistrationScreen;
import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestIntroScreen {
    /**
     * 1. Verify whether appeared logo, build, buttons are correct
     * 2. Verify the Authorization button leads to a Authorization page
     * 3. Verify the Registration button leads to a Registration page
     */

    private static AndroidDriver driver;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })

    @BeforeTest
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();
    }


    @Test
    public void Authorization_Button_Leads_To_The_Login_Page() {
        // Create objects of pages
        IntroScreen introScreen = new IntroScreen(driver);
        AuthorizationScreen authorizationScreen = new AuthorizationScreen(driver);

        // Go to the authorization page and checks whether the authorization page is open
        introScreen.authorizationBtn.click();
        Assert.assertTrue(authorizationScreen.loginBtn.isDisplayed());

        // Back to the previous page
        authorizationScreen.backBtn.click();
    }

    @Test
    public void Registration_Button_Leads_To_The_Registration_Page() {
        // Create objects of pages
        IntroScreen introScreen = new IntroScreen(driver);
        RegistrationScreen registrationScreen = new RegistrationScreen(driver);

        // Go to the registration page and checks whether the registration page is open
        introScreen.registrationBtn.click();
        Assert.assertTrue(registrationScreen.registrationBtn.isDisplayed());

        // Back to the previous page
        registrationScreen.backBtn.click();
    }

    @AfterTest
    public void endSuit() {
        driver.quit();
    }
}
