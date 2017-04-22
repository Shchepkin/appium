package pages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    public static int TIMEOUT = 5;
    private static final int LOG_LEVEL = 5;
    private static final boolean LOG_VIEW = false;

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
    public DashboardDevicesPage devicesPage;
    public DashboardNotificationsPage notificationsPage;

    private AndroidDriver driver = null;
    private Path path;
    private String jsonString, collection;
    private String locale;

    private Map localizeKeys, creds, dbSettings, appSet;

    private ArrayList<String> jsonStringArray;
    private String deviceName, UDID, platformVersion, URL, appPath, appPackage, appActivity;
    public static final String logFile = logfileName();

    public Base(String deviceName_) {
        log(1, "setup is started");
        creds = getJsonCollection("deviceData.json", deviceName_);
        appSet = getJsonCollection("deviceData.json", "appSet");

        log(4, "set creds for driver");
        deviceName = deviceName_;
        URL = getCredsWithKey("URL");
        UDID = getCredsWithKey("UDID");
        locale = getCredsWithKey("locale");
        platformVersion = getCredsWithKey("platformVersion");

        log(4, "set creds for app");
        appPath = getAppSetWithKey("appPath");
        appPackage = getAppSetWithKey("appPackage");
        appActivity = getAppSetWithKey("appActivity");

        log(4, "get localizeKeys Map with locale \"" + locale + "\"");
        localizeKeys = getLocalizeKeys(locale);

        log(4, "get creds for DataBase");
        dbSettings = getDbSettings();

    }

    public Base(AndroidDriver driver) {
        initPageObjects(driver);
    }

    public void initPageObjects(AndroidDriver driver) {

        log(4, "init Navigation()");
        nav = new Navigation(this);

        log(4, "init Sql()");
        sql = new Sql(this);

        log(4, "init Hub()");
        hub = new Hub(this);

        log(4, "init User()");
        user = new User(this);

        log(4, "init Wait()");
        wait = new Wait(this);

        log(4, "init Check()");
        check = new Check(this);

        log(4, "init PopUp()");
        popUp = new PopUp(this);

        log(4, "init Imitator()");
        imitator = new Imitator();

        log(4, "init DashboardHeader()");
        header = new DashboardHeader(this);

        log(4, "init RegistrationPage()");
        regPage = new RegistrationPage(this);

        log(4, "init DashboardActivePINPage()");
        pinPage = new PinPage(this);

        log(4, "init MenuMainPage()");
        menuPage = new MainMenuPage(this);

        log(4, "init Dashboard()");
        dashboard = new Dashboard(this);

        log(4, "init IntroPage()");
        introPage = new IntroPage(this);

        log(4, "init AuthorizationPage()");
        loginPage = new AuthorizationPage(this);

        log(4, "init DashboardRoomsPage()");
        roomsPage = new DashboardRoomsPage(this);

        log(4, "init DashboardRemotePage()");
        remotePage = new DashboardRemotePage(this);

        log(4, "init DashboardDevicesPage()");
        devicesPage = new DashboardDevicesPage(this);

        log(4, "init MenuAccountPage()");
        accountPage = new AccountMenuPage(this);

        log(4, "init AddImagePage()");
        addImagePage = new AddImagePage(this);

        log(4, "init DashboardHeader()");
        dashboardHeader = new DashboardHeader(this);

        log(4, "init ValidationCodePage()");
        validationCodePage = new ValidationCodePage(this);

        log(4, "init NotificationsPage()");
        notificationsPage = new DashboardNotificationsPage(this);

        log(4, "init ForgotPasswordPage()");
        forgotPasswordPage = new ForgotPasswordPage(this);

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getLocalizeTextForKey(String key) {
        return localizeKeys.get(key).toString();
    }

    public String getCredsWithKey(String key){
        return creds.get(key).toString();
    }

    public String getAppSetWithKey(String key){
        return appSet.get(key).toString();
    }

    public String getDbSettingsWithKey(String key){
        return dbSettings.get(key).toString();
    }

    private Map getLocalizeKeys(String locale) {
        log(4, "Method is started");
        log(3, "locale: \"" + locale + "\"");
        localizeKeys = new HashMap<>();
        try {
            log(4, "get Application start up path");
            path = getApplicationStartUp();

            log(4, "get json content ");
            collection = loadJSON(path + "/classes/lokaliseKeys/collection.json");
            jsonString = loadJSON(path + "/classes/lokaliseKeys/" + locale + ".json");

            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(jsonString).getAsJsonObject();
            Gson gson = new Gson();

            log(4, "create localizeKeys map ");
            List collectionList = gson.fromJson(parser.parse(collection).getAsJsonObject().getAsJsonArray("paramsArray"), List.class);
            for (Object i : collectionList) {
                Map map = gson.fromJson(jo.get(i.toString()).getAsJsonObject(), HashMap.class);
                localizeKeys.putAll(map);
            }

        } catch (UnsupportedEncodingException e) {
            log(2, "UnsupportedEncodingException" + e);
            e.printStackTrace();
        } catch (MalformedURLException e) {
            log(2, "MalformedURLException" + e);
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log(2, "FileNotFoundException" + e);
            e.printStackTrace();
        } catch (IOException e) {
            log(2, "IOException" + e);
            e.printStackTrace();
        }
        log(4, "Method is finished");
        return localizeKeys;
    }

    private Map getDbSettings() {
        log(4, "Method is started");
        try {
            dbSettings = new HashMap<>();

            log(4, "get Application start up path");
            path = getApplicationStartUp();

            log(4, "get json content ");
            jsonString = loadJSON(path + "/classes/db.json");

            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(jsonString).getAsJsonObject();
            Gson gson = new Gson();

            log(4, "create DB settings map ");
            Map map = gson.fromJson(jo.get("DB").getAsJsonObject(), HashMap.class);
            dbSettings.putAll(map);

        } catch (Exception e) {
            log(2, "Exception\n" + e + "\n");
        }
        log(4, "Method is finished");
        return dbSettings;
    }

//----------------------------------------------------------------------------------------------------------------------
// JSON
//----------------------------------------------------------------------------------------------------------------------

    public Map getJsonCollection(String filePath, String collection) {
        log(4, "Method is started");
        Map<String, String> jsonCollection = new HashMap<>();
        try {

            log(4, "get Application start up path");
            path = getApplicationStartUp();

            log(4, "get json content ");
            jsonString = loadJSON(path + "/classes/" + filePath);

            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(jsonString).getAsJsonObject();
            Gson gson = new Gson();

            log(4, "create collection map ");
            Map map = gson.fromJson(jo.get(collection).getAsJsonObject(), HashMap.class);
            jsonCollection.putAll(map);

        } catch (Exception e) {
            log(2, "Exception: " + e);
        }
        log(4, "Method is finished");
        return jsonCollection;
    }

    public ArrayList<String> getJsonStringArray(String filePath, String collection) {
        log(4, "Method is started");
        jsonStringArray = new ArrayList<>();
        try {

            log(4, "get Application start up path");
            path = getApplicationStartUp();

            log(4, "get json content from file");
            FileReader reader = new FileReader(path + "/classes/" + filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            log(4, "create collection array ");
            jsonStringArray.addAll((JSONArray) jsonObject.get(collection));

        } catch (Exception e) {
            log(2, "Exception\n" + e.getMessage() + "\n");
        }
        log(4, "created " + jsonStringArray.size() + " elements in arrayList");
        printArray(jsonStringArray);
        log(4, "Method is finished");
        return jsonStringArray;
    }

    private void printArray(ArrayList arrayList) {
        for (Object element : arrayList) {
            System.out.println(element.toString());
        }
    }

//----------------------------------------------------------------------------------------------------------------------

    private Path getApplicationStartUp() throws UnsupportedEncodingException, MalformedURLException {
        log(4, "Method is started");
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
        log(4, "Method is finished");
        return path;
    }

//----------------------------------------------------------------------------------------------------------------------

    private String loadJSON(String path) throws IOException {
        log(4, "Method is started");
        byte[] buf;
        try (RandomAccessFile f = new RandomAccessFile(path, "r")) {
            buf = new byte[(int) f.length()];
            f.read(buf);
        }
        log(4, "Method is finished");
        return new String(buf);
    }

//----------------------------------------------------------------------------------------------------------------------
// LOG
//----------------------------------------------------------------------------------------------------------------------

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
                typeOfMessage = "ERROR";
                break;
            case 3:
                typeOfMessage = "WARN ";
                break;
            case 4:
                typeOfMessage = "DEBUG";
                break;
            case 5:
                typeOfMessage = "NODEF";
                break;
            default:
                typeOfMessage = "NODEF";
                break;
        }

        if (type <= LOG_LEVEL) {
            // We need element with index 0 - it's current element "log"
            if (trace.length > 1) {
                StackTraceElement element = trace[1];
                if (type == 2 || type == 3) {
                    if (LOG_VIEW){
                        System.out.format("\033[31;49m[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n\033[39;49m", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
                    }
                    String logFormattedString = String.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
                    write(logFormattedString);
                } else {
                    if (LOG_VIEW) {
                        System.out.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
                    }
                    String logFormattedString = String.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
                    write(logFormattedString);
                }
            } else {
                if (LOG_VIEW) {
                    System.out.format("[%s] [%s] where:{ no info }, what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, message);
                }
                String logFormattedString = String.format("[%s] [%s] where:{ no info }, what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, message);
                write(logFormattedString);
            }
        }

    }



    private static final String logfileName(){
        String fullPathToFile, filename;

        try {
            Date currentDate = new Date();

            // Create formatted date and time for folders and filenames
            SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
            SimpleDateFormat time = new SimpleDateFormat("HHmmss");

            // Creating folder and filename for logfile
            filename = date.format(currentDate) + "_" + time.format(currentDate) + ".log";
            fullPathToFile = "logs/" + filename;

            File file = new File(fullPathToFile);
            if(file.createNewFile()){
                System.out.println("log: \"" + fullPathToFile + "\"");
            }else {
                System.out.println("log File already exist: \"" + fullPathToFile + "\"");
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return fullPathToFile;
    }


    private static void write(String text) {
        try(FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(text);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// Keyboard
//----------------------------------------------------------------------------------------------------------------------

    public void hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            log(4, "Exeption: \n\n" + e + "\n");
        }
    }

    public void openKeyboard() {
        log(1, "Method is started");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            log(4, "Exception: \n\n" + e + "\n");
        }
        log(1, "Method is finished");
    }

//----------------------------------------------------------------------------------------------------------------------
// AndroidDriver
//----------------------------------------------------------------------------------------------------------------------

    public AndroidDriver getDriver() {
        if (driver == null) {
            driver = initDriver();
        }
        return driver;
    }

    private AndroidDriver initDriver() {
        log(4, "Method is started");
        try {
            log(4, "get .apk file");
            File app = new File(appPath);

            log(4, "set capabilities settings");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("udid", UDID);
            capabilities.setCapability("platformVersion", platformVersion);
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("app", app.getAbsolutePath());
            capabilities.setCapability("appPackage", appPackage);
            capabilities.setCapability("appActivity", appActivity);

            log(4, "implement Android driver");
            driver = new AndroidDriver(new URL("http://" + URL), capabilities);

            log(4, "App Context: " + driver.getContextHandles().toString());

            log(4, "set implicitlyWait");
            driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);

        } catch (MalformedURLException e) {
            log(2, "MalformedURLException\n" + e);
        }
        log(4, "Method is finished");
        return driver;
    }

//----------------------------------------------------------------------------------------------------------------------
// ScreenShot
//----------------------------------------------------------------------------------------------------------------------

    public void getScreenShot() {
        log(4, "Method is started");
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
            log(2, "IOException:\n\n" + e1 + "\n");
        }
        log(4, "Method is finished");
    }

//----------------------------------------------------------------------------------------------------------------------
// DataProvider
//----------------------------------------------------------------------------------------------------------------------

    public Object[][] getDataProviderObjects(String pathToFileWithData) {
        log(4, "Method is started");
        ArrayList<Map> listOfMaps = listOfMapsForDataProvider(pathToFileWithData);

        log(4, "put maps from listOfMaps to the array");
        Object[][] objects = new Object[listOfMaps.size()][1];
        for (int i = 0; i < listOfMaps.size(); i++) {
            objects[i][0] = listOfMaps.get(i);
        }

        log(4, "Method is finished");
        return objects;
    }

    public Iterator<Object[]> getDataProviderIterator(String pathToFileWithData) {
        log(4, "Method is started");
        ArrayList<Map> listOfMaps = listOfMapsForDataProvider(pathToFileWithData);

        log(4, "put maps from listOfMaps to the Collection of objects");
        Collection<Object[]> objects = new ArrayList<>();
        for (int i = 0; i < listOfMaps.size(); i++) {
            objects.add(new Object[] {listOfMaps.get(i)});
        }

        log(4, "Method is finished");
        return objects.iterator();
    }

    private ArrayList<Map> listOfMapsForDataProvider(String pathToFileWithData){
        log(4, "Method is started");
        ArrayList<Map> listOfMaps = new ArrayList<>();
        Map map;
        int i = 0;
        while (true){
            log(4, "get collection from json");
            map = getJsonCollection(pathToFileWithData, String.valueOf(i));
            if(map.isEmpty()){
                log(4, "Method is started");
                break;
            }
            log(4, "add this Map to the listOfMapsForDataProvider");
            listOfMaps.add(map);
            i++;
        }
        return listOfMaps;
    }

}
