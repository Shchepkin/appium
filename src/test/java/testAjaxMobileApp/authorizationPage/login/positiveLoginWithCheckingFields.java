package testAjaxMobileApp.authorizationPage.login;

import ajaxMobileApp.*;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;

public class positiveLoginWithCheckingFields {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private DashboardHeader dashboardHeader;
    private MenuMainPage menuPage;
    private MenuAccountPage accountPage;
    private Check assertion;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })
    @BeforeMethod
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        dashboardHeader = new DashboardHeader(driver);

        // Create assertion object
        assertion = new Check(driver);

        // Go to the authorization page
        introPage.goToAuthorization();
        authorizationPage.longTapLoginButton();
        authorizationPage.serverDevelop.click();
    }

    @Test()
    public void Email_and_Pass_with_spaces() {
        authorizationPage.loginField.sendKeys("  ajax1@i.ua  ");
        authorizationPage.passwordField.sendKeys("  qwe123  ");
        authorizationPage.loginBtn.click();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        assertion.checkIsDisplayed(authorizationPage.loginBtn);
    }

    @Test()
    public void Capital_letters_in_Email() {
        authorizationPage.loginField.sendKeys("AjaX1@i.Ua");
        authorizationPage.passwordField.sendKeys("qwe123");
        authorizationPage.loginBtn.click();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        assertion.checkIsDisplayed(authorizationPage.loginBtn);
    }


    @AfterMethod
    public void endSuit() {
        driver.quit();
    }
}
