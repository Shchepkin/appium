//package introPage;
//
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.android.AndroidDriver;
//
//import org.testng.Reporter;
//import org.testng.annotations.*;
//import pages.AuthorizationPage;
//import pages.IntroPage;
//import pages.RegistrationPage;
//import utils.Check;
//import utils.Setup;
//
//public class linksLeadsToCorrectPages {
//    /**
//     * 1. Verify whether appeared logo, build, buttons are correct
//     * 2. Verify the Authorization button leads to a Authorization page
//     * 3. Verify the Registration button leads to a Registration page
//     */
//
//    private AppiumDriver driver;
//    private IntroPage introPage;
//    private AuthorizationPage loginPage;
//    private RegistrationPage registrationPage;
//    private Check assertion;
//
//    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
//    @BeforeClass
//    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
//        Reporter.log("Create setup", true);
//        Setup setup = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_);
//        driver = setup.getDriver();
//
//        // Create objects of pages
//        introPage = new IntroPage(driver);
//        loginPage = new AuthorizationPage(driver);
//        registrationPage = new RegistrationPage(driver);
//
//        // Create assertion object
//        assertion = new Check(driver);
//    }
//
//
//    @Test
//    public void Login_Button_Leads_To_The_Login_Page() {
//
//        // Go to the authorization page
//        introPage.goToAuthorization();
//
//        // Check whether the authorization page is open (Login button is exist)
//        assertion.isElementDisplayed(loginPage.loginBtn, 15);
//
//        // Back to the previous page
//        loginPage.backBtn.click();
//    }
//
//    @Test
//    public void Registration_Button_Leads_To_The_Registration_Page() {
//
//        // Go to the registration page and checks whether the registration page is open
//        introPage.goToRegistration();
//
//        // Check whether the registration page is open (registration button is exist)
//        assertion.isElementDisplayed(registrationPage.registrationBtn, 15);
//
//        // Back to the previous page
//        registrationPage.backBtn.click();
//    }
//
//    @AfterClass
//    public void endSuit() {
//        driver.quit();
//    }
//}
