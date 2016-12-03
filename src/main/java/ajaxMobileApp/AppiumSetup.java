package ajaxMobileApp;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
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

        File app = new File("/home/installer/Android/AndroidApp/build.2.8.4.apk");

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
        driver = new AndroidDriver(new URL("http://" + URL_), capabilities);

        // Create delay timer before next action for finding elements
        driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);

        return driver;
    }
}
/**
 * For parallel testing

node appium -a IP_ADDRESS -p PORT -cp CALLBACK_PORT -bp BOOTSTRAP_PORT

 Description about Command:
 - node Appium: To start the instance with node with Appium located in the location too provide Appium server arguments
 - For address we have to pass “-a” with the address here address is “127.0.0.1”:  “ -a 127.0.0.1 “
 - For port we have to pass “-p” with port number here port number is “1234”:  “-p 1234 “
 - For callbackPort we have to pass “-cp” with port number here port number is “1234”:  “-cp 1234 “
 - For BootstrapPort we have to pass “-bp” with port number here port number is “2345”:  “-bp 2345 “

 So, we have to run two or more terminals and fill there such commands as:
 node appium -a 127.0.0.1 -p 1234 -cp 1234 -bp 2345
 node appium -a 127.0.0.2 -p 3456 -cp 3456 -bp 4567

 After that we have to write IP_ADDRESS and PORT in TestNG_Configuration.xml
--------------------------------------------------------------------------------------------------------
 * 0123456789ABCDEF	    JiaYu       4.2.1
 * MTPB0252700596	    prestigio   4.4.2
 * SWIVR8YHDI7TYTS8	    lg          5.1
 */

