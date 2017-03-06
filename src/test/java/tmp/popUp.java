package tmp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AuthorizationPage;
import pages.DashboardHeader;
import pages.Hub;
import pages.IntroPage;
import utils.Check;
import utils.PopUp;
import utils.Setup;

/**
 * This test asserts whether the all server's links is exist on the ServerWindow screen
 */

public class popUp {
    private AppiumDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private PopUp popUp;
    private Setup s = new Setup();
    private Check check;
    private DashboardHeader header;
    private boolean result;
    private WebElement[] elements;
    private Hub hub;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        s.log("Method is started");
        String query = "DELETE FROM csa_accounts WHERE Login LIKE '%" + locale_ + "%'";
        s.log(query);
//        System.exit(0);
        Setup s = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        driver = s.getDriver();

        // Create objects of pages
        s.log("create objects of pages");
        hub = new Hub(driver, locale_);
        popUp = new PopUp(driver);
        check = new Check(driver);
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        header = new DashboardHeader(driver);

        // Go to the authorization page
        s.log("introPage.loginBtn.click()");
        introPage.loginBtn.click();
    }



    @Test(enabled = false)
    public void PopUp_Test() {
        s.log("== Method is started");
        authorizationPage.loginToTheServer("ajax1@i.u", "qwe123", "Production");

        elements = new WebElement[]{header.menuDrawer};
        int result = check.waitElements(elements, 10);
        switch (result){
            case 1:
                s.log(3, "snackBar with text: \"" + popUp.snackBar.getText() + "\"");
                Assert.fail("Login failed! SnackBar was shown with text: \"" + popUp.snackBar.getText() + "\"");
                break;
            case 2:
                s.log("login successful");
                s.log("loadingWin cancel");
                popUp.cancelBtn.click();
                break;
            case 3:
                s.log("login successful");
                break;
            default:
                Assert.fail("Login failed!");
                break;
        }
        s.log("= Method is finished");
    }


    @Test(enabled = false)
    public void sql(String locale_) {
        s.log(1,"Method is started");
        String query = "DELETE FROM csa_accounts WHERE Login LIKE '%" + locale_ + "%'";
        s.log(query);
        s.log(1,"Method is finished");
    }

    @Test(enabled = false)
    public void Check() {
        s.log(1,"Method is started");
        authorizationPage.loginToTheServer("ajax1@i.ua", "qwe123", "Release");
        Assert.assertFalse(popUp.result, "login is unsuccessfully, was shown SnackBar");

        popUp.waitLoadingPopUp(0);

        s.log(1,"Method is finished");
    }


    @AfterClass
    public void endSuit() {
        driver.quit();
    }

}
