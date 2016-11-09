package ajaxMobileApp;

import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


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

        File app = new File("/home/installer/Android/AndroidApp/" + APK);

        // Settings ajaxMobileApp AndroidDriver
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
        capabilities.setCapability("deviceName", "Prestigio");
        capabilities.setCapability("platformVersion", "4.4.2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.ajaxsystems");
        capabilities.setCapability("appActivity", "com.ajaxsystems.activity.DashboardActivity");

        // Create AndroidDriver object and connect to ajaxMobileApp server
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        System.out.println("Driver has been created successfully");

        // Create delay timer before next action for finding elements
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
