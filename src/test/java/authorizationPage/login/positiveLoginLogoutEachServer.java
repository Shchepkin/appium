package authorizationPage.login;

import io.appium.java_client.android.AndroidDriver;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.*;
import utils.AppiumSetup;
import utils.Check;
import utils.Setup;

import java.net.MalformedURLException;

public class positiveLoginLogoutEachServer {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private DashboardHeader dashboardHeader;
    private MenuMainPage menuPage;
    private MenuAccountPage accountPage;
    private Check assertion;
    private DashboardActivePINPage pinPage;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        Reporter.log("Create setup", true);
        Setup setup = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        driver = setup.getDriver();

        // Create objects of pages
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        dashboardHeader = new DashboardHeader(driver);
        menuPage = new MenuMainPage(driver);
        accountPage = new MenuAccountPage(driver);
        pinPage = new DashboardActivePINPage(driver);

        // Create assertion object
        assertion = new Check(driver);

        // Go to the authorization page
        introPage.goToAuthorization();
    }

    @Test()
    public void Login_to_the_Develop_with_correct_data() {
        authorizationPage.loginToTheServer("ajax1@i.ua", "qwe123", "Develop");
        pinPage.closePinWindowIfDisplayed();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        logOut();
    }

    @Test()
    public void Login_to_the_Production_with_correct_data() {
        authorizationPage.loginToTheServer("ajax1@i.ua", "qwe123", "Production");
        pinPage.closePinWindowIfDisplayed();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        logOut();
    }

    @Test(enabled = false)
    public void Login_to_the_Debug_with_correct_data() {
        authorizationPage.loginToTheServer("ajax1@i.ua", "qwe123", "Debug");
        pinPage.closePinWindowIfDisplayed();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        logOut();
    }

    @Test(enabled = false)
    public void Login_to_the_Eden_with_correct_data() {
        authorizationPage.loginToTheServer("ajax1@i.ua", "qwe123", "Eden");
        pinPage.closePinWindowIfDisplayed();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        logOut();
    }

    @Test(enabled = false)
    public void Login_to_the_Amazon_with_correct_data() {
        authorizationPage.loginToTheServer("ajax1@i.ua", "qwe123", "Amazon");
        pinPage.closePinWindowIfDisplayed();
        assertion.checkIsDisplayed(dashboardHeader.menuDrawer);
        logOut();
    }

    private void logOut() {
        dashboardHeader.menuDrawer.click();
        menuPage.accountBtn.click();
        accountPage.logoutBtn.click();
        assertion.checkIsDisplayed(introPage.authorizationBtn);
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
