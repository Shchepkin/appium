package authorizationPage;

import io.appium.java_client.android.AndroidDriver;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.AuthorizationPage;
import pages.IntroPage;
import utils.AppiumSetup;
import utils.Check;
import utils.Setup;

import java.net.MalformedURLException;

/**
 * This test asserts whether the all server's links is exist on the ServerWindow screen
 */

public class serversExisting {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private Check assertion;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        Reporter.log("Create setup", true);
        Setup setup = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        driver = setup.getDriver();

        // Create objects of pages
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);

        // Create assertion object
        assertion = new Check(driver);

        // Go to the authorization page
        introPage.goToAuthorization();
        authorizationPage.longTapLoginButton();
    }

    @Test()
    public void The_Debug_server_link_exists_on_the_Server_Selection_Window() {
        assertion.checkIsDisplayed(authorizationPage.serverDebug);
    }


    @Test
    public void The_Develop_server_link_exists_on_the_Server_Selection_Window() {
        assertion. checkIsDisplayed (authorizationPage.serverDevelop);
    }


    @Test()
    public void The_Production_server_link_exists_on_the_Server_Selection_Window() {
        assertion.checkIsDisplayed (authorizationPage.serverProduction);
    }


    @Test()
    public void The_Amazon_server_link_exists_on_the_Server_Selection_Window() {
        assertion.checkIsDisplayed (authorizationPage.serverAmazon);
    }


    @Test()
    public void The_Eden_server_link_exists_on_the_Server_Selection_Window() {
        assertion.checkIsDisplayed (authorizationPage.serverEden);
    }


    @AfterClass
    public void endSuit() {
        driver.quit();
    }

}