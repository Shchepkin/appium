package tmp;


import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;
import testCases.C42097_New_user_registration_with_validation;
import testCases.C42098_Login_to_the_existing_account;
import testCases.C42099_Add_new_Hub_manually;
import utils.*;

public class separateTest extends Base{

    private AppiumDriver driver;
    private Setup s = new Setup();
    private String locale;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeMethod
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        log("Method is started");
        this.locale = locale_;
        s = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        driver = s.getDriver();
    }

    @Test(priority = 1, enabled = false)
    public void C42097_New_user_registration_with_validation() {
        new C42097_New_user_registration_with_validation(driver, locale);
    }

    @Test(priority = 1, enabled = true)
    public void C42098_Login_to_the_existing_account() {
        new C42098_Login_to_the_existing_account(driver, locale);
    }

    @Test(priority = 1, enabled = true)
    public void C42099_Add_new_Hub_manually() {
        new C42099_Add_new_Hub_manually(driver, locale);
    }

    @AfterMethod
    public void endSuit() {
        log("close driver");
        driver.quit();
    }
}
