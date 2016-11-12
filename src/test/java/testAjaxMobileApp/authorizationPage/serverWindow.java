package testAjaxMobileApp.authorizationPage;

import ajaxMobileApp.AppiumSetup;
import ajaxMobileApp.AuthorizationScreen;
import ajaxMobileApp.IntroScreen;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class serverWindow {
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
    public void The_Debug_server_link_exists_on_the_Server_Selection_Window() {
        try {
            Assert.assertTrue(authorizationScreen.serverDebug.isDisplayed());
        }catch (NoSuchElementException e){
            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent, so");
        }
    }

    @Test
    public void The_Develop_server_link_exists_on_the_Server_Selection_Window() {

        try {
            Assert.assertTrue(authorizationScreen.serverDevelop.isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent, so");
        }
    }

    @Test()
    public void The_Production_server_link_exists_on_the_Server_Selection_Window() {

        try {
            Assert.assertTrue(authorizationScreen.serverProduction.isDisplayed());
        }catch (NoSuchElementException e){
            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent, so");
        }
    }

    @Test()
    public void The_Amazon_server_link_exists_on_the_Server_Selection_Window() {

        try {
            Assert.assertTrue(authorizationScreen.serverAmazon.isDisplayed());
        }catch (NoSuchElementException e){
            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent, so");
        }
    }

    @Test()
    public void The_Eden_server_link_exists_on_the_Server_Selection_Window() {

        try {
            Assert.assertTrue(authorizationScreen.serverEden.isDisplayed());
        }catch (NoSuchElementException e){
            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent, so");
        }
    }

    @AfterTest
    public void endSuit() {
        driver.quit();
    }

}
