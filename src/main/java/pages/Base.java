package pages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Parameters;
import utils.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class Base{

// TODO validate lines color if parameter is not valid (on registration page for example bg_state_mUseColor	-1754827 = red, and -11711155 = grey)


    public AppiumDriver driver;
    public Sql sql;
    public Hub hub;
    public User user;
    public Wait wait;
    public Check check;
    public Email email;
    public PopUp popUp;
    public DashboardHeader header;
    public IntroPage introPage;
    public Dashboard dashboard;
    public DashboardDevicesPage device;
    public DashboardHeader dashboardHeader;
    public DashboardRoomsPage roomsPage;
    public DashboardRemotePage remotePage;
    public Navigation nav;
    public MenuMainPage menuPage;
    public AddImagePage addImagePage;
    public MenuAccountPage accountPage;
    public RegistrationPage regPage;
    public AuthorizationPage loginPage;
    public ValidationCodePage validationCodePage;
    public DashboardActivePINPage pinPage;

    private Path path;
    private String localizeTextForKey;
    private String jsonString, collection;
    private String locale;

    private Map localizeKeys, dbSettings, jsonCollection;
    private ArrayList<String> jsonStringArray;

    @Parameters({ "locale_" })
    public Base(AppiumDriver driver, String locale_) {
        this.driver = driver;

        this.locale = locale_;
        log(3, "locale: \"" + locale + "\"");

        log(2, "init Wait(driver)");
        wait = new Wait(driver);

        log(2, "init Navigation(driver)");
        nav = new Navigation(driver);

        log(2, "init Check(driver)");
        check = new Check(driver);

        log(2, "init Hub(driver)");
        hub = new Hub(driver);

        log(2, "init PopUp(driver)");
        popUp = new PopUp(driver);

        log(2, "init DashboardDevicesPage(driver)");
        device = new DashboardDevicesPage(driver);

        log(2, "init RegistrationPage(driver)");
        regPage = new RegistrationPage(driver);

        log(2, "init MenuMainPage(driver)");
        menuPage = new MenuMainPage(driver);

        log(2, "init IntroPage(driver)");
        introPage = new IntroPage(driver);

        log(2, "init Dashboard(driver)");
        dashboard = new Dashboard(driver);

        log(2, "init AuthorizationPage(driver)");
        loginPage = new AuthorizationPage(driver);

        log(2, "init DashboardRoomsPage(driver)");
        roomsPage = new DashboardRoomsPage(driver);

        log(2, "init DashboardRemotePage(driver)");
        remotePage = new DashboardRemotePage(driver);

        log(2, "init MenuAccountPage(driver)");
        accountPage = new MenuAccountPage(driver);

        log(2, "init AddImagePage(driver)");
        addImagePage = new AddImagePage(driver);

        log(2, "init DashboardHeader(driver)");
        dashboardHeader = new DashboardHeader(driver);

        log(2, "init ValidationCodePage(driver)");
        validationCodePage = new ValidationCodePage(driver);

        log(2, "init DashboardActivePINPage(driver)");
        pinPage = new DashboardActivePINPage(driver);

        log(2, "init Sql()");
        sql = new Sql();


        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
//        page1 = PageFactory.initElements(driver, Page1.class);

    }

    public Base() {
    }




    public String getLocalizeTextForKey(String key){
        localizeTextForKey = getLocalizeKeys().get(key).toString();
        return localizeTextForKey;
    }

    @Parameters({ "locale_" })
    private String getLocale(String locale_){
        log(3, "locale: \"" + locale + "\"");
        return locale;
    }

    private Map getLocalizeKeys() {
        log("Method is started");
        log(3, "locale: \"" + locale + "\"");
        try {
            localizeKeys = new HashMap<>();

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

    public void hideKeyboard(){
        try{
            driver.hideKeyboard();
        }catch (Exception e){
            log("Exeption: \n" + e + "\n");
        }
    }

    public void openKeyboard() {
        log("Method is started");
        try{
            driver.hideKeyboard();
        }catch (Exception e){
            log(3, "Exception: \n" + e + "\n");
        }
        log("Method is finished");
    }

}
