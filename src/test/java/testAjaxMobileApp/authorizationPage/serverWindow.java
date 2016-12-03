package testAjaxMobileApp.authorizationPage;

import ajaxMobileApp.*;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class serverWindow {
    private AndroidDriver driver;
    private IntroScreen introScreen;
    private AuthorizationScreen authorizationScreen;
    private Check assertion;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })

    @BeforeTest
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introScreen = new IntroScreen(driver);
        authorizationScreen = new AuthorizationScreen(driver);

        // Create assertion object
        assertion = new Check(driver);

        // Go to the authorization page
        introScreen.goToAuthorization();
        authorizationScreen.longTapLoginButton();
    }

    @Test()
    public void The_Debug_server_link_exists_on_the_Server_Selection_Window() {
        assertion.checkIsDisplayed(authorizationScreen.serverDebug);
    }


    @Test
    public void The_Develop_server_link_exists_on_the_Server_Selection_Window() {
        assertion. checkIsDisplayed (authorizationScreen.serverDevelop);
    }


    @Test()
    public void The_Production_server_link_exists_on_the_Server_Selection_Window() {
        assertion.checkIsDisplayed (authorizationScreen.serverProduction);
    }


    @Test()
    public void The_Amazon_server_link_exists_on_the_Server_Selection_Window() {
        assertion.checkIsDisplayed (authorizationScreen.serverAmazon);
    }


    @Test()
    public void The_Eden_server_link_exists_on_the_Server_Selection_Window() {
        assertion.checkIsDisplayed (authorizationScreen.serverEden);
    }


    @AfterTest
    public void endSuit() {
        driver.quit();
    }

}
