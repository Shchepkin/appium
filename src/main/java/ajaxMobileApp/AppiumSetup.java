package ajaxMobileApp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by installer on 11/8/16.
 */
public class AppiumSetup{

    public AndroidDriver driver;

    public void AppiumSetupDriver() throws MalformedURLException{

    }

    public AndroidDriver getDriver()  throws MalformedURLException{
        File app = new File("/home/installer/Android/AndroidApp/app-release2.7.2.apk");

        // Settings ajaxMobileApp AndroidDriver
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
        capabilities.setCapability("deviceName", "Prestigio");
        capabilities.setCapability("platformVersion", "4.4.2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.ajaxsystems");
        capabilities.setCapability("appActivity", "com.ajaxsystems.activity.DashboardActivity");

        // Create AndroidDriver object and connect to ajaxMobileApp server
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        System.out.println("Driver has been created successfully");

        // Create delay timer before next action for finding elements
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        return driver;
    }
}

