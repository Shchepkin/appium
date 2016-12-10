package testAjaxMobileApp.authorizationPage;

import ajaxMobileApp.AppiumSetup;
import ajaxMobileApp.AuthorizationPage;
import ajaxMobileApp.Check;
import ajaxMobileApp.IntroPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;

/**
 * Created by installer on 12/3/16.
 */
public class emailField {
    private AndroidDriver driver;
    private IntroPage introScreen;
    private AuthorizationPage authorizationScreen;
    private Check assertion;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })

    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introScreen = new IntroPage(driver);
        authorizationScreen = new AuthorizationPage(driver);

        // Create assertion object
        assertion = new Check(driver);

        // Go to the authorization page
        introScreen.goToAuthorization();
    }

    @Test()
    public void SneckBar() {
        authorizationScreen.loginBtn.click();
        assertion.checkIsDisplayed(authorizationScreen.snackBar);
        System.out.println(authorizationScreen.snackBar.getText());
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
