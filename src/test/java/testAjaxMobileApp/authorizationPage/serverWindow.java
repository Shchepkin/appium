package testAjaxMobileApp.authorizationPage;

import ajaxMobileApp.AppiumSetup;
import ajaxMobileApp.AuthorizationScreen;
import ajaxMobileApp.IntroScreen;
import ajaxMobileApp.ScreenShot;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.io.IOException;
import java.net.MalformedURLException;

public class serverWindow {
    private AndroidDriver driver;
    private IntroScreen introScreen;
    private AuthorizationScreen authorizationScreen;
    private ScreenShot screenShot;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })

    @BeforeTest
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introScreen = new IntroScreen(driver);
        authorizationScreen = new AuthorizationScreen(driver);
        screenShot = new ScreenShot(driver);

        // Go to the authorization page
        introScreen.goToAuthorization();
        authorizationScreen.longTapLoginButton();
    }

    @Test()
    public void The_Debug_server_link_exists_on_the_Server_Selection_Window() {
        checkIsDisplayed (authorizationScreen.serverDebug);
    }


    @Test
    public void The_Develop_server_link_exists_on_the_Server_Selection_Window() {
        checkIsDisplayed (authorizationScreen.serverDevelop);
    }


    @Test()
    public void The_Production_server_link_exists_on_the_Server_Selection_Window() {
        checkIsDisplayed (authorizationScreen.serverProduction);
    }


    @Test()
    public void The_Amazon_server_link_exists_on_the_Server_Selection_Window() {
        checkIsDisplayed (authorizationScreen.serverAmazon);
    }


    @Test()
    public void The_Eden_server_link_exists_on_the_Server_Selection_Window() {
        checkIsDisplayed (authorizationScreen.serverEden);
    }


    @AfterTest
    public void endSuit() {
        driver.quit();
    }


    private void checkIsDisplayed(WebElement element)  {

        try {
            element.isDisplayed();
        } catch (NoSuchElementException e) {

            try {
                screenShot.getScreenShot();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent\n");
        }
    }
}
