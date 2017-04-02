package tmp;


import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import testCases.*;
import utils.*;

public class separateTest {

    private AppiumDriver driver;
    private Setup s;
    private String locale;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeMethod
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        this.locale = locale_;
        s = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_);
        driver = s.getDriver();
    }

    @Test(priority = 1, enabled = true)
    public void C42097_New_user_registration_with_validation() {
        new C42097_New_user_registration_with_validation(driver, locale);
    }
    // TODO C44141	- Удаление пользователя
    // TODO C44142	- Удаление датчика
    // TODO C44143	- Удаление комнаты
    // TODO C44144	- Удаление хаба

    @AfterMethod
    public void endSuit() {
        driver.quit();
    }
}
