package ajaxMobileApp;

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

//    private static String OS = System.getProperty("os.name").toLowerCase();
    private static AndroidDriver driver;
    private static String APK = "app-release2.7.2.apk";

    @BeforeTest
    public void setup() throws MalformedURLException {
        driver = (new AppiumSetup()).getDriver();
    }

    @Test
    public void Intro_Page_Appear_Correct() {
        // Create Intro Page object
        IntroScreen introScreen = new IntroScreen(driver);

        // Verify existing elements on the page
        Assert.assertTrue(introScreen.build.isDisplayed());
        Assert.assertTrue(introScreen.logo.isDisplayed());
        Assert.assertTrue(introScreen.authorizationBtn.isDisplayed());
        Assert.assertTrue(introScreen.registrationBtn.isDisplayed());
    }

    @Test
    public void Authorization_Button_Leads_Correct() {
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
    public void Registration_Button_Leads_Correct() {
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
