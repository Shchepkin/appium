package testAjaxMobileApp.authorizationPage;

import ajaxMobileApp.AppiumSetup;
import ajaxMobileApp.AuthorizationScreen;
import ajaxMobileApp.IntroScreen;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;

public class pageElementsExisting {
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


}
