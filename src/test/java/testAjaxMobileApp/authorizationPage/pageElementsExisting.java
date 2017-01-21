package testAjaxMobileApp.authorizationPage;

import ajaxMobileApp.AppiumSetup;
import ajaxMobileApp.AuthorizationPage;
import ajaxMobileApp.IntroPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;

public class pageElementsExisting {
    private AndroidDriver driver;
    private IntroPage introScreen;
    private AuthorizationPage authorizationScreen;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introScreen = new IntroPage(driver);
        authorizationScreen = new AuthorizationPage(driver);

        // Go to the authorization page
        introScreen.goToAuthorization();
        authorizationScreen.longTapLoginButton();
    }


}
