package pageObjects;

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
import pageObjects.object.Device;
import pageObjects.pages.dashboard.*;
import pageObjects.pages.intro.*;
import pageObjects.object.Hub;
import pageObjects.object.User;
import pageObjects.pages.AccountMenuPage;
import pageObjects.pages.AddImagePage;
import pageObjects.pages.MainMenuPage;
import pageObjects.pages.PinPage;
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

    public static final int DEFAULT_TIMEOUT = 5;
    private static final int LOG_LEVEL = 5;
    private static final boolean LOG_VIEW_IN_CONSOLE = false;
    private static final String logFile = logfileName();
    private static final String dataBase = "Develop";

    public Sql sql;
    public Hub hub;
    public User user;
    public Wait wait;
    public Check check;
    public Email email;
    public PopUp popUp;
    public Header header;
    public Device device;
    public PinPage pinPage;
    public IntroPage introPage;
    public RoomsPage roomsPage;
    public Dashboard dashboard;
    public RemotePage remotePage;
    public Navigation nav;
    public DevicesPage devicesPage;
    public MainMenuPage menuPage;
    public AddImagePage addImagePage;
    public AccountMenuPage accountPage;
    public RegistrationPage regPage;
    public AuthorizationPage loginPage;
    public NotificationsPage notificationsPage;
    public ForgotPasswordPage forgotPasswordPage;
    public ValidationCodePage validationPage;

    private AndroidDriver driver = null;
    private Path path;
    private String jsonString, collection;
    private String locale;

    private Map localizeKeys, creds, dbSettings, appSet;
    private ArrayList<String> jsonStringArray;
    private String deviceName, UDID, platformVersion, URL, appPath, appPackage, appActivity;

    public Base(String deviceName_) {
        log(4, "setup is started");
        creds = getJsonMapCollection("deviceData.json", deviceName_);
        appSet = getJsonMapCollection("deviceData.json", "appSet");

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

//TODO remove resources JSON to out of project folder

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

        log(4, "init Device()");
        device = new Device(this);

        log(4, "init Header()");
        header = new Header(this);

        log(4, "init RegistrationPage()");
        regPage = new RegistrationPage(this);

        log(4, "init PINPage()");
        pinPage = new PinPage(this);

        log(4, "init MenuMainPage()");
        menuPage = new MainMenuPage(this);

        log(4, "init Dashboard()");
        dashboard = new Dashboard(this);

        log(4, "init IntroPage()");
        introPage = new IntroPage(this);

        log(4, "init AuthorizationPage()");
        loginPage = new AuthorizationPage(this);

        log(4, "init RoomsPage()");
        roomsPage = new RoomsPage(this);

        log(4, "init RemotePage()");
        remotePage = new RemotePage(this);

        log(4, "init DevicesPage()");
        devicesPage = new DevicesPage(this);

        log(4, "init MenuAccountPage()");
        accountPage = new AccountMenuPage(this);

        log(4, "init AddImagePage()");
        addImagePage = new AddImagePage(this);

        log(4, "init ValidationCodePage()");
        validationPage = new ValidationCodePage(this);

        log(4, "init NotificationsPage()");
        notificationsPage = new NotificationsPage(this);

        log(4, "init ForgotPasswordPage()");
        forgotPasswordPage = new ForgotPasswordPage(this);

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private String getAppSetWithKey(String key){
        return appSet.get(key).toString();
    }

    public static String getLogFile() {
        return logFile;
    }

    public String getDeviceName() {
        return deviceName;
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
            Map map = gson.fromJson(jo.get(dataBase).getAsJsonObject(), HashMap.class);
            dbSettings.putAll(map);

        } catch (Exception e) {
            log(2, "Exception\n" + e + "\n");
        }
        log(4, "Method is finished");
        return dbSettings;
    }

// JSON
//----------------------------------------------------------------------------------------------------------------------
    public Map getJsonMapCollection(String filePath, String collection) {
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

    public String getStringValue (Map map, String key){
        return map.get(key).toString();
    }

    private void printArray(ArrayList arrayList) {
        for (Object element : arrayList) {
            log(4, element.toString());
        }
    }

    private Path getApplicationStartUp() throws UnsupportedEncodingException, MalformedURLException {
        URL startupUrl = getClass().getProtectionDomain().getCodeSource()
                .getLocation();
        Path path = null;
        try {
            path = Paths.get(startupUrl.toURI());
        } catch (Exception e) {
            log(3, "Exception: \n" + e.getMessage());
            try {
                path = Paths.get(new URL(startupUrl.getPath()).getPath());
            } catch (Exception ipe) {
                log(3, "Exception ipe: \n" + ipe.getMessage());
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

// LOG
//----------------------------------------------------------------------------------------------------------------------
    public static void log(int type, String message, boolean writeToReport) {
        if(writeToReport){
            System.out.println(log(type, message));
        }else {
            log(type, message);
        }
    }

    public static String log(int type, String message) {
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
                    if (LOG_VIEW_IN_CONSOLE){
                        System.out.format("\033[31;49m[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n\033[39;49m", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
                    }
                    write(String.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message));
                } else {
                    if (LOG_VIEW_IN_CONSOLE) {
                        System.out.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
                    }
                    write(String.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message));
                }
            } else {
                if (LOG_VIEW_IN_CONSOLE) {
                    System.out.format("[%s] [%s] where:{ no info }, what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, message);
                }
                write(String.format("[%s] [%s] where:{ no info }, what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, message));
            }
        }
        return message;
    }

    private static String logfileName(){
        String fullPathToFile, filename;
        try {
            Date currentDate = new Date();

            // Create formatted date and time for folders and filenames
            SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
            SimpleDateFormat time = new SimpleDateFormat("HHmmss");

            // Creating folder and filename for logfile
            File folder = new File("logs" + File.separator + date.format(currentDate));
            if (!folder.exists()) {
                folder.mkdirs();
            }

            filename = date.format(currentDate) + "_" + time.format(currentDate) + ".log";
            fullPathToFile = "logs" + File.separator + date.format(currentDate) + File.separator + filename;

            File file = new File(fullPathToFile);
            if (!file.exists()){
                file.createNewFile();
                System.out.println("log: \"" + fullPathToFile + "\"");
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

// Keyboard
//----------------------------------------------------------------------------------------------------------------------
    public void hideKeyboard() {
        log(1, "hide Keyboard");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            log(4, "Exeption: \n\n" + e.getMessage() + "\n");
        }
    }

// AndroidDriver
//----------------------------------------------------------------------------------------------------------------------
    public AndroidDriver getDriver() {
        if (driver == null) {
            driver = initDriver();
        }
        return driver;
    }

    private AndroidDriver initDriver() {
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
            driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        } catch (MalformedURLException e) {
            log(2, "MalformedURLException\n" + e);
        }
        return driver;
    }

// ScreenShot
//----------------------------------------------------------------------------------------------------------------------
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

            log(3, "Screenshot file: " + targetFile);
        } catch (IOException e1) {
            log(2, "IOException:\n\n" + e1 + "\n");
        }
    }

// DataProvider
//----------------------------------------------------------------------------------------------------------------------
    public Object[][] getDataProviderObjects(String pathToFileWithData) {
        ArrayList<Map> listOfMaps = listOfMapsForDataProvider(pathToFileWithData);

        log(4, "put maps from listOfMaps to the array");
        Object[][] objects = new Object[listOfMaps.size()][1];
        for (int i = 0; i < listOfMaps.size(); i++) {
            objects[i][0] = listOfMaps.get(i);
        }

        return objects;
    }

    public Iterator<Object[]> getDataProviderIterator(String pathToFileWithData) {
        ArrayList<Map> listOfMaps = listOfMapsForDataProvider(pathToFileWithData);

        log(4, "put maps from listOfMaps to the Collection of objects");
        Collection<Object[]> objects = new ArrayList<>();
        for (int i = 0; i < listOfMaps.size(); i++) {
            objects.add(new Object[] {listOfMaps.get(i)});
        }

        return objects.iterator();
    }

    private ArrayList<Map> listOfMapsForDataProvider(String pathToFileWithData){
        ArrayList<Map> listOfMaps = new ArrayList<>();
        Map map;
        int i = 0;
        while (true){
            log(4, "get collection from json");
            map = getJsonMapCollection(pathToFileWithData, String.valueOf(i));
            if(map.isEmpty()){
                log(4, "can not found data, perhaps it's end of data file");
                break;
            }
            log(4, "add this Map to the listOfMapsForDataProvider");
            listOfMaps.add(map);
            i++;
        }
        return listOfMaps;
    }

}
