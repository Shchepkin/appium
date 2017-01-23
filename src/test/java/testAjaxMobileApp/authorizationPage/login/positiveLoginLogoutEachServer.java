package testAjaxMobileApp.authorizationPage.login;

import ajaxMobileApp.*;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;

public class positiveLoginLogoutEachServer {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private DashboardHeader dashboardHeader;
    private MenuMainPage menuPage;
    private MenuAccountPage accountPage;
    private Check assertion;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        dashboardHeader = new DashboardHeader(driver);
        menuPage = new MenuMainPage(driver);
        accountPage = new MenuAccountPage(driver);

        // Create assertion object
        assertion = new Check(driver);

        // Go to the authorization page
        introPage.goToAuthorization();
    }

    @Test()
    public void Login_to_the_Develop_with_correct_data() {
        fillFields();
        authorizationPage.serverDevelop.click();
        authorizationPage.loginBtn.click();
        assertion.closePinWindowIfDisplayed();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        logOut();
    }

    @Test()
    public void Login_to_the_Production_with_correct_data() {
        fillFields();
        authorizationPage.serverProduction.click();
        authorizationPage.loginBtn.click();
        assertion.closePinWindowIfDisplayed();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        logOut();
    }

    @Test()
    public void Login_to_the_Debug_with_correct_data() {
        fillFields();
        authorizationPage.serverDebug.click();
        authorizationPage.loginBtn.click();
        assertion.closePinWindowIfDisplayed();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        logOut();
    }

    @Test()
    public void Login_to_the_Eden_with_correct_data() {
        fillFields();
        authorizationPage.serverEden.click();
        authorizationPage.loginBtn.click();
        assertion.closePinWindowIfDisplayed();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        logOut();
    }

    @Test()
    public void Login_to_the_Amazon_with_correct_data() {
        fillFields();
        authorizationPage.serverAmazon.click();
        authorizationPage.loginBtn.click();
        assertion.closePinWindowIfDisplayed();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        logOut();
    }

    private void fillFields() {
        authorizationPage.loginField.sendKeys("ajax1@i.ua");
        authorizationPage.passwordField.sendKeys("qwe123");
        authorizationPage.longTapLoginButton();
    }

    private void logOut() {
        dashboardHeader.menuDrawer.click();
        menuPage.accountBtn.click();
        accountPage.logoutBtn.click();
        assertion.checkIsDisplayed(authorizationPage.loginBtn);
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
