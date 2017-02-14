package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class AppiumSetup {

    private AndroidDriver driver;
    private String deviceName_, UDID_, platformVersion_, URL_, appPath_;

    public AppiumSetup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_) {
        this.deviceName_ = deviceName_;
        this.UDID_ = UDID_;
        this.platformVersion_ = platformVersion_;
        this.URL_ = URL_;
        this.appPath_ = appPath_;
    }

    public AndroidDriver getDriver(){
        // Create delay timer before next action for finding elements
        Reporter.log("===== Start getDriver ", true);
        try {
            File app = new File(appPath_);

            // Settings ajaxMobileApp AndroidDriver
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
            capabilities.setCapability("deviceName", deviceName_);
            capabilities.setCapability("udid", UDID_);
            capabilities.setCapability("platformVersion", platformVersion_);
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("app", app.getAbsolutePath());
            capabilities.setCapability("appPackage", "com.ajaxsystems");

//        capabilities.setCapability("appActivity", "com.ajaxsystems.activity.DashboardActivity");
            capabilities.setCapability("appActivity", "com.ajaxsystems.ui.activity.LauncherActivity");

            // Create AndroidDriver object and connect to ajaxMobileApp server
            Reporter.log("> implement Android driver ", true);
            driver = new AndroidDriver(new URL("http://" + URL_), capabilities);
            driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Reporter.log("===== End getDriver ", true);
        return driver;
    }
}

