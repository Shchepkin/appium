package introPage;

import org.testng.Reporter;
import utils.Check;
import pages.IntroPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.*;
import utils.Setup;

/**
 * This test asserts whether the all elements of the Intro Page is exist
 */
public class pageElementExisting {
    private AndroidDriver driver;
    private IntroPage introPage;
    private Check assertion;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        Reporter.log("Create setup", true);
        Setup setup = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        driver = setup.getDriver();

        // Create objects of Intro pages
        introPage = new IntroPage(driver);

        // Create assertion object
        assertion = new Check(driver);
    }

    @Test()
    public void The_Build_exists_on_the_Intro_page() {
        assertion.isElementDisplayed(introPage.build);
    }

    @Test()
    public void The_authorization_button_exists_on_the_Intro_page() {
        assertion.isElementDisplayed(introPage.loginBtn);
    }

    @Test()
    public void The_registration_button_exists_on_the_Intro_page() {
        assertion.isElementDisplayed(introPage.registrationBtn);
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
