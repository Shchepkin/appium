package pages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Base {

// TODO validate lines color if parameter is not valid (on registration page for example bg_state_mUseColor	-1754827 = red, and -11711155 = grey)

    public Sql sql;
    public Hub hub;
    public User user;
    public Wait wait;
    public Check check;
    public Email email;
    public PopUp popUp;
    public PinPage pinPage;
    public Imitator imitator;
    public IntroPage introPage;
    public Dashboard dashboard;
    public Navigation nav;
    public MainMenuPage menuPage;
    public AddImagePage addImagePage;
    public AccountMenuPage accountPage;
    public DashboardHeader header;
    public DashboardHeader dashboardHeader;
    public RegistrationPage regPage;
    public AuthorizationPage loginPage;
    public DashboardRoomsPage roomsPage;
    public ForgotPasswordPage forgotPasswordPage;
    public ValidationCodePage validationCodePage;
    public DashboardRemotePage remotePage;
    public DashboardDevicesPage device;

    private AppiumDriver driver = null;
    private Path path;
    private String jsonString, collection;
    private String locale;

    private Map localizeKeys, creds, dbSettings;

    private ArrayList<String> jsonStringArray;
    private String deviceName, UDID, platformVersion, URL, appPath;

    public Base(String deviceName_) {
        log("setup is started");
        creds = getJsonCollection("deviceData.json", deviceName_);

        log("set driver variables");
        deviceName = deviceName_;
        URL = creds.get("URL").toString();
        UDID = creds.get("UDID").toString();
        locale = creds.get("locale").toString();
        appPath = creds.get("appPath").toString();
        platformVersion = creds.get("platformVersion").toString();

        log("get localizeKeys Map with locale \"" + locale + "\"");
        localizeKeys = getLocalizeKeys(locale);
        dbSettings = getDbSettings();
    }

    public Base(AppiumDriver driver) {
        initPageObjects(driver);
    }


    public void initPageObjects(AppiumDriver driver) {
        log(2, "init Wait()");
        wait = new Wait(this);

        log(2, "init Navigation()");
        nav = new Navigation(this);

        log(2, "init Sql()");
        sql = new Sql(this);

        log(2, "init Hub()");
        hub = new Hub(this);

        log(2, "init Check()");
        check = new Check(this);

        log(2, "init PopUp()");
        popUp = new PopUp(this);

        log(2, "init DashboardDevicesPage()");
        device = new DashboardDevicesPage(this);

        log(2, "init RegistrationPage()");
        regPage = new RegistrationPage(this);

        log(2, "init MenuMainPage()");
        menuPage = new MainMenuPage(this);

        log(2, "init IntroPage()");
        introPage = new IntroPage(this);

        log(2, "init Dashboard()");
        dashboard = new Dashboard(this);

        log(2, "init AuthorizationPage()");
        loginPage = new AuthorizationPage(this);

        log(2, "init DashboardRoomsPage()");
        roomsPage = new DashboardRoomsPage(this);

        log(2, "init DashboardRemotePage()");
        remotePage = new DashboardRemotePage(this);

        log(2, "init MenuAccountPage()");
        accountPage = new AccountMenuPage(this);

        log(2, "init AddImagePage()");
        addImagePage = new AddImagePage(this);

        log(2, "init DashboardHeader()");
        dashboardHeader = new DashboardHeader(this);

        log(2, "init ValidationCodePage()");
        validationCodePage = new ValidationCodePage(this);

        log(2, "init DashboardActivePINPage()");
        pinPage = new PinPage(this);

        log(2, "init DashboardHeader()");
        header = new DashboardHeader(this);

        log(2, "init DashboardHeader()");
        user = new User(this);

        log(2, "init Imitator()");
        imitator = new Imitator();

        log(2, "init ForgotPasswordPage()");
        forgotPasswordPage = new ForgotPasswordPage(this);

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getLocalizeTextForKey(String key) {
        return localizeKeys.get(key).toString();
    }

    public String getCredsWithKey(String key){
        return creds.get(key).toString();
    }

    public String getDbSettingsWithKey(String key){
        return dbSettings.get(key).toString();
    }

    private Map getLocalizeKeys(String locale) {
        log("Method is started");
        log(3, "locale: \"" + locale + "\"");
        localizeKeys = new HashMap<>();
        try {
            log(2, "get Application start up path");
            path = getApplicationStartUp();

            log(2, "get json content ");
            collection = loadJSON(path + "/classes/lokaliseKeys/collection.json");
            jsonString = loadJSON(path + "/classes/lokaliseKeys/" + locale + ".json");

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

    private Map getDbSettings() {
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
        Map<String, String> jsonCollection = new HashMap<>();
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

    public ArrayList<String> getJsonStringArray(String filePath, String collection) {
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

    private void printArray(ArrayList arrayList) {
        for (Object element : arrayList) {
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

//**********************************************************************************************************************

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

    public static void log(String message) {
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

    public static void log(int type, String message) {
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

//**********************************************************************************************************************
// Keyboard
//**********************************************************************************************************************

    public void hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            log(2, "Exeption: \n\n" + e + "\n");
        }
    }

    public void openKeyboard() {
        log("Method is started");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            log(2, "Exception: \n\n" + e + "\n");
        }
        log("Method is finished");
    }

    //**********************************************************************************************************************
// Appium Driver
//**********************************************************************************************************************

    public AppiumDriver getDriver() {
        if (driver == null) {
            driver = initDriver();
        }
        return driver;
    }

    private AppiumDriver initDriver() {
        log("Method is started");
        try {
            log(2, "get .apk file");
            File app = new File(appPath);

            //Settings ajaxMobileApp AndroidDriver
            log(2, "set capabilities settings");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("udid", UDID);
            capabilities.setCapability("platformVersion", platformVersion);
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("app", app.getAbsolutePath());
            capabilities.setCapability("appPackage", "com.ajaxsystems");
            capabilities.setCapability("appActivity", "com.ajaxsystems.ui.activity.LauncherActivity");

            log(2, "implement Android driver");
            driver = new AndroidDriver(new URL("http://" + URL), capabilities);

            log(2, "set timeouts");
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        } catch (MalformedURLException e) {
            log(4, "MalformedURLException\n" + e);
        }
        log("Method is finished");
        return driver;
    }

//**********************************************************************************************************************
// ScreenShot
//**********************************************************************************************************************

    public void getScreenShot() {
        try {
            Date currentDate = new Date();

            // Create formatted date and time for folders and filenames
            SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
            SimpleDateFormat time = new SimpleDateFormat("HHmmss");

            // Make screenshot
            File srcFile = this.driver.getScreenshotAs(OutputType.FILE);

            // Creating folder and filename for screenshot
            String folder = "screenshot/" + date.format(currentDate);
            String filename = date.format(currentDate) + "_" + time.format(currentDate) + ".png";
            File targetFile = new File(folder + "/" + filename);

            // Move screenshot to the target folder
            FileUtils.copyFile(srcFile, targetFile);

            log(3, "Path to screenshot: " + targetFile.getAbsolutePath());
        } catch (IOException e1) {
            log(4, "IOException:\n\n" + e1 + "\n");
        }
    }

}
