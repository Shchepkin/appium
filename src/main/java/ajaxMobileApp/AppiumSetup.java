package ajaxMobileApp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class AppiumSetup {

    private AndroidDriver driver;
    private String deviceName_, UDID_, platformVersion_, URL_;

    public AppiumSetup(String deviceName_, String UDID_, String platformVersion_, String URL_) {
        this.deviceName_ = deviceName_;
        this.UDID_ = UDID_;
        this.platformVersion_ = platformVersion_;
        this.URL_ = URL_;
    }

    public AndroidDriver getDriver() throws MalformedURLException, InterruptedException {

        File app = new File("/home/installer/Android/AndroidApp/build.2.8.0.apk");

        // Settings ajaxMobileApp AndroidDriver
        DesiredCapabilities capabilities = new DesiredCapabilities();
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
        driver = new AndroidDriver(new URL("http://" + URL_), capabilities);

        // Create delay timer before next action for finding elements
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        return driver;
    }
}

