package Suits;


import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;
import testCases.*;
import utils.Setup;

public class Smoke{
    public Smoke() {
    }

    private AppiumDriver driver;
    private Setup s;
    public String locale;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeMethod
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        s = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_);
        driver = s.getDriver();
        this.locale = locale_;
        System.out.println(" setup locale: \"" + locale + "\"");
    }

    public String getLocale(){
        return locale;
    }

    @Test(priority = 1, enabled = false)
    public void C42097_New_user_registration_with_validation() {
        new C42097_New_user_registration_with_validation(driver, locale);
    }

    @Test(priority = 2, enabled = false)
    public void C42098_Login_to_the_existing_account() {
        new C42098_Login_to_the_existing_account(driver, locale);
    }

    @Test(priority = 3, enabled = false)
    public void C42099_Add_new_Hub_manually() {
        new C42099_Add_new_Hub_manually(driver, locale);
    }

    @Test(priority = 4, enabled = false)
    public void C42100_Add_new_room() {
        new C42100_Add_new_room(driver, locale);
    }

    @Test(priority = 5, enabled = false)
    public void C43875_Add_new_device() {
        new C43875_Add_new_device(driver, locale);
    }

    @Test(priority = 6, enabled = false)
    public void C42176_Virtual_Space_Control() {
        new C42176_Virtual_Space_Control(driver, locale);
    }

    @Test(priority = 7, enabled = false)
    public void C42102_Add_new_guest_user() {
        new C42102_Add_new_guest_user(driver, locale);
    }

    @AfterMethod
    public void endSuit() {
        driver.quit();
    }
}
