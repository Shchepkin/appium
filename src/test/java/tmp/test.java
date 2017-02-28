package tmp;

import io.appium.java_client.AppiumDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Hub;
import utils.*;


import java.util.Map;


public class test {
    private AppiumDriver driver;
    private Email email;
    private Map localizeKeys;
    private Setup s = new Setup();
    private Hub hub;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        s.log("Method is started");
        Setup s = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        driver = s.getDriver();

        localizeKeys = s.getLocalizeKeys();
        hub = new Hub(driver);
//        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
    }

    @Test(priority = 1, enabled = false)
    public void get_Path() {
        Reporter.log("===== Start get_Path test", true);
        System.out.println(localizeKeys.get("CFBundleName"));
    }

    @Test(priority = 1, enabled = false)
    public void get_Email_Text() {
        Reporter.log("===== Start get_Email_Text test", true);
//        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
        String emailText = email.getEmailTextFromNewMessage();
        System.out.println("------------------------------------------\n" + emailText);
    }

    @Test(priority = 2, enabled = false)
    public void get_Code() {
        Reporter.log("===== Start get_Code test", true);
//        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
        String emailCode = email.getValidationCode();
    }

    @Test(priority = 0, enabled = false)
    public void checkEmail() {
        Reporter.log("===== Start checkEmail test", true);
//        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
        email.checkNewMessage();
        String emailCode = email.getValidationCode();
        System.out.println("> ====== emailCode: " + emailCode);
        System.out.println("> ====== emailText: " + email.emailText);
        email.deleteAllMessage();

    }

    @Test(priority = 2, enabled = false)
    public void delete_all_message() {
        Reporter.log("===== Start delete_all_message test", true);
//        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
        email.deleteAllMessage();
        System.out.println("done\n");
    }

    @AfterClass
    public void endSuit() {
        Reporter.log("===== Close all folders and stores", true);
//        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
//        email.closeAll();
    }
}
