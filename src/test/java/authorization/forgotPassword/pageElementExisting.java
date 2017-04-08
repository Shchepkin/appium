//package authorization.forgotPassword;
//
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.android.AndroidDriver;
//import org.testng.Reporter;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//import pages.AuthorizationPage;
//import pages.ForgotPasswordPage;
//import pages.IntroPage;
//import utils.Check;
//import utils.Setup;
//
//public class pageElementExisting {
//    private AppiumDriver driver;
//    private IntroPage introPage;
//    private AuthorizationPage loginPage;
//    private ForgotPasswordPage forgotPasswordPage;
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
//        forgotPasswordPage = new ForgotPasswordPage(driver);
//
//        // Create assertion object
//        assertion = new Check(driver);
//
//        // Go to the authorization page
//        introPage.goToAuthorization();
//        loginPage.forgotPasswordBtn.click();
//    }
//
//    @Test()
//    public void The_Title_exists_on_the_ForgotPassword_page() {
//        assertion.isElementDisplayed(forgotPasswordPage.emailField, 15);
//    }
//
//    @Test()
//    public void The_Email_field_exists_on_the_ForgotPassword_page() {
//        assertion.isElementDisplayed(forgotPasswordPage.emailField, 15);
//    }
//
//    @Test()
//    public void The_Country_Code_field_exists_on_the_ForgotPassword_page() {
//        assertion.isElementDisplayed(forgotPasswordPage.countryCode, 15);
//    }
//
//    @Test()
//    public void The_Phone_field_exists_on_the_ForgotPassword_page() {
//        assertion.isElementDisplayed(forgotPasswordPage.phoneField, 15);
//    }
//
//    @Test()
//    public void The_Cancel_Button_exists_on_the_ForgotPassword_page() {
//        assertion.isElementDisplayed(forgotPasswordPage.canselBtn, 15);
//    }
//
//    @Test()
//    public void The_OK_Button_exists_on_the_ForgotPassword_page() {
//        assertion.isElementDisplayed(forgotPasswordPage.okBtn, 15);
//    }
//
//    @AfterClass
//    public void endSuit() {
//        driver.quit();
//    }
//
//}
