package testAjaxMobileApp.introPage;

import ajaxMobileApp.AppiumSetup;
import ajaxMobileApp.AuthorizationScreen;
import ajaxMobileApp.IntroScreen;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

/**
 * This test asserts whether the all elements of the Intro Page is exist
 */
public class pageElementExisting {
    private AndroidDriver driver;
    private IntroScreen introScreen;
    private AuthorizationScreen authorizationScreen;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })

    @BeforeTest
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introScreen = new IntroScreen(driver);
        authorizationScreen = new AuthorizationScreen(driver);

        // Go to the authorization page
        introScreen.goToAuthorization();
        authorizationScreen.longTapLoginButton();
    }


    @Test()
    public void The_Build_exists_on_the_Intro_page() {
        try {
            Assert.assertTrue(introScreen.build.isDisplayed());
        }catch (NoSuchElementException e){
            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent, so");
        }
    }


    @Test()
    public void The_Logo_exists_on_the_Intro_page() {
        try {
            Assert.assertTrue(introScreen.logo.isDisplayed());
        }catch (NoSuchElementException e){
            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent, so");
        }
    }


    @Test()
    public void The_authorization_button_exists_on_the_Intro_page() {
        try {
            Assert.assertTrue(introScreen.authorizationBtn.isDisplayed());
        }catch (NoSuchElementException e){
            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent, so");
        }
    }


    @Test()
    public void The_registration_button_exists_on_the_Intro_page() {
        try {
            Assert.assertTrue(introScreen.registrationBtn.isDisplayed());
        }catch (NoSuchElementException e){
            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent, so");
        }
    }
}
