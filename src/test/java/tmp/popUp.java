package tmp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AuthorizationPage;
import pages.IntroPage;
import utils.AppiumSetup;
import utils.Check;
import utils.PopUp;
import utils.Setup;

/**
 * This test asserts whether the all server's links is exist on the ServerWindow screen
 */

public class popUp {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private PopUp popUp;
    private Check assertion;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        Reporter.log("Start @BeforeClass", true);
        Setup setup = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        driver = setup.getDriver();

        // Create objects of pages
        Reporter.log("== Create objects of pages ", true);
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        popUp = new PopUp(driver);
        Reporter.log("> Done ", true);

        // Go to the authorization page
        Reporter.log("> introPage.authorizationBtn.click(); ", true);

        introPage.authorizationBtn.click();

        authorizationPage.loginToTheServer("ajax1@i.ua", "qwe123", "Release");
    }

    @Test()
    public void Check_PopUp() {
        Reporter.log("===== Start Check_PopUp test", true);
        popUp.waitPopUps();
    }


    @AfterClass
    public void endSuit() {
        driver.quit();
    }

}
