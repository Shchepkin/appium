//package tmp;
//
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.android.AndroidDriver;
//import org.openqa.selenium.WebElement;
//import org.testng.Assert;
//import org.testng.Reporter;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//import pages.*;
//import utils.Check;
//import utils.PopUp;
//
///**
// * This test asserts whether the all server's links is exist on the ServerWindow screen
// */
//
//public class popUp extends Base{
//    private AppiumDriver driver;
//    private boolean result;
//    private WebElement[] elements;
//    private Hub hub;
//
//    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
//    @BeforeClass
//    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
//        log("Method is started");
//        String query = "DELETE FROM csa_accounts WHERE Login LIKE '%" + locale_ + "%'";
//        log(query);
////        System.exit(0);
//        Setup s = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_);
//        driver = s.getDriver();
//
//        // Create objects of pages
//        log("create objects of pages");
//        hub = new Hub(driver, locale_);
//        popUp = new PopUp(driver);
//        check = new Check(driver);
//        introPage = new IntroPage(driver);
//        loginPage = new AuthorizationPage(driver);
//        header = new DashboardHeader(driver);
//
//        // Go to the authorization page
//        log("introPage.loginBtn.click()");
//        introPage.goToAuthorization();
//    }
//
//
//
//    @Test(enabled = false)
//    public void PopUp_Test() {
//        log("== Method is started");
//        loginPage.loginToTheServer("ajax1@i.u", "qwe123", "Production");
//
//        elements = new WebElement[]{header.menuDrawer};
//        int result = check.waitElements(elements, 10);
//        switch (result){
//            case 1:
//                log(3, "snackBar with text: \"" + popUp.snackBar.getText() + "\"");
//                Assert.fail("Login failed! SnackBar was shown with text: \"" + popUp.snackBar.getText() + "\"");
//                break;
//            case 2:
//                log("login successful");
//                log("loadingWindow cancel");
//                popUp.cancelBtn.click();
//                break;
//            case 3:
//                log("login successful");
//                break;
//            default:
//                Assert.fail("Login failed!");
//                break;
//        }
//        log("= Method is finished");
//    }
//
//
//    @Test(enabled = false)
//    public void sql(String locale_) {
//        log(1,"Method is started");
//        String query = "DELETE FROM csa_accounts WHERE Login LIKE '%" + locale_ + "%'";
//        log(query);
//        log(1,"Method is finished");
//    }
//
//    @Test(enabled = false)
//    public void Check() {
//        log(1,"Method is started");
//        loginPage.loginToTheServer("ajax1@i.ua", "qwe123", "Release");
//        Assert.assertFalse(popUp.result, "login is unsuccessfully, was shown SnackBar");
//
//        popUp.waitLoadingPopUp(0);
//
//        log(1,"Method is finished");
//    }
//
//
//    @AfterClass
//    public void endSuit() {
//        driver.quit();
//    }
//
//}
