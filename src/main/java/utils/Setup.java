package utils;

import com.google.gson.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class Setup {

    private AndroidDriver driver;
    private Path path;
    private String deviceName_, UDID_, platformVersion_, URL_, appPath_, jsonString, collection, locale_;

    public Map localizeKeys;

    public Setup() {
    }

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
        log(1,"=== Method is started");
        try {
            log(2,"get .apk file");
            File app = new File(appPath_);

            // Settings ajaxMobileApp AndroidDriver
            log(2,"set capabilities settings");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
            capabilities.setCapability("deviceName", deviceName_);
            capabilities.setCapability("udid", UDID_);
            capabilities.setCapability("platformVersion", platformVersion_);
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("app", app.getAbsolutePath());
            capabilities.setCapability("appPackage", "com.ajaxsystems");
            capabilities.setCapability("appActivity", "com.ajaxsystems.ui.activity.LauncherActivity");

//        capabilities.setCapability("appActivity", "com.ajaxsystems.activity.DashboardActivity");


            // Create AndroidDriver object and connect to ajaxMobileApp server
            log(2,"implement Android driver");
            driver = new AndroidDriver(new URL("http://" + URL_), capabilities);

            log(2,"set timeouts");
//            driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);

        } catch (MalformedURLException e) {
            log(4,"MalformedURLException\n" + e);
        }
        log("=== Method is finished");
        return driver;
    }

    public Map getLocalizeKeys() {
        log(1,"=== Method is started");
        try {
            localizeKeys = new HashMap<>();

            log(2,"get Application start up path");
            path = getApplicationStartUp();

            Reporter.log("> get json content ", true);
            collection = loadJSON(path + "/classes/lokaliseKeys/collection.json");
            jsonString = loadJSON(path + "/classes/lokaliseKeys/" + locale_ + ".json");

            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(jsonString).getAsJsonObject();
            Gson gson = new Gson();

            Reporter.log("> create localizeKeys map ", true);
            List collectionList = gson.fromJson(parser.parse(collection).getAsJsonObject().getAsJsonArray("paramsArray"), List.class);
            for (Object i : collectionList) {
                Map map = gson.fromJson(jo.get(i.toString()).getAsJsonObject(), HashMap.class);
                localizeKeys.putAll(map);
            }

        } catch (UnsupportedEncodingException e) {
            log(4,"UnsupportedEncodingException"+e);
            e.printStackTrace();
        } catch (MalformedURLException e) {
            log(4,"MalformedURLException"+e);
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log(4,"FileNotFoundException"+e);
            e.printStackTrace();
        } catch (IOException e) {
            log(4,"IOException"+e);
            e.printStackTrace();
        }
        log("=== Method is finished");
        return localizeKeys;
    }

    private Path getApplicationStartUp() throws UnsupportedEncodingException, MalformedURLException {
        log("=== Method is started");
        URL startupUrl = getClass().getProtectionDomain().getCodeSource()
                .getLocation();
        Path path = null;
        try {
            path = Paths.get(startupUrl.toURI());
        } catch (Exception e) {

            try {
                log(3, "Exception e");
                path = Paths.get(new URL(startupUrl.getPath()).getPath());

            } catch (Exception ipe) {
                log(3, "Exception ipe");
                path = Paths.get(startupUrl.getPath());
            }
        }
        path = path.getParent();
        log("=== Method is finished");
        return path;
    }


    private String loadJSON(String path) throws IOException {
        log("=== Method is started");
        byte[] buf;
        try (RandomAccessFile f = new RandomAccessFile(path, "r")) {
            buf = new byte[(int) f.length()];
            f.read(buf);
        }
        log("=== Method is finished");
        return new String(buf);
    }


    public void log(int type, String message)
    {
        Throwable t = new Throwable();
        StackTraceElement trace[] = t.getStackTrace();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        Date timeStamp = new Date();
        String typeOfMessage;
        switch (type) {
            case 1:  typeOfMessage = "INFO ";
                break;
            case 2:  typeOfMessage = "DEBUG";
                break;
            case 3: typeOfMessage = "WARN ";
                break;
            case 4:  typeOfMessage = "ERROR";
                break;
            default: typeOfMessage = "UNKNW";
                break;
        }

        // We need element with index 0 - it's current element "log"
        if (trace.length > 1)
        {
            StackTraceElement element = trace[1];
            if (type >= 3){
                System.out.format("\033[31;49m[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n\033[39;49m",sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);

            } else {
                System.out.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n",sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
            }
        }
        else {
            System.out.format("[%s] [%s] where:{ no info }, what:{ %s }\n",sdf.format(timeStamp), typeOfMessage, message);
        }
    }

    public void log(String message)
    {
        Throwable t = new Throwable();
        StackTraceElement trace[] = t.getStackTrace();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        Date timeStamp = new Date();
        String typeOfMessage = "INFO ";

        // We need element with index 0 - it's current element "log"
        if (trace.length > 1)
        {
            StackTraceElement element = trace[1];
            System.out.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n",sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
        }
        else {
            System.out.format("[%s] [%s] where:{ no info }, what:{ %s }\n",sdf.format(timeStamp), typeOfMessage, message);
        }
    }
}


