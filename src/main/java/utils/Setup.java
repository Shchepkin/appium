package utils;

import com.google.gson.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Setup {

    private AppiumDriver driver;
    private Path path;
    private String localizeTextForKey, oneUser;
    private String deviceName_, UDID_, platformVersion_, URL_, appPath_, jsonString, collection, locale_;

    private Map localizeKeys, dbSettings, jsonCollection;
    private ArrayList<String> jsonStringArray;

    public Setup() {
    }

    public Setup(String locale_) {
        this.locale_ = locale_;
    }

    public Setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_) {
        this.URL_ = URL_;
        this.UDID_ = UDID_;
        this.locale_ = locale_;
        this.appPath_ = appPath_;
        this.deviceName_ = deviceName_;
        this.platformVersion_ = platformVersion_;
    }

    public String getLocale_() {
        String locale = this.locale_;
        return locale;
    }

    public AppiumDriver getDriver() {
        log("Method is started");
        try {
            log(2, "get .apk file");
            File app = new File(appPath_);

            // Settings ajaxMobileApp AndroidDriver
            log(2, "set capabilities settings");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");

            // Set appium keyboard and disable it
//            capabilities.setCapability("unicodekeyboard", true);
//            capabilities.setCapability("resetkeyboard", true);

            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
            capabilities.setCapability("deviceName", deviceName_);
            capabilities.setCapability("udid", UDID_);
            capabilities.setCapability("platformVersion", platformVersion_);
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("app", app.getAbsolutePath());
            capabilities.setCapability("appPackage", "com.ajaxsystems");
            capabilities.setCapability("appActivity", "com.ajaxsystems.ui.activity.LauncherActivity");
//        capabilities.setCapability("appActivity", "com.ajaxsystems.activity.DashboardActivity");


            log(2, "implement Android driver");
            driver = new AndroidDriver(new URL("http://" + URL_), capabilities);

            log(2, "set timeouts");
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        } catch (MalformedURLException e) {
            log(4, "MalformedURLException\n" + e);
        }
        log("Method is finished");
        return driver;
    }

    public String getLocalizeTextForKey(String key){
        localizeTextForKey = getLocalizeKeys().get(key).toString();
        return localizeTextForKey;
    }


    private Map getLocalizeKeys() {
        log("Method is started");
        try {
            localizeKeys = new HashMap<>();

            log(2, "get Application start up path");
            path = getApplicationStartUp();

            log(2, "get json content ");
            collection = loadJSON(path + "/classes/lokaliseKeys/collection.json");
            jsonString = loadJSON(path + "/classes/lokaliseKeys/" + locale_ + ".json");

            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(jsonString).getAsJsonObject();
            Gson gson = new Gson();

            log(2, "create localizeKeys map ");
            List collectionList = gson.fromJson(parser.parse(collection).getAsJsonObject().getAsJsonArray("paramsArray"), List.class);
            for (Object i : collectionList) {
                Map map = gson.fromJson(jo.get(i.toString()).getAsJsonObject(), HashMap.class);
                localizeKeys.putAll(map);
            }

        } catch (UnsupportedEncodingException e) {
            log(4, "UnsupportedEncodingException" + e);
            e.printStackTrace();
        } catch (MalformedURLException e) {
            log(4, "MalformedURLException" + e);
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log(4, "FileNotFoundException" + e);
            e.printStackTrace();
        } catch (IOException e) {
            log(4, "IOException" + e);
            e.printStackTrace();
        }
        log("Method is finished");
        return localizeKeys;
    }


    public Map getDbSettings() {
        log("Method is started");
        try {
            dbSettings = new HashMap<>();

            log(2, "get Application start up path");
            path = getApplicationStartUp();

            log(2, "get json content ");
            jsonString = loadJSON(path + "/classes/db.json");

            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(jsonString).getAsJsonObject();
            Gson gson = new Gson();

            log(2, "create DB settings map ");
            Map map = gson.fromJson(jo.get("DB").getAsJsonObject(), HashMap.class);
            dbSettings.putAll(map);

        } catch (Exception e) {
            log(4, "Exception\n" + e + "\n");
        }
        log("Method is finished");
        return dbSettings;
    }

//**********************************************************************************************************************
// JSON
//**********************************************************************************************************************

    public Map getJsonCollection(String filePath, String collection) {
        log("Method is started");
        jsonCollection = new HashMap();
        try {

            log(2, "get Application start up path");
            path = getApplicationStartUp();

            log(2, "get json content ");
            jsonString = loadJSON(path + "/classes/" + filePath);

            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(jsonString).getAsJsonObject();
            Gson gson = new Gson();

            log(2, "create collection map ");
            Map map = gson.fromJson(jo.get(collection).getAsJsonObject(), HashMap.class);
            jsonCollection.putAll(map);

        } catch (Exception e) {
            log(4, "Exception\n" + e + "\n");
        }
        log("Method is finished");
        return jsonCollection;
    }

    public ArrayList <String> getJsonStringArray(String filePath, String collection) {
        log("Method is started");
        jsonStringArray = new ArrayList<>();
        try {

            log(2, "get Application start up path");
            path = getApplicationStartUp();

            log(2, "get json content from file");
            FileReader reader = new FileReader(path + "/classes/" + filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            log(2, "create collection array ");
            jsonStringArray.addAll((JSONArray) jsonObject.get(collection));

        } catch (Exception e) {
            log(4, "Exception\n" + e.getMessage() + "\n");
        }
        log("created " + jsonStringArray.size() + " elements in arrayList");
        printArray(jsonStringArray);
        log("Method is finished");
        return jsonStringArray;
    }

    private void printArray(ArrayList arrayList){
        for (Object element: arrayList) {
            System.out.println(element.toString());
        }
    }

//**********************************************************************************************************************

    private Path getApplicationStartUp() throws UnsupportedEncodingException, MalformedURLException {
        log("Method is started");
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
        log("Method is finished");
        return path;
    }


    private String loadJSON(String path) throws IOException {
        log("Method is started");
        byte[] buf;
        try (RandomAccessFile f = new RandomAccessFile(path, "r")) {
            buf = new byte[(int) f.length()];
            f.read(buf);
        }
        log("Method is finished");
        return new String(buf);
    }

//**********************************************************************************************************************
// LOG
//**********************************************************************************************************************
    public void log(int type, String message) {
        Throwable t = new Throwable();
        StackTraceElement trace[] = t.getStackTrace();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        Date timeStamp = new Date();
        String typeOfMessage;
        switch (type) {
            case 1:
                typeOfMessage = "INFO ";
                break;
            case 2:
                typeOfMessage = "DEBUG";
                break;
            case 3:
                typeOfMessage = "WARN ";
                break;
            case 4:
                typeOfMessage = "ERROR";
                break;
            default:
                typeOfMessage = "NODEF";
                break;
        }

        // We need element with index 0 - it's current element "log"
        if (trace.length > 1) {
            StackTraceElement element = trace[1];
            if (type >= 3) {
                System.out.format("\033[31;49m[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n\033[39;49m", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);

            } else {
                System.out.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
            }
        } else {
            System.out.format("[%s] [%s] where:{ no info }, what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, message);
        }
    }

    public void log(String message) {
        Throwable t = new Throwable();
        StackTraceElement trace[] = t.getStackTrace();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        Date timeStamp = new Date();
        String typeOfMessage = "INFO ";

        // We need element with index 0 - it's current element "log"
        if (trace.length > 1) {
            StackTraceElement element = trace[1];
            System.out.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
        } else {
            System.out.format("[%s] [%s] where:{ no info }, what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, message);
        }
    }
}


