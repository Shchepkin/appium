package utils;

import com.google.gson.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class Setup {

    private AndroidDriver driver;
    private Path path;
    private String deviceName_, UDID_, platformVersion_, URL_, appPath_, jsonString, collection, locale_;

    public Map localizeKeys;

    public Setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_) {
        this.deviceName_ = deviceName_;
        this.UDID_ = UDID_;
        this.platformVersion_ = platformVersion_;
        this.URL_ = URL_;
        this.appPath_ = appPath_;
        this.locale_ = locale_;
    }

    public AndroidDriver getDriver(){
        // Create delay timer before next action for finding elements
        Reporter.log("===== Start getDriver ", true);
        try {
            Reporter.log("> Get file ", true);
            File app = new File(appPath_);

            // Settings ajaxMobileApp AndroidDriver
            Reporter.log("> Set settings ", true);
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
            Reporter.log("> implement timeouts ", true);
            driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Reporter.log("===== End getDriver ", true);
        return driver;
    }

    public Map getLocalizeKeys() {
        try {
            localizeKeys = new HashMap<String, String>();
            path = getApplicationStartUp();

            collection = loadJSON(path + "/classes/lokaliseKeys/collection.json");
            jsonString = loadJSON(path + "/classes/lokaliseKeys/" + locale_ + ".json");

            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(jsonString).getAsJsonObject();
            Gson gson = new Gson();
            List collectionList = gson.fromJson(parser.parse(collection).getAsJsonObject().getAsJsonArray("paramsArray"), List.class);
            for (Object i : collectionList) {
                Map map = gson.fromJson(jo.get(i.toString()).getAsJsonObject(), HashMap.class);
                localizeKeys.putAll(map);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localizeKeys;
    }

    private Path getApplicationStartUp() throws UnsupportedEncodingException,
            MalformedURLException {
        URL startupUrl = getClass().getProtectionDomain().getCodeSource()
                .getLocation();
        Path path = null;
        try {
            path = Paths.get(startupUrl.toURI());
        } catch (Exception e) {
            try {
                path = Paths.get(new URL(startupUrl.getPath()).getPath());
            } catch (Exception ipe) {
                path = Paths.get(startupUrl.getPath());
            }
        }
        path = path.getParent();
        return path;
    }


    private String loadJSON(String path) throws IOException {
        byte[] buf;
        try (RandomAccessFile f = new RandomAccessFile(path, "r")) {
            buf = new byte[(int) f.length()];
            f.read(buf);
        }
        return new String(buf);
    }
}

