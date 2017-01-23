package testAjaxMobileApp.authorizationPage;

import ajaxMobileApp.AppiumSetup;
import ajaxMobileApp.AuthorizationPage;
import ajaxMobileApp.Check;
import ajaxMobileApp.IntroPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;

public class emailField {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private Check assertion;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })

    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);

        // Create assertion object
        assertion = new Check(driver);

        // Go to the authorization page
        introPage.goToAuthorization();
    }

    @Test()
    public void SneckBar() {
        System.out.println(authorizationPage.loginBtn.getText());
        System.out.println(authorizationPage.loginBtn);
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
