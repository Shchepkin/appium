//package utils;
//
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.remote.MobileCapabilityType;
//import org.openqa.selenium.remote.CapabilityType;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import pages.Base;
//
//import java.io.File;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.concurrent.TimeUnit;
//
//
//public class Setup extends Base{
//
//    private AppiumDriver driver;
//    private String deviceName_, UDID_, platformVersion_, URL_, appPath_;
//
//    public Setup() {
//    }
//
//    public Setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_) {
//        this.URL_ = URL_;
//        this.UDID_ = UDID_;
//        this.appPath_ = appPath_;
//        this.deviceName_ = deviceName_;
//        this.platformVersion_ = platformVersion_;
//    }
//
//    public AppiumDriver getDriver() {
////        log("Method is started");
//        try {
////            log(2, "get .apk file");
//            File app = new File(appPath_);
//
//            // Settings ajaxMobileApp AndroidDriver
////            log(2, "set capabilities settings");
//            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");
//            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
//            capabilities.setCapability("deviceName", deviceName_);
//            capabilities.setCapability("udid", UDID_);
//            capabilities.setCapability("platformVersion", platformVersion_);
//            capabilities.setCapability("platformName", "Android");
//            capabilities.setCapability("app", app.getAbsolutePath());
//            capabilities.setCapability("appPackage", "com.ajaxsystems");
//            capabilities.setCapability("appActivity", "com.ajaxsystems.ui.activity.LauncherActivity");
////        capabilities.setCapability("appActivity", "com.ajaxsystems.activity.DashboardActivity");
//
//
////            log(2, "implement Android driver");
//            driver = new AndroidDriver(new URL("http://" + URL_), capabilities);
//
////            log(2, "set timeouts");
//            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//
//        } catch (MalformedURLException e) {
////            log(4, "MalformedURLException\n" + e);
//        }
////        log("Method is finished");
//        return driver;
//    }
//}
//
//
